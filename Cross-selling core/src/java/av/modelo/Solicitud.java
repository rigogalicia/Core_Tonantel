package av.modelo;

import admin.modelo.Colaborador;
import admin.modelo.ReportConfig;
import dao.AvAsociado;
import dao.AvColindante;
import dao.AvDocumento;
import dao.AvInmueble;
import dao.AvPropietario;
import dao.AvSolicitud;
import dao.AvTelefono;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JasperRunManager;
import patrimonio.modelo.ConexionMySql;

public class Solicitud {
    private AvAsociado asociado = new AvAsociado();
    private String telefono = new String();
    private ArrayList<String> telefonos = new ArrayList<>();
    private AvDocumento documento = new AvDocumento();
    private AvPropietario propietario = new AvPropietario();
    private AvInmueble inmueble = new AvInmueble();
    private AvColindante colindante = new AvColindante();
    private ArrayList<AvColindante> colindantes = new ArrayList<>();
    private AvSolicitud solicitud = new AvSolicitud();
    private String userConect;
    
    
    private boolean btnGenerar = false;
    private boolean btnUpdate = false;
    private String msjConsultar;
    
    //Metodo contstructor
    public Solicitud(){
        HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        
        if(sesion.getAttribute("userConect") != null){
            userConect = sesion.getAttribute("userConect").toString();
        }
        else{
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/Cross-selling_core/faces/index.xhtml");
            } catch (IOException ex) {
                ex.printStackTrace(System.out);
            }
        }
        
    }

    public AvAsociado getAsociado() {
        return asociado;
    }

    public void setAsociado(AvAsociado asociado) {
        this.asociado = asociado;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public ArrayList<String> getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(ArrayList<String> telefonos) {
        this.telefonos = telefonos;
    }

    public AvDocumento getDocumento() {
        return documento;
    }

    public void setDocumento(AvDocumento documento) {
        this.documento = documento;
    }

    public AvPropietario getPropietario() {
        return propietario;
    }

    public void setPropietario(AvPropietario propietario) {
        this.propietario = propietario;
    }

    public AvInmueble getInmueble() {
        return inmueble;
    }

    public void setInmueble(AvInmueble inmueble) {
        this.inmueble = inmueble;
    }

    public AvColindante getColindante() {
        return colindante;
    }

    public void setColindante(AvColindante colindante) {
        this.colindante = colindante;
    }

    public ArrayList<AvColindante> getColindantes() {
        return colindantes;
    }

    public void setColindantes(ArrayList<AvColindante> colindantes) {
        this.colindantes = colindantes;
    }
    
    public AvSolicitud getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(AvSolicitud solicitud) {
        this.solicitud = solicitud;
    }

    public boolean isBtnGenerar() {
        return btnGenerar;
    }

    public void setBtnGenerar(boolean btnGenerar) {
        this.btnGenerar = btnGenerar;
    }

    public boolean isBtnUpdate() {
        return btnUpdate;
    }

    public void setBtnUpdate(boolean btnUpdate) {
        this.btnUpdate = btnUpdate;
    }
    
    public String getMsjConsultar() {
        return msjConsultar;
    }

    public void setMsjConsultar(String msjConsultar) {
        this.msjConsultar = msjConsultar;
    }
    
    // Metodo para agregar un numero de telefono
    public void agregarTelefono(){
        telefonos.add(telefono);
        telefono = new String();
    }
    
    // Metodo para quitar un numero de telefono
    public void quitarTelefono(String tel){
        telefonos.remove(tel);
    }
    
    // Agrega un registro en el array de colindante
    public void agregarColindante(){
        colindantes.add(colindante);
        colindante = new AvColindante();
    }
    
    // Elimina un registro de el array de colindante
    public void quitarColindante(AvColindante c){
        colindantes.remove(c);
    }
    
    //Metodo para limpiar el tipo de documento
    public void limpiarDocumento(){
        documento = new AvDocumento();
    }
    
    //Metodo para limpiar el formulario solicitud
    public void limpiarSolicitud(){
        //Inicializmos cada objeto de clase para limpiar el formulario
        solicitud.setMonto(null);
        solicitud.setNumeroCredito(null);
        solicitud.setReavaluo(null);
        asociado = new AvAsociado();
        documento = new AvDocumento();
        inmueble = new AvInmueble();
        propietario = new AvPropietario();
        colindantes = new ArrayList<>();
        telefonos = new ArrayList<>();
    }
    
    /* Este metodo crea una solicitud, envia los datos a la base de datos
    Por medio de una transaccion*/
    public void crearSolicitud(){
        EntityManagerFactory emf = null;
        EntityManager em = null;
        
        try{
            emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
            em = emf.createEntityManager();
            
            em.getTransaction().begin();
            Colaborador colaborador = Colaborador.datosColaborador(userConect);
            solicitud.setUsuario(userConect);
            solicitud.setAgencia(colaborador.getAgencia());
            solicitud.setFechahora(new Date());
            solicitud.setEstado('a');
            em.merge(asociado);
            for(String t : telefonos){
                AvTelefono telefonoBD = new AvTelefono();
                telefonoBD.setNumero(t);
                telefonoBD.setAsociadoCif(asociado);
                em.merge(telefonoBD);
            }
            
            em.merge(propietario);
            em.persist(documento);
            inmueble.setPropietarioDpi(propietario);
            inmueble.setDocumentoId(documento);
            em.persist(inmueble);
            for(AvColindante c : colindantes){
                c.setTipo('a');
                c.setInmuebleId(inmueble);
                em.persist(c);
            }
            solicitud.setAsociadoCif(asociado);
            solicitud.setInmuebleId(inmueble);
            em.persist(solicitud);
            em.getTransaction().commit();
            
            FacesContext.getCurrentInstance().getExternalContext().redirect("/Cross-selling_core/faces/vista/av/av_generadas.xhtml");
        }
        catch(IOException e){
            e.printStackTrace(System.out);
        }finally{
            if(emf != null && em != null){
                em.close();
                emf.close();
            }
        }
    }
    
    // Metodo para consultar si existe la solicitud
    public boolean existeSolicitud() {
        boolean result = false;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
        EntityManager em = emf.createEntityManager();
        
        String instruccion = "SELECT s "
                + "FROM AvSolicitud s "
                + "WHERE s.numeroSolicitud = :numSolicitud "
                + "AND s.usuario != :usrConect";
        
        Query consulta = em.createQuery(instruccion);
        consulta.setParameter("numSolicitud", solicitud.getNumeroSolicitud());
        consulta.setParameter("usrConect", this.userConect);
        List<AvSolicitud> resultado = consulta.getResultList();
        
        for(AvSolicitud s : resultado) {
            result = true;
            solicitud = new AvSolicitud();
            asociado = new AvAsociado();
            documento = new AvDocumento();
            inmueble = new AvInmueble();
            propietario = new AvPropietario();
            colindantes = new ArrayList<>();
            telefonos = new ArrayList<>();
            break;
        }
        
        em.close();
        emf.close();
        
        return result;
    }
    
    //Metodo para consultar los datos de la solictud
    public void consultarSolicitud(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
        EntityManager em = emf.createEntityManager();
        
        String instruccion =  "SELECT s, a, i, p, d "
                + "FROM AvSolicitud s "
                + "JOIN s.asociadoCif a "
                + "JOIN s.inmuebleId i "
                + "JOIN i.propietarioDpi p "
                + "JOIN i.documentoId  d "
                + "WHERE s.numeroSolicitud = :numSolicitud "
                + "AND s.usuario = :usuarioconectado";

        Query consulta = em.createQuery(instruccion);
        consulta.setParameter("numSolicitud", solicitud.getNumeroSolicitud());
        consulta.setParameter("usuarioconectado", userConect);
        List<Object[]> resultado = consulta.getResultList();
        
        //llamamos al metod que limpia el formulario
        limpiarSolicitud();
        //llenamos los objetos con los datos que nos devuelve la consulta     
        for(Object[] objeto: resultado){
            solicitud = (AvSolicitud) objeto[0];
            asociado  = (AvAsociado) objeto[1];
            inmueble = (AvInmueble) objeto[2];
            propietario = (AvPropietario) objeto[3];
            documento = (AvDocumento) objeto[4];
        }
            
        listTelefonos(asociado.getCif());
        listaColindantes(inmueble);

        //validamos el resultado de la consulta
        if(resultado.isEmpty()){
            btnGenerar = true;
            btnUpdate = false;
        }
        else if(!resultado.isEmpty() && !solicitud.getEstado().equals('d') && !solicitud.getEstado().equals('e')){
            btnGenerar = false;
            btnUpdate = true;
        }
        else{
            btnGenerar = false;
            btnUpdate = false;

            msjConsultar = "El estado actual de la solicitud no permite modificación.";
        }
        
        em.close();
        emf.close();
       
    }
    
    //Metodo para obtener el listado de telefonos
    private void listTelefonos(String cif){
        telefonos.clear();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
        EntityManager em = emf.createEntityManager();
        
        String instruccion = "SELECT t "
                + "FROM AvTelefono t "
                + "JOIN t.asociadoCif a "
                + "WHERE a.cif = :cif";
        Query consulta = em.createQuery(instruccion);
        consulta.setParameter("cif", cif);
        List<AvTelefono> resultado = consulta.getResultList();
        for(AvTelefono  t: resultado){
            telefonos.add(t.getNumero());
        }
        
        em.close();
        emf.close();
    }
    
    // Metodo para consultar los registros de punto cardinal
    private void listaColindantes(AvInmueble inmueble){
        char est = 'a';
        colindantes.clear();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
        EntityManager em = emf.createEntityManager();
        
        String instruccion = "SELECT c "
                + "FROM AvColindante c "
                + "JOIN c.inmuebleId i "
                + "WHERE i.id = :idInmueble "
                + "AND c.tipo = :est ";
        Query consulta = em.createQuery(instruccion);
        consulta.setParameter("idInmueble", inmueble.getId());
        consulta.setParameter("est", est);
        List<AvColindante> resultado = consulta.getResultList();
        for(AvColindante  c: resultado){
            colindantes.add(c);
        }
        
        em.close();
        emf.close();
    }
    
    // Metodo para eliminar los telefonos de un cif
    private void eliminarTelefonos(String cif){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conexion = DriverManager.getConnection(ConexionMySql.URL, ConexionMySql.USERNAME, ConexionMySql.PASSWORD);

            Statement st = conexion.createStatement();
            st.execute("DELETE FROM av_telefono WHERE asociado_cif = '"+cif+"'");

            st.close();
            conexion.close();
        }
        catch(Exception e){
            e.printStackTrace(System.out);
       }
       
    }
    
