package com.company;

import javafx.util.Pair;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;

public class RemoteFunction<A extends Serializable,B extends Serializable> {
    private int port;
    private InetAddress address;

    public RemoteFunction(int port,InetAddress address){
        this.port = port;
        this.address = address;

    }

    public B ask(A input){
        return ask("",input);
    }

    /* Blocking */
    public B ask(String funcName, A input){

        try {

            Socket socket = new Socket(address,port);


            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream oin = new ObjectInputStream(socket.getInputStream());

            Pair<String,A> info = new Pair<>(funcName,input);

            oos.writeObject(info);
            B result = (B)oin.readObject();

            return result;


        }catch (FileNotFoundException e){

        }catch (IOException e){
            System.out.println("Boo");
        }catch (ClassNotFoundException e){

        }

        return null;
    }

    public void reply(Function<A,B> func){
        reply(func,null);
    }

    public void reply(Object obj){
        reply(null,obj);
    }



    public void reply(Function<A,B> func,Object obj){
        try{
            ServerSocket server = new ServerSocket(port);
            while (true){
                Socket client = server.accept();



                Action<Void> act = new Action<>(new Function<Action.Unit, Void>() {
                    @Override
                    public Void func(Action.Unit input) {
                        try{
                            ObjectInputStream oin = new ObjectInputStream(client.getInputStream());
                            ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());

                            oos.writeObject(obj.getClass());


                            Pair<String,A> info = (Pair<String, A>) oin.readObject();


                            String funcName = info.getKey();
                            System.out.println(funcName);
                            A arg = info.getValue();
                            System.out.println(arg);

                            B rep = null;
                            if (func == null){
                                try{
                                    System.out.println("nullplace");
                                    Class cls = obj.getClass();
                                    Class temp = new Integer(1).getClass();
                                    rep = (B) (cls.getDeclaredMethod(funcName,temp).invoke(obj,arg));


                                }catch (NoSuchMethodException e){
                                    System.out.println("acess");
                                }catch (IllegalAccessException e){

                                }catch (InvocationTargetException e){

                                }

                            }else{
                                rep = func.func(arg);
                            }

                            oos.writeObject(rep);
                            client.close();

                        }catch (IOException e){

                        }catch (ClassNotFoundException e){

                        }

                        return null;
                    }
                });





                act.get_threaded_action()._eval();


            }
        }catch(IOException e){
            System.out.println(e);
        }

    }

}
