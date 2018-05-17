package admin.modelo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.eq;
import java.util.ArrayList;
import java.util.regex.Pattern;
import org.bson.Document;
import org.bson.types.ObjectId;

public class Rol {
    private ObjectId id;
    private String descripcion;
    private String forma;

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
    
    /* Metodo utilizado para insertar in registro de roles */
    public void insert(){
        MongoCollection<Document> coleccion = ConexionMongo.getInstance().getDatabase().getCollection("roles");
        Document documento = new Document("descripcion", this.descripcion)
                .append("forma", this.forma);
        coleccion.insertOne(documento);
    }
    
    /* Metodo utilizado para actualizar un registro del documento de roles */
    public void update(){
        MongoCollection<Document> coleccion = ConexionMongo.getInstance().getDatabase().getCollection("roles");
        coleccion.updateOne(eq("_id", this.id), 
                new Document("$set", new Document("descripcion", this.descripcion)
                .append("forma", this.forma)));
    }
    
    /* Metodo utilizado para mostrar los registros de roles */
    public ArrayList<Rol> mostrarRoles(){
        ArrayList<Rol> resultado = new ArrayList<>();
        MongoCollection<Document> coleccion = ConexionMongo.getInstance().getDatabase().getCollection("roles");
        MongoCursor<Document> cursor = coleccion.find().iterator();
        try{
            while(cursor.hasNext()){
                Document siguiente = cursor.next();
                Rol r = new Rol();
                r.setId(siguiente.getObjectId("_id"));
                r.setDescripcion(siguiente.getString("descripcion"));
                r.setForma(siguiente.getString("forma"));
                resultado.add(r);
            }
        }finally{
            cursor.close();
        }
        return resultado;
    }
    /* Metodo utilizado para filtrar registros de la coleccion*/
    public static ArrayList<Rol> buscarRoles(String nombreBuscar){
        ArrayList<Rol> listaRoles = new ArrayList<>();
        MongoCollection<Document> collection = ConexionMongo.getInstance().getDatabase().getCollection("roles");
        MongoCursor<Document> cursor = collection.find(new Document("descripcion", Pattern.compile(nombreBuscar))).iterator();
        try {
            while (cursor.hasNext()) {
                Document siguiente = cursor.next();
                Rol r = new Rol();
                r.setId(siguiente.getObjectId("_id"));
                r.setDescripcion(siguiente.getString("descripcion"));
                r.setForma(siguiente.getString("forma"));
                listaRoles.add(r);  
            }
        }finally{
            cursor.close();
        }
     
        return listaRoles;
    }
    
}
