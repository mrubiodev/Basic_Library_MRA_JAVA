package MD5_Tools;

import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import libs_MarioRubioAvila_DAM.ToolsAndMenu;
import libs_MarioRubioAvila_DAM.outStringDisplay;
import libs_MarioRubioAvila_DAM.GestionFileText.WriteReadToFile;

public class MD5_Tools {
	
    final static String destination = ("./res");
    final static String nameFile = "claves.txt";
    final static File origin = new File(destination + "/" + nameFile);
	
    /**
     * Genera el fichero claves encriptado
     * @param args
     */
	public static void main(String[] args) {
		ToolsAndMenu.title("GENERADOR MD5");
		outStringDisplay.muestraMensaje("Bienvenido al generado de claves","INFO");
		outStringDisplay.muestraMensaje("Deje en blanco para salir"       ,"INFO");
		while(true) {
			String newClave = ToolsAndMenu.getStringFromKB("clave");
			if (newClave.isEmpty())break;
			String MD5 = getMD5(newClave);
			outStringDisplay.muestraMensaje("Clave generada : "+ MD5,"INFO");
			setClaveInFile(MD5);
			outStringDisplay.muestraMensaje("Clave guardada"        ,"INFO");
		}	
		ArrayList<String>claves =clavesInFile();
		outStringDisplay.muestraMensaje("Las claves sobre el fichero son : ","INFO");
		for (String clave : claves) outStringDisplay.muestraMensaje("* "+ clave,"INFO");
	}
	
	/**
	 * Dado un String lo codifica en MD5
	 * @param input
	 * @return
	 */
	public static String getMD5(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(input.getBytes());
			BigInteger number = new BigInteger(1, messageDigest);
			String hashtext = number.toString(16);

			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		}
		catch (NoSuchAlgorithmException e) {
			outStringDisplay.muestraMensaje("Algo no fue bien","ERROR");
			throw new RuntimeException(e);
		}
	}
	
    /**
     * Crea fichero de claves y agnage claves
     * @param clave
     * @return
     */
    public static void setClaveInFile(String clave){
    	WriteReadToFile.WriteToFile(origin, clave);
        
    }

    /**
     * Escribe la nueva clave al final de fichero de claves
     * @return
     */
    public static ArrayList<String> clavesInFile() {
		return WriteReadToFile.ReadToFile(origin);
    }
    
    
    
    /**
     * Dada una clave sin codificar nos va a devolver si existe dentro del fichero de claves codificadas o no
     * @param claveNotCoded
     * @return
     */
    public static Boolean findKeyInFile(String claveNotCoded) {
    	if (!origin.exists()) {
    		outStringDisplay.muestraMensaje("Algo no fue bien, el fichero de claves no existe.","ERROR");
    	}else {
	    	String claveCode = getMD5(claveNotCoded);
	    	for (String claveFile : clavesInFile()) {
	    		if (claveFile.equals(claveCode)) return true;
	    	}
    	}
       return false;
    }
}
