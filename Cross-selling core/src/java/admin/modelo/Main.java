/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.modelo;

import org.bson.types.ObjectId;
import rally.modelo.Asignar;

/**
 *
 * @author RigoGalicia
 */
public class Main {
    
    public static void main(String args[]){
        Privilegio p = new Privilegio();
        p.setId(new ObjectId("5aeb63eb84e9dd036d15e1f4"));
        p.setDescripcion("otra cosa");
        p.setForma("Tambien");
        p.update();

    }
    
}
