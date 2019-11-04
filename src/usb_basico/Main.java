/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usb_basico;

import jPicUsb.*;

import java.util.Scanner;

/**
 *
 * @author Manager
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    static String vid_pid = "vid_04d8&pid_000c";    // Default Demo Application Firmware

    public static void main(String[] args) {
        try {
            iface.load();
            System.out.println("Libreria inicializada correctamente");
            if (iface.GetDeviceCount(vid_pid) == 1) {
                System.out.println("OK-Dispositivo Encontrado");
            } else {
                System.err.println("Error-Dispositivo no encontrado");
                System.exit(1);
            }


        } catch (Exception ex) {
            System.out.println("Error-0-" + ex.getMessage());
        }


        // temperatura() => me devuelve una string con la temperatura
        // secuencia() => ejecuta la secuencia
        // ejercicio1() => Entra en modo seleccion de leds a encender y etc.


        System.out.println("============================================");
        System.out.println("Software de Arquitectura de Computadores 2.");
        System.out.println("============================================");

        while (true) {
            String ing = menu();
            if (ing.equals("exit")) {
                break;
            }else{
                if (ing.equals("temperatura")) {
                    System.out.println(temperatura());
                }
                if(ing.equals("leds")){
                    ejercicio1();
                }
                if(ing.equals("secuencia")){
                    secuencia();
                    secuencia();
                    secuencia();
                    secuencia();
                }
            }

        }
    }

    private static String menu(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Para leer la temperatura, escriba 'temperatura' ");
        System.out.println("Para manejar los leds a gusto, escriba 'leds' ");
        System.out.println("Para ver la secuencia de luces, escriba 'secuencia'");
        System.out.println("Para salir, escriba 'exit'");
        System.out.println("============================================");
        System.out.print("Comando: ");
        String s = scanner.next();
        return s;
    }


    private static void delay(){
        for(int i = 0; i < 400000000; i++){
            int a = i;
        }
    }

    private static void ejercicio1(){  //no era asi pero we xdxd
        Scanner ing = new Scanner(System.in);
        int[] leds = new int[4];
        System.out.println("Leds a actuar?");
        String ingreso = ing.next();
        String nums = ingreso.replace(",", "");
        for(int i = 0; i < nums.length(); i++){
            int a = nums.charAt(i);
            if(a == 49){
                leds[i] = 1;
            }
            if(a==50){
                leds[i] = 2;
            }
            if(a==51){
                leds[i] = 3;
            }
            if(a == 52){
                leds[i] = 4;
            }
        }

        System.out.println("Para encender el led: 1");
        System.out.println("Para apagar el led: 0");
        int state = ing.nextInt();

        for(int i = 0; i < leds.length; i++){
            manejarled(leds[i], state);
        }

    }

    private static void secuencia(){

        for(int i = 0; i <4 ; i++){
            manejarled(i+1, 1);
            delay();
            manejarled(i+1, 0);
        }

        for(int i = 3; i >= 0 ; i--){
            manejarled(i+1, 1);
            delay();
            manejarled(i+1, 0);
        }

    }








    private static String temperatura() {
        byte[] out = new byte[128];
        out[0] = 'T';//comando para manejar temperatura
        out[1] = (byte) 0x00;//temperatura

        String data_in = "";

        try {
            //iface.WriteRead(byte[] salida, int writeout, int readin, long timeoutmsec)
            //Parametro 1: arreglo de bytes con los datos a enviar (out)
            //Parametro 2: cantidad de bytes del arreglo que se enviaran via usb (1)
            //Parametro 3: cantidad de bytes que se esperan de respuesta via usb (6)
            //Parametro 4: tiempo maximo que se esperara al dispositivo en el envio y la recepcion (1 segundo)
            //Retorna: arreglo de bytes con la respuesta del dispositivo usb
            byte[] respuesta = iface.WriteRead("vid_04d8&pid_000c", 0, out, 3, 64, 1);
            data_in = new String(respuesta,0,4);
            //iface.Write("vid_04d8&pid_000c", 0, out, 3, 50);
            return data_in;

        } catch (Exception ex) {
            System.out.println("ERROR-1-" + ex.getMessage());
            return "Eaa";
        }

    }










    private static String manejarled(int nroled, int estado) {
        byte[] out = new byte[128];
        out[0] = 'L';//comando para manejar led
        out[1] = (byte) nroled;//numero de led
        out[2] = (byte) estado;//estado del led   

        String data_in = "";

        try {
            //iface.WriteRead(byte[] salida, int writeout, int readin, long timeoutmsec)
            //Parametro 1: arreglo de bytes con los datos a enviar (out)
            //Parametro 2: cantidad de bytes del arreglo que se enviaran via usb (1)
            //Parametro 3: cantidad de bytes que se esperan de respuesta via usb (6)
            //Parametro 4: tiempo maximo que se esperara al dispositivo en el envio y la recepcion (1 segundo)
            //Retorna: arreglo de bytes con la respuesta del dispositivo usb
            byte[] respuesta = iface.WriteRead("vid_04d8&pid_000c", 0, out, 3, 64, 1);
            data_in = new String(respuesta, "utf-8");
            //iface.Write("vid_04d8&pid_000c", 0, out, 3, 50);
            return data_in;

        } catch (Exception ex) {
            System.out.println("ERROR-1-" + ex.getMessage());
            return "Error";
        }

    }

}
