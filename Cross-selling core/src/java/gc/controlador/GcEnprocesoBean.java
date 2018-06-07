package gc.controlador;

import dao.GcDestino;
import dao.GcEstado;
import dao.GcTipo;
import dao.GcTramite;
import gc.modelo.Destino;
import gc.modelo.Estado;
import gc.modelo.SolicitudesEnproceso;
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

@ManagedBean(name = "gc_enproceso")
@ViewScoped
public class GcEnprocesoBean {
    private SolicitudesEnproceso enProceso = new SolicitudesEnproceso();
    private ArrayList<SolicitudesEnproceso> listEnproceso = new ArrayList<>();
    private ArrayList<SelectItem> tipo = new ArrayList<>();
    private ArrayList<SelectItem> destino = new ArrayList<>();
    private ArrayList<SelectItem> tramite = new ArrayList<>();
    private ArrayList<SelectItem> estado = new ArrayList<>();
    private String userConect;
    private boolean filter = false;
    private boolean isChat = false;
    private int panel1 = 12;
    
    public GcEnprocesoBean() {
        HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        if(sesion.getAttribute("userConect") != null){
            userConect = sesion.getAttribute("userConect").toString();
            enProceso.setUserConect(userConect);
            listEnproceso = enProceso.mostrarDatos();
        }
        else{
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/Cross-selling_core/faces/index.xhtml");
            } catch (IOException ex) {
                ex.printStackTrace(System.out);
            }
        }
        
    }

    public SolicitudesEnproceso getEnProceso() {
        return enProceso;
    }

    public void setEnProceso(SolicitudesEnproceso enProceso) {
        this.enProceso = enProceso;
    }

    public ArrayList<SolicitudesEnproceso> getListEnproceso() {
        return listEnproceso;
    }

    public void setListEnproceso(ArrayList<SolicitudesEnproceso> listEnproceso) {
        this.listEnproceso = listEnproceso;
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

    public ArrayList<SelectItem> getEstado() {
        estado.clear();
        for(GcEstado e : Estado.mostrarDatos()){
            SelectItem itemEstado = new SelectItem(e.getId(), e.getDescripcion());
            estado.add(itemEstado);
        }
        return estado;
    }

    public void setEstado(ArrayList<SelectItem> estado) {
        this.estado = estado;
    }

    public boolean isFilter() {
        return filter;
    }

    public void setFilter(boolean filter) {
        this.filter = filter;
    }

    public boolean isIsChat() {
        return isChat;
    }

    public void setIsChat(boolean isChat) {
        this.isChat = isChat;
    }

    public int getPanel1() {
        return panel1;
    }

    public void setPanel1(int panel1) {
        this.panel1 = panel1;
    }
    
    /* Metodo para activar la opcion del chat */
    public void activarChat(){
        isChat = true;
        panel1 = 8;
    }
    
    /* Metodo para desactivar la opcion del chat */
    public void desactivarChat(){
        isChat = false;
        panel1 = 12;
    }
    
    /* Metodo utilizado para activar los filtros */
    public void activarFiltros(){
        filter = true;
    }
    
    /* Metodo utilizado para borrar los filtros */
    public void borrarFiltros(){
        filter = false;
        enProceso.setNumeroSolicitud(null);
        enProceso.setNombreAsociado(null);
        enProceso.setCif(null);
        enProceso.setTipoId(0);
        enProceso.setDestinoId(0);
        enProceso.setTramiteId(0);
        enProceso.setEstadoId(null);
        listEnproceso = enProceso.mostrarDatos();
    }
    
    /* Reliza la busqueda por medio de campos de texto */
    public void buscarPorCampo(){
        listEnproceso = enProceso.mostrarDatos();
    }
    
    /* Metodo que se utiliza para filtrar solicitudes por tipo */
    public void buscarPorTipo(ValueChangeEvent e){
        enProceso.setTipoId(Integer.parseInt(e.getNewValue().toString()));
        listEnproceso = enProceso.mostrarDatos();
    }
    
    /* Metodo que se utiliza para filtrar solicitudes por destino */
    public void buscarPorDestino(ValueChangeEvent e){
        enProceso.setDestinoId(Integer.parseInt(e.getNewValue().toString()));
        listEnproceso = enProceso.mostrarDatos();
    }
    
    /* Metodo que se utiliza para filtrar solicitudes por tramite */
    public void buscarPorTramite(ValueChangeEvent e){
        enProceso.setTramiteId(Integer.parseInt(e.getNewValue().toString()));
        listEnproceso = enProceso.mostrarDatos();
    }
    
    /* Metodo que se utiliza para filtrar solicitudes por estado */
    public void buscarPorEstado(ValueChangeEvent e){
        enProceso.setEstadoId(e.getNewValue().toString());
        listEnproceso = enProceso.mostrarDatos();
    }
}