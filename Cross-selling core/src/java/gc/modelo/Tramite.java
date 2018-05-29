package gc.modelo;

import dao.GcTramite;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class Tramite {
    private int id;
    private String descripcion;
    private char estado;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public char getEstado() {
        return estado;
    }

    public void setEstado(char estado) {
        this.estado = estado;
    }
    
    /* Metodo utilizado para mostrar los registros de tipo de tramite */
    public static List<GcTramite> mostrarDatos(){
        List<GcTramite> result = new ArrayList<>();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
        EntityManager em = emf.createEntityManager();
        
        String instruccion = "SELECT t "
                + "FROM GcTramite t "
                + "WHERE t.estado = :est";
        Query consulta = em.createQuery(instruccion);
        consulta.setParameter("est", 'a');
        result = consulta.getResultList();
        
        em.close();
        emf.close();
        
        return result;
    }
}
