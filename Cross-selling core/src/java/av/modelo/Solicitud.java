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
    private Colindante colindante = new Colindante();
    private ArrayList<Colindante> colindantes = new ArrayList<>();
    private AvSolicitud solicitud = new AvSolicitud();
    private String userConect;
    
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

    public Colindante getColindante() {
        return colindante;
    }

    public void setColindante(Colindante colindante) {
        this.colindante = colindante;
    }

    public ArrayList<Colindante> getColindantes() {
        return colindantes;
    }

    public void setColindantes(ArrayList<Colindante> colindantes) {
        this.colindantes = colindantes;
    }
    
    public AvSolicitud getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(AvSolicitud solicitud) {
        this.solicitud = solicitud;
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
        colindante = new Colindante();
    }
    
    // Elimina un registro de el array de colindante
    public void quitarColindante(Colindante c){
        colindantes.remove(c);
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
            em.merge(documento);
            inmueble.setPropietarioDpi(propietario);
            inmueble.setDocumentoId(documento);
            em.merge(inmueble);
            for(Colindante c : colindantes){
                AvColindante colindanteDB = new AvColindante();
                colindanteDB.setMetros(c.getMetros());
                colindanteDB.setVaras(c.getVaras());
                colindanteDB.setNombre(c.getNombre());
                colindanteDB.setTipo('a');
                colindanteDB.setPuntocardinal(c.getPuntoCardinal());
                colindanteDB.setInmuebleId(inmueble);
                em.merge(colindanteDB);
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
                + "WHERE s.numeroSolicitud = :numSolicitud ";
        
        Query consulta = em.createQuery(instruccion);
        consulta.setParameter("numSolicitud", solicitud.getNumeroSolicitud());
        List<Object[]> resultado = consulta.getResultList();
        
        for(Object[] objeto: resultado){
            solicitud = (AvSolicitud) objeto[0];
            asociado  = (AvAsociado) objeto[1];
            inmueble = (AvInmueble) objeto[2];
            propietario = (AvPropietario) objeto[3];
            documento = (AvDocumento) objeto[4];
        }
        listTelefonos();
        em.close();
        emf.close();
    }
    
    //Metodo para obtener el listado de telefonos
    public ArrayList<String> listTelefonos(){
        //ArrayList<AvTelefono> result = new ArrayList<>();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
        EntityManager em = emf.createEntityManager();
        
        String instruccion = "SELECT s, a, t "
                + "FROM AvSolicitud s "
                + "JOIN s.asociadoCif a "
                + "JOIN a.cif t " 
                + "WHERE s.numeroSolicitud = :numSolicitud ";
        Query consulta = em.createQuery(instruccion);
        consulta.setParameter("numSolicitud", solicitud.getNumeroSolicitud());
        List<String> resultado = consulta.getResultList();
        for(String  t: resultado){
            telefonos.add(t);            
        }
        
        em.close();
        emf.close();
        
        return telefonos;
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
