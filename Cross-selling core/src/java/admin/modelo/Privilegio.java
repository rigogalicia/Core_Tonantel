package admin.modelo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.eq;
import java.util.ArrayList;
import org.bson.Document;
import org.bson.types.ObjectId;

public class Privilegio {
    private ObjectId id;
    private String descripcion;
    private String forma;
    private String idRol;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getForma() {
        return forma;
    }

    public void setForma(String forma) {
        this.forma = forma;
    }

    public String getIdRol() {
        return idRol;
    }

    public void setIdRol(String idRol) {
        this.idRol = idRol;
    }
    
    /* Metodo para insertar un registro en documento de privilegios */
    public void insert(){
        MongoCollection<Document> coleccion = ConexionMongo.getInstance().getDatabase().getCollection("privilegios");
        Document documento = new Document("descripcion", descripcion)
                .append("forma", forma)
                .append("idRol", idRol);
        coleccion.insertOne(documento);
    }
    
    /* Metodo creado para actualizar un registro del documento de privilegios */
    public void update(){
        System.out.println("Se ejecuto el metodo de actualizar");
        MongoCollection<Document> coleccion = ConexionMongo.getInstance().getDatabase().getCollection("privilegios");
        coleccion.updateOne(eq("_id", id), 
                new Document("$set", new Document("descripcion", descripcion)
                .append("forma", forma)
                .append("idRol", idRol)));
    }
    
    /* Metodo utilizado para consultar los registro de privilegios */
    public ArrayList<Privilegio> mostrarPrivilegios(){
        ArrayList<Privilegio> resultado = new ArrayList<>();
        MongoCollection<Document> coleccion = ConexionMongo.getInstance().getDatabase().getCollection("privilegios");
        MongoCursor<Document> cursor = coleccion.find().iterator();
        
        try{
            while(cursor.hasNext()){
                Document siguiente = cursor.next();
                Privilegio p = new Privilegio();
                p.setId(siguiente.getObjectId("_id"));
                p.setDescripcion(siguiente.getString("descripcion"));
                p.setForma(siguiente.getString("forma"));
                p.setIdRol(siguiente.getString("idRol"));

                resultado.add(p);
            }
        }finally{
            cursor.close();
        }
        
        return resultado;
    }
    
    /* Metodo utilizado para consultar los registro de privilegios filtrado por rol */
    public ArrayList<Privilegio> privilegiosPorRol(){
        ArrayList<Privilegio> resultado = new ArrayList<>();
        MongoCollection<Document> coleccion = ConexionMongo.getInstance().getDatabase().getCollection("privilegios");
        MongoCursor<Document> cursor = coleccion.find(eq("idRol", idRol)).iterator();
        
        try{
            while(cursor.hasNext()){
                Document siguiente = cursor.next();
                Privilegio p = new Privilegio();
                p.setId(siguiente.getObjectId("_id"));
                p.setDescripcion(siguiente.getString("descripcion"));
                p.setForma(siguiente.getString("forma"));
                p.setIdRol(siguiente.getString("idRol"));

                resultado.add(p);
            }
        }finally{
            cursor.close();
        }
        
        return resultado;
    }
}
