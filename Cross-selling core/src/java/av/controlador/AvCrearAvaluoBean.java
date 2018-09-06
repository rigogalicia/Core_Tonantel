
package av.controlador;

import av.modelo.CrearAvaluo;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;


@ManagedBean(name = "av_CrearAvaluo")
@ViewScoped
public class AvCrearAvaluoBean {
    private CrearAvaluo llenarvaluo = new CrearAvaluo();

    public AvCrearAvaluoBean() {
    }

    public CrearAvaluo getLlenarvaluo() {
        return llenarvaluo;
    }

    public void setLlenarvaluo(CrearAvaluo llenarvaluo) {
        this.llenarvaluo = llenarvaluo;
    }

    public void insertarDatos(ActionEvent e){
        System.out.println("Se ejecuta...........");
        System.out.println(llenarvaluo.getInmueble().getDireccionFisica());
        System.out.println(llenarvaluo.getInmueble().getCoordenadas());
        System.out.println(llenarvaluo.getAvaluo().getFechahora());
    }
    
}
