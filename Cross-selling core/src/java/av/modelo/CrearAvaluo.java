
package av.modelo;

import dao.AvArea;
import dao.AvAsignacion;
import dao.AvAvaluo;
import dao.AvColindante;
import dao.AvConstruccion;
import dao.AvDetalle;
import dao.AvInmueble;
import dao.AvPuntocardinal;
import dao.AvSolicitud;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

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
    private double sumaTotalDetalle;
    private double total;
    
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
                AvPuntocardinal puntoInsert = new AvPuntocardinal(colin.getPuntoCardinalId());
                AvColindante colindanteInsert = new AvColindante();
                colindanteInsert.setMetros(colin.getMetros());
                colindanteInsert.setVaras(colin.getVaras());
                colindanteInsert.setNombre(colin.getNombre());
                colindanteInsert.setTipo('b');
                colindanteInsert.setInmuebleId(inmueble);
                colindanteInsert.setPuntocardinalId(puntoInsert);
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
            
            
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }finally{
            if(emf != null && em != null){
                em.close();
                emf.close();
            }
        }
    }
    
}
