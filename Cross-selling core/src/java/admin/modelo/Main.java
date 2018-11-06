package admin.modelo;


public class Main {
    
    public static void main(String args[]){
        Colaborador c = new Colaborador();
        for(Colaborador col : c.mostrarColaboradores()){
            System.out.println(col.getNombre());
        }
    }

}
