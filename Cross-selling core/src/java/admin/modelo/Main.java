package admin.modelo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    
    public static void main(String args[]) throws ParseException{
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
        Date fechaFutura = formatoFecha.parse("16-11-2018");
        Date fechaActual = new Date();
        System.out.println(fechaFutura.toString());
        System.out.println(fechaFutura.compareTo(fechaActual));
    }

}
