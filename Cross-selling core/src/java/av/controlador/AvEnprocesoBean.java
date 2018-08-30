
package av.controlador;

import av.modelo.SolicitudesEnproceso;
import java.io.IOException;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;


@ManagedBean(name = "av_Enproceso")
@RequestScoped
public class AvEnprocesoBean {
    private ArrayList<SolicitudesEnproceso> listEnproceso = new ArrayList<>();
    private SolicitudesEnproceso enproceso = new SolicitudesEnproceso();
    private String userConect;


    public ArrayList<SolicitudesEnproceso> getListEnproceso() {
        return listEnproceso;
    }

    public void setListEnproceso(ArrayList<SolicitudesEnproceso> listEnproceso) {
        this.listEnproceso = listEnproceso;
    }

    public SolicitudesEnproceso getEnproceso() {
        return enproceso;
    }

    public void setEnproceso(SolicitudesEnproceso enproceso) {
        this.enproceso = enproceso;
    }
    
    public AvEnprocesoBean() {
        HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        if(sesion.getAttribute("userConect") != null){
            userConect = sesion.getAttribute(userConect).toString();
            enproceso.setUserConect(userConect);
            enproceso.setEst('b');
            listEnproceso = enproceso.mostrarDatos();
        }
        else{
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/Cross-selling_core/faces/index.xhtml");
            } catch (IOException ex) {
                ex.printStackTrace(System.out);
            }
            
        }
    }
    
}
