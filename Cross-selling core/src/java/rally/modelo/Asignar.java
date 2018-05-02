package rally.modelo;

import admin.modelo.Colaborador;
import dao.RallyAsociado;
import dao.RallyProducto;
import dao.RallyReferencia;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class Asignar {
    private ArrayList<Referencia> listaParaAsignarme = new ArrayList<>();
    private String userConect;

    public ArrayList<Referencia> getListaParaAsignarme() {
        return listaParaAsignarme;
    }

    public void setListaParaAsignarme(ArrayList<Referencia> listaParaAsignarme) {
        this.listaParaAsignarme = listaParaAsignarme;
    }

    public String getUserConect() {
        return userConect;
    }

    public void setUserConect(String userConect) {
        this.userConect = userConect;
    }
    
    /* Este metodo es utilizado para consultar las referencias para poder asignarme 
    public void consultarRefParaAsignarme(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
        EntityManager em = emf.createEntityManager();
        
        Colaborador user = new Colaborador();
        user.mostrarUsuario(this.userConect);
        String productosAsignar = "";
        for(ProductosTonantel pt : user.getProductosAsignar()){
            productosAsignar += "'" + pt.getNombre_producto() + "'" + ", ";
        }
        
        productosAsignar += "0";
        
        String instruccion = "SELECT r, a, p "
                + "FROM RallyReferencia r "
                + "JOIN r.rallyAsociadoIdasociado a "
                + "JOIN r.rallyProductoIdproducto p "
                + "WHERE r.estado = :estadoActual "
                + "AND r.agenciaCercana = :agCercana "
                + "AND p.nombreProducto IN("+productosAsignar+")";
        Query consulta = em.createQuery(instruccion);
        consulta.setParameter("estadoActual", 'a');
        consulta.setParameter("agCercana", user.getAgencia());
        List<Object[]> resultado = consulta.getResultList();
        
        for(Object[] obj : resultado){
            RallyReferencia referencia = (RallyReferencia) obj[0];
            RallyAsociado asociado = (RallyAsociado) obj[1];
            RallyProducto producto = (RallyProducto) obj[2];
            
            Referencia ref = new Referencia();
            ref.setCodigo(referencia.getIdreferencia());
            ref.setCif(asociado.getCif());
            ref.setFecha(referencia.getFechaCreacion().toString());
            ref.setIdAsociado(asociado.getIdasociado());
            ref.setNombres(asociado.getNombre());
            ref.setApellidos(asociado.getApellido());
            ref.setNombre(asociado.getNombre() + ", " + asociado.getApellido());
            ref.setDireccion(asociado.getDireccion());
            ref.setTelefono_movil(asociado.getTelefonomovil());
            ref.setTelefono_casa(asociado.getTelefonocasa());
            ref.setProducto(producto.getIdproducto());
            ref.setNombre_producto(producto.getNombreProducto());
            ref.setAgenciaCercana(referencia.getAgenciaCercana());
            ref.setComentario(referencia.getComentario());
            ref.setReferidoPor(referencia.getUsuario());
            
            listaParaAsignarme.add(ref);
        }
        
        em.close();
        emf.close();
    }
*/
}
