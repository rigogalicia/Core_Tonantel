package gc.controlador;

import dao.GcDestino;
import dao.GcEstado;
import dao.GcTipo;
import dao.GcTramite;
import gc.modelo.Destino;
import gc.modelo.Estado;
import gc.modelo.SolicitudesGeneradas;
import gc.modelo.Tipo;
import gc.modelo.Tramite;
import java.io.IOException;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;


@ManagedBean(name = "gc_generadas")
@ViewScoped
public class GcGeneradasBean {
    private SolicitudesGeneradas generadas = new SolicitudesGeneradas();
    private ArrayList<SolicitudesGeneradas> listGeneradas = new ArrayList<>();
    private ArrayList<SelectItem> listEstado = new ArrayList<>();
    private ArrayList<SelectItem> listDestino = new ArrayList<>();
    private ArrayList<SelectItem> listTipo = new ArrayList<>();
    private ArrayList<SelectItem> listTramite = new ArrayList<>();
    private boolean filter = false;
    private String userConect;
    
    public GcGeneradasBean() {
        HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        if(sesion.getAttribute("userConect") != null){
            userConect = sesion.getAttribute("userConect").toString();
            mostrarDatos();
        }
        else{
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/Cross-selling_core/faces/index.xhtml");
            } catch (IOException ex) {
                ex.printStackTrace(System.out);
            }
        }
    }

    public SolicitudesGeneradas getGeneradas() {
        return generadas;
    }

    public void setGeneradas(SolicitudesGeneradas generadas) {
        this.generadas = generadas;
    }

    public ArrayList<SolicitudesGeneradas> getListGeneradas() {
        return listGeneradas;
    }

    public void setListGeneradas(ArrayList<SolicitudesGeneradas> listGeneradas) {
        this.listGeneradas = listGeneradas;
    }

    public ArrayList<SelectItem> getListEstado() {
        listEstado.clear();
        for(GcEstado e : Estado.mostrarDatos()){
            SelectItem itemEstado = new SelectItem(e.getId(), e.getDescripcion());
            listEstado.add(itemEstado);
        }
        return listEstado;
    }

    public void setListEstado(ArrayList<SelectItem> listEstado) {
        this.listEstado = listEstado;
    }

    public ArrayList<SelectItem> getListDestino() {
        listDestino.clear();
        for(GcDestino d : Destino.mostrarDatos()){
            SelectItem itemDestino = new SelectItem(d.getId(), d.getDescripcion());
            listDestino.add(itemDestino);
        }
        return listDestino;
    }

    public void setListDestino(ArrayList<SelectItem> listDestino) {
        this.listDestino = listDestino;
    }

    public ArrayList<SelectItem> getListTipo() {
        listTipo.clear();
        for(GcTipo t : Tipo.mostrarDatos()){
            SelectItem itemTipo = new SelectItem(t.getId(), t.getDescripcion());
            listTipo.add(itemTipo);
        }
        return listTipo;
    }

    public void setListTipo(ArrayList<SelectItem> listTipo) {
        this.listTipo = listTipo;
    }

    public ArrayList<SelectItem> getListTramite() {
        listTramite.clear();
        for(GcTramite f : Tramite.mostrarDatos()){
            SelectItem itemTramite = new SelectItem(f.getId(), f.getDescripcion());
            listTramite.add(itemTramite);
        }
        return listTramite;
    }

    public void setListTramite(ArrayList<SelectItem> listTramite) {
        this.listTramite = listTramite;
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
        filter = false;
        generadas.setNumeroSolicitud(null);
        generadas.setNombreAsociado(null);
        generadas.setTipoId(0);
        generadas.setDestinoId(0);
        generadas.setTramiteId(0);
        generadas.setEstadoId("a");
        listGeneradas = generadas.mostrarDatos(userConect);
    }
    
    /* Metodo utilizado para mostrar las solicitudes sin filtro */
    private void mostrarDatos(){
        generadas.setEstadoId("a");
        listGeneradas = generadas.mostrarDatos(userConect);
    }
    
    /* Metodo utilizado para mostrar por numero de solicitud */
    public void mostrarPorCampo(){
        listGeneradas = generadas.mostrarDatos(userConect);
    }
    
    /* Metodo utilizado para mostrar por tipo de credito */
    public void mostrarPorTipo(ValueChangeEvent e){
        generadas.setTipoId(Integer.parseInt(e.getNewValue().toString()));
        listGeneradas = generadas.mostrarDatos(userConect);
    }
    
    /* Metodo utilizado para mostrar por tipo de credito */
    public void mostrarPorDestino(ValueChangeEvent e){
        generadas.setDestinoId(Integer.parseInt(e.getNewValue().toString()));
        listGeneradas = generadas.mostrarDatos(userConect);
    }
    
    /* Metodo utilizado para mostrar por tipo de credito */
    public void mostrarPorTramite(ValueChangeEvent e){
        generadas.setTramiteId(Integer.parseInt(e.getNewValue().toString()));
        listGeneradas = generadas.mostrarDatos(userConect);
    }
    
    /* Metodo utilizado para mostrar por tipo de credito */
    public void mostrarPorEstado(ValueChangeEvent e){
        generadas.setEstadoId(e.getNewValue().toString());
        listGeneradas = generadas.mostrarDatos(userConect);
    }
}
