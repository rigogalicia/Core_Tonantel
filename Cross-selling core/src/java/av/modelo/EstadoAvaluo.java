package av.modelo;

public class EstadoAvaluo {
    
    // Metodo para convertir estados
    public static String convert(char est){
        String result = new String();
        
        switch(est){
            case 'a':
                result = "Avaluo Solicitado";
                break;
                
            case 'b':
                result = "Avaluo en Proceso";
                break;
                
            case 'c':
                result = "Avaluo Realizado";
                break;
                
            case 'd':
                result = "Avaluo Autorizado";
                break;
                
            case 'e':
                result = "Avaluo Rechazado";
                break;
        }
        
        return result;
    }
    
}
