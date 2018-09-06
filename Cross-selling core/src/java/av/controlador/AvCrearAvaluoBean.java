
package av.controlador;

import av.modelo.CrearAvaluo;
import av.modelo.SolicitudesEnproceso;
import java.io.IOException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;


@ManagedBean(name = "av_CrearAvaluo")
@ViewScoped
public class AvCrearAvaluoBean {
    private String numeroSolicitud = null;
    private CrearAvaluo llenarvaluo = new CrearAvaluo();

    public AvCrearAvaluoBean() {
        
    }

    public String getNumeroSolicitud() {
        return numeroSolicitud;
    }

    public void setNumeroSolicitud(String numeroSolicitud) {
        this.numeroSolicitud = numeroSolicitud;
    }

    public CrearAvaluo getLlenarvaluo() {
        return llenarvaluo;
    }

    public void setLlenarvaluo(CrearAvaluo llenarvaluo) {
        this.llenarvaluo = llenarvaluo;
    }
    
    /* Metodo utiliado para seleccionar la solicitud a crear */
    public void selectSolicitud(SolicitudesEnproceso s){
        try {
            numeroSolicitud = s.getNumeroSolicitud();
            FacesContext.getCurrentInstance().getExternalContext().redirect("/Cross-selling_core/faces/vista/av/av_crearavaluo.xhtml");
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
    }

    public void insertarDatos(ActionEvent e){
        System.out.println("Se ejecuta...........");
        System.out.println(llenarvaluo.getInmueble().getDireccionFisica());
        System.out.println(llenarvaluo.getInmueble().getCoordenadas());
        System.out.println(llenarvaluo.getAvaluo().getFechahora());
    }
    
}
