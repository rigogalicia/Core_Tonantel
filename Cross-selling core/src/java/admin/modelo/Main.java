package admin.modelo;

import gc.modelo.Chat;
import java.util.Date;

public class Main {
    
    public static void main(String args[]){
        
        Colaborador c = new Colaborador();
        c.setOperador(1);
        c = c.datosColaborador();
        
        System.out.println(c.toString());
    }
    
}
