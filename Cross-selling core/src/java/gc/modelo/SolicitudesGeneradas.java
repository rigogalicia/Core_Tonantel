package gc.modelo;

import dao.GcAsociado;
import dao.GcDestino;
import dao.GcEstado;
import dao.GcSolicitud;
import dao.GcTipo;
import dao.GcTramite;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class SolicitudesGeneradas {
    private String numeroSolicitud;
    private String nombreAsociado;
    private int tipoId;
    private String tipo;
    private int destinoId;
    private String destino;
    private int tramiteId;
    private String origenTramite;
    private String estadoId;
    private String estado;
    
    public String getNumeroSolicitud() {
        return numeroSolicitud;
    }

    public void setNumeroSolicitud(String numeroSolicitud) {
        this.numeroSolicitud = numeroSolicitud;
    }

    public String getNombreAsociado() {
        return nombreAsociado;
    }

    public void setNombreAsociado(String nombreAsociado) {
        this.nombreAsociado = nombreAsociado;
    }

    public int getTipoId() {
        return tipoId;
    }

    public void setTipoId(int tipoId) {
        this.tipoId = tipoId;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getDestinoId() {
        return destinoId;
    }

    public void setDestinoId(int destinoId) {
        this.destinoId = destinoId;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public int getTramiteId() {
        return tramiteId;
    }

    public void setTramiteId(int tramiteId) {
        this.tramiteId = tramiteId;
    }

    public String getOrigenTramite() {
        return origenTramite;
    }

    public void setOrigenTramite(String origenTramite) {
        this.origenTramite = origenTramite;
    }

    public String getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(String estadoId) {
        this.estadoId = estadoId;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public ArrayList<SolicitudesGeneradas> mostrarDatos(String asesorFinanciero){
        ArrayList<SolicitudesGeneradas> result = new ArrayList<>();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
        EntityManager em = emf.createEntityManager();
        
        String instruccion = "SELECT e, a, s, d, t, f "
                + "FROM GcSolicitud s "
                + "JOIN s.estadoId e "
                + "JOIN s.asociadoCif a "
                + "JOIN s.destinoId d "
                + "JOIN s.tipoId t "
                + "JOIN s.tramiteId f "
                + "WHERE s.asesorFinanciero = '"+asesorFinanciero+"' ";
        
        if(numeroSolicitud != null){
            if(!numeroSolicitud.isEmpty()){
                instruccion += "AND s.numeroSolicitud = '"+numeroSolicitud+"' ";
            }
        }
        
        if(nombreAsociado != null){
            if(!nombreAsociado.isEmpty()){
                instruccion += "AND a.nombre LIKE '%"+nombreAsociado+"%' ";
            }
        }
        
        if(tipoId != 0){
            instruccion += "AND t.id = "+tipoId+" ";
        }
        
        if(destinoId != 0){
            instruccion += "AND d.id = "+destinoId+" ";
        }
        
        if(tramiteId != 0){
            instruccion += "AND f.id = "+tramiteId+" ";
        }
        
        if(!estadoId.equals("-")){
            instruccion += "AND e.id = '"+estadoId+"' ";
        }
        
        instruccion += "ORDER BY s.fecha DESC ";
        
        Query consulta = em.createQuery(instruccion);
        List<Object[]> resultado = consulta.getResultList();
        
        for(Object[] obj : resultado){
            GcEstado e = (GcEstado) obj[0];
            GcAsociado a = (GcAsociado) obj[1];
            GcSolicitud s = (GcSolicitud) obj[2];
            GcDestino d = (GcDestino) obj[3];
            GcTipo t = (GcTipo) obj[4];
            GcTramite f = (GcTramite) obj[5];
            
            SolicitudesGeneradas sg = new SolicitudesGeneradas();
            sg.setNumeroSolicitud(s.getNumeroSolicitud());
            sg.setNombreAsociado(a.getNombre());
            sg.setTipo(t.getDescripcion());
            sg.setDestino(d.getDescripcion());
            sg.setOrigenTramite(f.getDescripcion());
            sg.setEstado(e.getDescripcion());
            
            result.add(sg);
        }
        
        em.close();
        emf.close();
        
        return result;
    }
}
