package net42;

import java.io.File;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Date;

public class ServidorUtiles {
    private static final String HOMEDIR="C:\\Users\\Raul\\IdeaProjects\\SistemasDistribuidos\\src\\net41\\descarga";
    public File buscaFichero(String m) {
        String fileName="";
        if (m.startsWith("GET ")){
            // A partir de una cadena de mensaje (m) correcta (comienza por GET)
            fileName = m.substring(4, m.indexOf(" ", 5));
            if (fileName.equals("/")) {
                fileName += "index.html";
            }}
        if (m.startsWith("HEAD ")){
            // A partir de una cadena de mensaje (m) correcta (comienza por HEAD)
            fileName = m.substring(6, m.indexOf(" ", 7));
            if (fileName.equals("/")) {
                fileName += "index.html";
            }
        }
        System.out.println(fileName);
        return new File(HOMEDIR, fileName);
    }

    public void sendMIMEHeading(OutputStream os, int code, String cType, long fSize) {
        PrintStream dos = new PrintStream(os);
        dos.print("HTTP/1.1 " + code + " ");
        if (code == 200) {
            dos.print("OK\r\n");
            dos.print("Date: " + new Date() + "\r\n");
            dos.print("Server: Cutre http Server ver. -6.0\r\n");
            dos.print("Connection: close\r\n");
            dos.print("Content-length: " + fSize + "\r\n");
            dos.print("Content-type: " + cType + "\r\n");
            dos.print("\r\n");
        } else if (code == 404) {
            dos.print("File Not Found\r\n");
            dos.print("Date: " + new Date() + "\r\n");
            dos.print("Server: Cutre http Server ver. -6.0\r\n");
            dos.print("Connection: close\r\n");
            dos.print("Content-length: " + fSize + "\r\n");
            dos.print("Content-type: " + "text/html" + "\r\n");
            dos.print("\r\n");
        } else if (code == 501) {
            dos.print("Not Implemented\r\n");
            dos.print("Date: " + new Date() + "\r\n");
            dos.print("Server: Cutre http Server ver. -6.0\r\n");
            dos.print("Connection: close\r\n");
            dos.print("Content-length: " + fSize + "\r\n");
            dos.print("Content-type: " + "text/html" + "\r\n");
            dos.print("\r\n");
        }
        dos.flush();
    }
    public String makeHTMLErrorText(int code, String txt) {
        StringBuffer msg = new StringBuffer("<HTML>\r\n");
        msg.append(" <HEAD>\r\n");
        msg.append(" <TITLE>" + txt + "</TITLE>\r\n");
        msg.append(" </HEAD>\r\n");
        msg.append(" <BODY>\r\n");
        msg.append(" <H1>HTTP Error " + code + ": " + txt + "</H1>\r\n");
        msg.append(" </BODY>\r\n");
        msg.append("</HTML>\r\n");
        return msg.toString();
    }

}
