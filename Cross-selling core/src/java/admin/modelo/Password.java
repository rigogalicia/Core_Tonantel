package admin.modelo;

public class Password {
    private String pass;
    private String mensaje;
    
    // Metodos contructores de la clase
    public Password(){}
    
    public Password(String nuevoPassword){
        this.pass = nuevoPassword;
    }
    
    // Declaracion de metodos establecer y obtener
    public String getMensaje() {
        return mensaje;
    }
    
    /*
    Este metodo revisa que el password contenga letras minusculas, mayusculas, digitos
    y caracteres especiales, de no ser asi devuelve como resultado false
    */
    private boolean isCaracteresVarios(){
        boolean result = false;
        
        if(isLetrasMinusculas()){
            if(isLetrasMayusculas()){
                if(isDigitos()){
                    if(isCaracteresEspeciales()){
                        result = true;
                    }
                }
            }
        }
        
        return result;
    }
    
    /*
    Metodo para verificar que el password contenga letras minusculas
    */
    private boolean isLetrasMinusculas(){
        boolean result = false;
        String letras = "abcdefghijklmnopqrstuvwxyzñ";
        
        for(int i = 0; i < this.pass.length(); i++){
            for(int j = 0; j < letras.length(); j++){
                if(this.pass.charAt(i) == letras.charAt(j)){
                    result = true;
                    break;
                }
            }
        }
        
        return result;
    }
    
    /*
    Metodo para verificar que el password contenga letras mayusculas
    */
    private boolean isLetrasMayusculas(){
        boolean result = false;
        String letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZÑ";
        
        for(int i = 0; i < this.pass.length(); i++){
            for(int j = 0; j < letras.length(); j++){
                if(this.pass.charAt(i) == letras.charAt(j)){
                    result = true;
                    break;
                }
            }
        }
        
        return result;
    }
    
    /*
    Metodo para verificar que el password contenga digitos
    */
    private boolean isDigitos(){
        boolean result = false;
        String letras = "0123456789";
        
        for(int i = 0; i < this.pass.length(); i++){
            for(int j = 0; j < letras.length(); j++){
                if(this.pass.charAt(i) == letras.charAt(j)){
                    result = true;
                    break;
                }
            }
        }
        
        return result;
    }
    
    /*
    Metodo para verificar que el password contenga caracteres especiales
    */
    private boolean isCaracteresEspeciales(){
        boolean result = false;
        String letras = "!@#$%^&*()-_?";
        
        for(int i = 0; i < this.pass.length(); i++){
            for(int j = 0; j < letras.length(); j++){
                if(this.pass.charAt(i) == letras.charAt(j)){
                    result = true;
                    break;
                }
            }
        }
        
        return result;
    }
    
    /*
    Este metodo verifica que el password cumpla con las especificaciones de seguridad
    devuelve true si cumple con todas las especificaciones de lo contrario devuelve false.
    */
    public boolean verificar(){
        boolean result = false;

        if(isCaracteresVarios()){
            result = true;
        }
        else{
            this.mensaje = "El password debe contener letras minusculas, mayusculas, digitos y caracteres !@#$%^&*()-_?";
        }  
        
        return result;
    }
    
    /*
    Este metodo es utilizado para generar un password que cumpla con los lineamientos de seguridad
    esto con el fin de evitar tener un password que lo puedan encontrar con el metodo de fuerza bruta
    */
    public String generar(){
        String result = "";
        String caracteres = "abcdefghijklmnopqrstuvwxyzñABCDEFGHIJKLMNOPQRSTUVWXYZÑ0123456789!@#$%^&*()-_?";
        
        while(result.length() < 8){
            result += caracteres.charAt((int) (Math.random() * caracteres.length()) + 1);
        }
        
        return result;
    }
}
