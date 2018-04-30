package admin.modelo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import java.util.ArrayList;
import org.bson.Document;
import org.bson.types.ObjectId;
import static com.mongodb.client.model.Filters.eq;

public class Puesto {
    private ObjectId id;
    private String descripcion;
    private Departamento idDepartamento;
    
    public Puesto(){}
    
    public Puesto(ObjectId id){
        this.id = id;
    }

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

    public Departamento getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(Departamento idDepartamento) {
        this.idDepartamento = idDepartamento;
    }
    
    public void insert(){
        MongoCollection<Document> coleccion = ConexionMongo.getInstance().getDatabase().getCollection("puestos");
        Document doc = new Document("descripcion", this.descripcion)
                .append("_idDepartamento", idDepartamento.getId());
        coleccion.insertOne(doc);
    }
    
    public void update(){
        MongoCollection<Document> coleccion = ConexionMongo.getInstance().getDatabase().getCollection("puestos");
        coleccion.updateOne(eq("_id", this.id), 
                new Document("$set", new Document("descripcion", this.descripcion)
                        .append("_idDeparamento", this.idDepartamento.getId())));
    }
    
    public ArrayList<Puesto> mostrarPuestos(){
        ArrayList<Puesto> listaPuesto = new ArrayList<>();
        MongoCollection<Document> coleccion = ConexionMongo.getInstance().getDatabase().getCollection("puestos");
        MongoCursor<Document> cursor = coleccion.find().iterator();
        try{
            while(cursor.hasNext()){
                Document siguiente = cursor.next();
                Puesto p = new Puesto();
                p.setId(siguiente.getObjectId("_id"));
                p.setDescripcion(siguiente.getString("descripcion"));
                Departamento deparamento = new Departamento();
                deparamento.setId(siguiente.getObjectId("_idDepartamento"));
                p.setIdDepartamento(deparamento);
                listaPuesto.add(p);
            }
        }finally{
            cursor.close();
        }
        return listaPuesto;
    }
}
