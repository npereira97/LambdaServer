package com.company;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

public class Server {
    public static void main(String[] args){
        try{
           RemoteFunction<Integer,Integer> f = new RemoteFunction<>(8001,InetAddress.getLocalHost());
           f.reply(new Server());





        }catch (IOException e){

        }

    }

    public Integer square(Integer n){
        return n*n;
    }
}
