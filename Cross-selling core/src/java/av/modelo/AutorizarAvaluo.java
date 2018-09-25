
package av.modelo;

import admin.modelo.Agencia;
import admin.modelo.Colaborador;
import dao.AvAsignacion;
import dao.AvAsociado;
import dao.AvAvaluo;
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
    private int idAvaluo;
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

    public int getIdAvaluo() {
        return idAvaluo;
    }

    public void setIdAvaluo(int idAvaluo) {
        this.idAvaluo = idAvaluo;
    }
    
    public char getEst() {
        return est;
    }

    public void setEst(char est) {
        this.est = est;
    }

    // Metodo utilizado para consusltar los avaluos creados
    public ArrayList<AutorizarAvaluo> consultarAvaluo(){
        ArrayList<AutorizarAvaluo> result = new ArrayList<>();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
        EntityManager em = emf.createEntityManager();
        
        String instruccion = "SELECT a, b, c, d "
                + "FROM AvAvaluo a "
                + "JOIN a.asignacionId b "
                + "JOIN b.solicitudNumeroSolicitud c "
                + "JOIN c.asociadoCif d "
                + "WHERE c.estado = :est";
        
        Query consulta = em.createQuery(instruccion);
        consulta.setParameter("est", est);
        
        List<Object[]> resultado = consulta.getResultList();
        
        SimpleDateFormat formatofecha = new SimpleDateFormat("dd/MM/yyyy");

        for(Object[] obj: resultado){
            AvAvaluo a = (AvAvaluo) obj[0];
            AvAsignacion b = (AvAsignacion) obj [1];
            AvSolicitud c = (AvSolicitud) obj [2];
            AvAsociado d = (AvAsociado) obj [3];

            AutorizarAvaluo autAvaluo = new AutorizarAvaluo();
            
            autAvaluo.setNumeroSolicitud(c.getNumeroSolicitud());
            autAvaluo.setCif(d.getCif());
            autAvaluo.setAsociado(d.getNombre());
            autAvaluo.setValuador(Colaborador.datosColaborador(b.getUsuario()).getNombre());
            autAvaluo.setAgencia(Agencia.descripcionAgencia(c.getAgencia()));
            autAvaluo.setFechaSolicitud(formatofecha.format(c.getFechahora()));
            autAvaluo.setEstado(EstadoAvaluo.convert(c.getEstado()));
            autAvaluo.setIdAvaluo(a.getId());
            
            result.add(autAvaluo);
        }
        
        em.close();
        emf.close();
        
        return result;
    }
}
