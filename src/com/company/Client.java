package com.company;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Client {

    public static void main(String[] args){

        try {
            RemoteFunction<String,String> f = new RemoteFunction<>(8000,InetAddress.getLocalHost());
            String k = f.ask("hey");



            System.out.println(k);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }


    public static<A extends Serializable,B extends Serializable> B test(A val){

    }



}
