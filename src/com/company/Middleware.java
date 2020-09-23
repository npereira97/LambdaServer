package com.company;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Middleware {

    static Action<Void> server = new Action<>(new Function<Action.Unit, Void>() {
        @Override
        public Void func(Action.Unit input) {
            try {
                ServerSocket socket = new ServerSocket(8000);
                while (true) {
                    Socket client = socket.accept();


                    Action<Void> clientHandler = new Action<>(new Function<Action.Unit, Void>() {
                        @Override
                        public Void func(Action.Unit input) {


                            try {
                                ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
                                ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());


                                String s = (String) ois.readObject();
                                System.out.println(s);
                                client.close();
                            } catch (IOException e) {

                            } catch (ClassNotFoundException e) {

                            }
                            return null;
                        }
                    });

                    clientHandler.get_threaded_action()._eval();

                }
            } catch (IOException e) {

            }
            return null;
        }

    });



    public static void main(String[] args){
        server.get_threaded_action()._eval();
    }









}
