package av.controlador;

import av.modelo.Solicitud;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;


@ManagedBean(name = "av_solicitud")
@ViewScoped
public class AvSolicitudBean {
    private Solicitud solicitudController = new Solicitud();
    private String msjTelefono;
    private String msjColindante;
    private int tipoSolicitud = 0;

    public AvSolicitudBean() {}
    
    public Solicitud getSolicitudController() {
        return solicitudController;
    }

    public void setSolicitudController(Solicitud solicitudController) {
        this.solicitudController = solicitudController;
    }

    public String getMsjTelefono() {
        return msjTelefono;
    }

    public void setMsjTelefono(String msjTelefono) {
        this.msjTelefono = msjTelefono;
    }

    public String getMsjColindante() {
        return msjColindante;
    }

    public void setMsjColindante(String msjColindante) {
        this.msjColindante = msjColindante;
    }

    public int getTipoSolicitud() {
        return tipoSolicitud;
    }

    public void setTipoSolicitud(int tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }
    
    /* Metodo para verificar si es publica o privada */
    public void tipoDocumento(){
        if(null != solicitudController.getDocumento().getTipo())switch (solicitudController.getDocumento().getTipo()) {
            case 'a':
                tipoSolicitud = 1;
                solicitudController.limpiarDocumento();
                break;
            case 'b':
                tipoSolicitud = 2;
                solicitudController.limpiarDocumento();
                break;
            default:
                tipoSolicitud = 3;
                solicitudController.limpiarDocumento();
                break;
        }
    }
    
    // Valida los campos que contienen arrays
    private boolean isComplit(){
        boolean result = false;
        msjTelefono = null;
        msjColindante = null;
        if(solicitudController.getTelefonos().isEmpty()){
            msjTelefono = "Ingrese almenos un teléfono";
        }
        else if(solicitudController.getDocumento().getTipo() == 'a' || solicitudController.getDocumento().getTipo() == 'c'){
                if(solicitudController.getColindantes().isEmpty()){
                msjColindante = "Ingrese los datos de colindates";
                
            }
            else{
                result = true;
            }
        }

        else{
            result = true;
        }
        
        return result;
    }
        
    /* Este metodo controla el insert de la solicitud */
    public void insertarDatos(ActionEvent e){
        solicitudController.setMsjConsultar("");
        if(isComplit()){
            if(!solicitudController.existeSolicitud()) {
                solicitudController.crearSolicitud();
            }
            else{
                solicitudController.setMsjConsultar("El numero de solicitud existe para otro usuario");
            }
        }
    }
    
    //Metodo controlador de consultar los datos de la solicitud
    public void consultarDatos(){
        solicitudController.setMsjConsultar("");
        if(!solicitudController.existeSolicitud()) {
            solicitudController.consultarSolicitud();
        }
        else{
            solicitudController.setMsjConsultar("El numero de solicitud existe para otro usuario");
        }
    }
    
    //Metodo que controla el update de la solicitud
    public void updateSolicitud(){
        if(isComplit()){
           solicitudController.updateSolicitud();  
        }       
    }
}
