package cc.modelo;

import admin.modelo.Agencia;
import admin.modelo.Colaborador;
import admin.modelo.ConexionMongo;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.eq;
import java.util.ArrayList;
import org.bson.Document;
import org.bson.types.ObjectId;

public class Autorizadores {
    private ObjectId id;
    private String agencia;
    private String usuario;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
    /* Metodo para insertar un nuevo registro de autorizadores */
    public void insert(){
        MongoCollection<Document> coleccion = ConexionMongo.getInstance().getDatabase().getCollection("autorizadores");
        Document doc = new Document("agencia", agencia)
                .append("usuario", usuario);
        coleccion.insertOne(doc);
    }
    
    /* Metodo para actualizar un registro de la collecion autorizadores */
    public void update(){
        MongoCollection<Document> coleccion = ConexionMongo.getInstance().getDatabase().getCollection("autorizadores");
        coleccion.updateOne(eq("_id", id), 
                new Document("$set", new Document("agencia", agencia)
                .append("usuario", usuario)));
    }
    
    /* Metodo utilizado para consultar los datos de autorizadores */
    public ArrayList<Autorizadores> mostrarAutorizadores(){
        ArrayList<Autorizadores> listaAutorizadores = new ArrayList<>();
        MongoCollection<Document> coleccion = ConexionMongo.getInstance().getDatabase().getCollection("autorizadores");
        MongoCursor<Document> cursor = coleccion.find().iterator();
        try{
            while(cursor.hasNext()){
                Document next = cursor.next();
                Autorizadores a = new Autorizadores();
                a.setId(next.getObjectId("_id"));
                a.setAgencia(Agencia.descripcionAgencia(next.getString("agencia")));
                a.setUsuario(Colaborador.datosColaborador(next.getString("usuario")).getNombre());
                listaAutorizadores.add(a);
            }
        }finally{
            cursor.close();
        }
        return listaAutorizadores;
    }
    
    /* Metodo utilizado para consultar los datos de autorizadores por agencia */
    public ArrayList<Autorizadores> mostrarAutorizadores(String ag){
        ArrayList<Autorizadores> listaAutorizadores = new ArrayList<>();
        MongoCollection<Document> coleccion = ConexionMongo.getInstance().getDatabase().getCollection("autorizadores");
        MongoCursor<Document> cursor = coleccion.find(eq("agencia", ag)).iterator();
        try{
            while(cursor.hasNext()){
                Document next = cursor.next();
                Autorizadores a = new Autorizadores();
                a.setId(next.getObjectId("_id"));
                a.setAgencia(Agencia.descripcionAgencia(next.getString("agencia")));
                a.setUsuario(Colaborador.datosColaborador(next.getString("usuario")).getCorreo());
                listaAutorizadores.add(a);
            }
        }finally{
            cursor.close();
        }
        return listaAutorizadores;
    }
}
