import java.net.ServerSocket;
import java.net.Socket;

class ServerWithThreads{

    public static void main(String args[])throws Exception{
        ServerSocket serverSocket = new ServerSocket(3333);
        MyModel model = new MyModel();
        for(int i=0; i<3; i++){
            Socket socket = serverSocket.accept();
            MyThread t = new MyThread("Thread number " + (i+1), socket, model);
            Thread th = new Thread(t);
            th.start();
        }
        serverSocket.close();
    }

}
