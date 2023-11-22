package net41;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CountDownLatch;

public class Descargador implements Runnable{
    URL url;
    String directorio;
    long inicial;
    long fin;
    CountDownLatch countDownLatch;

    public Descargador(URL url, String directorio, long inicial, long fin, CountDownLatch countDownLatch) {
        this.url = url;
        this.directorio = directorio;
        this.inicial = inicial;
        this.fin = fin;
        this.countDownLatch = countDownLatch;
    }


    @Override
    public void run() {
        try{
            HttpURLConnection con= (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Range","bytes="+inicial+"-"+fin);
            System.out.println(con.getRequestProperty("Range"));
            System.out.println(con.getResponseCode());
            try(DataInputStream dis=new DataInputStream(con.getInputStream());
                RandomAccessFile raf=new RandomAccessFile(directorio,"rw")){
                System.out.println("length "+ con.getContentLength());
                raf.seek(inicial);
                byte [] buff=new byte[1024*1024];
                int leidos=dis.read(buff);
                while (leidos!=-1){
                    raf.write(buff,0,leidos);
                    leidos=dis.read(buff);
                }
                countDownLatch.countDown();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
