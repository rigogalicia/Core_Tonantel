
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
    
    //Metodo para calcular el total del avaluo
    public void calcularTotal(){
        crearAvaluo.total();
    }

    public void insertarDatos(ActionEvent e){
        System.out.println(numeroSolicitud);
        System.out.println(crearAvaluo.getAvaluo().getFechahora());
        System.out.println(crearAvaluo.getArea().getRegistrada());
        System.out.println(crearAvaluo.getArea().getFisica());
        System.out.println(crearAvaluo.getArea().getAvaluar());
        System.out.println(crearAvaluo.getArea().getConstruida());
        System.out.println(crearAvaluo.getArea().getExceso());
        System.out.println(crearAvaluo.getArea().getFrenteyfondo());
        
        System.out.println(crearAvaluo.getConstruccion().getMuro());
        System.out.println(crearAvaluo.getConstruccion().getTecho());
        System.out.println(crearAvaluo.getConstruccion().getCielo());
        System.out.println(crearAvaluo.getConstruccion().getPiso());
        System.out.println(crearAvaluo.getConstruccion().getNiveles());
        System.out.println(crearAvaluo.getConstruccion().getElectricidad());
        System.out.println(crearAvaluo.getConstruccion().getSanitario());
        System.out.println(crearAvaluo.getConstruccion().getAmbiente());
        System.out.println(crearAvaluo.getConstruccion().getAgua());
        System.out.println(crearAvaluo.getConstruccion().getDestino());
        System.out.println(crearAvaluo.getConstruccion().getRiesgo());
        System.out.println(crearAvaluo.getConstruccion().getFactoresPositivos());
        System.out.println(crearAvaluo.getInmueble().getObservaciones());
    }
    
}
