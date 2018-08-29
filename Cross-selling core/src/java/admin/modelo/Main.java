package admin.modelo;

import java.util.ArrayList;

public class Main {
    
    public static void main(String args[]){
        
        ArrayList<String> telefonos = new ArrayList<>();
        
        String t1 = "1";
        String t2 = "2";
        String t3 = "3";
        
        telefonos.add(t1);
        telefonos.add(t2);
        telefonos.add(t3);
        
        if(t1.equals(t2)){
            System.out.println("Los objetos son iguales");
        }
        else{
            System.out.println("Los objetos no son iguales");
        }
        
        telefonos.remove(t3);
        
        telefonos.forEach((t) -> {
            System.out.println(t);
        });
    }

}
