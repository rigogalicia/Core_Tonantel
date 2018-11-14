/*
 * La clase gestiona todo lo relacionado con la configuracion y creacion
 * de la Evaluacion de desempenio
 */
package ed.modelo;

import admin.modelo.ConexionMongo;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Sorts;
import java.util.ArrayList;
import java.util.Date;
import org.bson.Document;
import org.bson.types.ObjectId;

public class Evaluacion {
    private final String COLLECTION_NAME = "ed_evaluacion";
    private ObjectId id;
    private String nombre;
    private Date fechaInicio = new Date();
    private Date fechaFin;
    private String instrucciones;
    private String estado;
    private ArrayList<Ponderacion> ponderaciones = new ArrayList<>();
    private Cualitativo cualitativo = new Cualitativo();

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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
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
        MongoCollection<Document> collection = ConexionMongo.getInstance().getDatabase().getCollection(COLLECTION_NAME);
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
    
    /* Metodo para consultar el listado de las evaluaciones creadas */
    public ArrayList<Evaluacion> mostrarEvaluaciones() {
        ArrayList<Evaluacion> result = new ArrayList<>();
        MongoCollection<Document> collection = ConexionMongo.getInstance().getDatabase().getCollection(COLLECTION_NAME);
        MongoCursor<Document> cursor = collection.find().sort(Sorts.orderBy(Sorts.descending("fechaInicio"))).iterator();
        
        try {
            while(cursor.hasNext()) {
                Document next = cursor.next();
                Evaluacion e = new Evaluacion();
                e.setId(next.getObjectId("_id"));
                e.setNombre(next.getString("nombre"));
                e.setFechaInicio(next.getDate("fechaInicio"));
                e.setFechaFin(next.getDate("fechaFin"));
                e.setInstrucciones(next.getString("instrucciones"));
                e.setEstado(next.getString("estado"));
                
                ArrayList<Document> listPonderaciones = (ArrayList<Document>) next.get("ponderaciones");
                ArrayList<Ponderacion> misPonderaciones = new ArrayList<>();
                if(listPonderaciones != null) {
                    for(Document docPonderaciones : listPonderaciones) {
                        Ponderacion ponderacion = new Ponderacion();
                        ponderacion.setDescripcion(docPonderaciones.getString("descripcion"));
                        ponderacion.setPeso(docPonderaciones.getInteger("peso"));
                        misPonderaciones.add(ponderacion);
                    }
                }
                e.setPonderaciones(misPonderaciones);
                
                Document documentCualitativo = (Document) next.get("cualitativo");
                Cualitativo resultCualitativo = new Cualitativo();
                resultCualitativo.setDescripcion(documentCualitativo.getString("descripcion"));
                resultCualitativo.setPeso(documentCualitativo.getInteger("peso"));
                
                ArrayList<Document> listAspectos = (ArrayList<Document>) documentCualitativo.get("aspectos");
                ArrayList<Aspecto> misAspectos = new ArrayList<>();
                if(listAspectos != null) {
                    for(Document docAspectos : listAspectos) {
                        Aspecto aspecto = new Aspecto();
                        aspecto.setDescripcion(docAspectos.getString("descripcion"));
                        aspecto.setPeso(docAspectos.getInteger("peso"));
                        
                        ArrayList<Document> listConductas = (ArrayList<Document>) docAspectos.get("conductas");
                        ArrayList<Conducta> misConductas = new ArrayList<>();
                        if(listConductas != null) {
                            for(Document docConducta : listConductas) {
                                Conducta conducta = new Conducta();
                                conducta.setDescripcion(docConducta.getString("descripcion"));
                                conducta.setPeso(docConducta.getInteger("peso"));
                                misConductas.add(conducta);
                            }
                        }
                        aspecto.setConductas(misConductas);
                        misAspectos.add(aspecto);
                    }
                }
                resultCualitativo.setAspectos(misAspectos);
                e.setCualitativo(resultCualitativo);
                
                result.add(e);
            }
        }finally {
            cursor.close();
        }
        
        return result;
    }
}
