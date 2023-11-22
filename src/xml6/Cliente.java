package xml6;

import java.io.*;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Cliente {
    public static void main(String [] args){
        Scanner sc=new Scanner(System.in);
        try(Socket s=new Socket("localhost",8080);
            ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream ois=new ObjectInputStream(s.getInputStream())) {
            while (true) {
                System.out.println("Elige una opcion: \r\n" +
                        "1. Enviar información de una persona confinada\r\n" +
                        "2. Mostrar el XML de todas las personas confinadas\r\n" +
                        "3. Desconectar");

                String opcion = sc.nextLine();
                switch (opcion) {
                    case "1":
                        oos.writeObject("Voy a enviar una persona");
                        Persona persona = new Persona();
                        System.out.println("Proporcione la siguiente informacion de la persona que se va a enviar:");
                        System.out.println("Primer contacto de la persona:");
                        String contacto = sc.nextLine();
                        persona.addContacto(contacto);
                        String respuesta = "";
                        while (respuesta.toLowerCase().equals("no")) {
                            System.out.println("¿Añadir mas contactos? si/no");
                            if ((respuesta = sc.nextLine()).toLowerCase().equals("si")) {
                                System.out.println("Siguiente contacto:");
                                contacto = sc.nextLine();
                                persona.addContacto(contacto);
                            }
                        }
                        boolean formato = false;
                        while (!formato) {
                            try {
                                System.out.println("Fecha de inicio (dd/MM/yyyy):");
                                String fecha = sc.nextLine();
                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                Date date = sdf.parse(fecha);
                                persona.setFechaInicio(date);
                                formato = true;
                            } catch (ParseException e) {
                                System.out.println("Formato de fecha invalido.");
                            }
                        }
                        oos.writeObject(persona);
                        break;
                    case "2":
                        oos.writeObject("Muestrame los confinados");
                        break;
                    case "3":
                        oos.writeObject("Desconectar");
                        break;
                    default:
                        System.out.println("No se ha ingresado un numero valido");
                }

            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
