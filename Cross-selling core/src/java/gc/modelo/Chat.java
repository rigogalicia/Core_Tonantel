package gc.modelo;

import admin.modelo.ConexionMongo;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.eq;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import org.bson.Document;
import org.bson.types.ObjectId;

public class Chat {
    private ObjectId id;
    private String numeroSolicitud;
    private Date fecha;
    private String fechaFormateada;
    private String mensaje;
    private String nombreUsuario;
    private String usuario;
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

    public String getFechaFormateada() {
        return fechaFormateada;
    }

    public void setFechaFormateada(String fechaFormateada) {
        this.fechaFormateada = fechaFormateada;
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
                .append("estado", estado);
        
        coleccion.insertOne(doc);
    }
    
    /* Metodo utilizado para marcar los mensajes como leidos */
    public void marcarComoLeidos(){
        MongoCollection<Document> coleccion = ConexionMongo.getInstance().getDatabase().getCollection("chatgc");
        
        BasicDBObject filtro = new BasicDBObject();
        filtro.put("numeroSolicitud", numeroSolicitud);
        filtro.put("usuario", new BasicDBObject("$ne", usuario));
        filtro.put("estado", "a");
        
        coleccion.updateMany(filtro, new Document("$set", new Document("estado", 'b')));
    }
    
    /* Metodo utilizado para consultar los mensajes enviados por solicitud */
    public ArrayList<Chat> mostrarMensajes(){
        ArrayList<Chat> result = new ArrayList<>();
        MongoCollection<Document> coleccion = ConexionMongo.getInstance().getDatabase().getCollection("chatgc");
        MongoCursor<Document> cursor = coleccion.find(eq("numeroSolicitud", numeroSolicitud)).iterator();
        
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd 'de' MMMM h:mm a", new Locale("es"));
        
        try{
            while(cursor.hasNext()){
                Document next = cursor.next();
                Chat c = new Chat();
                c.setNumeroSolicitud(next.getString("numeroSolicitud"));
                c.setFecha(next.getDate("fecha"));
                c.setFechaFormateada(formatoFecha.format(next.getDate("fecha")));
                c.setMensaje(next.getString("mensaje"));
                c.setNombreUsuario(next.getString("nombreUsuario"));
                c.setUsuario(next.getString("usuario"));
                c.setEstado(next.getString("estado").toCharArray()[0]);
                result.add(c);
            }
        }
        finally{
            cursor.close();
        }
        
        return result;
    }
    
    /* Metodo utilizado para mostrar la cantidad de mensajes no leidos */
    public int mensajesNoLeidos(){
        int result = 0;
        MongoCollection<Document> coleccion = ConexionMongo.getInstance().getDatabase().getCollection("chatgc");
        
        BasicDBObject filtro = new BasicDBObject();
        filtro.put("numeroSolicitud", numeroSolicitud);
        filtro.put("usuario", new BasicDBObject("$ne", usuario));
        filtro.put("estado", "a");
        
        result = (int) coleccion.count(filtro);
        
        return result;
    }
    
    /* Este metodo devuelve el nombre del colaborador */
    public String nombreColaborador(String usuarioColaborador){
        String result = null;
        MongoCollection<Document> coleccion = ConexionMongo.getInstance().getDatabase().getCollection("colaboradores");
        MongoCursor<Document> cursor = coleccion.find(eq("_id", usuarioColaborador)).iterator();
        
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
