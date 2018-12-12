
package av.controlador;

import admin.modelo.Colaborador;
import admin.modelo.Puesto;
import av.modelo.Supervision;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;



@ManagedBean(name = "av_asignar")
@ViewScoped
public class AvAsignarBean {
private Supervision supervision = new Supervision();
private ArrayList<SelectItem> valuadores =new ArrayList<>();

private String userConect;

    public AvAsignarBean() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession sesion= (HttpSession) facesContext.getExternalContext().getSession(true);
        Map params = facesContext.getExternalContext().getRequestParameterMap();
        if(sesion.getAttribute("userConect")!= null){
            userConect = sesion.getAttribute("userConect").toString();
            supervision.setAsignador(userConect);
            supervision.setNumSolicitud(params.get("numeroSolicitud").toString());
            supervision.consultarSolicitud();
  
        }
        else{
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/Cross-selling_core/faces/index.xhtml");
            } catch (IOException ex) {
                ex.printStackTrace(System.out);
            }
        } 
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
        supervision.asignar();
    }
    
    public void reasignarValuador(){
        supervision.reasignar();
    }
}