//  Metodo para eliminar las colindantes
    private void eliminarColindantes(int inmuebleid){
        try{
            char tipo = 'a';
            Class.forName("com.mysql.jdbc.Driver");
            Connection conexion = DriverManager.getConnection(ConexionMySql.URL, ConexionMySql.USERNAME, ConexionMySql.PASSWORD);

            Statement st = conexion.createStatement();
            st.execute("DELETE FROM av_colindante WHERE inmueble_id = '"+inmuebleid+"' AND tipo = '"+tipo+"' ");
            
            st.close();
            conexion.close();
           
        }
        catch(Exception e){
            e.printStackTrace(System.out);
        }
    }
    
    //Metodo utilizdo para actualizar la solicitud de avaluos
    public void updateSolicitud(){
        EntityManagerFactory emf = null;
        EntityManager em = null;
        
            try{
            emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
            em = emf.createEntityManager();
            
            em.getTransaction().begin();
            Colaborador colaborador = Colaborador.datosColaborador(userConect);
            solicitud.setUsuario(userConect);
            solicitud.setAgencia(colaborador.getAgencia());
            solicitud.setFechahora(new Date());
            em.merge(asociado);
            eliminarTelefonos(asociado.getCif());
            for(String t : telefonos){
                AvTelefono telefonoBD = new AvTelefono();
                telefonoBD.setNumero(t);
                telefonoBD.setAsociadoCif(asociado);
                em.merge(telefonoBD);
            }
            
            em.merge(propietario);
            em.merge(documento);
            inmueble.setPropietarioDpi(propietario);
            inmueble.setDocumentoId(documento);
            em.merge(inmueble);
            eliminarColindantes(inmueble.getId());
            for(AvColindante c : colindantes){
                c.setTipo('a');
                c.setInmuebleId(inmueble);
                em.merge(c);
            }
            solicitud.setAsociadoCif(asociado);
            solicitud.setInmuebleId(inmueble);
            em.merge(solicitud);
            em.getTransaction().commit();
            
            FacesContext.getCurrentInstance().getExternalContext().redirect("/Cross-selling_core/faces/vista/av/av_generadas.xhtml");
        }
        catch(IOException e){
            e.printStackTrace(System.out);
        }finally{
            if(emf != null && em != null){
                em.close();
                emf.close();
            }
        }
    }
  
    /* Este metodo es utilizado para mostrar el detalle de solicitud */
    public static void detalle(String numeroSolicitud){ 
  
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conexion = DriverManager.getConnection(ConexionMySql.URL, ConexionMySql.USERNAME, ConexionMySql.PASSWORD);
            
            String nombreReporte = "av_solicitud.jasper";
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("numeroSolicitud", numeroSolicitud);

            byte[] bytes = JasperRunManager.runReportToPdf(ReportConfig.path_avaluos + nombreReporte, parametros, conexion);
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            ServletOutputStream outStream = response.getOutputStream();
            outStream.write(bytes, 0, bytes.length);

            outStream.flush();
            outStream.close();
            conexion.close();
            
            FacesContext.getCurrentInstance().responseComplete();
        }
        catch(Exception e){
            e.printStackTrace(System.out);
        }

    }

}
