package gc.modelo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class SolicitudesEnproceso {
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
    
    /* Metodo utilizado para devolver los registros asignados */
    public ArrayList<SolicitudesEnproceso> mostrarDatos(){
        ArrayList<SolicitudesEnproceso> result = new ArrayList<>();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
        EntityManager em = emf.createEntityManager();
        
        String instruccion = "SELECT S.numero_solicitud numeroSolicitud, "
                + "A.nombre nombreAsociado, "
                + "A.cif cif, "
                + "T.id tipoId, "
                + "T.descripcion tipo, "
                + "D.id destinoId, "
                + "D.descripcion destino, "
                + "F.id tramiteId, "
                + "F.descripcion tramite, "
                + "S.monto monto "
                + "FROM gc_solicitud S "
                + "LEFT JOIN gc_asociado A "
                + "ON S.asociado_cif = A.cif "
                + "LEFT JOIN gc_destino D "
                + "ON S.destino_id = D.id "
                + "LEFT JOIN gc_tipo T "
                + "ON S.tipo_id = T.id "
                + "LEFT JOIN gc_tramite F "
                + "ON S.tramite_id = F.id "
                + "LEFT JOIN gc_gestion G "
                + "ON S.numero_solicitud = G.solicitud_numero_solicitud";
        Query consulta = em.createNativeQuery(instruccion);
        List<Object[]> resultado = consulta.getResultList();
        
        for(Object[] obj : resultado){
            SolicitudesEnproceso se = new SolicitudesEnproceso();
            se.setNumeroSolicitud((String) obj[0]);
            se.setNombreAsociado((String) obj[1]);
            se.setCif((String) obj[2]);
            se.setTipoId((int) obj[3]);
            se.setTipo((String) obj[4]);
            se.setDestinoId((int) obj[5]);
            se.setDestino((String) obj[6]);
            se.setTramiteId((int) obj[7]);
            se.setTramite((String) obj[8]);
            BigDecimal resultadoMonto = (BigDecimal) obj[9];
            se.setMonto(resultadoMonto.toString());
            
            result.add(se);
        }
        
        em.close();
        emf.close();
        
        return result;
    }
}
