package xml6;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Servidor {
    public static void main(String [] args){
        try(ExecutorService pool= Executors.newCachedThreadPool();
            ServerSocket ss=new ServerSocket(8080)){
            while(true){
                Socket s=ss.accept();

            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
