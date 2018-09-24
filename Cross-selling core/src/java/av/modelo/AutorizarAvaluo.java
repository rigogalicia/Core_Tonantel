
package av.modelo;

import admin.modelo.Agencia;
import admin.modelo.Colaborador;
import dao.AvAsignacion;
import dao.AvAsociado;
import dao.AvSolicitud;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;


public class AutorizarAvaluo {
    private String numeroSolicitud;
    private String cif;
    private String asociado;
    private String valuador;
    private String agencia;
    private String fechaSolicitud;
    private String estado;
    
    private char est;

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

    public String getValuador() {
        return valuador;
    }

    public void setValuador(String valuador) {
        this.valuador = valuador;
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

    public char getEst() {
        return est;
    }

    public void setEst(char est) {
        this.est = est;
    }
    
    
    
    //Metodo utilizado para consusltar los avaluos creados
    public ArrayList<AutorizarAvaluo> consultarAvaluo(){
        ArrayList<AutorizarAvaluo> result = new ArrayList<>();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
        EntityManager em = emf.createEntityManager();
        
        String instruccion = "SELECT a, s, p "
                + "FROM AvAsignacion a "
                + "JOIN a.solicitudNumeroSolicitud s "
                + "JOIN s.asociadoCif p "
                +"WHERE s.estado = :est ";
        
        Query consulta = em.createQuery(instruccion);
        consulta.setParameter("est", est);
        
        List<Object[]> resultado = consulta.getResultList();
        
        SimpleDateFormat formatofecha = new SimpleDateFormat("dd/MM/yyyy");

        for(Object[] obj: resultado){
            AvAsignacion a = (AvAsignacion) obj [0];
            AvSolicitud s = (AvSolicitud) obj [1];
            AvAsociado p = (AvAsociado) obj [2];
            

            
            AutorizarAvaluo autAvaluo = new AutorizarAvaluo();
            Colaborador c = new Colaborador();
            
            autAvaluo.setNumeroSolicitud(s.getNumeroSolicitud());
            autAvaluo.setCif(p.getCif());
            autAvaluo.setAsociado(p.getNombre());
            autAvaluo.setValuador(a.getUsuario());
            autAvaluo.setAgencia(Agencia.descripcionAgencia(s.getAgencia()));
            autAvaluo.setFechaSolicitud(formatofecha.format(s.getFechahora()));
            autAvaluo.setEstado(EstadoAvaluo.convert(s.getEstado()));
            
            result.add(autAvaluo);
        }
        
        em.close();
        emf.close();
        return result;
    }
}
