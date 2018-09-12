package gc.modelo;

import dao.GcAsociado;
import dao.GcDestino;
import dao.GcSolicitud;
import dao.GcTipo;
import dao.GcTipocliente;
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
    private String asesorFinanciero;
    private String cif;
    private int tipoId;
    private String tipo;
    private int destinoId;
    private String destino;
    private int tramiteId;
    private String tramite;
    private String monto;
    private String fecha;
    private int clienteId;
    private String cliente;

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

    public String getAsesorFinanciero() {
        return asesorFinanciero;
    }

    public void setAsesorFinanciero(String asesorFinanciero) {
        this.asesorFinanciero = asesorFinanciero;
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

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }
    
    /* Metodo utilizado para consultar los registros de solicitudes recibidas */
    public ArrayList<SolicitudesRecibidas> mostrarDatos(){
        ArrayList<SolicitudesRecibidas> result = new ArrayList<>();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
        EntityManager em = emf.createEntityManager();
        
        String instruccion = "SELECT a, s, d, t, f, e, c "
                + "FROM GcSolicitud s "
                + "JOIN s.asociadoCif a "
                + "JOIN s.destinoId d "
                + "JOIN s.tipoId t "
                + "JOIN s.tramiteId f "
                + "JOIN s.estadoId e "
                + "JOIN s.tipoclienteId c "
                + "WHERE e.id = :estadoGeneradas "
                + "AND s.est = null ";
        
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
        
        if(clienteId != 0){
            instruccion += "AND c.id = "+clienteId+" ";
        }
        
        instruccion += "ORDER BY s.fecha DESC ";
        
        Query consulta = em.createQuery(instruccion);
        consulta.setParameter("estadoGeneradas", "a");
        List<Object[]> resultado = consulta.getResultList();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy | hh:mm a");
        
        for(Object[] obj : resultado){
            GcAsociado a = (GcAsociado) obj[0];
            GcSolicitud s = (GcSolicitud) obj[1];
            GcDestino d = (GcDestino) obj[2];
            GcTipo t = (GcTipo) obj[3];
            GcTramite f = (GcTramite) obj[4];
            GcTipocliente c = (GcTipocliente) obj[6];
            
            SolicitudesRecibidas recibidas = new SolicitudesRecibidas();
            recibidas.setNumeroSolicitud(s.getNumeroSolicitud());
            recibidas.setNombreAsociado(a.getNombre());
            recibidas.setAsesorFinanciero(s.getAsesorFinanciero());
            recibidas.setCif(a.getCif());
            recibidas.setTipo(t.getDescripcion());
            recibidas.setDestino(d.getDescripcion());
            recibidas.setTramite(f.getDescripcion());
            DecimalFormat formato = new DecimalFormat("0,000.00");
            recibidas.setMonto(formato.format(s.getMonto().doubleValue()));
            recibidas.setFecha(formatoFecha.format(s.getFecha()));
            recibidas.setCliente(c.getDescripcion());
            
            result.add(recibidas);
        }
        
        em.close();
        emf.close();
        
        return result;
    }
    
}
