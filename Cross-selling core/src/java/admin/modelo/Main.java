/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.modelo;

import java.util.ArrayList;
import org.apache.commons.codec.digest.DigestUtils;
import rally.modelo.Asignar;
import rally.modelo.Referencia;

/**
 *
 * @author RigoGalicia
 */
public class Main {
    
    public static void main(String args[]){
        Asignar a = new Asignar();
        a.setUserConect("todesaragb");
        a.consultarRefParaAsignarme();
    }
    
}
