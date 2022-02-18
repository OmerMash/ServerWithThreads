package com.hit.dao;

import com.hit.controller.MyThread;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static MyDaoFileImpl dao;

    public Main() {
    }

    public static void main(String[] args) throws IOException {
        try {
            ServerSocket ss = new ServerSocket(3333);

            for(int i = 0; i < 3; ++i) {
                Socket s = ss.accept();
                MyThread t = new MyThread("Thread number " + (i + 1), s);
                Thread th = new Thread(t);
                th.start();
            }

            ss.close();
        } catch (IOException var6) {
            var6.printStackTrace();
        }

    }
}
