package gc.controlador;

import admin.modelo.Agencia;
import dao.GcDestino;
import dao.GcEstado;
import dao.GcTipo;
import dao.GcTipocliente;
import dao.GcTramite;
import gc.modelo.Chat;
import gc.modelo.Destino;
import gc.modelo.Estado;
import gc.modelo.Monitoreo;
import gc.modelo.Tipo;
import gc.modelo.Tipocliente;
import gc.modelo.Tramite;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

@ManagedBean(name = "gc_monitoreo")
@ViewScoped
public class GcMonitoreo {
    private int tipoReporte;
    private Monitoreo monitoreo = new Monitoreo();
    private ArrayList<Monitoreo> solicitudes = new ArrayList<>();
    private ArrayList<SelectItem> destino = new ArrayList<>();
    private ArrayList<SelectItem> tipo = new ArrayList<>();
    private ArrayList<SelectItem> tramite = new ArrayList<>();
    private ArrayList<SelectItem> cliente = new ArrayList<>();
    private ArrayList<SelectItem> estado = new ArrayList<>();
    private ArrayList<SelectItem> agencia = new ArrayList<>();
    private boolean filter = false;
    
    // Campos para monitoreo de chat
    private boolean isChat = false;
    private int panel1 = 12;
    private int panel2 = 0;
    private Chat chat = new Chat();
    private ArrayList<Chat> mensajes = new ArrayList<>();
    private String asesorFinanciero;
    
    public GcMonitoreo() {
        monitoreo.setEstadoId("a");
        solicitudes = monitoreo.consultar();
    }

    public int getTipoReporte() {
        return tipoReporte;
    }

    public void setTipoReporte(int tipoReporte) {
        this.tipoReporte = tipoReporte;
    }

    public Monitoreo getMonitoreo() {
        return monitoreo;
    }

    public void setMonitoreo(Monitoreo monitoreo) {
        this.monitoreo = monitoreo;
    }

    public ArrayList<Monitoreo> getSolicitudes() {
        return solicitudes;
    }

    public void setSolicitudes(ArrayList<Monitoreo> solicitudes) {
        this.solicitudes = solicitudes;
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

    public ArrayList<SelectItem> getCliente() {
        cliente.clear();
        for(GcTipocliente c : Tipocliente.mostrarDatos()){
            SelectItem itemCliente = new SelectItem(c.getId(), c.getDescripcion());
            cliente.add(itemCliente);
        }
        return cliente;
    }

    public void setCliente(ArrayList<SelectItem> cliente) {
        this.cliente = cliente;
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

    public ArrayList<SelectItem> getAgencia() {
        agencia.clear();
        Agencia ag = new Agencia();
        for(Agencia a : ag.mostrarAgencias()){
            SelectItem itemAgencia = new SelectItem(a.getId(), a.getNombre());
            agencia.add(itemAgencia);
        }
        return agencia;
    }

    public void setAgencia(ArrayList<SelectItem> agencia) {
        this.agencia = agencia;
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

    public int getPanel2() {
        return panel2;
    }

    public void setPanel2(int panel2) {
        this.panel2 = panel2;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public ArrayList<Chat> getMensajes() {
        return mensajes;
    }

    public void setMensajes(ArrayList<Chat> mensajes) {
        this.mensajes = mensajes;
    }

    public String getAsesorFinanciero() {
        return asesorFinanciero;
    }

    public void setAsesorFinanciero(String asesorFinanciero) {
        this.asesorFinanciero = asesorFinanciero;
    }
    
    // Metodo para activar los filtros del reporte
    public void activarFiltros(){
        filter = true;
    }
    
    // Metodo para desactivar los filtros del reporte
    public void desactivarFiltros(){
        monitoreo.setNumeroSolicitud(null);
        monitoreo.setCif(null);
        monitoreo.setNombre(null);
        monitoreo.setDestinoId(0);
        monitoreo.setTipoId(0);
        monitoreo.setTramiteId(0);
        monitoreo.setClienteId(0);
        monitoreo.setEstadoId("a");
        
        solicitudes = monitoreo.consultar();
        filter = false;
    }
    
    /* Metodo utilizado para filtrar una solicitud por campo */
    public void filtrarPorCampo(){
        solicitudes = monitoreo.consultar();
    }
    
    // Metodo para filtrar registros por destino
    public void filtrarPorDestino(ValueChangeEvent e){
        monitoreo.setDestinoId(Integer.parseInt(e.getNewValue().toString()));
        solicitudes = monitoreo.consultar();
    }
    
    // Metodo para filtar registros por tipo de garantia
    public void filtrarPorTipo(ValueChangeEvent e){
        monitoreo.setTipoId(Integer.parseInt(e.getNewValue().toString()));
        solicitudes = monitoreo.consultar();
    }
    
    // Metodo para filtar registros por tipo de tramite
    public void filtrarPorTramite(ValueChangeEvent e){
        monitoreo.setTramiteId(Integer.parseInt(e.getNewValue().toString()));
        solicitudes = monitoreo.consultar();
    }
    
    // Metodo para filtrar registros por tipo de cliente
    public void filtrarPorCliente(ValueChangeEvent e){
        monitoreo.setClienteId(Integer.parseInt(e.getNewValue().toString()));
        solicitudes = monitoreo.consultar();
    }
    
    // Metodo para filtrar registros por estado
    public void filtrarPorEstado(ValueChangeEvent e){
        monitoreo.setEstadoId(e.getNewValue().toString());
        solicitudes = monitoreo.consultar();
    }
    
    // Metodo para filtrar registros por agencia
    public void filtrarPorAgencia(ValueChangeEvent e){
        monitoreo.setIdAgencia(e.getNewValue().toString());
        solicitudes = monitoreo.consultar();
    }
    
    /* --------------------------------------------------------------- */
    /* Metodo utilizado para activar el chat de solicitudes en proceso */
    public void activarChat(Monitoreo s){
        chat.setNumeroSolicitud(s.getNumeroSolicitud());
        mensajes = chat.mostrarMensajes();
        asesorFinanciero = s.getAsesorFinanciero();
        solicitudes = monitoreo.consultar();
        isChat = true;
        panel1 = 8;
        panel2 = 4;
    }
    
    /* Metodo utilizado para desactivar el chat */
    public void desactivarChat(){
        isChat = false;
        panel1 = 12;
        panel2 = 0;
    }
}
