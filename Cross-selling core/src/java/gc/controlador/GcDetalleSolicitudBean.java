package gc.controlador;

import dao.GcEstado;
import dao.GcGestion;
import dao.GcRiesgo;
import dao.GcSeguimiento;
import dao.GcSolicitud;
import gc.modelo.DetalleSolicitud;
import gc.modelo.Estado;
import gc.modelo.Riesgo;
import gc.modelo.Seguimiento;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpSession;

@ManagedBean(name = "gc_detallesolicitud")
@ViewScoped
public class GcDetalleSolicitudBean {
    private DetalleSolicitud detalle;
    private GcRiesgo riesgo = new GcRiesgo();
    private GcEstado estado = new GcEstado();
    private ArrayList<SelectItem> estadoItem = new ArrayList<>();
    private ArrayList<SelectItem> riesgoItem = new ArrayList<>();
    private ArrayList<Seguimiento> listSeguimiento = new ArrayList<>();
    private String comentario;
    private String userConect;

    public GcDetalleSolicitudBean() {
         HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        if(sesion.getAttribute("userConect") != null){
            userConect = sesion.getAttribute("userConect").toString();
            FacesContext facesContext = FacesContext.getCurrentInstance();
            Map params = facesContext.getExternalContext().getRequestParameterMap();
            detalle = new DetalleSolicitud(params.get("numeroSolicitud").toString());
            detalle.consultarDatos();
            estado.setId(detalle.getEstadoId());
            
            consultarSeguimiento();
        }
        else{
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/Cross-selling_core/faces/index.xhtml");
            } catch (IOException ex) {
                ex.printStackTrace(System.out);
            }
        }
    }

    public DetalleSolicitud getDetalle() {
        return detalle;
    }

    public void setDetalle(DetalleSolicitud detalle) {
        this.detalle = detalle;
    }

    public GcRiesgo getRiesgo() {
        return riesgo;
    }

    public void setRiesgo(GcRiesgo riesgo) {
        this.riesgo = riesgo;
    }

    public GcEstado getEstado() {
        return estado;
    }

    public void setEstado(GcEstado estado) {
        this.estado = estado;
    }

    public ArrayList<SelectItem> getEstadoItem() {
        estadoItem.clear();
        for(GcEstado e : Estado.mostrarDatos()){
            if(!e.getId().equals("a") && !e.getId().equals("b")){
                SelectItem itemEstado = new SelectItem(e.getId(), e.getDescripcion());
                estadoItem.add(itemEstado);
            }
        }
        return estadoItem;
    }

    public void setEstadoItem(ArrayList<SelectItem> estadoItem) {
        this.estadoItem = estadoItem;
    }

    public ArrayList<SelectItem> getRiesgoItem() {
        riesgoItem.clear();
        for(GcRiesgo r : Riesgo.mostrarDatos()){
            SelectItem itemRiesgo = new SelectItem(r.getId(), r.getDescripcion());
            riesgoItem.add(itemRiesgo);
        }
        return riesgoItem;
    }

    public void setRiesgoItem(ArrayList<SelectItem> riesgoItem) {
        this.riesgoItem = riesgoItem;
    }

    public ArrayList<Seguimiento> getListSeguimiento() {
        return listSeguimiento;
    }

    public void setListSeguimiento(ArrayList<Seguimiento> listSeguimiento) {
        this.listSeguimiento = listSeguimiento;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
    
    /* Metodo utilizado para consultar los comentario de seguimiento */
    private void consultarSeguimiento(){
        Seguimiento seguimiento = new Seguimiento();
        seguimiento.setNumeroSolicitud(detalle.getNumeroSolicitud());
        listSeguimiento = seguimiento.mostrarDatos();
    }
    
    /* Metodo utilizado para cambiar de estado una solicitud */
    public void cambiarEstado(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
        EntityManager em = emf.createEntityManager();
        
        em.getTransaction().begin();
        
        GcGestion gestion = new GcGestion();
        gestion.setAnalista(userConect);
        gestion.setFecha(new Date());
        gestion.setSolicitudNumeroSolicitud(new GcSolicitud(detalle.getNumeroSolicitud()));
        gestion.setEstadoId(estado);
        
        GcSolicitud solicitud = em.find(GcSolicitud.class, detalle.getNumeroSolicitud());
        solicitud.setEstadoId(estado);
        solicitud.setRiesgoId(riesgo);
        
        em.persist(gestion);
        
        em.getTransaction().commit();
        
        em.close();
        emf.close();
        
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/Cross-selling_core/faces/vista/gc/gc_enproceso.xhtml");
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
    }
    
    /* Metodo utilizado para agregar comentario de seguimiento */
    public void agregarSeguimiento(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
        EntityManager em = emf.createEntityManager();
        
        em.getTransaction().begin();
        
        GcSeguimiento seguimiento = new GcSeguimiento();
        seguimiento.setFecha(new Date());
        seguimiento.setComentario(comentario);
        seguimiento.setSolicitudNumeroSolicitud(new GcSolicitud(detalle.getNumeroSolicitud()));
        em.persist(seguimiento);
        
        em.getTransaction().commit();
        
        consultarSeguimiento();
        comentario = null;
        
        em.close();
        emf.close();
    }
}
