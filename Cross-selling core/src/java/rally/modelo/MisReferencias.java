package rally.modelo;

import dao.RallyAsignacion;
import dao.RallyAsociado;
import dao.RallyProducto;
import dao.RallyReferencia;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class MisReferencias {
    private String userConect;
    private char estado;

    public String getUserConect() {
        return userConect;
    }

    public void setUserConect(String userConect) {
        this.userConect = userConect;
    }

    public char getEstado() {
        return estado;
    }

    public void setEstado(char estado) {
        this.estado = estado;
    }
    
    /* Metodo utilizado para consultar mis referencias por estado */
    public ArrayList<Referencia> listaReferencias(){
        ArrayList<Referencia> result = new ArrayList<>();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
        EntityManager em = emf.createEntityManager();

        String instruccion = "SELECT r, a, p "
                + "FROM RallyReferencia r "
                + "JOIN r.rallyAsociadoIdasociado a "
                + "JOIN r.rallyProductoIdproducto p "
                + "WHERE r.usuario = :userConect "
                + "AND r.estado = :estado "
                + "ORDER BY r.idreferencia DESC";
        Query consulta = em.createQuery(instruccion);
        consulta.setParameter("userConect", userConect);
        consulta.setParameter("estado", estado);
        
        List<Object[]> resultado = consulta.getResultList();
        
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        
        for(Object[] objeto : resultado){
            RallyReferencia r = (RallyReferencia) objeto[0];
            RallyAsociado a = (RallyAsociado) objeto[1];
            RallyProducto p = (RallyProducto) objeto[2];
            
            Referencia referencia = new Referencia();
            referencia.setCodigo(r.getIdreferencia());
            referencia.setCif(a.getCif());
            referencia.setFecha(formatoFecha.format(r.getFechaCreacion()));
            referencia.setIdAsociado(a.getIdasociado());
            referencia.setNombre(a.getNombre() + ", " + a.getApellido());
            referencia.setNombres(a.getNombre());
            referencia.setApellidos(a.getApellido());
            referencia.setDireccion(a.getDireccion());
            referencia.setTelefono_movil(a.getTelefonomovil());
            referencia.setTelefono_casa(a.getTelefonocasa());
            referencia.setProducto(p.getIdproducto());
            referencia.setNombre_producto(p.getNombreProducto());
            referencia.setAgenciaCercana(r.getAgenciaCercana());
            referencia.setComentario(r.getComentario());
            
            result.add(referencia);
        }
        
        em.close();
        emf.close();
        
        return result;
    }
    
    /* Metodo utilizado para consultar datos de referencia */
    public static RallyAsignacion consultarAsignacion(int codigoReferencia){
        RallyAsignacion result = null;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
        EntityManager em = emf.createEntityManager();
        
        String instruccion = "SELECT a "
                + "FROM RallyAsignacion a "
                + "JOIN a.rallyReferenciaIdreferencia r "
                + "WHERE r.idreferencia = :codigo";
        Query consulta = em.createQuery(instruccion);
        consulta.setParameter("codigo", codigoReferencia);
        
        List<RallyAsignacion> resultado = consulta.getResultList();
        for(RallyAsignacion asig : resultado){
            result = asig;
        }
        
        em.close();
        emf.close();
        
        return result;
    }
}
