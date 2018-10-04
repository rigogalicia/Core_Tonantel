
package av.controlador;

import av.modelo.CrearAvaluo;
import dao.AvPuntocardinal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

@ManagedBean(name = "av_crearavaluo")
@ViewScoped
public class AvCrearAvaluoBean {
    private String numeroSolicitud;
    private CrearAvaluo crearAvaluo = new CrearAvaluo();
    ArrayList<SelectItem> puntosCardinales = new ArrayList<>();
    
    private String msjColindantes;
    private String msjDetalle;
    private String msjConstruccion;

    public AvCrearAvaluoBean() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Map params = facesContext.getExternalContext().getRequestParameterMap();
        numeroSolicitud = params.get("numeroSolicitud").toString();
        crearAvaluo.getSolicitud().setNumeroSolicitud(numeroSolicitud);
        crearAvaluo.consultarSolicitud();
    }

    public String getNumeroSolicitud() {
        return numeroSolicitud;
    }

    public void setNumeroSolicitud(String numeroSolicitud) {
        this.numeroSolicitud = numeroSolicitud;
    }

    public CrearAvaluo getCrearAvaluo() {
        return crearAvaluo;
    }

    public void setCrearAvaluo(CrearAvaluo crearAvaluo) {
        this.crearAvaluo = crearAvaluo;
    }

    public ArrayList<SelectItem> getPuntosCardinales() {
        puntosCardinales.clear();
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
        EntityManager em = emf.createEntityManager();
        
        String instruccion = "SELECT p "
                +" FROM AvPuntocardinal p ";
        
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

    public String getMsjColindantes() {
        return msjColindantes;
    }

    public void setMsjColindantes(String msjColindantes) {
        this.msjColindantes = msjColindantes;
    }

    public String getMsjDetalle() {
        return msjDetalle;
    }

    public void setMsjDetalle(String msjDetalle) {
        this.msjDetalle = msjDetalle;
    }

    public String getMsjConstruccion() {
        return msjConstruccion;
    }

    public void setMsjConstruccion(String msjConstruccion) {
        this.msjConstruccion = msjConstruccion;
    }
    
    //Metodo utilizado para validar el Array de Colindantes
    public boolean isComplit(){
        boolean resultado = false;
        msjColindantes = null;
        msjDetalle = null;
        msjConstruccion = null;
        if(crearAvaluo.getColindantes().isEmpty()){
            msjColindantes = "Ingrese los Datos de Colindantes";
        }

        else if(crearAvaluo.getDetalleAvaluo().isEmpty()){
            msjDetalle = "Ingrese los Datos del Detalle";
        }
        else{
            resultado = true;
        }
        return resultado;
    }
    
    //Metodo para calcular el total del avaluo
    public void calcularTotal(){
        crearAvaluo.total();
    }

    public void insertarDatos(ActionEvent e){
        if(isComplit()){
            crearAvaluo.insert();
        }
    }
    
}
