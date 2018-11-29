
package av.controlador;

import admin.modelo.Colaborador;
import admin.modelo.Puesto;
import av.modelo.Supervision;
import java.util.ArrayList;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;



@ManagedBean(name = "av_asignar")
@ViewScoped
public class AvAsignarBean {
private Supervision supervision = new Supervision();
private ArrayList<SelectItem> valuadores =new ArrayList<>();

    public AvAsignarBean() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Map params = facesContext.getExternalContext().getRequestParameterMap();
        
        supervision.setNumSolicitud(params.get("numeroSolicitud").toString());
        supervision.consultarSolicitud();
    }

    public Supervision getSupervision() {
        return supervision;
    }

    public void setSupervision(Supervision supervision) {
        this.supervision = supervision;
    }

    public ArrayList<SelectItem> getValuadores() {
        valuadores.clear();
        for(Colaborador c: Colaborador.ColaboradoresPorPuesto(Puesto.idPuesto("Valuador"))){
            SelectItem itemValuador = new SelectItem(c.getUsuario(), c.getNombre());
            valuadores.add(itemValuador);
            
        }
        return valuadores;
    }

    public void setValuadores(ArrayList<SelectItem> valuadores) {
        this.valuadores = valuadores;
    }

    public void asiganrValuador(){
        supervision.asiganrAvaluo();
    }

}
