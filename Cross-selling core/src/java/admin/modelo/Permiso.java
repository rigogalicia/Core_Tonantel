package admin.modelo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import java.util.ArrayList;
import org.bson.Document;
import org.bson.types.ObjectId;

public class Permiso extends Rol{
    private ObjectId idPermiso;
    private String idUsuario;
    private ArrayList<Privilegio> privilegios;

    public ObjectId getIdPermiso() {
        return idPermiso;
    }

    public void setIdPermiso(ObjectId idPermiso) {
        this.idPermiso = idPermiso;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public ArrayList<Privilegio> getPrivilegios() {
        return privilegios;
    }

    public void setPrivilegios(ArrayList<Privilegio> privilegios) {
        this.privilegios = privilegios;
    }
    
    /* Metodo para agregar un rol al colaborador */
    public void asignarRol(){
        MongoCollection<Document> coleccion = ConexionMongo.getInstance().getDatabase().getCollection("permisos");
        Document documento = new Document("idUsuario", idUsuario)
                .append("idRol", super.getId().toString())
                .append("descripcionRol", super.getDescripcion())
                .append("formaRol", super.getForma());
        coleccion.insertOne(documento);
    }
    
    /* Metodo utilizado para asignar los privilegios */
    public void asignarPrivilegio(){
        MongoCollection<Document> coleccion = ConexionMongo.getInstance().getDatabase().getCollection("permisos");
        ArrayList<Document> docPrivilegios = new ArrayList<>();
        for(Privilegio p : privilegios){
            docPrivilegios.add(new Document("descripcionPrivilegios", p.getDescripcion())
            .append("formaPrivilegios", p.getForma()));        
        }
        coleccion.updateOne(eq("_id", idPermiso), new Document("$set", new Document("privilegios", docPrivilegios)));
    }
    
    /* Metodo para eliminar un registro de rol asignado */
    public void eliminarRol(){
        MongoCollection<Document> coleccion = ConexionMongo.getInstance().getDatabase().getCollection("permisos");
        Document documento = new Document("_id", idPermiso);
        coleccion.deleteOne(documento);
    }
    
    /* Metodo para consultar los roles asignados por usuario */
    public ArrayList<Permiso> mostrarPermisos(){
        ArrayList<Permiso> resultado = new ArrayList<>();
        MongoCollection<Document> coleccion = ConexionMongo.getInstance().getDatabase().getCollection("permisos");
        MongoCursor<Document> cursor = coleccion.find(eq("idUsuario", idUsuario)).iterator();
        try{
            while(cursor.hasNext()){
                Document next = cursor.next();
                Permiso p = new Permiso();
                p.setIdPermiso(next.getObjectId("_id"));
                p.setIdUsuario(next.getString("idUsuario"));
                p.setId(new ObjectId(next.getString("idRol")));
                p.setDescripcion(next.getString("descripcionRol"));
                p.setForma(next.getString("formaRol"));
                
                ArrayList<Document> listPrivilegios = (ArrayList<Document>) next.get("privilegios");
                ArrayList<Privilegio> misPrivilegios = new ArrayList<>();
                if(listPrivilegios != null){
                    for(Document docPrivilegios : listPrivilegios){
                        Privilegio priv = new Privilegio();
                        priv.setDescripcion(docPrivilegios.getString("descripcionPrivilegios"));
                        priv.setForma(docPrivilegios.getString("formaPrivilegios"));
                        misPrivilegios.add(priv);
                    }
                }
                
                p.setPrivilegios(misPrivilegios);

                resultado.add(p);
            }
        }finally{
            cursor.close();
        }
        return resultado;
    }
    
    /* Metodo para consultar los privilegios asignados por rol */
    public ArrayList<Privilegio> consultarPrivilegios(){
        ArrayList<Privilegio> resultado = new ArrayList<>();
        MongoCollection<Document> coleccion = ConexionMongo.getInstance().getDatabase().getCollection("permisos");
        MongoCursor<Document> cursor = coleccion.find(and(eq("idUsuario", idUsuario), eq("idRol", super.getId().toString()))).iterator();
        try{
            while(cursor.hasNext()){
                Document next = cursor.next();
                ArrayList<Document> listPrivilegios = (ArrayList<Document>) next.get("privilegios");
                if(listPrivilegios != null){
                    for(Document docPrivilegios : listPrivilegios){
                        Privilegio priv = new Privilegio();
                        priv.setDescripcion(docPrivilegios.getString("descripcionPrivilegios"));
                        priv.setForma(docPrivilegios.getString("formaPrivilegios"));
                        resultado.add(priv);
                    }
                }
            }
        }finally{
            cursor.close();
        }
        return resultado;
    }
}
