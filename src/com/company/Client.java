package com.company;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Client {

    public static void main(String[] args){
        try {
            RemoteFunction<Integer,Integer> f = new RemoteFunction<>(8001, InetAddress.getLocalHost());
            System.out.println("Client");
            Integer n = f.ask("square",5);
            System.out.println(n);
/*
            for(int i = 0; i <100000; i++){
                System.out.println(f.ask("lala",i));
            }*/


        }catch (UnknownHostException e){

        }
    }



}
