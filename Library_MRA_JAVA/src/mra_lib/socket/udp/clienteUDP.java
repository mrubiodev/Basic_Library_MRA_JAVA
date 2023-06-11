/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mra_lib.socket.udp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import libs_MarioRubioAvila_DAM.ToolsAndMenu;
import libs_MarioRubioAvila_DAM.outStringDisplay;

/**
 *
 * @author minak
 */
public class clienteUDP {
    private static Socket socket;
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 6000;
    private static byte[] buf = new byte[256];

    public static void main(String[] args) throws IOException {
        ToolsAndMenu.title("Cliente TCP");
        outStringDisplay.muestraMensaje("El espacio en blanco apaga servidor y cliente.");
        String claveInsertada;
        outStringDisplay.muestraMensaje("Iniciando Cliente");
        try {
            socket = new Socket(HOST,PORT);
            DataOutputStream out = new DataOutputStream (socket.getOutputStream());
            //do {
            claveInsertada = ToolsAndMenu.getStringFromKB("clave");
            outStringDisplay.muestraMensaje("Enviando clave.","INFO");
            out.writeUTF(claveInsertada);
            DataInputStream in = new  DataInputStream(socket.getInputStream());
            String retornoServer = in.readUTF();
            outStringDisplay.muestraMensaje(retornoServer,"INFO");
            if (retornoServer.equals("Conexion establecida.")) {
                while (socket.isConnected()) {
                        //AQUI VA EL CODIGO CLIENTE
                        outStringDisplay.muestraMensaje(in.readUTF(),"INFO");
                }
            }

            out.close();
            in.close();
            socket.close();
            //}while (claveInsertada != "");

        }catch (ConnectException e) {
                outStringDisplay.muestraMensaje("NO SE PUDO CONECTAR CON EL SERVIDOR.","ERROR");
        }catch (SocketException e) {
                        outStringDisplay.muestraMensaje("SE PERDIO LA CONEXION CON EL SERVIDOR.","ERROR");	
        }catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();

        }
        outStringDisplay.muestraMensaje("Hasta Pronto","INFO");
    }
}
