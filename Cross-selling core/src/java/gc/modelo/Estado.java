package gc.modelo;

import dao.GcEstado;
import dao.GcTipo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class Estado {
    private String estado;
    private String descripcion;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    /* Metodo para mostrar los registros de estado */
    public static List<GcEstado> mostrarDatos(){
        List<GcEstado> result = new ArrayList<>();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
        EntityManager em = emf.createEntityManager();
        
        String instruccion = "SELECT e "
                + "FROM GcEstado e";
        Query consulta = em.createQuery(instruccion);
        result = consulta.getResultList();
        
        em.close();
        emf.close();
        
        return result;
    }
}
