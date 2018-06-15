package admin.modelo;

import gc.modelo.Chat;
import java.util.Date;

public class Main {
    
    public static void main(String args[]){
        Chat c = new Chat("869586");
        c.setUsuario("usuariob");
//        c.setNumeroSolicitud("12345");
//        c.setFecha(new Date());
//        c.setMensaje("Esta es una preuba de insert");
//        c.setNombreUsuario("Rigo Antonio Galicia Barrera");
//        c.setUsuario("todesaragb");
//        c.setEstado('a');

        System.out.println(c.mensajesNoLeidos());
    }
    
}
