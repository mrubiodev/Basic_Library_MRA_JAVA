/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mra_lib.socket.udp;

import MD5_Tools.MD5_Tools;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import libs_MarioRubioAvila_DAM.ToolsAndMenu;
import libs_MarioRubioAvila_DAM.outStringDisplay;

/**
 *
 * @author minak
 */
public class servidorUDP {
    private static ServerSocket socket;
    private final static int PORT = 6000;
    //private static DatagramSocket socket;
    private static byte[] buf = new byte[256];
    
    public static void main(String[] args) {
        ToolsAndMenu.title("Server TCP");
        outStringDisplay.muestraMensaje("El espacio en blanco enviado desde cliente el apaga servidor. Si no siempre a la escucha.");
        outStringDisplay.muestraMensaje("Iniciando Servidor","INFO");
        DataInputStream in;
        DataOutputStream out;
        try {
                socket = new ServerSocket(PORT);
                while (true) {
                    Socket socket_cli = socket.accept();
                    outStringDisplay.muestraMensaje("Cliente conectado","INFO");
                    in = new DataInputStream(socket_cli.getInputStream());
                    out = new DataOutputStream(socket_cli.getOutputStream());
                    String received = "";
                    received = in.readUTF();
                    outStringDisplay.muestraMensaje("Hemos recibido :" + received, "INFO");
            if (received.equals("")) {// Orden desde un cliente para apagar el servido
                out.writeUTF("El servidor sera desconectado.");
                outStringDisplay.muestraMensaje("*.*.*.*.*.*.*.*.*.*.*.*.*","INFO");
                break;
            }else {
                if (MD5_Tools.findKeyInFile(received)) {
                        outStringDisplay.muestraMensaje("La conexion fue establecida con el cliente","INFO");
                        out.writeUTF("Conexion establecida.");
                        while (socket_cli.isConnected()) {
                                /**AQUI VA EL CODIGO DEL PROGRAMA*/
                                out.writeUTF("Estas Conectado al servidor");
                                outStringDisplay.muestraMensaje("El cliente sigue conectado","INFO");
                                ToolsAndMenu.sleepThread(5);
                        }//FIN BUCLE
                }else {
                        outStringDisplay.muestraMensaje("Error en el logeo del cliente se procede al cierre de conexion","INFO");
                        out.writeUTF("Acceso NO autorizado.");
                }
             out.close();
             in.close();
             outStringDisplay.muestraMensaje("ESPERANDO NUEVO CLIENTE","INFO");
            }
                }
                outStringDisplay.muestraMensaje("Cerramos el servidor.","INFO");
        socket.close();

        } catch (IOException e) {
                e.printStackTrace();
        }catch (Exception e) {
                e.printStackTrace();
        }
        outStringDisplay.muestraMensaje("Hasta Pronto","INFO");

    }
    
    
}
