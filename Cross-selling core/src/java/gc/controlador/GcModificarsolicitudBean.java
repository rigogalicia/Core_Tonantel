package gc.controlador;

import dao.GcDestino;
import dao.GcTipo;
import dao.GcTipocliente;
import dao.GcTramite;
import gc.modelo.Destino;
import gc.modelo.Solicitud;
import gc.modelo.Tipo;
import gc.modelo.Tipocliente;
import gc.modelo.Tramite;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

@ManagedBean(name = "gc_modificarsolicitud")
@ViewScoped
public class GcModificarsolicitudBean {
    private Solicitud solicitud = new Solicitud();
    private ArrayList<SelectItem> destino = new ArrayList<>();
    private ArrayList<SelectItem> tipo = new ArrayList<>();
    private ArrayList<SelectItem> tramite = new ArrayList<>();
    private ArrayList<SelectItem> tipoCliente = new ArrayList<>();
    private String msjExito = null;
    
    public GcModificarsolicitudBean() {}

    public Solicitud getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
    }

    public ArrayList<SelectItem> getDestino() {
        destino.clear();
        for(GcDestino d : Destino.mostrarDatos()){
            SelectItem itemDestino = new SelectItem(d.getId(), d.getDescripcion());
            destino.add(itemDestino);
        }
        return destino;
    }

    public void setDestino(ArrayList<SelectItem> destino) {
        this.destino = destino;
    }

    public ArrayList<SelectItem> getTipo() {
        tipo.clear();
        for(GcTipo t : Tipo.mostrarDatos()){
            SelectItem itemTipo = new SelectItem(t.getId(), t.getDescripcion());
            tipo.add(itemTipo);
        }
        return tipo;
    }

    public void setTipo(ArrayList<SelectItem> tipo) {
        this.tipo = tipo;
    }

    public ArrayList<SelectItem> getTramite() {
        tramite.clear();
        for(GcTramite t : Tramite.mostrarDatos()){
            SelectItem itemTramite = new SelectItem(t.getId(), t.getDescripcion());
            tramite.add(itemTramite);
        }
        return tramite;
    }

    public void setTramite(ArrayList<SelectItem> tramite) {
        this.tramite = tramite;
    }

    public ArrayList<SelectItem> getTipoCliente() {
        tipoCliente.clear();
        for(GcTipocliente c : Tipocliente.mostrarDatos()){
            SelectItem itemTipoCliente = new SelectItem(c.getId(), c.getDescripcion());
            tipoCliente.add(itemTipoCliente);
        }
        return tipoCliente;
    }

    public void setTipoCliente(ArrayList<SelectItem> tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public String getMsjExito() {
        return msjExito;
    }

    public void setMsjExito(String msjExito) {
        this.msjExito = msjExito;
    }
    
    // Metodo utilizado para consultar los datos de la solicitud
    public void consultarSolicitud(){
        solicitud.consultarSolicitud();
        msjExito = null;
    }
    
    // Metodo utilizado para actualizar los datos de una solicitud
    public void actualizarSolicitud(){
        solicitud.actualizarSolicitud();
        msjExito = "! Datos actualizados exitosamente";
        solicitud.consultarSolicitud();
    }
    
    // Metodo utilizado para cancelar la accion de modificar
    public void cancelarAccion(){
        solicitud = new Solicitud();
        msjExito = null;
    }
}
