package net42;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Optional;

public class AtenderPeticion implements Runnable{
    Socket socket;
    ServidorUtiles servidorUtiles;
    public AtenderPeticion(Socket socket) {
        this.socket = socket;
        this.servidorUtiles=new ServidorUtiles();
    }

    @Override
    public void run() {
        try(BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            OutputStream os=socket.getOutputStream();
            PrintStream ps=new PrintStream(os)){
            boolean range=false;
            String peticion=br.readLine();
            String linea=br.readLine();
            long inicial=0;
            long fin=0;
            if(linea.startsWith("Range")){
                range=true;
                int index = linea.indexOf('-');
                inicial = Long.parseLong(linea.substring("Range: bytes=".length(), index));
                fin = Long.parseLong(linea.substring(index + 1));
            }
            File fichero=servidorUtiles.buscaFichero(peticion);
            if(fichero!=null){
                try(RandomAccessFile raf=new RandomAccessFile(fichero,"rw")) {
                    servidorUtiles.sendMIMEHeading(os, 200, "multipart", fichero.length());
                    byte[] contenido;
                    if(peticion.startsWith("GET")) {
                        if (range) {
                            raf.seek(inicial);
                            contenido=new byte[(int) (fin-inicial)];
                            raf.readFully(contenido);
                            ps.write(contenido);
                        } else {
                            contenido = Files.readAllBytes(fichero.toPath());
                            ps.write(contenido);
                        }
                    }
                }
            }else{
                String paginaError=servidorUtiles.makeHTMLErrorText(404,"No se ha encontrado el fichero.");
                servidorUtiles.sendMIMEHeading(os,404,"text/html",paginaError.getBytes().length);
                ps.println(paginaError);
            }
            br.readLine();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
