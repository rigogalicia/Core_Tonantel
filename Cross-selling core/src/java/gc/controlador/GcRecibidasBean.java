package gc.controlador;

import dao.GcDestino;
import dao.GcTipo;
import dao.GcTramite;
import gc.modelo.Destino;
import gc.modelo.SolicitudesRecibidas;
import gc.modelo.Tipo;
import gc.modelo.Tramite;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

@ManagedBean(name = "gc_recibidas")
@ViewScoped
public class GcRecibidasBean {
    private SolicitudesRecibidas recibidas = new SolicitudesRecibidas();
    private ArrayList<SolicitudesRecibidas> listaRecibidas = new ArrayList<>();
    private ArrayList<SelectItem> tipoCredito = new ArrayList<>();
    private ArrayList<SelectItem> destinoCredito = new ArrayList<>();
    private ArrayList<SelectItem> tramiteCredito = new ArrayList<>();
    private boolean filter = false;
    
    public GcRecibidasBean() {
        listaRecibidas = recibidas.mostrarDatos();
    }

    public SolicitudesRecibidas getRecibidas() {
        return recibidas;
    }

    public void setRecibidas(SolicitudesRecibidas recibidas) {
        this.recibidas = recibidas;
    }

    public ArrayList<SolicitudesRecibidas> getListaRecibidas() {
        return listaRecibidas;
    }

    public void setListaRecibidas(ArrayList<SolicitudesRecibidas> listaRecibidas) {
        this.listaRecibidas = listaRecibidas;
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

    public boolean isFilter() {
        return filter;
    }

    public void setFilter(boolean filter) {
        this.filter = filter;
    }
    
    /* Metodo utilizado para activar los filtros */
    public void activarFiltros(){
        filter = true;
    }
    
    /* Metodo utilizado para borrar los filtros */
    public void borrarFiltros(){
        recibidas.setNumeroSolicitud(null);
        recibidas.setNombreAsociado(null);
        recibidas.setCif(null);
        recibidas.setTipoId(0);
        recibidas.setDestinoId(0);
        recibidas.setTramiteId(0);
        listaRecibidas = recibidas.mostrarDatos();
        filter = false;
    }
    
    /* Metodo utilizado para mostrar las solicitudes recibidas filtradas por campo */
    public void mostrarPorCampo(){
        listaRecibidas = recibidas.mostrarDatos();
    }
    
    public void mostrarPorTipo(ValueChangeEvent e){
        recibidas.setTipoId(Integer.parseInt(e.getNewValue().toString()));
        listaRecibidas = recibidas.mostrarDatos();
    }
    
    public void mostrarPorDestino(ValueChangeEvent e){
        recibidas.setDestinoId(Integer.parseInt(e.getNewValue().toString()));
        listaRecibidas = recibidas.mostrarDatos();
    }
    
    public void mostrarPorTramite(ValueChangeEvent e){
        recibidas.setTramiteId(Integer.parseInt(e.getNewValue().toString()));
        listaRecibidas = recibidas.mostrarDatos();
    }
}
