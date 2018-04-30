package rally.controlador;

import admin.modelo.Agencia;
import dao.RallyAsociado;
import dao.RallyProducto;
import dao.RallyReferencia;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpSession;
import rally.modelo.Producto;

@ManagedBean(name = "referir")
@RequestScoped
public class ReferirBean {
    private String userConect;
    private RallyAsociado asociado = new RallyAsociado();
    private RallyProducto producto = new RallyProducto();
    private RallyReferencia referencia = new RallyReferencia();
    private List<SelectItem> listaProductos = new ArrayList<>();
    private List<SelectItem> listaAgencias = new ArrayList<>();
    
    public ReferirBean() {
        HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        if(sesion.getAttribute("userConect") != null){
            userConect = sesion.getAttribute("userConect").toString();
        }
        else{
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/Cross-selling_core/faces/index.xhtml");
            } catch (IOException ex) {
                ex.printStackTrace(System.out);
            }
        }
    }

    public RallyAsociado getAsociado() {
        return asociado;
    }

    public void setAsociado(RallyAsociado asociado) {
        this.asociado = asociado;
    }

    public RallyProducto getProducto() {
        return producto;
    }

    public void setProducto(RallyProducto producto) {
        this.producto = producto;
    }

    public RallyReferencia getReferencia() {
        return referencia;
    }

    public void setReferencia(RallyReferencia referencia) {
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
    
    /* Metodo utilizado para almacenar los registros de una nueva referencia */
    public void guardarReferencia(ActionEvent event) throws IOException{
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
        EntityManager em = emf.createEntityManager();
        
        referencia.setFechaCreacion(new Date());
        referencia.setEstado('a');
        referencia.setUsuario(this.userConect);
        referencia.setRallyAsociadoIdasociado(asociado);
        referencia.setRallyProductoIdproducto(producto);
        
        em.getTransaction().begin();
        em.persist(asociado);
        em.persist(referencia);
        em.getTransaction().commit();
        
        em.close();
        emf.close();
        
        FacesContext.getCurrentInstance().getExternalContext().redirect("/Cross-selling_core/faces/vista/rally/mis_referencias.xhtml");
    }
    
}
