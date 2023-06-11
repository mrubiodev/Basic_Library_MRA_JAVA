/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libs_MarioRubioAvila_DAM;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

/**
 * Dependencia de outStringDisplay
 * @author Mario Rubio Avila
 * @version 0.1
 * @since  2020
 */
public class ProxyPrintStream extends PrintStream{    
    private PrintStream fileStream = null;
    private PrintStream originalPrintStream = null;
    public ProxyPrintStream(PrintStream out, String FilePath) {
        super(out);
        originalPrintStream = out;
         try {
             FileOutputStream fout = new FileOutputStream(FilePath,true);
             fileStream = new PrintStream(fout);
        } catch (FileNotFoundException e) {
                e.printStackTrace();
        }
    }    
    public void print(final String str) {
        originalPrintStream.print(str);
        fileStream.print(str);
    }
    public void println(final String str) {
        originalPrintStream.println(str);
        fileStream.println(str);
    }        
}
