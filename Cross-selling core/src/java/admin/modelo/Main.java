/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.modelo;

import gc.modelo.SolicitudesGeneradas;

/**
 *
 * @author RigoGalicia
 */
public class Main {
    
    public static void main(String args[]){
        SolicitudesGeneradas sg = new SolicitudesGeneradas();
        sg.setEstadoId("a");
        for(SolicitudesGeneradas s : sg.mostrarDatos("todesaragb")){
            System.out.println(s.getNombreAsociado());
        }
    }
    
}
