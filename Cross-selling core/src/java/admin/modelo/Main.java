package admin.modelo;

import av.modelo.SolicitudesEnproceso;

public class Main {
    
    public static void main(String args[]){
        SolicitudesEnproceso enProceso = new SolicitudesEnproceso();

        enProceso.setUserConect("tocomenpm");
        //enProceso.setEst('b');
        enProceso.mostrarDatos();
       
    }

}
