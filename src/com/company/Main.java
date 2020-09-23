package com.company;



import java.io.Serializable;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Action<Integer> test = new Action<>(new Function<Action.Unit, Integer>() {
            @Override
            public Integer func(Action.Unit input) {

                for(int i = 0; i < 10; i++){
                        System.out.println(i);
                }



                return 1;
            }
        });


        Action<String> test1 = new Action<>(new Function<Action.Unit, String>() {
            @Override
            public String func(Action.Unit input) {
                return null;
            }
        });

/*
        Object obj = new Main();
        for (Method m: obj.getClass().getMethods()){
            System.out.println(m);
        }

*/





        System.out.println(test.get_threaded_action().eval());



    }

    public Integer square(Integer n){
        return n*n;
    }
}
