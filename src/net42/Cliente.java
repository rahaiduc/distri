package net42;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Cliente {
    public static void main(String [] args){
        try{
            URL url = new URL("http", "localhost", 8080,"/");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Range","bytes=0-99");
            // Leer la respuesta
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                String line;
                System.out.println(con.getResponseCode());
                System.out.println(con.getHeaderFields());
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }


            // Cerrar la conexión
            con.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
