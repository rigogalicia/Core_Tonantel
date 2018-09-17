package admin.modelo;

import gc.modelo.Negociacion;

public class Main {
    
    public static void main(String args[]){
        Negociacion n = new Negociacion();
        n.setEstado('a');
        n.setIdAgencia("5b7731ad6d1e8f0416a60557");
        
        for(Negociacion neg : n.consultarDatos()){
            System.out.println(neg.getAsesorFinanciero());
        }
    }

}
