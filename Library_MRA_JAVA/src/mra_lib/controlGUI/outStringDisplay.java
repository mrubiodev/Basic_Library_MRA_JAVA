/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libs_MarioRubioAvila_DAM;

import java.awt.TextArea;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 * Tiene depencia de la clase ColorConsole, CustomOutputStream, ProxyPrintStream.
 * Esta clase no permite redireccionar facilmente el flujo de salida del programa haciendo que los textos cambiando
 * las variable outputString se cambie todo el flujo.
 * Ademas nos permite crear un fichero de log del programa, asi todos los mensajes seran guardados en un fichero.
 * @author Mario Rubio Avila
 * @version 0.9
 * @since  2020
 */
public class outStringDisplay {
  
    /**Determina la salida del texto
     * 0 -> Terminal (Default)
     * 1 -> JOptionPane
     * 2 -> Lo muestra en el areaTextLogs y JAreaTextLogs si estan definidos
     */
    public static final int outputDefaultString = 0;
    /**
     * Indica si reseteamos el fichero log al inicio
     */
    public static final boolean flagResetLogInInit = false;
    private static TextArea areaTextLogs = null;
    private static JTextArea JAreaTextLogs = null;
    /**
     * Estas variables son para crear el fichero logs.
     */
    final static boolean fileLogs = true;
    final static String destination = ("./res");
    final static String nameFile = "logs.txt";
    final static File origin = new File(destination + "/" + nameFile);
    
    /**Tipos de mensajes 
     * https://docs.oracle.com/javase/7/docs/api/constant-values.html#javax.swing.JOptionPane.ERROR_MESSAGE
     * -1 ->SIN ASUNTO
     * 0 -> ERROR_MESSAGE
     * 1 -> INFORMATION_MESSAGE
     * 2 -> WARNING_MESSAGE
     * 3 -> QUESTION_MESSAGE
     * 4 -> DEBUG MODE
     */
    final static int defaultTypeMensaje = -1;

    public static void setAreaTextLogs(TextArea areaTextLogs) {
        outStringDisplay.areaTextLogs = areaTextLogs;
    }
    
    public static void setJAreaTextLogs(JTextArea areaTextLogs) {
        outStringDisplay.JAreaTextLogs = areaTextLogs;
    }
    
    public static void muestraMensaje(String mensaje){
        writeMensajeInLog(mensaje);
    }
    
    /**
     * Generamos un mensaje de dialogo con el mensaje y asunto elegido y el mensaje a no tener tipo de mensaje se usa el generico
     * @param mensaje
     * @param asunto
     * @return 
     */
    public static void muestraMensaje(String mensaje,String asunto){
        muestraMensaje(mensaje,asunto,defaultTypeMensaje);
    }
    
    /**
     * Generamos un mensaje de dialogo con el mensaje , asunto elegido,y con el tipo de mensaje elegido
     * @param tipoMensaje
     * @param mensaje
     * @param asunto 
     */
    public static void muestraMensaje(String mensaje,String asunto, int tipoMensaje){
        if (fileLogs){// Lo grabo en el fichero de logs
            writeMensajeInLog(mensaje,asunto,tipoMensaje);
        }
        switch(outputDefaultString){
            case 1:    //JOptionPane
                JFrame dialog=new JFrame();
                dialog.setAlwaysOnTop(true);
                JOptionPane.showMessageDialog(dialog, mensaje, asunto,tipoMensaje);
                break;
            case 2:
                if (areaTextLogs!=null) agregarTextoAlfinalAreaText(mensaje);
                if (JAreaTextLogs!=null) agregarTextoAlfinalJAreaText(mensaje);
                break;
            default:    //Este es mensaje por consola
                if (!asunto.equals("")) asunto = asunto + " : ";
                switch(tipoMensaje){
                    case 0: //ERROR_MESSAGE
                        System.out.println(ColorConsole.RED + asunto + mensaje + ColorConsole.RESET );
                        break;
                    case 1: //INFORMATION_MESSAGE
                        System.out.println(ColorConsole.BLACK + asunto + mensaje + ColorConsole.RESET );
                        break;
                    case 2: //WARNING_MESSAGE
                        System.out.println(ColorConsole.YELLOW + asunto + mensaje + ColorConsole.RESET );
                        break;
                    case 3: //QUESTION_MESSAGE
                        System.out.println(ColorConsole.PURPLE + asunto + mensaje + ColorConsole.RESET );
                        break;
                    default:
                        System.out.println(ColorConsole.BLACK + mensaje + ColorConsole.RESET );
                        break;
                }
        }
           
        
    }

    /**
     * Escribimos un mensaje en las salidas configuradas y agnadimos al fichero de log
     * @param mensaje 
     */
    public static void writeMensajeInLog(String mensaje){
        if (!origin.exists() || flagResetLogInInit) crearArchivo();
        agregarTextoAlFinalLogsFile(mensaje);
        if (areaTextLogs!=null) agregarTextoAlfinalAreaText(mensaje);
        if (JAreaTextLogs!=null) agregarTextoAlfinalJAreaText(mensaje);
    }
    
