package gc.modelo;

import admin.modelo.Colaborador;
import dao.GcAsociado;
import dao.GcEstado;
import dao.GcGestion;
import dao.GcSolicitud;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class Reasignar {
    private String numeroSolicitud;
    private String cif;
    private String nombre;
    private String asignadoA;
    private String analista;
    
    public Reasignar(){}
    
    public Reasignar(String numeroSolicitud){
        this.numeroSolicitud = numeroSolicitud;
    }

    public String getNumeroSolicitud() {
        return numeroSolicitud;
    }

    public void setNumeroSolicitud(String numeroSolicitud) {
        this.numeroSolicitud = numeroSolicitud;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAsignadoA() {
        return asignadoA;
    }

    public void setAsignadoA(String asignadoA) {
        this.asignadoA = asignadoA;
    }

    public String getAnalista() {
        return analista;
    }

    public void setAnalista(String analista) {
        this.analista = analista;
    }
    
    /* Metodo para buscar los datos de la solicitud por numero */
    public void consultarDatos(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
        EntityManager em = emf.createEntityManager();
        
        String instruccion = "SELECT g, s, a "
                + "FROM GcGestion g "
                + "JOIN g.solicitudNumeroSolicitud s "
                + "JOIN s.asociadoCif a "
                + "JOIN s.estadoId e "
                + "WHERE s.numeroSolicitud = :numSol "
                + "AND e.id = :est";
        Query consulta = em.createQuery(instruccion);
        consulta.setParameter("numSol", numeroSolicitud);
        consulta.setParameter("est", "b");
        List<Object[]> resultado = consulta.getResultList();
        for(Object[] obj : resultado){
            GcGestion g = (GcGestion) obj[0];
            GcSolicitud s = (GcSolicitud) obj[1];
            GcAsociado a = (GcAsociado) obj[2];
            
            this.numeroSolicitud = s.getNumeroSolicitud();
            this.cif = a.getCif();
            this.nombre = a.getNombre();
            this.asignadoA = Colaborador.datosColaborador(g.getAnalista()).getNombre();
        }
        
        em.close();
        emf.close();
    }
    
    // Metodo para cambiar el analista al que esta asignado
    public void cambiarAnalista(){
        int idGestion = 0;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
        EntityManager em = emf.createEntityManager();
        
        String instruccion = "SELECT g "
                + "FROM GcGestion g "
                + "JOIN g.solicitudNumeroSolicitud s "
                + "JOIN g.estadoId e "
                + "WHERE s.numeroSolicitud = :numSol "
                + "AND e.id = :est ";
        Query consulta = em.createQuery(instruccion);
        consulta.setParameter("numSol", numeroSolicitud);
        consulta.setParameter("est", "b");
        List<GcGestion> resultado = consulta.getResultList();
        for(GcGestion g : resultado){
            idGestion = g.getId();
        }
        
        em.getTransaction().begin();
        GcGestion gestion = em.find(GcGestion.class, idGestion);
        gestion.setAnalista(analista);
        em.getTransaction().commit();
        
        em.close();
        emf.close();
    }
    
    // Metodo utilizado para cambiar a estado en proceso
    public void quitarAsignacion(){
        int idGestion = 0;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
        EntityManager em = emf.createEntityManager();
        
        String instruccion = "SELECT g "
                + "FROM GcGestion g "
                + "JOIN g.solicitudNumeroSolicitud s "
                + "JOIN g.estadoId e "
                + "WHERE s.numeroSolicitud = :numSol "
                + "AND e.id = :est";
        Query consulta = em.createQuery(instruccion);
        consulta.setParameter("numSol", numeroSolicitud);
        consulta.setParameter("est", "b");
        List<GcGestion> resultado = consulta.getResultList();
        for(GcGestion g : resultado){
            idGestion = g.getId();
        }
        
        em.getTransaction().begin();
        
        GcEstado estado = new GcEstado("a");
        GcSolicitud solicitud = em.find(GcSolicitud.class, numeroSolicitud);
        solicitud.setEstadoId(estado);
        
        GcGestion gestion = em.find(GcGestion.class, idGestion);
        em.remove(gestion);
        
        em.getTransaction().commit();
        
        em.close();
        emf.close();
    }
}
