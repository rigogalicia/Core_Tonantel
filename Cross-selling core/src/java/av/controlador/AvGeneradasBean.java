
package av.controlador;

import av.modelo.Solicitud;
import av.modelo.SolicitudesGeneradas;
import java.io.IOException;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpSession;


@ManagedBean(name = "av_Generadas")
@ViewScoped
public class AvGeneradasBean {
    private ArrayList<SolicitudesGeneradas> listGeneradas = new ArrayList<>();
    private SolicitudesGeneradas generadas = new SolicitudesGeneradas();
    private String userConect;

    public AvGeneradasBean() {
        HttpSession sesion = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        if(sesion.getAttribute("userConect") != null){
            userConect = sesion.getAttribute("userConect").toString();
            generadas.setEst('a');
            listGeneradas = generadas.mostraDatos(userConect);
        }
        else{
            try {
               FacesContext.getCurrentInstance().getExternalContext().redirect("/Cross-selling_core/faces/index.xhtml"); 
            } catch (IOException ex) {
                ex.printStackTrace(System.out);
            }
        }
    }
    
    public ArrayList<SolicitudesGeneradas> getListGeneradas() {
        return listGeneradas;
    }

    public void setListGeneradas(ArrayList<SolicitudesGeneradas> listGeneradas) {
        this.listGeneradas = listGeneradas;
    }

    public SolicitudesGeneradas getGeneradas() {
        return generadas;
    }

    public void setGeneradas(SolicitudesGeneradas generadas) {
        this.generadas = generadas;
    }
    
    public String getUserConect() {
        return userConect;
    }

    public void setUserConect(String userConect) {
        this.userConect = userConect;
    }
    
    public void filtrarDatos(ValueChangeEvent e){
        char newEst[] = e.getNewValue().toString().toCharArray();
        generadas.setEst(newEst[0]);
        listGeneradas = generadas.mostraDatos(userConect);
    }
    
    /* Este metodo es utilizado para controlar el detalle de solicitud */
    public void verDetalleSolicitud(SolicitudesGeneradas s){
        Solicitud.detalle(s.getNumeroSolicitud());
    }
}
