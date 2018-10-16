
package av.modelo;

import admin.modelo.Colaborador;
import admin.modelo.ReportConfig;
import dao.AvAnexos;
import dao.AvArea;
import dao.AvAsignacion;
import dao.AvAvaluo;
import dao.AvColindante;
import dao.AvConstruccion;
import dao.AvDetalle;
import dao.AvInmueble;
import dao.AvSolicitud;
import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
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
import net.sf.jasperreports.engine.JasperRunManager;
import patrimonio.modelo.ConexionMySql;

public class CrearAvaluo {
    private AvSolicitud solicitud = new AvSolicitud();
    private AvInmueble inmueble = new AvInmueble();
    private Colindante colindante = new Colindante();
    private ArrayList<Colindante> colindantes = new ArrayList<>();
    private AvArea area = new AvArea();
    private AvConstruccion construccion = new AvConstruccion();
    private AvAsignacion asignacion = new AvAsignacion();
    private AvAvaluo avaluo = new AvAvaluo();
    private DetalleAvaluo detalle = new DetalleAvaluo();
    private ArrayList<DetalleAvaluo> detalleAvaluo = new ArrayList<>();
    private AnexosAvaluo anexos = new AnexosAvaluo();
    private ArrayList<AnexosAvaluo> listAnexos = new ArrayList<>();
    private double sumaTotalDetalle;
    private double total;
    
    private String msjInsertar;
    
    public CrearAvaluo() {
        
    }

