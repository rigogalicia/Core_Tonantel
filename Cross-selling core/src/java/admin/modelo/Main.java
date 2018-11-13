package admin.modelo;

import ed.modelo.Aspecto;
import ed.modelo.Conducta;
import ed.modelo.Cualitativo;
import ed.modelo.Evaluacion;
import ed.modelo.Ponderacion;


public class Main {
    
    public static void main(String args[]){
        Evaluacion e = new Evaluacion();
        for(Evaluacion ev : e.mostrarEvaluaciones()) {
            System.out.println(ev.getNombre());
            System.out.println(ev.getFechaInicio());
            System.out.println(ev.getFechaFin());
            System.out.println(ev.getInstrucciones());
            System.out.println(ev.getEstado());
            
            for(Ponderacion p : ev.getPonderaciones()) {
                System.out.println("\t" + p.getDescripcion() + " " + p.getPeso());
            }
            
            Cualitativo cualitativo = ev.getCualitativo();
            System.out.println(cualitativo.getDescripcion() + " " + cualitativo.getPeso());
            
            for(Aspecto as : cualitativo.getAspectos()) {
                System.out.println("\t" + as.getDescripcion() + " " + as.getPeso());
                for(Conducta con : as.getConductas()) {
                    System.out.println("\t\t" + con.getDescripcion() + " " + con.getPeso());
                }
            }
        }
    }

}
