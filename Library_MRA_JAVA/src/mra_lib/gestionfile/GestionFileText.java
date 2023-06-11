package libs_MarioRubioAvila_DAM;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class GestionFileText {

	
	/**
	 * Esta clase de la libreria se encarga de guardar y leer en fichero de textos string.
	 * Tiene dependecia outStringDisplay.
	 * Se crea sobrecarga de funciones en ReadToFile y WriteToFile
	 * @author Mario Rubio Avila
	 * @version 0.3
	 * @since  2019
	 */
	public static class WriteReadToFile {
	    
	    final static boolean debugMode = false;
	    
	    
	    
	    /**
	     * Indicando un ruta como string devuelve el array con los datos del ficheros
	     * @param Ruta
	     * @return
	     */
	    public static ArrayList<String> ReadToFile(String Ruta){
	    	return ReadToFile(new File(Ruta));
	    }
	    
	    /**
	     * Indicado la ruta con variable File de un fichero ./res/text.txt lo lee y devuelve un ArrayList, con cada linea del fichero.
	     * @param Ruta 
	     * @return
	     */
	    public static ArrayList<String> ReadToFile(File archivo){
	        ArrayList<String> documento= new ArrayList<String>(); ;
	        //String documento
	            try {
	                //File archivo = new File (Ruta);
	                FileReader FileRead = new FileReader (archivo);             // Apertura del fichero y creacion de BufferedReader para poder
	                BufferedReader BufferRead = new BufferedReader(FileRead);   // Apertura del buffer  para hacer una lectura comoda y disponer del metodo readLine()).
	                // Lectura del fichero
	                String linea;
	                while((linea=BufferRead.readLine())!=null){
	                    if (debugMode) outStringDisplay.muestraMensaje("Linea leida : " + linea,"DEBUG");
	                    //documento = documento + linea;
	                    documento.add(linea);               //Optimizando codigo nos vamos a llevar linea por linea para luego restaura linea a linea
	                }
	                //Vamos cerrando que aqui ya no hay nada que ver
	                FileRead.close();
	                BufferRead.close();
	            }catch(IOException e){
	            	outStringDisplay.muestraMensaje("Uchs,que mal algo no fue bien : " + e,"ERROR");
	                if (debugMode) e.printStackTrace();
	            }
	        return documento;
	    }
	    

	    /**
	     * Dado un file y un string los grabamos al final del fichero
	     * @param Ruta
	     * @param nuevaLinea
	     */
	    public static boolean WriteToFile(File Ruta, String nuevaLinea){
	    	ArrayList<String> insertar = new ArrayList<String>();
	    	insertar.add(nuevaLinea);
	    	return WriteToFile(Ruta,insertar);
	    }
	    
	    /**
	     * Dado un string de ruta y un string los grabamos al final del fichero
	     * @param Ruta
	     * @param nuevaLinea
	     */
	    public static boolean WriteToFile(String Ruta, String nuevaLinea){
	    	File archivo = new File (Ruta);
	    	ArrayList<String> insertar = new ArrayList<String>();
	    	insertar.add(nuevaLinea);
	    	return WriteToFile(archivo,insertar);
	    }
	    
	    /**
	     * Indicado la ruta de un fichero ./res/text.txt escribe en el fichero lo pasado en un ArrayList.
	     * @param Archivo
	     * @param lineasNuevas
	     */
	    public static boolean WriteToFile(File archivo, ArrayList<String> lineasNuevas){
	    	BufferedWriter bw = null;
	        FileWriter     fw = null;
	        try {
	            if (!archivo.exists()) {
	            	if (debugMode) outStringDisplay.muestraMensaje("El fichero " +archivo.getName() +" a sido creado.","DEBUG");
	            	archivo.createNewFile();
	            }
	            fw = new FileWriter(archivo, true);
	            for (String linea : lineasNuevas) {
		            bw = new BufferedWriter(fw);
		            bw.write(linea + "\n");
		            if (bw != null)
		                bw.close();
	            }
	            if (fw != null)
	                fw.close();
	            return true;
	        } catch (IOException ex) {
	            System.err.println("Error: "+ex.getMessage());
	            return false;
	        }
	        
	        
	    }
	    
	    
	    /**
	     * Sirve para crear pequeño mensaje por consola si se desea sustituir el fichero o no
	     * @param fichero
	     * @return
	     */
	    public static boolean sustituirFile(File fichero){//Le pasamos si queremos solo consultarlo o queremos confirmacion de sustitucion
	        if (fichero.exists()){
	        	outStringDisplay.muestraMensaje("El fichero " + fichero + " existe","INFO");
	            return (ToolsAndMenu.getYESorNOTFromKB("sustituir")); //Devuelve que podemos pisotear el fichero o false si no queremos sustituir
	            }
	        else
	        	outStringDisplay.muestraMensaje("El fichero " + fichero + " no existe","INFO");
	        return true; //Devuelve que podemos pisotear el fichero porque no exise
	    }
	}
}
