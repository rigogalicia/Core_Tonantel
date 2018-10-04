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
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

@ManagedBean(name = "gc_solicitud")
@ViewScoped
public class GcSolicitudBean {
    private Solicitud solicitud = new Solicitud();
    private ArrayList<SelectItem> destinoCredito = new ArrayList<>();
    private ArrayList<SelectItem> tipoCredito = new ArrayList<>();
    private ArrayList<SelectItem> tramiteCredito = new ArrayList<>();
    private ArrayList<SelectItem> tipoCliente = new ArrayList<>();
    private boolean isNegociacion = false;
    private String ventaja;
    private String desventaja;
    
    public GcSolicitudBean() {}

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

    public ArrayList<SelectItem> getTipoCliente() {
        tipoCliente.clear();
        for(GcTipocliente t : Tipocliente.mostrarDatos()){
            SelectItem itemTipocliente = new SelectItem(t.getId(), t.getDescripcion());
            tipoCliente.add(itemTipocliente);
        }
        return tipoCliente;
    }

    public void setTipoCliente(ArrayList<SelectItem> tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public boolean isIsNegociacion() {
        return isNegociacion;
    }

    public void setIsNegociacion(boolean isNegociacion) {
        this.isNegociacion = isNegociacion;
    }

    public String getVentaja() {
        return ventaja;
    }

    public void setVentaja(String ventaja) {
        this.ventaja = ventaja;
    }

    public String getDesventaja() {
        return desventaja;
    }

    public void setDesventaja(String desventaja) {
        this.desventaja = desventaja;
    }

    /* Valida si el tramite se esta haciendo por negociacion */
    public void esNegociacion(ValueChangeEvent e){
        isNegociacion = false;
        if(Integer.parseInt(e.getNewValue().toString()) == 2){
            isNegociacion = true;
        }
    }
    
    /* Metodo para agregar una ventaja a la ficha de negociacion */
    public void addVentaja(){
        solicitud.getVentajas().add(ventaja);
        ventaja = "";
    }
    
    /* Metodo para eliminar una ventaja de la ficha de negociacion */
    public void removeVentajas(String v){
        solicitud.getVentajas().remove(v);
    }
    
    public void addDesventaja(){
        solicitud.getDesventajas().add(desventaja);
        desventaja = "";
    }
    
    public void removeDesventaja(String d){
        solicitud.getDesventajas().remove(d);
    }
    
    /* Metodo utilizado para generar una nueva solicitud */
    public void generarSolicutid(ActionEvent event){
        solicitud.generar(isNegociacion);
    }
    
    /* Metodo utilizado para consultar los datos del asociado por cif */
    public void consultarAsociado(){
        solicitud.consultarAsociado();
    }
}
