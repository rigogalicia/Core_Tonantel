package admin.modelo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.eq;
import java.util.ArrayList;
import java.util.regex.Pattern;
import org.bson.Document;
import org.bson.types.ObjectId;

public class Puesto {
    private ObjectId id;
    private String nombre;
    private String idDepartamento;

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

    public String getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(String idDepartamento) {
        this.idDepartamento = idDepartamento;
    }
    
    public void insertar(){
        MongoCollection<Document> coleccion = ConexionMongo.getInstance().getDatabase().getCollection("puestos");
        Document doc = new Document("nombre", this.nombre)
                .append("idDepartamento", this.idDepartamento);
        coleccion.insertOne(doc);
    }
    
    public void update(){
        MongoCollection<Document> coleccion = ConexionMongo.getInstance().getDatabase().getCollection("puestos");
        coleccion.updateOne(eq("_id", this.id), 
                new Document("$set", new Document("nombre", this.nombre)
                .append("idDepartamento", this.idDepartamento)));
    }
    
    public ArrayList<Puesto> mostrarPuesto(){
        ArrayList<Puesto> listaPuestos = new ArrayList<>();
        MongoCollection<Document> coleccion = ConexionMongo.getInstance().getDatabase().getCollection("puestos");
        MongoCursor<Document> cursor = coleccion.find().iterator();
        try{
            while(cursor.hasNext()){
                Document siguiente = cursor.next();
                Puesto p = new Puesto();
                p.setId(siguiente.getObjectId("_id"));
                p.setNombre(siguiente.getString("nombre"));
                p.setIdDepartamento(siguiente.getString("idDepartamento"));
                listaPuestos.add(p);
            }
        }finally{
            cursor.close();
        }
        return listaPuestos;
    }
    
    public ArrayList<Puesto> puestosPorDepartamento(){
        ArrayList<Puesto> listaPuestos = new ArrayList<>();
        MongoCollection<Document> coleccion = ConexionMongo.getInstance().getDatabase().getCollection("puestos");
        MongoCursor<Document> cursor = coleccion.find(eq("idDepartamento", idDepartamento)).iterator();
        try{
            while(cursor.hasNext()){
                Document siguiente = cursor.next();
                Puesto p = new Puesto();
                p.setId(siguiente.getObjectId("_id"));
                p.setNombre(siguiente.getString("nombre"));
                p.setIdDepartamento(siguiente.getString("idDepartamento"));
                listaPuestos.add(p);
            }
        }finally{
            cursor.close();
        }
        return listaPuestos;
    }
    
    /*Metodo utilizado para filtrar registros de la collecion*/
    public static ArrayList<Puesto> buscarPuestos(String nombreBuscar){
       ArrayList<Puesto> listaPuestos = new ArrayList<>();
       MongoCollection<Document> collection = ConexionMongo.getInstance().getDatabase().getCollection("puestos");
       MongoCursor<Document> cursor = collection.find(new Document("nombre", Pattern.compile(nombreBuscar))).iterator();
        try {
            while (cursor.hasNext()) {
                Document siguiente = cursor.next();
                Puesto p = new Puesto();
                p.setId(siguiente.getObjectId("_id"));
                p.setNombre(siguiente.getString("nombre"));
                p.setIdDepartamento(siguiente.getString("idDepartamento"));
                listaPuestos.add(p);
            }
            
        } 
        finally{
            cursor.close();
        }
       return listaPuestos;
    }
    
        /* Metodo que devuelve el id del departamento, consultado por nombre */
    public static String idPuesto(String nombrePuesto){
        String result = "";
        MongoCollection<Document> collection = ConexionMongo.getInstance().getDatabase().getCollection("puestos");
        MongoCursor<Document> cursor = collection.find(eq("nombre", nombrePuesto)).iterator();
        try {
            while (cursor.hasNext()) {
                Document next = cursor.next();
                result = next.getObjectId("_id").toString();
            }
        }
        finally{
            cursor.close();
        }
        return result;
    }
    
    
}
