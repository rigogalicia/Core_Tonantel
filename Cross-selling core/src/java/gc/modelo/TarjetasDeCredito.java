package gc.modelo;

import dao.GcAsociado;
import dao.GcGestion;
import dao.GcSolicitud;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class TarjetasDeCredito {
    private String numeroSolicitud;
    private String cif;
    private String nombre;
    private String monto;
    private String fechaSolicitud;
    private String fechaAutorizacion;
    private String agencia;

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

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(String fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public String getFechaAutorizacion() {
        return fechaAutorizacion;
    }

    public void setFechaAutorizacion(String fechaAutorizacion) {
        this.fechaAutorizacion = fechaAutorizacion;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }
    
    // Metodo para consultar las solicitudes de creditos autorizadas
    public static ArrayList<TarjetasDeCredito> autorizadas(){
        ArrayList<TarjetasDeCredito> result = new ArrayList<>();
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
        EntityManager em = emf.createEntityManager();
        
        String instruccion = "SELECT DISTINCT g, s, a "
                + "FROM GcGestion g "
                + "JOIN g.solicitudNumeroSolicitud s "
                + "JOIN g.estadoId e "
                + "JOIN s.asociadoCif a "
                + "WHERE e.id = :estado "
                + "AND s.est IS NULL "
                + "ORDER BY g.fecha DESC";
        Query consulta = em.createQuery(instruccion);
        consulta.setParameter("estado", "c");
        List<Object[]> resultado = consulta.getResultList();
        
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        
        resultado.stream().map((obj) -> {
            GcGestion g = (GcGestion) obj[0];
            GcSolicitud s = (GcSolicitud) obj[1];
            GcAsociado a = (GcAsociado) obj[2];
            TarjetasDeCredito t = new TarjetasDeCredito();
            t.setNumeroSolicitud(s.getNumeroSolicitud());
            t.setCif(a.getCif());
            t.setNombre(a.getNombre());
            t.setMonto(s.getMonto().toString());
            t.setFechaSolicitud(formatoFecha.format(s.getFecha()));
            t.setFechaAutorizacion(formatoFecha.format(g.getFecha()));
            return t;            
        }).forEach((t) -> {
            result.add(t);
        });
        
        em.close();
        emf.close();
        
        return result;
    }
}
