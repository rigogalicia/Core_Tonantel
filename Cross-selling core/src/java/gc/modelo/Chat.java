package gc.modelo;

import admin.modelo.ConexionMongo;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.eq;
import java.util.ArrayList;
import java.util.Date;
import org.bson.Document;
import org.bson.types.ObjectId;

public class Chat {
    private ObjectId id;
    private String numeroSolicitud;
    private Date fecha;
    private String mensaje;
    private String nombreUsuario;
    private String usuario;
    private String receptor;
    private char estado;
    
    public Chat(){}
    
    public Chat(String numeroSolicitud){
        this.numeroSolicitud = numeroSolicitud;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getNumeroSolicitud() {
        return numeroSolicitud;
    }

    public void setNumeroSolicitud(String numeroSolicitud) {
        this.numeroSolicitud = numeroSolicitud;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getReceptor() {
        return receptor;
    }

    public void setReceptor(String receptor) {
        this.receptor = receptor;
    }

    public char getEstado() {
        return estado;
    }

    public void setEstado(char estado) {
        this.estado = estado;
    }
    
    /* Metodo utilizado para enviar un mensaje */
    public void enviarMensaje(){
        MongoCollection<Document> coleccion = ConexionMongo.getInstance().getDatabase().getCollection("chatgc");
        
        Document doc = new Document("numeroSolicitud", numeroSolicitud)
                .append("fecha", fecha)
                .append("mensaje", mensaje)
                .append("nombreUsuario", nombreUsuario)
                .append("usuario", usuario)
                .append("receptor", receptor)
                .append("estado", estado);
        
        coleccion.insertOne(doc);
    }
    
    /* Metodo utilizado para consultar los mensajes enviados por solicitud */
    public ArrayList<Chat> mostrarMensajes(){
        ArrayList<Chat> result = new ArrayList<>();
        MongoCollection<Document> coleccion = ConexionMongo.getInstance().getDatabase().getCollection("chatgc");
        MongoCursor<Document> cursor = coleccion.find(eq("numeroSolicitud", numeroSolicitud)).iterator();
        try{
            while(cursor.hasNext()){
                Document next = cursor.next();
                Chat c = new Chat();
                c.setNumeroSolicitud(next.getString("numeroSolicitud"));
                c.setFecha(next.getDate("fecha"));
                c.setMensaje(next.getString("mensaje"));
                c.setNombreUsuario(next.getString("nombreUsuario"));
                c.setUsuario(next.getString("usuario"));
                c.setReceptor(next.getString("receptor"));
                c.setEstado(next.getString("estado").toCharArray()[0]);
                result.add(c);
            }
        }
        finally{
            cursor.close();
        }
        
        return result;
    }
}
