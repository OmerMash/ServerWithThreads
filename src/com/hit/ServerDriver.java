package com.hit;

public class ServerDriver {
    public static void main(String[] args) {
        Server server = new Server(3333);
        new Thread(server).start(); }
}
