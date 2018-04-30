package patrimonio.modelo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Registrar <T> {
    private T entidad;
    
    public Registrar(T e){
        entidad = e;
    }
    
    public void guardar(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
        EntityManager em = emf.createEntityManager();
        
        em.getTransaction().begin();
        em.merge(entidad);
        em.getTransaction().commit();
        
        em.close();
        emf.close();
    }
}
