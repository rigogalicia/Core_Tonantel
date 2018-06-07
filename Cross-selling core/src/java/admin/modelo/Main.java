package admin.modelo;

import gc.modelo.DetalleSolicitud;
import gc.modelo.SolicitudesEnproceso;

public class Main {
    
    public static void main(String args[]){
        
        DetalleSolicitud d = new DetalleSolicitud("54321");
        d.consultarDatos();
//        System.out.println(d.getNombre());
    }
    
}
