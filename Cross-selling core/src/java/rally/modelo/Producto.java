package rally.modelo;

import dao.RallyProducto;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class Producto {
    
    /* Metodo utilizado para consultar el listado de productos activos */
    public ArrayList<RallyProducto> listaProductos(){
        ArrayList<RallyProducto> result = new ArrayList<>();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
        EntityManager em = emf.createEntityManager();
        
        String instruccion = "SELECT p "
                + "FROM RallyProducto p "
                + "WHERE p.estado = :estado";
        Query consulta = em.createQuery(instruccion);
        consulta.setParameter("estado", 'a');
        List<RallyProducto> resultado = consulta.getResultList();
        resultado.stream().forEach((p) -> {
            result.add(p);
        });
        
        em.close();
        emf.close();
        
        return result;
    }
    
}