    /**
     * Escribimos un mensaje en las salidas configuradas y agnadimos al fichero de log
     * @param mensaje 
     * @param asunto 
     * @param tipoMensaje 
     */
    private static void writeMensajeInLog(String mensaje,String asunto, int tipoMensaje){
        java.util.Date fecha = new Date();
        switch(tipoMensaje){
            case 0:
                asunto = "(ERROR)       - " + asunto  + " : ";
                break ;
            case 1:
                asunto = "(INFORMACION) - " + asunto  + " : ";
                break ;
            case 2:
                asunto = "(ADVERTENCIA) - " + asunto  + " : ";
                break;
            case 3:
                asunto = "(PREGUNTA)    - " + asunto  + " : ";
                break;
            default:
                asunto = "(DEFAULT)     - " + asunto  + " : ";
                break;
        }
        if (!origin.exists() || flagResetLogInInit) crearArchivo();
        agregarTextoAlFinalLogsFile((fecha + " - " + asunto + mensaje));
        if (areaTextLogs!=null) agregarTextoAlfinalAreaText(fecha + " - " + asunto + mensaje);
        if (JAreaTextLogs!=null) agregarTextoAlfinalJAreaText(fecha + " - " + asunto + mensaje);
    }
    
    //**TRATAMIENTO FICHERO DE LOG TANTO LECTURA COMO ESCRITURA**/
    public static boolean crearArchivo(){
        FileWriter fwArchTemp;
        try {
            //File origin = new File(destination + "/" + nameFile);
            //if (!origin.exists() || flagResetLogInInit){
                fwArchTemp = new FileWriter(origin);
                BufferedWriter bwArchTemp;
                bwArchTemp = new BufferedWriter(fwArchTemp);
                try (PrintWriter wrArchTemp = new PrintWriter(bwArchTemp)) {
                    //java.util.Date fecha = new Date();
                    //wrArchTemp.print(fecha + " - " + "INFORMACION : " + "Fichero de logs creado" + "\n");
                    wrArchTemp.close(); /*Cerrando archivo de escritura*/
                    writeMensajeInLog("Fichero de logs creado con exito.","SISTEMA LOGS",1);
                }
                bwArchTemp.close(); /*Cerrando apertura de archivo*/
                fwArchTemp.close();
            //}else{
            //    writeMensajeInLog("Aplicacion iniciada.","SISTEMA LOGS",1);
            //}
            return true;
        } catch (IOException ex) {
            System.err.println("Error: "+ex.getMessage());
            return false;
        }
    }
    
    /**
     * Agnadimos el mensaje al area de texto que muestra el Logs
     * @param mensaje 
     */
    private static void agregarTextoAlfinalAreaText(String mensaje){
        areaTextLogs.append(mensaje + "\n");
    }
    
    /**
     * Agnadimos el mensaje al area de texto que muestra el Logs
     * @param mensaje 
     */
    private static void agregarTextoAlfinalJAreaText(String mensaje){
        JAreaTextLogs.append(mensaje + "\n");
    }
    
    /**
     * Reiniciamo el area de texto que muestra los Logs
     */
    public static void resetAreaText(){
        areaTextLogs.setText("");
    }
    /**
     * Reiniciamo el area de texto que muestra los Logs
     */
    public static void resetJAreaText(){
        JAreaTextLogs.setText("");
    }
    
    
    /**
     * Agrega el mensaje al final del fichero de logs
     * @param mensaje
     * @return 
     */
    private static boolean agregarTextoAlFinalLogsFile(String mensaje){   
        try {
            String nombreArch =destination + "/" + nameFile;
            FileWriter fstream = new FileWriter(nombreArch, true);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(mensaje + "\n");
            out.close();
            return true;
        }  catch (IOException ioe) {
            System.out.println(ColorConsole.RED + "Uch fallo la apertura del fichero." + ColorConsole.RESET );
            return false;
        } catch (IllegalArgumentException ioe) {
            System.out.println(ColorConsole.RED + "El fichero no fue encontrado es posible que no este creado aun el fichero." + ColorConsole.RESET );
            return false;
        }
    }
    
    /**
     * Dado un destino que puede ser salida por defecto(0), salida a fichero(1) y salida a textview(2)
     * envia todo lo escrito a la salida
     * @param destino 
     */
    public static void setSystemOutput (int destino){ 
        switch(destino){
            case 0: 
                setSystemOutputSystemDefaultOut();
                break;
            case 1:    
                setSystemOutputFile();
                break;
            case 2:
                setSystemOutputTextArea();
                break;
            default:
               break;
        }

    }  
    
   
    private static void setSystemOutputSystemDefaultOut (){
        System.setOut(System.out);
        System.setErr(System.err); 
    }
    
    /**
     * La salida estandar pasa a ser ficheros.
     * http://www.jcgonzalez.com/java-system-err-system-out-ejemplos
     */
    private static void setSystemOutputFile(){
        System.setOut(new ProxyPrintStream(System.out, "stdout.log"));
        System.setErr(new ProxyPrintStream(System.err, "stderr.log"));   
    }
    /**
     * https://stackoverflow.com/questions/5107629/how-to-redirect-console-content-to-a-textarea-in-java
     */
    private static void setSystemOutputTextArea (){
        PrintStream printStream = new PrintStream(new CustomOutputStream(JAreaTextLogs));
        System.setOut(printStream);
        System.setErr(printStream); 
    }
        
}
