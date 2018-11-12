/*
 * La clase gestiona todo lo relacionado con la configuracion y creacion
 * de la Evaluacion de desempenio
 */
package ed.modelo;

import admin.modelo.ConexionMongo;
import com.mongodb.client.MongoCollection;
import java.util.ArrayList;
import java.util.Date;
import org.bson.Document;

public class Evaluacion {
    private final String INSTANCE = "ed_evaluacion";
    private String nombre;
    private Date fechaInicio = new Date();
    private Date fechaFin;
    private String instrucciones;
    private char estado;
    private Cualitativo cualitativo = new Cualitativo();
    private ArrayList<Ponderacion> ponderaciones = new ArrayList<>();

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getInstrucciones() {
        return instrucciones;
    }

    public void setInstrucciones(String instrucciones) {
        this.instrucciones = instrucciones;
    }

    public char getEstado() {
        return estado;
    }

    public void setEstado(char estado) {
        this.estado = estado;
    }

    public Cualitativo getCualitativo() {
        return cualitativo;
    }

    public void setCualitativo(Cualitativo cualitativo) {
        this.cualitativo = cualitativo;
    }

    public ArrayList<Ponderacion> getPonderaciones() {
        return ponderaciones;
    }

    public void setPonderaciones(ArrayList<Ponderacion> ponderaciones) {
        this.ponderaciones = ponderaciones;
    }
    
    /* Metodo utilizado para insertar un registro de evaluacion */
    public void insert() {
        MongoCollection<Document> collection = ConexionMongo.getInstance().getDatabase().getCollection(INSTANCE);
        ArrayList<Document> documentAspectos =new ArrayList<>();
        
        ArrayList<Document> documentPonderaciones = new ArrayList<>();
        for(Ponderacion p : ponderaciones) {
            documentPonderaciones.add(new Document("descripcion", p.getDescripcion())
                    .append("peso", p.getPeso()));
        }
        
        Document documento = new Document("nombre", nombre)
                .append("fechaInicio", fechaInicio)
                .append("fechaFin", fechaFin)
                .append("instrucciones", instrucciones)
                .append("estado", estado)
                .append("ponderaciones", documentPonderaciones)
                .append("cualitativo", new Document("descripcion", cualitativo.getDescripcion())
                        .append("peso", cualitativo.getPeso())
                        .append("aspectos", cualitativo.getDocumentAspectos()));
        
        collection.insertOne(documento);
    }
}
