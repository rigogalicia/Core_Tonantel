package admin.modelo;

import av.modelo.CrearAvaluo;
import av.modelo.Solicitud;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import patrimonio.modelo.ConexionMySql;

public class Main {
    
    public static void main(String args[]) throws ParseException{
        //System.out.println(CrearAvaluo.autorizadorResponsable("54321"));
        try{
            System.out.println("Ejecutar---------------------------------------------------------------------------------");
            Class.forName("com.mysql.jdbc.Driver");
            Connection conexion = DriverManager.getConnection(ConexionMySql.URL, ConexionMySql.USERNAME, ConexionMySql.PASSWORD);

            Statement st = conexion.createStatement();
            st.execute("DELETE FROM av_telefono WHERE asociado_cif = '123'");

            st.close();
            conexion.close();
        }
        catch(Exception e){
            e.printStackTrace(System.out);
       }
    }

}
