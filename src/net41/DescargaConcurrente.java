package net41;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DescargaConcurrente {
    public static void main(String [] args){
        Scanner sc=new Scanner(System.in);
        CountDownLatch countDownLatch=new CountDownLatch(3);
        try(ExecutorService pool= Executors.newFixedThreadPool(3)){
            System.out.println("Introduce la ruta del recurso:");
            String ruta="http://localhost:8080/on1-1620063088.webp";//sc.nextLine();
            URL url=new URL(ruta);
            System.out.println("Introduce el directorio donde se guardara el recurso:");
            String dir="C:\\Users\\Raul\\IdeaProjects\\SistemasDistribuidos\\src\\net41\\descarga\\de";//sc.nextLine();
            String [] dirPartes=ruta.split("/");
            String directorio=dir+"\\"+dirPartes[dirPartes.length-1];
            HttpURLConnection con= (HttpURLConnection) url.openConnection();
            con.setRequestMethod("HEAD");
            long tamano=con.getContentLengthLong();
            int partes= (int) (tamano/3);
            int i=0;
            while(i<2){
                pool.execute(new Descargador(url,directorio,partes*i,partes*(i+1)-1,countDownLatch));
                i++;
            }
            pool.submit(new Descargador(url,directorio,partes*i,tamano-1,countDownLatch));
            countDownLatch.await();
        }catch (IOException e){
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
//http://i1.wp.com/hipertextual.com/wp-content/uploads/2015/09/googles-new-logo-.gif