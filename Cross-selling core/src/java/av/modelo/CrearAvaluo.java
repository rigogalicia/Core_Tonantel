
package av.modelo;

import dao.AvArea;
import dao.AvAsignacion;
import dao.AvAvaluo;
import dao.AvColindante;
import dao.AvConstruccion;
import dao.AvDetalle;
import dao.AvInmueble;
import dao.AvSolicitud;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class CrearAvaluo {
    private AvSolicitud solicitud = new AvSolicitud();
    private AvInmueble inmueble = new AvInmueble();
    private AvArea area = new AvArea();
    private ArrayList<AvColindante> colindantes = new ArrayList<>();
    private AvConstruccion construccion = new AvConstruccion();
    private AvAsignacion asignacion = new AvAsignacion();
    private AvAvaluo avaluo = new AvAvaluo();
    private ArrayList<AvDetalle> detalleAvaluo = new ArrayList<>();

    //metodo constructor para tomar e inicializar el usuario conectado
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

    public AvArea getArea() {
        return area;
    }

    public void setArea(AvArea area) {
        this.area = area;
    }

    public ArrayList<AvColindante> getColindantes() {
        return colindantes;
    }

    public void setColindantes(ArrayList<AvColindante> colindantes) {
        this.colindantes = colindantes;
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

    public ArrayList<AvDetalle> getDetalleAvaluo() {
        return detalleAvaluo;
    }

    public void setDetalleAvaluo(ArrayList<AvDetalle> detalleAvaluo) {
        this.detalleAvaluo = detalleAvaluo;
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