    public AvSolicitud getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(AvSolicitud solicitud) {
        this.solicitud = solicitud;
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

    public AvArea getArea() {
        return area;
    }

    public void setArea(AvArea area) {
        this.area = area;
    }

    public AvConstruccion getConstruccion() {
        return construccion;
    }

    public void setConstruccion(AvConstruccion construccion) {
        this.construccion = construccion;
    }

    public AvAsignacion getAsignacion() {
        return asignacion;
    }

    public void setAsignacion(AvAsignacion asignacion) {
        this.asignacion = asignacion;
    }

    public AvAvaluo getAvaluo() {
        return avaluo;
    }

    public void setAvaluo(AvAvaluo avaluo) {
        this.avaluo = avaluo;
    }

    public DetalleAvaluo getDetalle() {
        return detalle;
    }

    public void setDetalle(DetalleAvaluo detalle) {
        this.detalle = detalle;
    }

    public ArrayList<DetalleAvaluo> getDetalleAvaluo() {
        return detalleAvaluo;
    }

    public void setDetalleAvaluo(ArrayList<DetalleAvaluo> detalleAvaluo) {
        this.detalleAvaluo = detalleAvaluo;
    }

    public AnexosAvaluo getAnexos() {
        return anexos;
    }

    public void setAnexos(AnexosAvaluo anexos) {
        this.anexos = anexos;
    }

    public ArrayList<AnexosAvaluo> getListAnexos() {
        return listAnexos;
    }

    public void setListAnexos(ArrayList<AnexosAvaluo> listAnexos) {
        this.listAnexos = listAnexos;
    }
        
    public double getSumaTotalDetalle() {
        return sumaTotalDetalle;
    }

    public void setSumaTotalDetalle(double sumaTotalDetalle) {
        this.sumaTotalDetalle = sumaTotalDetalle;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getMsjInsertar() {
        return msjInsertar;
    }

    public void setMsjInsertar(String msjInsertar) {
        this.msjInsertar = msjInsertar;
    }
    
    
    
    // Este metodo realiza la consulta de la clase avaluo e inmueble
    public void consultarSolicitud(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
        EntityManager em = emf.createEntityManager();
        
        String instruccion = "SELECT a, s, i "
                + "FROM AvAsignacion a "
                + "JOIN a.solicitudNumeroSolicitud s "
                + "JOIN s.inmuebleId i "
                + "WHERE s.numeroSolicitud = :numeroSolicitud";
        Query consulta = em.createQuery(instruccion);
        consulta.setParameter("numeroSolicitud", solicitud.getNumeroSolicitud());
        List<Object[]> resultado = consulta.getResultList();
        
        for(Object[] obj : resultado){
            this.asignacion = (AvAsignacion) obj[0];
            this.solicitud = (AvSolicitud) obj[1];
            this.inmueble = (AvInmueble) obj[2];
        }
        
        em.close();
        emf.close();
    }
    
    //Metodo utilizado para agrengar colindates de avaluo
    public void agregarColindante(){
        colindantes.add(colindante);
        colindante = new Colindante();
    }
    
    //Metodo utilizado para borrar colindantes agregados en la tabla
    public void quitarColindante(Colindante c){
        colindantes.remove(c);
    }
    
    // Metodo para agregar detalle de solicitud
    public void agregarDetalle(){
        detalle.setDescripcionTipo(detalle.getTipo() == 'a' ? "Solar" : "Construccion");
        detalle.setTotal(detalle.getMedidas() * detalle.getValor());
        detalleAvaluo.add(detalle);
        detalle = new DetalleAvaluo();
        
        calcularTotal();
    }
    
    // Metodo para quitar el detalle de solicitud
    public void quitarDetalle(DetalleAvaluo d){
        detalleAvaluo.remove(d);
        calcularTotal();
    }
    
    //Metodo para llenar anexos del avaluo
    public void agregarAnexos(String path, String nombreImagen){
        anexos.setDescripcion(anexos.getDescripcion());
        anexos.setUrl(path);
        anexos.setNombreImagen(nombreImagen);
        listAnexos.add(anexos);
        anexos = new AnexosAvaluo();
    }
    
    //Metodo para quitar anello
    public void quitarAnexo(AnexosAvaluo a){
        listAnexos.remove(a);
    }
    
    // Metodo que es utilizado para calcular la suma total de detalle
    private void calcularTotal(){
        sumaTotalDetalle = 0;
        detalleAvaluo.forEach((d) -> {
            
            sumaTotalDetalle += d.getTotal();
        });
    }
    
    // Este metodo calcula el total del avaluo tamando en cuenta el valor vancario
    public void total(){
        total = (avaluo.getValorRedondeado().doubleValue() * avaluo.getValorBancario()) / 100;
    }
    
    //Metodo par crear el avaluo
    public void insert(){
        EntityManagerFactory emf = null;
        EntityManager em = null;
        
        try {
            emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
            em = emf.createEntityManager();

            em.getTransaction().begin();
            
            AvSolicitud solicitudModif = em.find(AvSolicitud.class, solicitud.getNumeroSolicitud());
            solicitudModif.setEstado('c');
            AvInmueble inmuebleModif = em.find(AvInmueble.class, inmueble.getId());
            inmuebleModif.setDireccionFisica(inmueble.getDireccionFisica());
            inmuebleModif.setCoordenadas(inmueble.getCoordenadas());
            inmuebleModif.setObservaciones(inmueble.getObservaciones());
            area.setInmuebleId(inmueble);
            em.persist(area);
            for(Colindante colin : colindantes){
                AvColindante colindanteInsert = new AvColindante();
                colindanteInsert.setMetros(colin.getMetros());
                colindanteInsert.setVaras(colin.getVaras());
                colindanteInsert.setNombre(colin.getNombre());
                colindanteInsert.setTipo('b');
                colindanteInsert.setInmuebleId(inmueble);
                colindanteInsert.setPuntocardinal(colin.getPuntoCardinal());
                em.persist(colindanteInsert);
            }
            construccion.setInmuebleId(inmueble);
            em.persist(construccion);
            avaluo.setInmuebleId(inmueble);
            avaluo.setAsignacionId(asignacion);
            em.persist(avaluo);
            for(DetalleAvaluo da : detalleAvaluo){
                AvDetalle detalleInsert = new AvDetalle();
                detalleInsert.setDescripcion(da.getDescripcion());
                detalleInsert.setMedidas(new BigDecimal(da.getMedidas()));
                detalleInsert.setValor(new BigDecimal(da.getValor()));
                detalleInsert.setTipo(da.getTipo());
                detalleInsert.setAvaluoId(avaluo);
                em.persist(detalleInsert);
            }
            
            
            em.getTransaction().commit();
            msjInsertar = "Datos enviados Exitosamente! ahora puedes adjuntar los anexos al avaluo";
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }finally{
            if(emf != null && em != null){
                em.close();
                emf.close();
            }
        }
    }
    
    //Metodo para insertar Anexos 
    public void insertAnexos(){
        EntityManagerFactory emf = null;
        EntityManager em = null;
        
        try {
            emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
            em = emf.createEntityManager();
            
            em.getTransaction().begin();
            
            for(AnexosAvaluo anex: listAnexos){
                AvAnexos anexosInsert = new AvAnexos();
                anexosInsert.setDescripcion(anex.getDescripcion());
                anexosInsert.setUrl(anex.getUrl());
                anexosInsert.setAvaluoId(avaluo);
                em.persist(anexosInsert);
            }
            
            em.getTransaction().commit();
            FacesContext.getCurrentInstance().getExternalContext().redirect("/Cross-selling_core/faces/vista/av/av_enproceso.xhtml");
            
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }finally{
            if(emf != null && em != null){
                em.close();
                emf.close();
            }
        }
    }
    
    /* Este metodo es utilizado para mostrar el detalle de avaluo */
    public static void detalle(String numeroSolicitud, String nombreValuador){ 
  
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conexion = DriverManager.getConnection(ConexionMySql.URL, ConexionMySql.USERNAME, ConexionMySql.PASSWORD);
            
            String nombreReporte = "av_detalle.jasper";
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("numeroSolicitud", numeroSolicitud);
            parametros.put("Valuador", nombreValuador);

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
    
    // Metodo para consultar el nombre del valuador responsable
    public static String valuadorResponsable(String numeroSolicitud){
        String result = "";
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
        EntityManager em = emf.createEntityManager();
        
        String instruccion = "SELECT a "
                + "FROM AvAsignacion a "
                + "JOIN a.solicitudNumeroSolicitud s "
                + "WHERE s.numeroSolicitud = :numSol";
        
        Query consulta = em.createQuery(instruccion);
        consulta.setParameter("numSol", numeroSolicitud);
        List<AvAsignacion> resultado = consulta.getResultList();
        
        for(AvAsignacion a : resultado){
            result = Colaborador.datosColaborador(a.getUsuario()).getNombre();
        }
        
        em.close();
        emf.close();
        
        return result;
    }
    
}
