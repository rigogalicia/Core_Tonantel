package admin.controlador;

import admin.modelo.Colaborador;
import admin.modelo.Password;
import java.io.IOException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;
import org.apache.commons.codec.digest.DigestUtils;

@ManagedBean(name = "admin_reset")
@ViewScoped
public class ResetBean {
    private String claveActual;
    private String nuevaClave;
    private String confirmarClave;
    private String msjError = null;
    private int operador;
    
    public ResetBean() {
        HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        operador = Integer.parseInt(sesion.getAttribute("operador").toString());
    }

    public String getClaveActual() {
        return claveActual;
    }

    public void setClaveActual(String claveActual) {
        this.claveActual = claveActual;
    }

    public String getNuevaClave() {
        return nuevaClave;
    }

    public void setNuevaClave(String nuevaClave) {
        this.nuevaClave = nuevaClave;
    }

    public String getConfirmarClave() {
        return confirmarClave;
    }

    public void setConfirmarClave(String confirmarClave) {
        this.confirmarClave = confirmarClave;
    }

    public String getMsjError() {
        return msjError;
    }

    public void setMsjError(String msjError) {
        this.msjError = msjError;
    }
    
    /* Metodo utilizado para cambiar la clave del usuario */
    public void cambiarClave(ActionEvent e){
        msjError = null;
        if(isClaveActual()){
            Password p = new Password(nuevaClave);
            if(p.verificar()){
                if(nuevaClave.equals(confirmarClave)){
                    Colaborador c = new Colaborador();
                    c.setOperador(operador);
                    c.setClave(DigestUtils.md5Hex(nuevaClave));
                    c.resetClave();
                    
                    /* Redirecciona a la pagina princial */
                    try {
                        FacesContext.getCurrentInstance().getExternalContext().redirect("/Cross-selling_core/faces/index.xhtml");
                    } catch (IOException ex) {
                        ex.printStackTrace(System.out);
                    }
                    
                }
                else{
                    msjError = "! El nuevo password no es igual a confirmar password";
                }
            }
            else{
                msjError = p.getMensaje();
            }
        }
        else{
            msjError = "! El password actual no es correcto";
        }
    }
  
    /* Metodo utilizado para validar la clave actual */
    private boolean isClaveActual(){
        boolean result = false;
        
        Colaborador c = new Colaborador();        
        c.setOperador(operador);
        c = c.datosColaborador();
        
        if(c.getClave().equals(DigestUtils.md5Hex(claveActual))){
            result = true;
        }
        
        return result;
    }
}
