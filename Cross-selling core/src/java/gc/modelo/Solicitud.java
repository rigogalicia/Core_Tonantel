package gc.modelo;

import dao.GcAsociado;
import dao.GcDestino;
import dao.GcSolicitud;
import dao.GcTipo;
import dao.GcTramite;
import java.io.IOException;
import java.util.Date;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;

public class Solicitud {
    private GcAsociado asociadoGc = new GcAsociado();
    private GcSolicitud solicitudGc = new GcSolicitud();
    private GcDestino destinoGc = new GcDestino();
    private GcTipo tipoGc = new GcTipo();
    private GcTramite tramiteGc = new GcTramite();
    private String userConect;
    
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

    public GcAsociado getAsociadoGc() {
        return asociadoGc;
    }

    public void setAsociadoGc(GcAsociado asociadoGc) {
        this.asociadoGc = asociadoGc;
    }

    public GcSolicitud getSolicitudGc() {
        return solicitudGc;
    }

    public void setSolicitudGc(GcSolicitud solicitudGc) {
        this.solicitudGc = solicitudGc;
    }

    public GcDestino getDestinoGc() {
        return destinoGc;
    }

    public void setDestinoGc(GcDestino destinoGc) {
        this.destinoGc = destinoGc;
    }

    public GcTipo getTipoGc() {
        return tipoGc;
    }

    public void setTipoGc(GcTipo tipoGc) {
        this.tipoGc = tipoGc;
    }

    public GcTramite getTramiteGc() {
        return tramiteGc;
    }

    public void setTramiteGc(GcTramite tramiteGc) {
        this.tramiteGc = tramiteGc;
    }
    
    /* Metodo utilizado para generar una solicitud */
    public void generar(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
        EntityManager em = emf.createEntityManager();
        
        em.getTransaction().begin();
        
        solicitudGc.setAsesorFinanciero(userConect);
        solicitudGc.setFecha(new Date());
        solicitudGc.setEstado('a');
        solicitudGc.setAsociadoCif(asociadoGc);
        solicitudGc.setDestinoId(destinoGc);
        solicitudGc.setTipoId(tipoGc);
        solicitudGc.setTramiteId(tramiteGc);
        em.merge(asociadoGc);
        em.persist(solicitudGc);
        
        em.getTransaction().commit();
        
        em.close();
        emf.close();
    }
   
    /* Metodo utilizado para obtener el nombre del asociado por el cif */
    public void consultarAsociado(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
        EntityManager em = emf.createEntityManager();
        
        String instruccion = "SELECT a "
                + "FROM GcAsociado a "
                + "WHERE a.cif = :miCif";
        Query consulta = em.createQuery(instruccion);
        consulta.setParameter("miCif", asociadoGc.getCif());
        
        try{
            asociadoGc = (GcAsociado) consulta.getSingleResult();
        }
        catch(Exception e){
            e.printStackTrace(System.out);
        }
        
        em.close();
        emf.close();
    }
}