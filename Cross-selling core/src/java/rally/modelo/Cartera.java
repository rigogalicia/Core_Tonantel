package rally.modelo;

import dao.RallyCartera;
import dao.RallySeguimientoCartera;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class Cartera {
    private int operador;
    
    public Cartera(int operador){
        this.operador = operador;
    }
    
    /* Metodo utilizado para consultar los registros de cartera por asesor financiero */
    public ArrayList<RallyCartera> obtenerCartera(){
        ArrayList<RallyCartera> result = new ArrayList<>();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
        EntityManager em = emf.createEntityManager();
        
        String instruccion = "SELECT c "
                + "FROM RallyCartera c "
                + "WHERE c.asesorFinanciero = :operador";
        Query consulta = em.createQuery(instruccion);
        consulta.setParameter("operador", operador);
        List<RallyCartera> resultado = consulta.getResultList();
        resultado.stream().forEach((c) -> {
            result.add(c);
        });
        
        em.close();
        emf.close();
        
        return result;
    }
    
    /* Metodo utilizado para agregar seguimiento a los casos de cartera */
    public static void agregarSeguimiento(RallySeguimientoCartera seguimiento){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
        EntityManager em = emf.createEntityManager();
        
        em.getTransaction().begin();
        em.persist(seguimiento);
        em.getTransaction().commit();
        
        em.close();
        emf.close();
    }
    
    /* Consulta el listado de seguimiento por numero de prestamo */
    public static List<RallySeguimientoCartera> consultarSeguimiento(String numeroPrestamo){
        List<RallySeguimientoCartera> result = new ArrayList<>();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
        EntityManager em = emf.createEntityManager();
        
        String instruccion = "SELECT s "
                + "FROM RallySeguimientoCartera s "
                + "WHERE s.numeroPrestamo = :numeroPrestamo";
        Query consulta = em.createQuery(instruccion);
        consulta.setParameter("numeroPrestamo", numeroPrestamo);
        result = consulta.getResultList();
        
        em.close();
        emf.close();
        
        return result;
    }
}
