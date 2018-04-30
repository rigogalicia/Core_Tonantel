package rally.controlador;

import admin.modelo.Agencia;
import dao.RallyAsignacion;
import dao.RallyAsociado;
import dao.RallyProducto;
import dao.RallyReferencia;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpSession;
import rally.modelo.MisReferencias;
import rally.modelo.Producto;
import rally.modelo.Referencia;

@ManagedBean(name = "misReferencias")
@ViewScoped
public class MisReferenciasBean {
    private String userConect;
    private char estado = 'a';
    private ArrayList<Referencia> listaReferencias = new ArrayList<>();
    private Referencia referencia = new Referencia();
    private List<SelectItem> listaProductos = new ArrayList<>();
    private List<SelectItem> listaAgencias = new ArrayList<>();
    private boolean disabled = false;
    private boolean editable = false;
    
    public MisReferenciasBean() {
        HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        if(sesion.getAttribute("userConect") != null){
            userConect = sesion.getAttribute("userConect").toString();
            consultarReferencia();
        }
        else{
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/Cross-selling_core/faces/index.xhtml");
            } catch (IOException ex) {
                ex.printStackTrace(System.out);
            }
        }
    }

    public ArrayList<Referencia> getListaReferencias() {
        return listaReferencias;
    }

    public void setListaReferencias(ArrayList<Referencia> listaReferencias) {
        this.listaReferencias = listaReferencias;
    }

    public char getEstado() {
        return estado;
    }

    public void setEstado(char estado) {
        this.estado = estado;
    }

    public Referencia getReferencia() {
        return referencia;
    }

    public void setReferencia(Referencia referencia) {
        this.referencia = referencia;
    }

    public List<SelectItem> getListaProductos() {
        listaProductos.clear();
        Producto prod = new Producto();
        prod.listaProductos().stream().forEach((p) -> {
            SelectItem productoItem = new SelectItem(p.getIdproducto(), p.getNombreProducto());
            listaProductos.add(productoItem);
        });
        return listaProductos;
    }

    public void setListaProductos(List<SelectItem> listaProductos) {
        this.listaProductos = listaProductos;
    }

    public List<SelectItem> getListaAgencias() {
         listaAgencias.clear();
        Agencia agencia = new Agencia();
        agencia.mostrarAgencias().stream().map((a) -> new SelectItem(a.getNombre(), a.getNombre())).forEach((agenciaItem) -> {
            listaAgencias.add(agenciaItem);
        });
        return listaAgencias;
    }

    public void setListaAgencias(List<SelectItem> listaAgencias) {
        this.listaAgencias = listaAgencias;
    }

    public boolean isDisabled() {
        if(estado == 'c' || estado == 'd'){
            disabled = true;
        }
        else{
            disabled = false;
        }
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }
    
    /* Metodo para consultar el listado de referencias */
    private void consultarReferencia(){
        MisReferencias misRef = new MisReferencias();
        misRef.setUserConect(userConect);
        misRef.setEstado(estado);
        listaReferencias = misRef.listaReferencias();
    }
    
    /* Metodo utilizado para consultar la referencia */
    public void consultar(){
        consultarReferencia();
    }
    
    /* Metodo para mostrar el detalle de la referencia seleccionada */
    public void mostrarDetalle(Referencia r) throws IOException{
        referencia = r;
        RallyAsignacion asig = MisReferencias.consultarAsignacion(r.getCodigo());
        //System.out.println(asig.getFechaCierre());
        editable = true;
    }
    
    /* Boton utilizado para simular la accion atras del formulario */
    public void atras(){
        editable = false;
    }
    
    /* Metodo utilizado para actualizar los datos de la referencia */
    public void actualizarDatos(ActionEvent event) throws Exception{
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
        EntityManager em = emf.createEntityManager();
        
        em.getTransaction().begin();
        RallyAsociado r_asociado = em.find(RallyAsociado.class, referencia.getIdAsociado());
        r_asociado.setCif(referencia.getCif());
        r_asociado.setNombre(referencia.getNombres());
        r_asociado.setApellido(referencia.getApellidos());
        r_asociado.setDireccion(referencia.getDireccion());
        r_asociado.setTelefonomovil(referencia.getTelefono_movil());
        r_asociado.setTelefonocasa(referencia.getTelefono_casa());
        
        RallyReferencia r_referencia = em.find(RallyReferencia.class, referencia.getCodigo());
        r_referencia.setComentario(referencia.getComentario());
        r_referencia.setAgenciaCercana(referencia.getAgenciaCercana());
        RallyProducto r_producto = new RallyProducto(referencia.getProducto());
        r_referencia.setRallyProductoIdproducto(r_producto);
        r_referencia.setRallyAsociadoIdasociado(r_asociado);
        em.getTransaction().commit();
        
        editable = false;
        consultarReferencia();
        
        em.close();
        emf.close();
    }
}