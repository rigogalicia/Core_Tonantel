package av.controlador;

import av.modelo.Solicitud;
import dao.AvPuntocardinal;
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
    private Solicitud solicitudController = new Solicitud();
    private String msjTelefono;
    private String msjColindante;
    ArrayList<SelectItem> puntosCardinales = new ArrayList<>();
    private int tipoSolicitud = 0;

    public AvSolicitudBean() {
    }
    
    public Solicitud getSolicitudController() {
        return solicitudController;
    }

    public void setSolicitudController(Solicitud solicitudController) {
        this.solicitudController = solicitudController;
    }

    public String getMsjTelefono() {
        return msjTelefono;
    }

    public void setMsjTelefono(String msjTelefono) {
        this.msjTelefono = msjTelefono;
    }

    public String getMsjColindante() {
        return msjColindante;
    }

    public void setMsjColindante(String msjColindante) {
        this.msjColindante = msjColindante;
    }

    public int getTipoSolicitud() {
        return tipoSolicitud;
    }

    public void setTipoSolicitud(int tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
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
    
    /* Metodo para verificar si es publica o privada */
    public void tipoDocumento(){
        if(solicitudController.getDocumento().getTipo() == 'a'){
            tipoSolicitud = 1;
        }
        else{
            tipoSolicitud = 2;
        }
    }
    
    // Valida los campos que contienen arrays
    private boolean isComplit(){
        boolean result = false;
        
        if(solicitudController.getTelefonos().isEmpty()){
            msjTelefono = "Ingrese almenos un telefono";
        }
        else if(solicitudController.getColindantes().isEmpty()){
            msjColindante = "Ingrese los datos de colindates";
        }
        else{
            result = true;
        }
        
        return result;
    }
    
    /* Este metodo controla el insert de la solicitud */
    public void insertarDatos(ActionEvent e){
        if(isComplit()){
            solicitudController.crearSolicitud();
        }
    }
}
