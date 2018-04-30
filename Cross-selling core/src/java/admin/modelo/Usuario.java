package admin.modelo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import java.util.ArrayList;
import org.bson.Document;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import org.apache.commons.codec.digest.DigestUtils;

public class Usuario {
    private String usuario;
    private String clave;
    private String nombre;
    private String correo;
    private int operador;
    private String agencia;
    private String departamento;
    private String puesto;
    private ArrayList<ProductosTonantel> productosAsignar = new ArrayList<>();
    private ArrayList<RolesPrivilegios> roles = new ArrayList<>();
    private ArrayList<RolesPrivilegios> privilegios = new ArrayList<>();
    

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getOperador() {
        return operador;
    }

    public void setOperador(int operador) {
        this.operador = operador;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public ArrayList<ProductosTonantel> getProductosAsignar() {
        return productosAsignar;
    }

    public void setProductosAsignar(ArrayList<ProductosTonantel> productosAsignar) {
        this.productosAsignar = productosAsignar;
    }

    public ArrayList<RolesPrivilegios> getRoles() {
        return roles;
    }

    public void setRoles(ArrayList<RolesPrivilegios> roles) {
        this.roles = roles;
    }

    public ArrayList<RolesPrivilegios> getPrivilegios() {
        return privilegios;
    }

    public void setPrivilegios(ArrayList<RolesPrivilegios> privilegios) {
        this.privilegios = privilegios;
    }
    
    public void insertar(){
        MongoCollection<Document> coleccion = ConexionMongo.getInstance().getDatabase().getCollection("usuarios");
        
        ArrayList<Document> docAsignar = new ArrayList<>();
        for(ProductosTonantel p : productosAsignar){
            docAsignar.add(new Document("idproducto", p.getIdproducto())
            .append("nombre_producto", p.getNombre_producto()));
        }
        
        ArrayList<Document> docRoles = new ArrayList<>();
        for(RolesPrivilegios r : roles){
            docRoles.add(new Document("descripcion", r.getDescripcion()));
        }
        
        ArrayList<Document> docPrivilegios = new ArrayList<>();
        for(RolesPrivilegios p : privilegios){
            docPrivilegios.add(new Document("descripcion", p.getDescripcion()));
        }
        
        Document doc = new Document("_id", this.usuario)
                .append("clave", this.clave)
                .append("nombre", this.nombre)
                .append("correo", this.correo)
                .append("operador", this.operador)
                .append("agencia", this.agencia)
                .append("departamento", this.departamento)
                .append("puesto", this.puesto)
                .append("asignar", docAsignar)
                .append("roles", docRoles)
                .append("privilegios", docPrivilegios);
        
        coleccion.insertOne(doc);
    }
    
    public void update(){
        MongoCollection<Document> coleccion = ConexionMongo.getInstance().getDatabase().getCollection("usuarios");
        coleccion.updateOne(eq("_id", this.usuario), 
                new Document("$set", new Document("clave", this.clave)
                .append("nombre", this.nombre)
                .append("correo", this.correo)
                .append("operador", this.operador)
                .append("agencia", this.agencia)
                .append("departamento", this.departamento)
                .append("puesto", this.puesto)));
    }
    
    public ArrayList<Usuario> mostrarUsuarios(){
        ArrayList<Usuario> listaUsuarios = new ArrayList<>();
        MongoCollection<Document> coleccion = ConexionMongo.getInstance().getDatabase().getCollection("usuarios");
        MongoCursor<Document> cursor = coleccion.find().iterator();
        try{
            while(cursor.hasNext()){
                Document siguiente = cursor.next();
                Usuario u = new Usuario();
                u.setUsuario(siguiente.getString("_id"));
                u.setClave(siguiente.getString("clave"));
                u.setNombre(siguiente.getString("nombre"));
                u.setCorreo(siguiente.getString("correo"));
                u.setOperador(siguiente.getInteger("operador"));
                u.setAgencia(siguiente.getString("agencia"));
                u.setDepartamento(siguiente.getString("departamento"));
                u.setPuesto(siguiente.getString("puesto"));
                listaUsuarios.add(u);
            }
        }finally{
            cursor.close();
        }
        return listaUsuarios;
    }
    
    public void mostrarUsuario(String miUser){
        MongoCollection<Document> coleccion = ConexionMongo.getInstance().getDatabase().getCollection("usuarios");
        MongoCursor<Document> cursor = coleccion.find(eq("_id", miUser)).iterator();
        try{
            while(cursor.hasNext()){
                Document next = cursor.next();
                this.usuario = next.getString("_id");
                this.clave = next.getString("clave");
                this.nombre = next.getString("nombre");
                this.correo = next.getString("correo");
                this.operador = next.getInteger("operador");
                this.agencia = next.getString("agencia");
                this.departamento = next.getString("departamento");
                this.puesto = next.getString("puesto");
                ArrayList<Document> listAsignar = (ArrayList<Document>) next.get("asignar");
                for(Document docAsignar : listAsignar){
                    productosAsignar.add(new ProductosTonantel(docAsignar.getInteger("idproducto"), docAsignar.getString("nombre_producto")));
                }
                ArrayList<Document> listRoles = (ArrayList<Document>) next.get("roles");
                for(Document docRoles : listRoles){
                    roles.add(new RolesPrivilegios(docRoles.getString("descripcion")));
                }
                ArrayList<Document>listPrivilegios = (ArrayList<Document>) next.get("privilegios");
                for(Document docPrivilegios : listPrivilegios){
                    privilegios.add(new RolesPrivilegios(docPrivilegios.getString("descripcion")));
                }
            }
        }
        finally{
            cursor.close();
        }
    }
    
    public boolean estaAutorizado(){
        MongoCollection<Document> coleccion = ConexionMongo.getInstance().getDatabase().getCollection("usuarios");
        Document doc = coleccion.find(and(eq("_id", this.usuario), eq("clave", DigestUtils.md5Hex(this.clave)))).first();
        if(doc != null){
            this.nombre = doc.getString("nombre");
            this.operador = doc.getInteger("operador");
            this.correo = doc.getString("correo");
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public String toString() {
        return "Usuario{" + "usuario=" + usuario + ", clave=" + clave + ", nombre=" + 
                nombre + ", correo=" + correo + ", operador=" + operador + ", agencia=" + 
                agencia + ", departamento=" + departamento + ", puesto=" + puesto + '}';
    }
    
}
