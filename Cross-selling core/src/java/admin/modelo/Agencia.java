package admin.modelo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import java.util.ArrayList;
import org.bson.Document;
import org.bson.types.ObjectId;
import static com.mongodb.client.model.Filters.eq;
import java.util.regex.Pattern;

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
    
    /* Metodo utilizado para insertar un registro en la coleccion */
    public void insertar(){
        MongoCollection<Document> coleccion = ConexionMongo.getInstance().getDatabase().getCollection("agencias");
        Document doc = new Document("nombre", this.nombre);
        coleccion.insertOne(doc);
    }
    
    /* Metodo utilizado para actualizar un registro de la coleccion */
    public void update(){
        MongoCollection<Document> coleccion = ConexionMongo.getInstance().getDatabase().getCollection("agencias");
        coleccion.updateOne(eq("_id", this.id), 
                new Document("$set", new Document("nombre", this.nombre)));
    }
    
    /* Metodo utilizado para mostrar todos los registros de la colecion */
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
    
    /* Metodo utilizado para buscar un registro de la coleccion */
    public static ArrayList<Agencia> buscarAgencias(String nombreBuscar){
        ArrayList<Agencia> listaAgencias = new ArrayList<>();
        MongoCollection<Document> coleccion = ConexionMongo.getInstance().getDatabase().getCollection("agencias");
        MongoCursor<Document> cursor = coleccion.find(new Document("nombre", Pattern.compile(nombreBuscar))).iterator();
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
    
    /* Metodo utilizado para mostrar la descripcion de la agencia */
    public static String descripcionAgencia(String id){
        String result = null;
        ObjectId objid = new ObjectId(id);
        MongoCollection<Document> coleccion = ConexionMongo.getInstance().getDatabase().getCollection("agencias");
        MongoCursor<Document> cursor = coleccion.find(eq("_id", objid)).iterator();
        try{
            while(cursor.hasNext()){
                Document next = cursor.next();
                result = next.getString("nombre");
            }
        }finally{
            cursor.close();
        }
        return result;
    }
}
