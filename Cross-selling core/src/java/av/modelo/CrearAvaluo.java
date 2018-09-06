
package av.modelo;

import dao.AvArea;
import dao.AvAsignacion;
import dao.AvAvaluo;
import dao.AvDetalle;
import dao.AvInmueble;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import net.sf.jasperreports.engine.fill.SimpleTextFormat;




public class CrearAvaluo {
    private String fechainspeccion;
    private AvAvaluo avaluo = new AvAvaluo();
    private AvAsignacion asignacion = new AvAsignacion();
    private AvArea area = new AvArea();
    private AvInmueble inmueble = new AvInmueble();
    private AvDetalle detalle = new AvDetalle();
    
    
    //metodo constructor para tomar e inicializar el usuario conectado
    public CrearAvaluo(){
        
    }

    public String getFechainspeccion() {
        return fechainspeccion;
    }

    public void setFechainspeccion(String fechainspeccion) {
        this.fechainspeccion = fechainspeccion;
    }

    public AvAvaluo getAvaluo() {
        return avaluo;
    }

    public void setAvaluo(AvAvaluo avaluo) {
        this.avaluo = avaluo;
    }

    public AvAsignacion getAsignacion() {
        return asignacion;
    }

    public void setAsignacion(AvAsignacion asignacion) {
        this.asignacion = asignacion;
    }
    
    public AvArea getArea() {
        return area;
    }

    public void setArea(AvArea area) {
        this.area = area;
    }

    public AvInmueble getInmueble() {
        return inmueble;
    }

    public void setInmueble(AvInmueble inmueble) {
        this.inmueble = inmueble;
    }

    public AvDetalle getDetalle() {
        return detalle;
    }

    public void setDetalle(AvDetalle detalle) {
        this.detalle = detalle;
    }
    
    
    
    //Metodo par crear el avaluo
    public void crearAvaluo(){
        EntityManagerFactory emf = null;
        EntityManager em = null;
        
        try {
            emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
            em = emf.createEntityManager();
            
            
            SimpleDateFormat formatofecha = new SimpleDateFormat("dd/mm/yyyy");
            ;
            
            em.getTransaction().begin();
            em.merge(inmueble);
            em.merge(asignacion);
            avaluo.setInmuebleId(inmueble);
            avaluo.setAsignacionId(asignacion);
            avaluo.setFechahora(formatofecha.parse(fechainspeccion));
            em.merge(avaluo);
            detalle.setAvaluoId(avaluo);
            em.merge(detalle);
            em.merge(inmueble);
            area.setInmuebleId(inmueble);
            em.merge(area);
            
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
