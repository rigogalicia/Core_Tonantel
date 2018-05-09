package patrimonio.controlador;

import admin.modelo.Permiso;
import admin.modelo.Privilegio;
import java.io.IOException;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean(name = "principal")
@RequestScoped
public class PrincipalBean {
    private String nombreUsuario;
    private ArrayList<Permiso> permisos = new ArrayList<>();
    
    public PrincipalBean() {
        HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        if(sesion.getAttribute("nombreUsuario") != null){
            nombreUsuario = sesion.getAttribute("nombreUsuario").toString();
            Permiso p = new Permiso();
            p.setIdUsuario(sesion.getAttribute("userConect").toString());
            permisos = p.mostrarPermisos();
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

    public ArrayList<Permiso> getPermisos() {
        return permisos;
    }

    public void setPermisos(ArrayList<Permiso> permisos) {
        this.permisos = permisos;
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
