package gc.controlador;

import admin.modelo.Colaborador;
import gc.modelo.Negociacion;
import java.io.IOException;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean(name = "gc_negociacion")
@ViewScoped
public class GcNegociacion {
    private Negociacion negociacion = new Negociacion();
    private ArrayList<Negociacion> listaNegociacion = new ArrayList<>();
    
    public GcNegociacion() {
        HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        if(sesion.getAttribute("userConect") != null){
            negociacion.setEstado('a');
            negociacion.setIdAgencia(Colaborador.datosColaborador(sesion.getAttribute("userConect").toString()).getAgencia());
            
            listaNegociacion = negociacion.consultarDatos();
        }
        else{
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/Cross-selling_core/faces/index.xhtml");
            } catch (IOException ex) {
                ex.printStackTrace(System.out);
            }
        }
    }

    public Negociacion getNegociacion() {
        return negociacion;
    }

    public void setNegociacion(Negociacion negociacion) {
        this.negociacion = negociacion;
    }

    public ArrayList<Negociacion> getListaNegociacion() {
        return listaNegociacion;
    }

    public void setListaNegociacion(ArrayList<Negociacion> listaNegociacion) {
        this.listaNegociacion = listaNegociacion;
    }
    
}
