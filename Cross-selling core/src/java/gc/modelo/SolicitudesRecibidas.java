package gc.modelo;

import dao.GcAsociado;
import dao.GcDestino;
import dao.GcSolicitud;
import dao.GcTipo;
import dao.GcTramite;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class SolicitudesRecibidas {
    private String numeroSolicitud;
    private String nombreAsociado;
    private String cif;
    private int tipoId;
    private String tipo;
    private int destinoId;
    private String destino;
    private int tramiteId;
    private String tramite;
    private String monto;
    private String fecha;

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

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
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

    public String getTramite() {
        return tramite;
    }

    public void setTramite(String tramite) {
        this.tramite = tramite;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
    /* Metodo utilizado para consultar los registros de solicitudes recibidas */
    public ArrayList<SolicitudesRecibidas> mostrarDatos(){
        ArrayList<SolicitudesRecibidas> result = new ArrayList<>();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
        EntityManager em = emf.createEntityManager();
        
        String instruccion = "SELECT a, s, d, t, f, e "
                + "FROM GcSolicitud s "
                + "JOIN s.asociadoCif a "
                + "JOIN s.destinoId d "
                + "JOIN s.tipoId t "
                + "JOIN s.tramiteId f "
                + "JOIN s.estadoId e "
                + "WHERE e.id = :estadoGeneradas ";
        
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
        
        if(cif != null){
            if(!cif.isEmpty()){
                instruccion += "AND a.cif = '"+cif+"' ";
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
        
        instruccion += "ORDER BY s.fecha DESC ";
        
        Query consulta = em.createQuery(instruccion);
        consulta.setParameter("estadoGeneradas", "a");
        List<Object[]> resultado = consulta.getResultList();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy | HH:mm:ss");
        
        for(Object[] obj : resultado){
            GcAsociado a = (GcAsociado) obj[0];
            GcSolicitud s = (GcSolicitud) obj[1];
            GcDestino d = (GcDestino) obj[2];
            GcTipo t = (GcTipo) obj[3];
            GcTramite f = (GcTramite) obj[4];
            
            SolicitudesRecibidas recibidas = new SolicitudesRecibidas();
            recibidas.setNumeroSolicitud(s.getNumeroSolicitud());
            recibidas.setNombreAsociado(a.getNombre());
            recibidas.setCif(a.getCif());
            recibidas.setTipo(t.getDescripcion());
            recibidas.setDestino(d.getDescripcion());
            recibidas.setTramite(f.getDescripcion());
            DecimalFormat formato = new DecimalFormat("0,000.00");
            recibidas.setMonto(formato.format(s.getMonto().doubleValue()));
            recibidas.setFecha(formatoFecha.format(s.getFecha()));
            
            result.add(recibidas);
        }
        
        em.close();
        emf.close();
        
        return result;
    }
    
}
