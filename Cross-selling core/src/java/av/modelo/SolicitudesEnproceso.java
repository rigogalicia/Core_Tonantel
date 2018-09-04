
package av.modelo;

import admin.modelo.Agencia;
import dao.AvAsignacion;
import dao.AvAsociado;
import dao.AvSolicitud;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;


public class SolicitudesEnproceso {
    private String numeroSolicitud;
    private String asociado;
    private String cif;
    private String agencia;
    private String fechaSolicitad;
    private String estado;
    private String userConect;
    private String valuador;
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

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
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

    public String getValuador() {
        return valuador;
    }

    public void setValuador(String valuador) {
        this.valuador = valuador;
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
        
        String instruccion="SELECT s, a, r "
                +"FROM AvSolicitud s "
                +"JOIN s.asociadoCif a "
                +"JOIN s.numeroSolicitud r"
                +"WHERE r.usuario = :userConect"
                +"ON s.estado = : est ";
        
        Query consulta = em.createQuery(instruccion);
        consulta.setParameter("userConect", userConect);
        consulta.setParameter("est", est );
        
        
        List<Object[]> resultado = consulta.getResultList();
        
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        
        for(Object[] obj: resultado){
            AvSolicitud s = (AvSolicitud) obj[0];
            AvAsociado a = (AvAsociado) obj[1];
            AvAsignacion r = (AvAsignacion) obj[2];
            
            SolicitudesEnproceso solEnproceso = new SolicitudesEnproceso();
            solEnproceso.setNumeroSolicitud(s.getNumeroSolicitud());
            solEnproceso.setCif(a.getCif());
            solEnproceso.setAsociado(a.getNombre());
            solEnproceso.setAgencia(Agencia.descripcionAgencia(s.getAgencia()));
            solEnproceso.setFechaSolicitad(formatoFecha.format(s.getFechahora()));
            solEnproceso.setEstado(EstadoAvaluo.convert(s.getEstado()));
            solEnproceso.setUserConect(r.getUsuario());
            
            result.add(solEnproceso);
            
            
        }
        em.close();
        emf.close();
        
        return result;
    }
}
