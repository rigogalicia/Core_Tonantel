/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.modelo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.eq;
import org.apache.commons.codec.digest.DigestUtils;
import org.bson.Document;

/**
 *
 * @author Desarrollo
 */
public class Resetear {
    private Colaborador colaborador = new Colaborador();
    public String nuevaClave;
    public String error;

    public Colaborador getColaborador() {
        return colaborador;
    }

    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }

    public String getNuevaClave() {
        return nuevaClave;
    }

    public void setNuevaClave(String nuevaClave) {
        this.nuevaClave = nuevaClave;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
   
    /*Metodo utilizado para consultar Datos de usuario para Resetear*/
    public void ConsultarDatos(){
        MongoCollection<Document> collection = ConexionMongo.getInstance().getDatabase().getCollection("colaboradores");
        MongoCursor<Document> cursor = collection.find(eq("operador", colaborador.getOperador())).iterator();
        try {
            while (cursor.hasNext()) {
                Document next = cursor.next();
                colaborador.setUsuario(next.getString("_id"));
                colaborador.setClave(next.getString("clave"));
                colaborador.setNombre(next.getString("nombre"));
                colaborador.setCorreo(next.getString("correo"));
                colaborador.setOperador(next.getInteger("operador"));
                colaborador.setAgencia(next.getString("agencia"));
                colaborador.setDepartamento(next.getString("departamento"));
                colaborador.setPuesto(next.getString("puesto"));
            }
        } finally{
            cursor.close();
        }
    }
    
    /*Metodo utilizado para Resetear Clave*/
    public void update(){
        Password p = new Password(nuevaClave);
        if(p.verificar()){
            MongoCollection<Document> coleccion = ConexionMongo.getInstance().getDatabase().getCollection("colaboradores");
            coleccion.updateOne(eq("operador", colaborador.getOperador()), 
                    new Document("$set", new Document("clave", DigestUtils.md5Hex (nuevaClave))));
        }
        else{
            error = "El password debe contener letras minusculas, mayusculas, digitos y caracteres !@#$%^&*()-_? ";
        }
    }
   
}

