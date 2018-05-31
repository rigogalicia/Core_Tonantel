/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.modelo;

import gc.modelo.SolicitudesRecibidas;

/**
 *
 * @author RigoGalicia
 */
public class Main {
    
    public static void main(String args[]){
        SolicitudesRecibidas recibidas = new SolicitudesRecibidas();
        recibidas.setTramiteId(1);
        for(SolicitudesRecibidas r : recibidas.mostrarDatos()){
            System.out.println(r.getNombreAsociado() + " " + r.getMonto());
        }
    }
    
}
