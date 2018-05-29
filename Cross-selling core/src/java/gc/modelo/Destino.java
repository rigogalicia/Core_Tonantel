package gc.modelo;

import dao.GcDestino;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class Destino {
    private int id;
    private String descripcion;
    private String estado;

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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    /* Metodo para consultar los destinos de credito */
    public static List<GcDestino> mostrarDatos(){
        List<GcDestino> result = new ArrayList<>();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
        EntityManager em = emf.createEntityManager();
        
        String instruccion = "SELECT d "
                + "FROM GcDestino d "
                + "WHERE d.estado = :est";
        Query consulta = em.createQuery(instruccion);
        consulta.setParameter("est", 'a');
        result = consulta.getResultList();
        
        em.close();
        emf.close();
        
        return result;
    }
}
