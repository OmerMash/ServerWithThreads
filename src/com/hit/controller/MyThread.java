package com.hit.controller;

import com.google.gson.Gson;
import com.hit.algorithm.Person;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MyThread implements Runnable {
    String myName;
    Socket mySocket;
    DataInputStream din;
    DataOutputStream dout;

    public MyThread(String name, Socket s) {
        this.myName = name;
        this.mySocket = s;

        try {
            this.din = new DataInputStream(s.getInputStream());
            this.dout = new DataOutputStream(s.getOutputStream());
        } catch (IOException var4) {
        }

    }

    public void run() {
        String str = "";

        while(!str.equals("stop")) {
            try {
                str = this.din.readUTF();
                System.out.println("client says: " + str);
                Person person = (Person)(new Gson()).fromJson(str, Person.class);
                System.out.println(person.toString());
                this.dout.flush();
            } catch (IOException var4) {
            }
        }

        try {
            this.din.close();
            this.dout.close();
            this.mySocket.close();
        } catch (IOException var3) {
        }

    }
}
