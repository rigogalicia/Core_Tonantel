package gc.controlador;

import admin.modelo.Colaborador;
import dao.GcDestino;
import dao.GcEstado;
import dao.GcGestion;
import dao.GcSolicitud;
import dao.GcTipo;
import dao.GcTramite;
import gc.modelo.Destino;
import gc.modelo.SolicitudesRecibidas;
import gc.modelo.Tipo;
import gc.modelo.Tramite;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpSession;

@ManagedBean(name = "gc_recibidas")
@ViewScoped
public class GcRecibidasBean {
    private SolicitudesRecibidas recibidas = new SolicitudesRecibidas();
    private ArrayList<SolicitudesRecibidas> listaRecibidas = new ArrayList<>();
    private ArrayList<SelectItem> tipoCredito = new ArrayList<>();
    private ArrayList<SelectItem> destinoCredito = new ArrayList<>();
    private ArrayList<SelectItem> tramiteCredito = new ArrayList<>();
    private boolean filter = false;
    private String userConect;
    
    public GcRecibidasBean() {
        HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        if(sesion.getAttribute("userConect") != null){
            userConect = sesion.getAttribute("userConect").toString();
            listaRecibidas = recibidas.mostrarDatos();
        }
        else{
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/Cross-selling_core/faces/index.xhtml");
            } catch (IOException ex) {
                ex.printStackTrace(System.out);
            }
        }
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
    
    /* Metodo para asignarse una solicitud para realizar una gestion */
    public void asignarSolicitud(SolicitudesRecibidas s){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
        EntityManager em = emf.createEntityManager();
        
        em.getTransaction().begin();
        GcGestion gestion = new GcGestion();
        gestion.setAnalista(userConect);
        gestion.setFecha(new Date());
        gestion.setSolicitudNumeroSolicitud(new GcSolicitud(s.getNumeroSolicitud()));
        gestion.setEstadoId(new GcEstado("b"));
        GcSolicitud solicitud = em.find(GcSolicitud.class, s.getNumeroSolicitud());
        solicitud.setEstadoId(new GcEstado("b"));
        em.persist(gestion);
        em.getTransaction().commit();
        
        String msj = "La solicitud numero "+s.getNumeroSolicitud()+" generada el "+s.getFecha()+"\n"
                + "pertenece al asociado "+s.getNombreAsociado()+" fue asignada a\n"
                + "un analista de créditos para su debido proceso de aprobación, para\n"
                + "una mejor comunicación con el analista puedes utilizar el servicio\n"
                + "de chat que se encuentra dentro de la aplicación Crosselling Core\n"
                + "que puedes ingresar en el siguiente enlace:\n\n"
                + "https://core.app-tonantel.com/Cross-selling_core\n\n\n"
                + "Copyright © Investigación y Desarrollo de Tecnología Cooperativa Tonantel R.L";
        
        Correo correo = new Correo(Colaborador.correoColaborador(s.getAsesorFinanciero()), "Solicitud No. " + s.getNumeroSolicitud(), msj);
        correo.enviar();
        
        listaRecibidas = recibidas.mostrarDatos();
        
        em.close();
        emf.close();
    }
}
