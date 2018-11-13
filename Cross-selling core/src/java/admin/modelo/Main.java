package admin.modelo;

import av.controlador.AvCrearAvaluoBean;
import av.controlador.AvRecibidasBean;
import av.modelo.CrearAvaluo;
import av.modelo.Solicitud;
import dao.AvSolicitud;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import patrimonio.modelo.ConexionMySql;

public class Main {
    
    public static void main(String args[]) throws ParseException{
        CrearAvaluo a = new CrearAvaluo();

        AvCrearAvaluoBean v = new AvCrearAvaluoBean();
        AvRecibidasBean r = new AvRecibidasBean();
        AvSolicitud s = new AvSolicitud();

  

        
    }

}
