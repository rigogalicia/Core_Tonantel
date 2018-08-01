package cc.controlador;

import cc.modelo.Registrados;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean (name = "cc_registrados")
@ViewScoped
public class CcRegistradosBean {
    private ArrayList<Registrados> registrados = new ArrayList<>();
    
    public CcRegistradosBean() {
        HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        if(sesion.getAttribute("userConect") != null){
            String userConect = sesion.getAttribute("userConect").toString();
            Registrados reg = new Registrados(userConect);
            registrados = reg.chequesRegistrados();
        }
    }

    public ArrayList<Registrados> getRegistrados() {
        return registrados;
    }

    public void setRegistrados(ArrayList<Registrados> registrados) {
        this.registrados = registrados;
    }
    
}
