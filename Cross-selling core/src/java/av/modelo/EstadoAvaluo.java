package av.modelo;

public class EstadoAvaluo {
    
    // Metodo para convertir estados
    public static String convert(char est){
        String result = new String();
        
        switch(est){
            case 'a':
                result = "Avalúo Solicitado";
                break;
                
            case 'b':
                result = "Avalúo en Proceso";
                break;
                
            case 'c':
                result = "Avalúo Realizado";
                break;
                
            case 'd':
                result = "Avalúo Autorizado";
                break;
                
            case 'e':
                result = "Avalúo Rechazado";
                break;
        }
        
        return result;
    }
    
}
