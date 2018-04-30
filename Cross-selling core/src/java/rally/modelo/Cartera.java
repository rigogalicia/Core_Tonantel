package rally.modelo;

import dao.RallyCartera;
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
}
