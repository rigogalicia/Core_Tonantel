package rally.controlador;

import dao.RallyCartera;
import java.io.IOException;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import rally.modelo.Cartera;

@ManagedBean(name = "cartera")
@RequestScoped
public class CarteraBean {
     private String nombreUsuario;
     private int operador;
     private ArrayList<RallyCartera> listCartera = new ArrayList<>();
     
    public CarteraBean() {
        HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        if(sesion.getAttribute("nombreUsuario") != null){
            nombreUsuario = sesion.getAttribute("nombreUsuario").toString();
            operador = Integer.parseInt(sesion.getAttribute("operador").toString());
            
            obtenerDatos();
        }
        else{
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/Cross-selling_core/faces/index.xhtml");
            } catch (IOException ex) {
                ex.printStackTrace(System.out);
            }
        }
    }

    public ArrayList<RallyCartera> getListCartera() {
        return listCartera;
    }

    public void setListCartera(ArrayList<RallyCartera> listCartera) {
        this.listCartera = listCartera;
    }
    
    /* Metodo utilizado para consultar la cartera por colaborador */
    private void obtenerDatos(){
        Cartera cartera = new Cartera(operador);
        listCartera = cartera.obtenerCartera();
    }
    
}
