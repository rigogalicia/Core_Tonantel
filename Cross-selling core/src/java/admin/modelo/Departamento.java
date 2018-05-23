package admin.modelo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import java.util.ArrayList;
import org.bson.Document;
import org.bson.types.ObjectId;
import static com.mongodb.client.model.Filters.eq;
import java.util.regex.Pattern;

public class Departamento {
    private ObjectId id;
    private String nombre;

    public Departamento(){}
    
    public Departamento(ObjectId id){
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
        MongoCollection<Document> coleccion = ConexionMongo.getInstance().getDatabase().getCollection("departamentos");
        Document doc = new Document("nombre", this.nombre);
        coleccion.insertOne(doc);
    }
    
    public void update(){
        MongoCollection<Document> coleccion = ConexionMongo.getInstance().getDatabase().getCollection("departamentos");
        coleccion.updateOne(eq("_id", this.id), 
                new Document("$set", new Document("nombre", this.nombre)));
    }
    
    public ArrayList<Departamento> mostrarDepartamentos(){
        ArrayList<Departamento> listaDepartamentos = new ArrayList<>();
        MongoCollection<Document> coleccion = ConexionMongo.getInstance().getDatabase().getCollection("departamentos");
        MongoCursor<Document> cursor = coleccion.find().iterator();
        try{
            while(cursor.hasNext()){
                Document siguiente = cursor.next();
                Departamento d = new Departamento();
                d.setId(siguiente.getObjectId("_id"));
                d.setNombre(siguiente.getString("nombre"));
                listaDepartamentos.add(d);
            }
        }finally{
            cursor.close();
        }
        return listaDepartamentos;
    }
    
    /* Metodo utilizado para buscar un registro de la coleccion */
    public static ArrayList<Departamento> buscarDepartamentos(String nombreBuscar){
       ArrayList<Departamento> listaDepartamentos = new ArrayList<>();
       MongoCollection<Document> collection = ConexionMongo.getInstance().getDatabase().getCollection("departamentos");
       MongoCursor<Document> cursor = collection.find(new Document("nombre", Pattern.compile(nombreBuscar))).iterator();
        try {
            while (cursor.hasNext()) {
                Document siguiente = cursor.next();
                Departamento d = new Departamento();
                d.setId(siguiente.getObjectId("_Id"));
                d.setNombre(siguiente.getString("nombre"));
                listaDepartamentos.add(d);
            }
            
        } 
        finally{
            cursor.close();
        }
       
       
       return listaDepartamentos;
    }
}
