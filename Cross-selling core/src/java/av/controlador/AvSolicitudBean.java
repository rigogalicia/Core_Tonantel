package av.controlador;

import av.modelo.Solicitud;
import dao.AvColindante;
import dao.AvPuntocardinal;
import dao.AvTelefono;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

@ManagedBean(name = "av_solicitud")
@ViewScoped
public class AvSolicitudBean {
    Solicitud solicitudController = new Solicitud();
    AvTelefono telefono = new AvTelefono();
    AvColindante colindante = new AvColindante();
    AvPuntocardinal punto = new AvPuntocardinal();
    
    ArrayList<SelectItem> puntosCardinales = new ArrayList<>();
    
    public AvSolicitudBean() {
    }
    public Solicitud getSolicitudController() {
        return solicitudController;
    }

    public void setSolicitudController(Solicitud solicitudController) {
        this.solicitudController = solicitudController;
    }

    public AvTelefono getTelefono() {
        return telefono;
    }

    public void setTelefono(AvTelefono telefono) {
        this.telefono = telefono;
    }

    public AvPuntocardinal getPunto() {
        return punto;
    }

    public void setPunto(AvPuntocardinal punto) {
        this.punto = punto;
    }

    public AvColindante getColindante() {
        return colindante;
    }

    public void setColindante(AvColindante colindante) {
        this.colindante = colindante;
    }

    

    public ArrayList<SelectItem> getPuntosCardinales() {
        puntosCardinales.clear();
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
        EntityManager em = emf.createEntityManager();
        
        String instruccion = "SELECT p "
                + "FROM AvPuntocardinal p";
        Query consulta = em.createQuery(instruccion);
        List<AvPuntocardinal> resultado = consulta.getResultList();
        for(AvPuntocardinal p : resultado){
            SelectItem itemPunto = new SelectItem(p.getId(), p.getDescripcion());
            puntosCardinales.add(itemPunto);
        }
        
        em.close();
        emf.close();
        
        return puntosCardinales;
    }

    public void setPuntosCardinales(ArrayList<SelectItem> puntosCardinales) {
        this.puntosCardinales = puntosCardinales;
    }
    

    /* Agrega los registros de telefono al array de objetos */
    public void agregarTelefono(){
        solicitudController.getTelefonos().add(telefono);
        telefono = new AvTelefono();
    }
    /*Agra los registros de colindantes al array de objetos*/
    public void agregarColindate(){
        colindante.setPuntocardinalId(punto);
        solicitudController.getColindantes().add(colindante);
        colindante = new AvColindante();
        punto = new AvPuntocardinal();
    }
    
    /* Este metodo controla el insert de la solicitud */
    public void insertarDatos(ActionEvent e){

        solicitudController.crearSolicitud();
    }
}
