package admin.modelo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import java.util.ArrayList;
import org.bson.Document;
import org.bson.types.ObjectId;
import static com.mongodb.client.model.Filters.eq;

public class Agencia {
    private ObjectId id;
    private String nombre;
    
    public Agencia(){}
    
    public Agencia(ObjectId id){
        this.id = id;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public void insertar(){
        MongoCollection<Document> coleccion = ConexionMongo.getInstance().getDatabase().getCollection("agencias");
        Document doc = new Document("nombre", this.nombre);
        coleccion.insertOne(doc);
    }
    
    public void update(){
        MongoCollection<Document> coleccion = ConexionMongo.getInstance().getDatabase().getCollection("agencias");
        coleccion.updateOne(eq("_id", this.id), 
                new Document("$set", new Document("nombre", this.nombre)));
    }
    
    public ArrayList<Agencia> mostrarAgencias(){
        ArrayList<Agencia> listaAgencias = new ArrayList<>();
        MongoCollection<Document> coleccion = ConexionMongo.getInstance().getDatabase().getCollection("agencias");
        MongoCursor<Document> cursor = coleccion.find().iterator();
        try{
            while(cursor.hasNext()){
                Document siguiente = cursor.next();
                Agencia a = new Agencia();
                a.setId(siguiente.getObjectId("_id"));
                a.setNombre(siguiente.getString("nombre"));
                listaAgencias.add(a);
            }
        }finally{
            cursor.close();
        }
        return listaAgencias;
    }
}
