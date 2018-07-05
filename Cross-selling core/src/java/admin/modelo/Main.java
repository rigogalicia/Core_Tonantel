package admin.modelo;

import gc.modelo.Reasignar;

public class Main {
    
    public static void main(String args[]){
        
        for(Colaborador c : Colaborador.colaboradoresPorDepartamento(Departamento.idDepartamento("Fábrica de Créditos"))){
            System.out.println(c.getNombre());
        }
    }
    
}
