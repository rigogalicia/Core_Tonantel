package ed.controlador;

import ed.modelo.Evaluacion;
import ed.modelo.Interrogante;
import java.io.IOException;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean(name = "ed_interrogantes")
@ViewScoped
public class EdInterrogantesBean {
    private String userConect;
    private Interrogante interrogante = new Interrogante();
    
    public EdInterrogantesBean() {
        // Metodo constructor de la clase
        HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        if(sesion.getAttribute("userConect") != null){
            userConect = sesion.getAttribute("userConect").toString();
            FacesContext facesContext = FacesContext.getCurrentInstance();
            Map params = facesContext.getExternalContext().getRequestParameterMap();
            interrogante.setEvaluacion(params.get("Evaluacion").toString());
            System.out.println("Nombre de evaluacion : " + this.interrogante.getEvaluacion());
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

    public Interrogante getInterrogante() {
        return interrogante;
    }

    public void setInterrogante(Interrogante interrogante) {
        this.interrogante = interrogante;
    }

}
