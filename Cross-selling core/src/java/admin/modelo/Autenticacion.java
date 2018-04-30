package admin.modelo;

import java.io.IOException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.HttpSession;

@ManagedBean
@RequestScoped
public class Autenticacion {
    private String usuario;
    private String clave;

    public Autenticacion() {
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
    
    // Valida la clave que tenga los caracteres requeridos
    public void validate(FacesContext arg0, UIComponent arg1, Object arg2) 
            throws ValidatorException{
        Password password = new Password((String)arg2);
        if(!password.verificar()){
            throw new ValidatorException(new FacesMessage(password.getMensaje()));
        }
    }
    
    // Valida que el usuario se encuentre en la base de datos
    public void ingresar() throws IOException{
        Usuario user = new Usuario();
        user.setUsuario(this.usuario);
        user.setClave(this.clave);
        
        if(user.estaAutorizado()){
            HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            sesion.setAttribute("userConect", user.getUsuario());
            sesion.setAttribute("operador", user.getOperador());
            sesion.setAttribute("nombreUsuario", user.getNombre());
            FacesContext.getCurrentInstance().getExternalContext().redirect("vista/principal.xhtml");
        }
        
    }
}
