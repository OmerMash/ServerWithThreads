package com.hit;
import com.hit.dao.IDAO;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable{

    int socket;
    public Server(int socket) {
        this.socket = socket;
    }

    public void run() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(3333);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        MyModel model = new MyModel();
        IDAO model = null;
        try {
            model = new MyDaoFileImpl("person_ser");
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(int i=0; i<3; i++){
            Socket socket = null;
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            HandleRequest t = new HandleRequest("Thread number " + (i+1), socket, model);
            Thread th = new Thread(t);
            th.start();
        }
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
