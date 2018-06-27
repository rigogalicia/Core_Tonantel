package gc.modelo;

import dao.GcAsociado;
import dao.GcDestino;
import dao.GcEstado;
import dao.GcSolicitud;
import dao.GcTipo;
import dao.GcTipocliente;
import dao.GcTramite;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class DetalleSolicitud {
    private String numeroSolicitud;
    private String cif;
    private String nombre;
    private String asesorFinanciero;
    private String monto;
    private String fecha;
    private String estadoId;
    private String estado;
    private String destino;
    private String tipo;
    private String tramite;
    private String tipoCliente;
    
    public DetalleSolicitud(String NumeroSolicitud){
        this.numeroSolicitud = NumeroSolicitud;
    }

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

    public String getAsesorFinanciero() {
        return asesorFinanciero;
    }

    public void setAsesorFinanciero(String asesorFinanciero) {
        this.asesorFinanciero = asesorFinanciero;
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

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTramite() {
        return tramite;
    }

    public void setTramite(String tramite) {
        this.tramite = tramite;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }
    
    /* Consulta los datos de la solicitud por numero de solicitud */
    public void consultarDatos(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
        EntityManager em = emf.createEntityManager();
        
        String instruccion = "SELECT s, a, e, c, d, t, f "
                + "FROM GcSolicitud s "
                + "JOIN s.asociadoCif a "
                + "JOIN s.estadoId e "
                + "JOIN s.tipoclienteId c "
                + "JOIN s.destinoId d "
                + "JOIN s.tipoId t "
                + "JOIN s.tramiteId f "
                + "WHERE s.numeroSolicitud = :numeroSolicitud";
        Query consulta = em.createQuery(instruccion);
        consulta.setParameter("numeroSolicitud", this.numeroSolicitud);
        List<Object[]> resultado = consulta.getResultList();
        
        for(Object[] obj : resultado){
            GcSolicitud s = (GcSolicitud) obj[0];
            GcAsociado a = (GcAsociado) obj[1];
            GcEstado e = (GcEstado) obj[2];
            GcTipocliente c = (GcTipocliente) obj[3];
            GcDestino d = (GcDestino) obj[4];
            GcTipo t = (GcTipo) obj[5];
            GcTramite f = (GcTramite) obj[6];
            
            DecimalFormat formatoMonto = new DecimalFormat("0,000.00");
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
            
            numeroSolicitud = s.getNumeroSolicitud();
            cif = a.getCif();
            nombre = a.getNombre();
            asesorFinanciero = s.getAsesorFinanciero();
            monto = formatoMonto.format(s.getMonto().doubleValue());
            fecha = formatoFecha.format(s.getFecha());
            estadoId = e.getId();
            estado = e.getDescripcion();
            destino = d.getDescripcion();
            tipo = t.getDescripcion();
            tramite = f.getDescripcion();
            tipoCliente = c.getDescripcion();
        }
        
        em.close();
        emf.close();
    }
}
