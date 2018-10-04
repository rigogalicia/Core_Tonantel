
package av.modelo;

import admin.modelo.Agencia;
import dao.AvAsociado;
import dao.AvSolicitud;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;



public class SolicitudesRecibidas {
    private String numeroSolicitud;
    private String cif;
    private String asociado;
    private String agencia;
    private String fechaSolicitud;
    private String estado;
    private String Asesor;

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

    public String getAsociado() {
        return asociado;
    }

    public void setAsociado(String asociado) {
        this.asociado = asociado;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(String fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getAsesor() {
        return Asesor;
    }

    public void setAsesor(String Asesor) {
        this.Asesor = Asesor;
    }
    
    //Metodo utilizado para consultar todas las solucitudes generadas
    public ArrayList<SolicitudesRecibidas> mostrarDato(){
        ArrayList<SolicitudesRecibidas> result = new ArrayList<>();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
        EntityManager em = emf.createEntityManager();
        
        String instruccion = "SELECT s, a "
                +"FROM AvSolicitud s "
                +"JOIN s.asociadoCif a "
                +"WHERE s.estado = 'a' "; 
                
        Query consulta = em.createQuery(instruccion);
        
        List<Object[]> resultado = consulta.getResultList();
        
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        
        for(Object[] obj: resultado){
           AvSolicitud s = (AvSolicitud) obj[0];
           AvAsociado a = (AvAsociado) obj[1];
           
           SolicitudesRecibidas solrecibidas = new SolicitudesRecibidas();
           solrecibidas.setNumeroSolicitud(s.getNumeroSolicitud());
           solrecibidas.setCif(a.getCif());
           solrecibidas.setAsociado(a.getNombre());
           solrecibidas.setAgencia(Agencia.descripcionAgencia(s.getAgencia()));
           solrecibidas.setFechaSolicitud(formatoFecha.format(s.getFechahora()));
           solrecibidas.setAsesor(s.getUsuario());
           
           result.add(solrecibidas);
        }
         em.close();
         emf.close();
        
        
        return result;
    }
    
}
