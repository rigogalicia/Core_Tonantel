package admin.modelo;

import gc.modelo.TarjetasDeCredito;

public class Main {
    
    public static void main(String args[]){
        
        for(TarjetasDeCredito t : TarjetasDeCredito.autorizadas()){
            System.out.println(t.getNombre());
        }
        
    }
    
}
