
package rally.controlador;
import dao.RallySeguimientoCartera;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;
import rally.modelo.Cartera;


@ManagedBean(name = "seguimiento")
@ViewScoped
public class SeguimientoBean {
    private String nombreUsuario;
    private String numeroPrestamo;
    private String nombreAsociado;
    private String fecha;
    private RallySeguimientoCartera seguimientoCartera = new RallySeguimientoCartera();
    private List<RallySeguimientoCartera> listaSeguimiento = new ArrayList<>();
    
    public SeguimientoBean() {
        HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        if(sesion.getAttribute("nombreUsuario") != null){
            nombreUsuario = sesion.getAttribute("nombreUsuario").toString();
            FacesContext facesContext = FacesContext.getCurrentInstance();
            Map params = facesContext.getExternalContext().getRequestParameterMap();
            numeroPrestamo = (String) params.get("numeroCredito");
            nombreAsociado = (String) params.get("nombreAsociado");
            consultarComentario();
        }
    }

    public String getNumeroPrestamo() {
        return numeroPrestamo;
    }

    public void setNumeroPrestamo(String numeroPrestamo) {
        this.numeroPrestamo = numeroPrestamo;
    }

    public String getNombreAsociado() {
        return nombreAsociado;
    }

    public void setNombreAsociado(String nombreAsociado) {
        this.nombreAsociado = nombreAsociado;
    }

    public String getFecha() {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        fecha = formato.format(new Date());
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public RallySeguimientoCartera getSeguimientoCartera() {
        return seguimientoCartera;
    }

    public void setSeguimientoCartera(RallySeguimientoCartera seguimientoCartera) {
        this.seguimientoCartera = seguimientoCartera;
    }

    public List<RallySeguimientoCartera> getListaSeguimiento() {
        return listaSeguimiento;
    }

    public void setListaSeguimiento(List<RallySeguimientoCartera> listaSeguimiento) {
        this.listaSeguimiento = listaSeguimiento;
    }
    
    /* Consulta el listado de seguimientos por numero de prestamo */
    private void consultarComentario(){
        if(numeroPrestamo != null){
           listaSeguimiento = Cartera.consultarSeguimiento(numeroPrestamo);
        }
    }
    
    /* Metodo utilizado para insertar un registro de seguimiento */
    public void agregarSeguimiento(ActionEvent event){
        seguimientoCartera.setAsesor(nombreUsuario);
        seguimientoCartera.setNumeroPrestamo(numeroPrestamo);
        seguimientoCartera.setFecha(new Date());
        Cartera.agregarSeguimiento(seguimientoCartera);
        consultarComentario();
        
        seguimientoCartera = new RallySeguimientoCartera();
    }
}
