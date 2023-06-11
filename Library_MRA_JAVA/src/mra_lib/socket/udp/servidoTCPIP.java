/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mra_lib.socket.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import static sun.rmi.transport.DGCAckHandler.received;


/**
 *
 * @author minak
 */
public class servidoTCPIP {
    private static DatagramSocket socket;
    private static byte[] buf = new byte[256];

	
	public static void main(String[] args) {
            // TODO Auto-generated method stub
            System.out.println("Iniciando Servidor");
            try {
                socket = new DatagramSocket(1987);
                while (true) {
                    DatagramPacket packet = new DatagramPacket(buf, buf.length);
                    socket.receive(packet);

                    InetAddress address = packet.getAddress();
                    int port = packet.getPort();
                    //packet = new DatagramPacket(buf, buf.length, address, port);
                    //Numeros received = new Numeros ((Numeros) Numeros.deserializeBytes(packet.getData()));
                    //received.calculate();
                    //System.out.println("Los datos a devolver son :");
                    //received.printSystemOutNumeros();
                    //buf = received.serializeObject();
                    packet = new DatagramPacket(buf, buf.length, address, port);
                    socket.send(packet);
                    
                    //if (received.getNumero() <= 0) {
                    if (0 <= 0) {
                        System.out.println("*.*.*.*.*.*.*.*.*.*.*.*.*");
                        break;
                    }else {
                        System.out.println("Los datos devueltos son :");
                        //received.printSystemOutNumeros();
                        System.out.println("Esperando otro numero que calcular.");
                    }
                    
                }
                System.out.println("Hasta Pronto");
                socket.close();
            } catch (IOException e) {
                    e.printStackTrace();
            }
	}
}
