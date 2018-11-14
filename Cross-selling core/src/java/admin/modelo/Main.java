package admin.modelo;

import ed.modelo.Interrogante;

public class Main {
    
    public static void main(String args[]){
        Interrogante i = new Interrogante();
        i.setEvaluacion("Prueba ED");
        i.setConducta("Prueba Conducta");
        i.setPuesto("Prueba puesto");
        i.setLider("Si");
        i.setDescripcion("Prueba de descripcion");
        
        i.insert();
        
        for(Interrogante in : i.mostrarInterrogantes()) {
            System.out.println(in.toString());
        }
    }

}
