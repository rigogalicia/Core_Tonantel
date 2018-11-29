/* 
    Clase para administrar las interrogantes de la evaluacion
*/
package ed.modelo;

import admin.modelo.ConexionMongo;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import java.util.ArrayList;
import org.bson.Document;
import org.bson.types.ObjectId;

public class Interrogante {
    private final String COLLECTION_NAME = "ed_interrogantes";
    private ObjectId id;
    private String evaluacion = "";
    private String conducta = "";
    private String puesto = "";
    private String lider = "";
    private String descripcion = "";

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(String evaluacion) {
        this.evaluacion = evaluacion;
    }

    public String getConducta() {
        return conducta;
    }

    public void setConducta(String conducta) {
        this.conducta = conducta;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public String getLider() {
        return lider;
    }

    public void setLider(String lider) {
        this.lider = lider;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    /* Metodo para insertar un registro en interrogante */
    public void insert() {
        MongoCollection<Document> collection = ConexionMongo.getInstance().getDatabase().getCollection(COLLECTION_NAME);
        
        Document documentInterrogante = new Document("evaluacion", this.evaluacion)
                .append("conducta", this.conducta)
                .append("puesto", this.puesto)
                .append("lider", this.lider)
                .append("descripcion", this.descripcion);
        
        collection.insertOne(documentInterrogante);
    }
    
    /* Este metodo se utiliza para obtener todos los registros de interrogantes */
    public ArrayList<Interrogante> mostrarInterrogantes() {
        ArrayList<Interrogante> result = new ArrayList<>();
        MongoCollection<Document> collection = ConexionMongo.getInstance().getDatabase().getCollection(COLLECTION_NAME);
        
        MongoCursor<Document> cursor = collection.find().iterator();
        try {
            while(cursor.hasNext()) {
                Document next = cursor.next();
                Interrogante i = new Interrogante();
                i.setId(next.getObjectId("_id"));
                i.setEvaluacion(next.getString("evaluacion"));
                i.setConducta(next.getString("conducta"));
                i.setPuesto(next.getString("puesto"));
                i.setLider(next.getString("lider"));
                i.setDescripcion(next.getString("descripcion"));
                
                result.add(i);
            }
        } finally {
            cursor.close();
        }
        
        return result;
    }
    
    /* Este metodo se utiliza para obtener todos los registros de interrogantes */
    public ArrayList<Interrogante> mostrarInterrogantesFiller() {
        ArrayList<Interrogante> result = new ArrayList<>();
        MongoCollection<Document> collection = ConexionMongo.getInstance().getDatabase().getCollection(COLLECTION_NAME);
        
        MongoCursor<Document> cursor = collection.find(and(eq("evaluacion", this.evaluacion), and(eq("conducta", this.conducta), and(eq("puesto", this.puesto), eq("lider", this.lider))))).iterator();
        try {
            while(cursor.hasNext()) {
                Document next = cursor.next();
                Interrogante i = new Interrogante();
                i.setId(next.getObjectId("_id"));
                i.setEvaluacion(next.getString("evaluacion"));
                i.setConducta(next.getString("conducta"));
                i.setPuesto(next.getString("puesto"));
                i.setLider(next.getString("lider"));
                i.setDescripcion(next.getString("descripcion"));
                
                result.add(i);
            }
        } finally {
            cursor.close();
        }
        
        return result;
    }
    
    /* Este metodo elimina un registro de interrogante */
    public void delete(Interrogante i) {
        MongoCollection<Document> collection = ConexionMongo.getInstance().getDatabase().getCollection(COLLECTION_NAME);
        collection.deleteOne(eq("_id", i.getId()));
    }
    
    /* Metodo que representa al objeto en un String */
    @Override
    public String toString() {
        return "Interrogante{" + "COLLECTION_NAME=" + COLLECTION_NAME + ", id=" + id + ", evaluacion=" 
                + evaluacion + ", conducta=" + conducta + ", puesto=" + puesto + ", lider=" 
                + lider + ", descripcion=" + descripcion + '}';
    }
    
}
