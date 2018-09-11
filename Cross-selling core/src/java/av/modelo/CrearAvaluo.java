
package av.modelo;

import dao.AvArea;
import dao.AvAsignacion;
import dao.AvAvaluo;
import dao.AvConstruccion;
import dao.AvInmueble;
import dao.AvSolicitud;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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
    }
    
    // Metodo que es utilizado para calcular la suma total de detalle
    private void calcularTotal(){
        sumaTotalDetalle = 0;
        detalleAvaluo.forEach((d) -> {
            sumaTotalDetalle += d.getTotal();
        });
    }
    
    //Metodo par crear el avaluo
    public void crearAvaluo(){
        EntityManagerFactory emf = null;
        EntityManager em = null;
        
        try {
            emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
            em = emf.createEntityManager();

            em.getTransaction().begin();
            
            
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
