package gc.modelo;

import dao.GcSeguimiento;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class Seguimiento {
    private int id;
    private Date fecha;
    private String fechaFormateada;
    private String comentario;
    private String numeroSolicitud;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getFechaFormateada() {
        return fechaFormateada;
    }

    public void setFechaFormateada(String fechaFormateada) {
        this.fechaFormateada = fechaFormateada;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getNumeroSolicitud() {
        return numeroSolicitud;
    }

    public void setNumeroSolicitud(String numeroSolicitud) {
        this.numeroSolicitud = numeroSolicitud;
    }
    
    /* Metodo utilizado para obtener los datos de seguimiento */
    public ArrayList<Seguimiento> mostrarDatos(){
        ArrayList<Seguimiento> result = new ArrayList<>();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
        EntityManager em = emf.createEntityManager();
        
        String instruccion = "SELECT s "
                + "FROM GcSeguimiento s "
                + "JOIN s.solicitudNumeroSolicitud d "
                + "WHERE d.numeroSolicitud = :numSol";
        Query consulta = em.createQuery(instruccion);
        consulta.setParameter("numSol", numeroSolicitud);
        List<GcSeguimiento> resultado = consulta.getResultList();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        for(GcSeguimiento s : resultado){
            Seguimiento seg = new Seguimiento();
            seg.setFecha(s.getFecha());
            seg.setFechaFormateada(formatoFecha.format(s.getFecha()));
            seg.setComentario(s.getComentario());
            result.add(seg);
        }
        
        em.close();
        emf.close();
        
        return result;
    }
}
