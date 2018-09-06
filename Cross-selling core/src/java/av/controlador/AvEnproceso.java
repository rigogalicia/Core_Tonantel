
package av.controlador;

import av.modelo.Solicitud;
import av.modelo.SolicitudesEnproceso;
import java.io.IOException;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpSession;


@ManagedBean (name = "av_Enproceso")
@ViewScoped
public class AvEnproceso {
private ArrayList<SolicitudesEnproceso> listEnproceso = new ArrayList<>();
private SolicitudesEnproceso Enproceso = new SolicitudesEnproceso();
private String userConect;

    public AvEnproceso() {
        HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        if(sesion.getAttribute("userConect") != null){
            userConect = sesion.getAttribute("userConect").toString();
            Enproceso.setUserConect(userConect);
            Enproceso.setEst('b');
            listEnproceso = Enproceso.mostrarDatos();
        }
        else
        {
            try {
               FacesContext.getCurrentInstance().getExternalContext().redirect("/Cross-selling_core/faces/index.xhtml"); 
            } catch (IOException ex) {
                ex.printStackTrace(System.out);
            }
            
        }
    }

    public ArrayList<SolicitudesEnproceso> getListEnproceso() {
        return listEnproceso;
    }

    public void setListEnproceso(ArrayList<SolicitudesEnproceso> listEnproceso) {
        this.listEnproceso = listEnproceso;
    }

    public SolicitudesEnproceso getEnproceso() {
        return Enproceso;
    }

    public void setEnproceso(SolicitudesEnproceso Enproceso) {
        this.Enproceso = Enproceso;
    }
    
    public void filtrarDatos(ValueChangeEvent e){
    char newEst[] = e.getNewValue().toString().toCharArray();
    Enproceso.setEst(newEst[0]);
    listEnproceso = Enproceso.mostrarDatos(); 
    }
    
    /* Este metodo se utiliza para mostrar el detalle de la solicitud */
    public void verDetalleSolicitud(SolicitudesEnproceso s){
        Solicitud.detalle(s.getNumeroSolicitud());
    }
    
}
