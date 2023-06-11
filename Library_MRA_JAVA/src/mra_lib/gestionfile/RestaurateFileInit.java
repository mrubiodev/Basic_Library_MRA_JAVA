/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libs_MarioRubioAvila_DAM;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Permite hacer copias de seguridad de ficheros/ carpetas criticas de los proyectos
 * @author Mario Rubio Avila
 * @version 0.1
 * @since  2020
 */
public class RestaurateFileInit {
    final static String origin = ("./src/res.cs/");
    final static String destination = ("./src/res/");
    final static String nameFile = "";
    
    public static boolean copyDirectory() {
        return copyDirectory(origin,destination);
    }
    
    public static boolean copyDirectory(String originDir, String destinationDir) {
        File origin = new File(originDir);
        File destination = new File(destinationDir);        
        if (origin.isDirectory()){
            if (destination.isDirectory()){//Todo es directorio
                String[] ficheros = origin.list();
                for (int x=0;x<ficheros.length;x++) {
                    copyFile(new File(origin,ficheros[x]),new File(destination,ficheros[x]));                           
                }               
            }else{}//Destino no es directorio
        }else{//Origen no es directorio
    // Copiamos d1 a d2, ya que serán ficheros
        }
        return false;
    }
 
    
    public static boolean copyFile(File origin, File destination) {
        if (origin.exists()) {
            try {
                InputStream in = new FileInputStream(origin);
                OutputStream out = new FileOutputStream(destination);
                // We use a buffer for the copy (Usamos un buffer para la copia).
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                in.close();
                out.close();
                return true;
            } catch (IOException ioe) {
                    System.out.println(ColorConsole.RED + "Los ficheros a copiar no estan disponibles." + ColorConsole.RESET );
                //ioe.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }
    
    public static boolean abrirFichero(File fichero) {
        Desktop ficheroAEjecutar = Desktop.getDesktop();
        try {
            ficheroAEjecutar.open(fichero);
            return true;
        } catch (IOException ioe) {
            System.out.println(ColorConsole.RED + "Uch fallo la apertura del fichero." + ColorConsole.RESET );
            return false;
        } catch (IllegalArgumentException ioe) {
            System.out.println(ColorConsole.RED + "El fichero no fue encontrado es posible que no este creado aun el fichero." + ColorConsole.RESET );
            return false;
        }
    }
    /*
    //Queremos sustituir el fichero si existe
    public static boolean sustituirFile(File fichero){//Le pasamos si queremos solo consultarlo o queremos confirmacion de sustitucion
        if (fichero.exists()){
            System.out.println("El fichero " + fichero + " existe");
            return (getYESorNOTFromKB("sustituir")); //Devuelve que podemos pisotear el fichero o false si no queremos sustituir
            }
        else
            System.out.println("El fichero " + fichero + " no existe");
        return true; //Devuelve que podemos pisotear el fichero porque no exise
    }*/
}
