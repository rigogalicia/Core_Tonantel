package admin.modelo;

import gc.modelo.Monitoreo;
import java.util.ArrayList;

public class Main {
    
    public static void main(String args[]){
        
        Monitoreo m = new Monitoreo();
        m.setIdAgencia("5af9aa316e94b90397e18cfc");
        ArrayList<Monitoreo> result = m.consultar();
        for(Monitoreo mon : result){
            System.out.println(mon.getNumeroSolicitud());
        }
    }
    
}
