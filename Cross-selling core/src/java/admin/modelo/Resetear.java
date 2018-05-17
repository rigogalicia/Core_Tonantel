/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.modelo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.eq;
import java.util.ArrayList;
import org.bson.Document;

/**
 *
 * @author Desarrollo
 */
public class Resetear {
    private int operador;
    private String usuario;
    private String clave;
    

    public int getOperador() {
        return operador;
    }

    public void setOperador(int operador) {
        this.operador = operador;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
    
    
    /*Metodo utilizado para consultar Datos de usuario para Resetear*/
    public ArrayList<Colaborador> ConsultarDatos(){
        ArrayList<Colaborador> list= new ArrayList<>();
        MongoCollection<Document> collection = ConexionMongo.getInstance().getDatabase().getCollection("colaboradores");
        MongoCursor<Document> cursor = collection.find(eq("operador", operador)).iterator();
        try {
            while (cursor.hasNext()) {
                Document siguiente = cursor.next();
                Colaborador c = new Colaborador();
                c.setOperador(siguiente.getInteger("operador"));
                c.setUsuario(siguiente.getString("usuario"));
                c.setClave(siguiente.getString("clave"));
                
            }
        } finally{
            cursor.close();
        }
       return list;
    }

   
}

