package admin.modelo;

import gc.modelo.SolicitudesEnproceso;

public class Main {
    
    public static void main(String args[]){
        SolicitudesEnproceso se = new SolicitudesEnproceso();
        se.setUserConect("todesaragb");
        se.setTramiteId(1);
        for(SolicitudesEnproceso s : se.mostrarDatos()){
            System.out.println(s.getNombreAsociado());
        }
    }
    
}
