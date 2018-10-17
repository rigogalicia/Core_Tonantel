
package av.modelo;

import admin.modelo.Agencia;
import dao.AvAsignacion;
import dao.AvAsociado;
import dao.AvInmueble;
import dao.AvSolicitud;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;


public class SolicitudesEnproceso {
    private String numeroSolicitud;
    private String asociado;
    private String direccionRegistrada;
    private String agencia;
    private String fechaSolicitad;
    private String estado;
    private String userConect;

    private char est;

    public String getNumeroSolicitud() {
        return numeroSolicitud;
    }

    public void setNumeroSolicitud(String numeroSolicitud) {
        this.numeroSolicitud = numeroSolicitud;
    }

    public String getAsociado() {
        return asociado;
    }

    public void setAsociado(String asociado) {
        this.asociado = asociado;
    }

    public String getDireccionRegistrada() {
        return direccionRegistrada;
    }

    public void setDireccionRegistrada(String direccionRegistrada) {
        this.direccionRegistrada = direccionRegistrada;
    }


    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getFechaSolicitad() {
        return fechaSolicitad;
    }

    public void setFechaSolicitad(String fechaSolicitad) {
        this.fechaSolicitad = fechaSolicitad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getUserConect() {
        return userConect;
    }

    public void setUserConect(String userConect) {
        this.userConect = userConect;
    }

    public char getEst() {
        return est;
    }

    public void setEst(char est) {
        this.est = est;
    }
    
    //Metodo utilizado para consultar las solicitudes que se han asiganado los valuadores
    public ArrayList<SolicitudesEnproceso> mostrarDatos(){
        ArrayList<SolicitudesEnproceso> result = new ArrayList<>();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
        EntityManager em = emf.createEntityManager();
        
        String instruccion="SELECT a, s, p, i "
                + "FROM AvAsignacion a "
                + "JOIN a.solicitudNumeroSolicitud s "
                + "JOIN s.asociadoCif p "
                + "JOIN s.inmuebleId i "
                + "WHERE a.usuario = :userConect "
                + "AND s.estado = :est ";
        
        Query consulta = em.createQuery(instruccion);
        consulta.setParameter("userConect", userConect);
        consulta.setParameter("est", est);
        List<Object[]> resultado = consulta.getResultList();
        
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        
        for(Object[] obj: resultado){
            AvAsignacion a = (AvAsignacion) obj[0];
            AvSolicitud s = (AvSolicitud) obj[1];
            AvAsociado p = (AvAsociado) obj[2];
            AvInmueble i = (AvInmueble) obj[3];
            
            SolicitudesEnproceso solEnproceso = new SolicitudesEnproceso();
            solEnproceso.setNumeroSolicitud(s.getNumeroSolicitud());
            solEnproceso.setDireccionRegistrada(i.getDireccionRegistrada());
            solEnproceso.setAsociado(p.getNombre());
            solEnproceso.setAgencia(Agencia.descripcionAgencia(s.getAgencia()));
            solEnproceso.setFechaSolicitad(formatoFecha.format(s.getFechahora()));
            solEnproceso.setEstado(EstadoAvaluo.convert(s.getEstado()));
            solEnproceso.setUserConect(a.getUsuario());
            solEnproceso.setEst(s.getEstado());
            
            result.add(solEnproceso);
        }
        
        em.close();
        emf.close();
        
        return result;
    }
}
