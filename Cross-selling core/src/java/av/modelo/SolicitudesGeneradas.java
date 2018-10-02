
package av.modelo;

import dao.AvAsociado;
import dao.AvSolicitud;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;


public class SolicitudesGeneradas {
    private String numeroSolicitud;
    private String cif;
    private String nombreAsociado;
    private String fecha;
    private String estado;
    private char est;
    
    //metodo constructor para tomar la sesion
    public SolicitudesGeneradas(){}

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

    public String getNombreAsociado() {
        return nombreAsociado;
    }

    public void setNombreAsociado(String nombreAsociado) {
        this.nombreAsociado = nombreAsociado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public char getEst() {
        return est;
    }

    public void setEst(char est) {
        this.est = est;
    }

    public ArrayList<SolicitudesGeneradas> mostraDatos(String userConect){
        ArrayList<SolicitudesGeneradas> result = new ArrayList<>();
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
        EntityManager em = emf.createEntityManager();
        
        String instruccion = "SELECT s, a "
                + "FROM AvSolicitud s "
                + "JOIN s.asociadoCif a "
                + "WHERE s.usuario = :userConect "
                + "AND s.estado = :est";
        
        Query consulta = em.createQuery(instruccion);
        consulta.setParameter("userConect", userConect);
        consulta.setParameter("est", est);
        List<Object[]> resultado = consulta.getResultList();
        
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        
        for(Object[] obj : resultado){
            AvSolicitud s = (AvSolicitud) obj[0];
            AvAsociado a = (AvAsociado) obj[1];
            
            SolicitudesGeneradas solGen = new SolicitudesGeneradas();
            solGen.setNumeroSolicitud(s.getNumeroSolicitud());
            solGen.setCif(a.getCif());
            solGen.setNombreAsociado(a.getNombre());
            solGen.setFecha(formatoFecha.format(s.getFechahora()));
            solGen.setEstado(EstadoAvaluo.convert(s.getEstado()));
            solGen.setEst(s.getEstado());
            
            result.add(solGen);
        }
        
        em.close();
        emf.close();
        
        return result;
    }
    
}
