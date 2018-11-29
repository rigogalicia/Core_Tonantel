
package av.controlador;

import av.modelo.Supervision;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;

@ManagedBean(name = "av_supervision")
@ViewScoped
public class AvSupervisionBean {
    ArrayList<Supervision> listSolicitud = new ArrayList<>();
    Supervision supervision = new Supervision();

    public AvSupervisionBean() {
        supervision.setEst('a');
        listSolicitud = supervision.mostrarSolicitud();
        
    }

    public ArrayList<Supervision> getListSolicitud() {
        return listSolicitud;
    }

    public void setListSolicitud(ArrayList<Supervision> listSolicitud) {
        this.listSolicitud = listSolicitud;
    }

    public Supervision getSupervision() {
        return supervision;
    }

    public void setSupervision(Supervision supervision) {
        this.supervision = supervision;
    }
    
    //Metodo para filtrar las solicitudes
    public void filtrarDatos(ValueChangeEvent e){
        char newEstado[] = e.getNewValue().toString().toCharArray();
        supervision.setEst(newEstado[0]);
        listSolicitud = supervision.mostrarSolicitud();
    }
    
}
