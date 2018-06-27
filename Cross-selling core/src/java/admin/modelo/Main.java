package admin.modelo;

import dao.GcSolicitud;
import gc.controlador.Correo;
import gc.modelo.Chat;
import gc.modelo.Solicitud;
import java.util.Date;

public class Main {
    
    public static void main(String args[]){
        
        Correo c = new Correo("rigo.galicia@cooperativa-tonantel.com.gt", "Correo de Prueba", "Este es un correo de prueba");
        c.enviar();
        
    }
    
}
