/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.modelo;

import javax.swing.JOptionPane;
import org.apache.commons.codec.digest.DigestUtils;
import org.bson.types.ObjectId;
import rally.modelo.Asignar;

/**
 *
 * @author RigoGalicia
 */
public class Main {
    
    public static void main(String args[]){
        String clave = JOptionPane.showInputDialog("Ingrese Clave");
        System.out.println(DigestUtils.md5Hex(clave));
    }
    
}
