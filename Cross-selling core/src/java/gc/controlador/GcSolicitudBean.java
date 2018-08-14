package gc.controlador;

import dao.GcDestino;
import dao.GcDesventajas;
import dao.GcTipo;
import dao.GcTipocliente;
import dao.GcTramite;
import dao.GcVentajas;
import gc.modelo.Destino;
import gc.modelo.Solicitud;
import gc.modelo.Tipo;
import gc.modelo.Tipocliente;
import gc.modelo.Tramite;
import java.io.IOException;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
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
    private GcVentajas ventaja = new GcVentajas();
    private ArrayList<GcVentajas> ventajas = new ArrayList<>();
    private GcDesventajas desventaja = new GcDesventajas();
    private ArrayList<GcDesventajas> desventajas = new ArrayList<>();
    
    private boolean isNegociacion = false;
    
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

    public GcVentajas getVentaja() {
        return ventaja;
    }

    public void setVentaja(GcVentajas ventaja) {
        this.ventaja = ventaja;
    }
    
    public ArrayList<GcVentajas> getVentajas() {
        return ventajas;
    }

    public void setVentajas(ArrayList<GcVentajas> ventajas) {
        this.ventajas = ventajas;
    }

    public GcDesventajas getDesventaja() {
        return desventaja;
    }

    public void setDesventaja(GcDesventajas desventaja) {
        this.desventaja = desventaja;
    }

    public ArrayList<GcDesventajas> getDesventajas() {
        return desventajas;
    }

    public void setDesventajas(ArrayList<GcDesventajas> desventajas) {
        this.desventajas = desventajas;
    }

    public boolean isIsNegociacion() {
        return isNegociacion;
    }

    public void setIsNegociacion(boolean isNegociacion) {
        this.isNegociacion = isNegociacion;
    }
    
    /* Valida si el tramite se esta haciendo por negociacion */
    public void esNegociacion(ValueChangeEvent e){
        isNegociacion = false;
        if(Integer.parseInt(e.getNewValue().toString()) == 2){
            isNegociacion = true;
        }
    }
    
    /* Se agregan las ventajas a la lista para luego ser insertadas */
    public void addVentajas(){
        if(ventaja.getDescripcion() != null && !ventaja.getDescripcion().isEmpty() && !ventaja.getDescripcion().equals(" ")){
            ventajas.add(ventaja);
            ventaja = new GcVentajas();
        }
    }
    
    /* Se agregan las desventajas a la lista para luego ser insertadas */
    public void addDesventajas(){
        if(desventaja.getDescripcion() != null && !desventaja.getDescripcion().isEmpty() && !desventaja.getDescripcion().equals(" ")){
            desventajas.add(desventaja);
            desventaja = new GcDesventajas();
        }
    }
    
    /* Metodo utilizado para generar una nueva solicitud */
    public void generarSolicutid(ActionEvent event){
        solicitud.generar();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/Cross-selling_core/faces/vista/gc/gc_generadas.xhtml");
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
    }
    
    /* Metodo utilizado para consultar los datos del asociado por cif */
    public void consultarAsociado(){
        solicitud.consultarAsociado();
    }
}
