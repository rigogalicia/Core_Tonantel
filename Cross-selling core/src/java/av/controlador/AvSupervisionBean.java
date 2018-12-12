
package av.controlador;

import admin.modelo.Colaborador;
import admin.modelo.Puesto;
import av.modelo.Solicitud;
import av.modelo.Supervision;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

@ManagedBean(name = "av_supervision")
@ViewScoped
public class AvSupervisionBean {
    ArrayList<Supervision> listSolicitud = new ArrayList<>();
    Supervision supervision = new Supervision();
    private ArrayList<SelectItem> valuador = new ArrayList<>();

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

    public ArrayList<SelectItem> getValuador() {
        valuador.clear();
        for(Colaborador c: Colaborador.ColaboradoresPorPuesto(Puesto.idPuesto("Valuador"))){
            SelectItem itemValuador = new SelectItem(c.getUsuario(), c.getNombre());
            valuador.add(itemValuador);
            
        }
        return valuador;
    }

    public void setValuador(ArrayList<SelectItem> valuador) {
        this.valuador = valuador;
    }
    
    //Metodo para buscar por campos
    public void buscarPorCampos(){
        listSolicitud = supervision.mostrarSolicitud();
    }
    
    //Metodo para mostrar el detalle de la solicitud
    public void verDetalleSolicitud(Supervision s){
        Solicitud.detalle(s.getNumSolicitud());
    }
    
}
