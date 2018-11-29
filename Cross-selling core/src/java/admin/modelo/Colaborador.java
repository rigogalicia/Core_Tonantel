package admin.modelo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import java.util.ArrayList;
import org.bson.Document;
import org.apache.commons.codec.digest.DigestUtils;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import java.util.regex.Pattern;

public class Colaborador {
    private String usuario;
    private String clave;
    private String nombre;
    private String correo;
    private int operador;
    private String agencia;
    private String departamento;
    private String puesto;
    private String firma;

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

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }
    
    /* Este metodo se utiliza para insertar un nuevo colaborador */
    public void insertar(){
        MongoCollection<Document> coleccion = ConexionMongo.getInstance().getDatabase().getCollection("colaboradores");
        
        Document doc = new Document("_id", this.usuario)
                .append("clave", DigestUtils.md5Hex(this.clave))
                .append("nombre", this.nombre)
                .append("correo", this.correo)
                .append("operador", this.operador)
                .append("agencia", this.agencia)
                .append("departamento", this.departamento)
                .append("puesto", this.puesto);
        
        coleccion.insertOne(doc);
    }
    
    /* Este metodo se utiliza para actualizar los datos de un colaborador */
    public void update(){
        MongoCollection<Document> coleccion = ConexionMongo.getInstance().getDatabase().getCollection("colaboradores");
        coleccion.updateOne(eq("_id", this.usuario), 
                new Document("$set", new Document("nombre", this.nombre)
                .append("correo", this.correo)
                .append("operador", this.operador)
                .append("agencia", this.agencia)
                .append("departamento", this.departamento)
                .append("puesto", this.puesto)));
    }
    
    /* Metodo para actualizar la direccion de la firma */
    public void actualizarFirma(){
        MongoCollection<Document> coleccion = ConexionMongo.getInstance().getDatabase().getCollection("colaboradores");
        coleccion.updateOne(eq("_id", this.usuario), 
                new Document("$set", new Document("firma", this.firma)));
    }
    
    /* Metodo utilizado para mostrar todos los registros de los colaboradores */
    public ArrayList<Colaborador> mostrarColaboradores(){
        ArrayList<Colaborador> listaUsuarios = new ArrayList<>();
        MongoCollection<Document> coleccion = ConexionMongo.getInstance().getDatabase().getCollection("colaboradores");
        MongoCursor<Document> cursor = coleccion.find().iterator();
        try{
            while(cursor.hasNext()){
                Document siguiente = cursor.next();
                Colaborador u = new Colaborador();
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
    
    /* Metodo utilizado para mostrar todos los registros de los colaboradores */
    public static ArrayList<Colaborador> ColaboradoresPorAgencia(String ag){
        ArrayList<Colaborador> listaUsuarios = new ArrayList<>();
        MongoCollection<Document> coleccion = ConexionMongo.getInstance().getDatabase().getCollection("colaboradores");
        MongoCursor<Document> cursor = coleccion.find(eq("agencia", ag)).iterator();
        try{
            while(cursor.hasNext()){
                Document siguiente = cursor.next();
                Colaborador u = new Colaborador();
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
    
    /* Metodo utilizado para buscar un registro de la coleccion */
    public static ArrayList<Colaborador> buscarColaboradores(String nombreBuscar){
        ArrayList<Colaborador> listaUsuarios = new ArrayList<>();
        MongoCollection<Document> collection = ConexionMongo.getInstance().getDatabase().getCollection("colaboradores");
        MongoCursor<Document> cursor = collection.find(new Document("nombre", Pattern.compile(nombreBuscar))).iterator();
        try {
            while (cursor.hasNext()) {
                Document siguiente = cursor.next();
                Colaborador u = new Colaborador();
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
        } finally{
            cursor.close();
        }
        return listaUsuarios;
    }
    
    /* Metodo para mostrar los datos del colaborador */
    public Colaborador datosColaborador(){
        Colaborador resultado = new Colaborador();
        MongoCollection<Document> coleccion = ConexionMongo.getInstance().getDatabase().getCollection("colaboradores");
        MongoCursor<Document> cursor = coleccion.find(eq("operador", operador)).iterator();
        try{
            while(cursor.hasNext()){
                Document next = cursor.next();
                resultado.setUsuario(next.getString("_id"));
                resultado.setClave(next.getString("clave"));
                resultado.setNombre(next.getString("nombre"));
                resultado.setCorreo(next.getString("correo"));
                resultado.setOperador(next.getInteger("operador"));
                resultado.setAgencia(next.getString("agencia"));
                resultado.setDepartamento(next.getString("departamento"));
                resultado.setPuesto(next.getString("puesto"));
            }
        }finally{
            cursor.close();
        }
        
        return resultado;
    }
    
    /* Metodo para mostrar los datos del colaborador */
    public static Colaborador datosColaborador(String user){
        Colaborador resultado = new Colaborador();
        MongoCollection<Document> coleccion = ConexionMongo.getInstance().getDatabase().getCollection("colaboradores");
        MongoCursor<Document> cursor = coleccion.find(eq("_id", user)).iterator();
        try{
            while(cursor.hasNext()){
                Document next = cursor.next();
                resultado.setUsuario(next.getString("_id"));
                resultado.setClave(next.getString("clave"));
                resultado.setNombre(next.getString("nombre"));
                resultado.setCorreo(next.getString("correo"));
                resultado.setOperador(next.getInteger("operador"));
                resultado.setAgencia(next.getString("agencia"));
                resultado.setDepartamento(next.getString("departamento"));
                resultado.setPuesto(next.getString("puesto"));
            }
        }finally{
            cursor.close();
        }
        
        return resultado;
    }
    
    /* Este metodo es utilizado para consultar los colaboradores por departamento */
    public static ArrayList<Colaborador> colaboradoresPorDepartamento(String depto){
        ArrayList<Colaborador> listaUsuarios = new ArrayList<>();
        MongoCollection<Document> collection = ConexionMongo.getInstance().getDatabase().getCollection("colaboradores");
        MongoCursor<Document> cursor = collection.find(eq("departamento", depto)).iterator();
        try {
            while (cursor.hasNext()) {
                Document siguiente = cursor.next();
                Colaborador u = new Colaborador();
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
        } finally{
            cursor.close();
        }
        return listaUsuarios;
    }
    
    /* Metodo utilizado para mostrar los los colaboradores por Puesto */
    public static ArrayList<Colaborador> ColaboradoresPorPuesto(String puest){
        ArrayList<Colaborador> listaUsuarios = new ArrayList<>();
        MongoCollection<Document> collection = ConexionMongo.getInstance().getDatabase().getCollection("colaboradores");
        MongoCursor<Document> cursor = collection.find(eq("puesto", puest)).iterator();
        try {
            while (cursor.hasNext()) {
                Document siguiente = cursor.next();
                Colaborador u = new Colaborador();
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
        } finally {
            cursor.close();
        }
        return  listaUsuarios;
    }
    /* Metodo utilizado para mostrar la agencia en la que trabaja el colaborador */
    public static String agenciaColaborador(String user){
        String result = null;
        MongoCollection<Document> coleccion = ConexionMongo.getInstance().getDatabase().getCollection("colaboradores");
        MongoCursor<Document> cursor = coleccion.find(eq("_id", user)).iterator();
        
        try{
            while(cursor.hasNext()){
                Document next = cursor.next();
                result = Agencia.descripcionAgencia(next.getString("agencia"));
            }
        }finally{
            cursor.close();
        }
        
        return result;
    }
    
    /* Metodo utilizado para consultar el correo del colaborador */
    public static String correoColaborador(String user){
        String result = null;
        MongoCollection<Document> coleccion = ConexionMongo.getInstance().getDatabase().getCollection("colaboradores");
        MongoCursor<Document> cursor = coleccion.find(eq("_id", user)).iterator();
        
        try{
            while(cursor.hasNext()){
                Document next = cursor.next();
                result = next.getString("correo");
            }
        }finally{
            cursor.close();
        }
        
        return result;
    }
    
    /* Este metodo se utiliza para resetear la clave del colaborador */
    public void resetClave(){
        MongoCollection<Document> coleccion = ConexionMongo.getInstance().getDatabase().getCollection("colaboradores");
        coleccion.updateOne(eq("operador", this.operador), 
                new Document("$set", new Document("clave", this.clave)));
    }
    
    /* Este metodo se utiliza para validar si un usuario esta autorizado o no */
    public boolean estaAutorizado(){
        MongoCollection<Document> coleccion = ConexionMongo.getInstance().getDatabase().getCollection("colaboradores");
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
    
    public static String urlImagen(String usuario){
        String result = "";
        
        MongoCollection<Document> coleccion = ConexionMongo.getInstance().getDatabase().getCollection("colaboradores");
        MongoCursor<Document> cursor = coleccion.find(eq("_id", usuario)).iterator();
        try{
            while(cursor.hasNext()){
                Document siguiente = cursor.next();
                result = siguiente.getString("firma");
            }
        }finally{
            cursor.close();
        }
        
        return result;
    }

    @Override
    public String toString() {
        return "Usuario{" + "usuario=" + usuario + ", clave=" + clave + ", nombre=" + 
                nombre + ", correo=" + correo + ", operador=" + operador + ", agencia=" + 
                agencia + ", departamento=" + departamento + ", puesto=" + puesto + '}';
    }
    
}
