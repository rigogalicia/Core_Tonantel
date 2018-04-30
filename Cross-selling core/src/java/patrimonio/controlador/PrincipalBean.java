package patrimonio.controlador;

import java.io.IOException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean(name = "principal")
@RequestScoped
public class PrincipalBean {
    private String nombreUsuario;
    
    public PrincipalBean() {
        HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        if(sesion.getAttribute("nombreUsuario") != null){
            nombreUsuario = sesion.getAttribute("nombreUsuario").toString();
        }
        else{
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/Cross-selling_core/faces/index.xhtml");
            } catch (IOException ex) {
                ex.printStackTrace(System.out);
            }
        }
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
    
    public void cerrarSesion(){
        HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        try{
            sesion.removeAttribute("userConect");
            sesion.removeAttribute("operador");
            sesion.removeAttribute("nombreUsuario");
            FacesContext.getCurrentInstance().getExternalContext().redirect("/Cross-selling_core/faces/index.xhtml");
        }
        catch(Exception e){
           e.printStackTrace(System.out);
        }
    }
    
}
