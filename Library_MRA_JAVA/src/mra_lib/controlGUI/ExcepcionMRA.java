/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libs_MarioRubioAvila_DAM;

/**
 * Permite dotar a mis aplicaciones de excepciones propias
 * @author Mario Rubio Avila
 * @version 0.1
 * @since  2020
 */
public class ExcepcionMRA extends Exception {
     
    private int codigoError;
     
    public ExcepcionMRA(int codigoError){
        super();
        this.codigoError=codigoError;
    }
     
    @Override
    public String getMessage(){
        String mensaje="";
        switch(codigoError){
            case 1001:
                mensaje="Inconsitencia de datos, esta devolucion de datos indica un error externo a nuestros dominios";
                break;
            default:
                mensaje="Ocurrio un error no posible de cuantificar";
                break;

        }
         
        return mensaje;
         
    }
     
}

