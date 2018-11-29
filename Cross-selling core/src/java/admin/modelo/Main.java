package admin.modelo;

import ed.modelo.Interrogante;

public class Main {
    
    public static void main(String args[]){
        Interrogante i = new Interrogante();
        i.setConducta("Conductas Genéricas");
        i.setPuesto("");
        i.setLider("");
        
//        i.insert();
        
        for(Interrogante in : i.mostrarInterrogantesFiller()) {
            System.out.println(in.getDescripcion());
        }
    }

}
