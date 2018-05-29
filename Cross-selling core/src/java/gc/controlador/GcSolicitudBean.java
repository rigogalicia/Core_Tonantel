package gc.controlador;

import dao.GcDestino;
import dao.GcTipo;
import dao.GcTramite;
import gc.modelo.Destino;
import gc.modelo.Solicitud;
import gc.modelo.Tipo;
import gc.modelo.Tramite;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

@ManagedBean(name = "gc_solicitud")
@ViewScoped
public class GcSolicitudBean {
    private Solicitud solicitud = new Solicitud();
    private ArrayList<SelectItem> destinoCredito = new ArrayList<>();
    private ArrayList<SelectItem> tipoCredito = new ArrayList<>();
    private ArrayList<SelectItem> tramiteCredito = new ArrayList<>();
    
    public GcSolicitudBean() {
        
    }

    public Solicitud getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
    }

    public ArrayList<SelectItem> getDestinoCredito() {
        destinoCredito.clear();
        for(GcDestino d : Destino.mostrarDatos()){
            SelectItem itemDestino = new SelectItem(d.getId(), d.getDescripcion());
            destinoCredito.add(itemDestino);
        }
        return destinoCredito;
    }

    public void setDestinoCredito(ArrayList<SelectItem> destinoCredito) {
        this.destinoCredito = destinoCredito;
    }

    public ArrayList<SelectItem> getTipoCredito() {
        tipoCredito.clear();
        for(GcTipo t : Tipo.mostrarDatos()){
            SelectItem itemTipo = new SelectItem(t.getId(), t.getDescripcion());
            tipoCredito.add(itemTipo);
        }
        return tipoCredito;
    }

    public void setTipoCredito(ArrayList<SelectItem> tipoCredito) {
        this.tipoCredito = tipoCredito;
    }

    public ArrayList<SelectItem> getTramiteCredito() {
        tramiteCredito.clear();
        for(GcTramite t : Tramite.mostrarDatos()){
            SelectItem itemTramite = new SelectItem(t.getId(), t.getDescripcion());
            tramiteCredito.add(itemTramite);
        }
        return tramiteCredito;
    }

    public void setTramiteCredito(ArrayList<SelectItem> tramiteCredito) {
        this.tramiteCredito = tramiteCredito;
    }
    
    /* Metodo utilizado para generar una nueva solicitud */
    public void generarSolicutid(ActionEvent event){
        solicitud.generar();
    }
    
    /* Metodo utilizado para consultar los datos del asociado por cif */
    public void consultarAsociado(){
        solicitud.consultarAsociado();
    }
}
