package gc.modelo;

import dao.GcAsociado;
import dao.GcDestino;
import dao.GcEstado;
import dao.GcSolicitud;
import dao.GcTipo;
import dao.GcTipocliente;
import dao.GcTramite;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class Monitoreo {
    private String numeroSolicitud;
    private String asesorFinanciero;
    private String cif;
    private String nombre;
    private int destinoId;
    private String destino;
    private int tipoId;
    private String Tipo;
    private int tramiteId;
    private String tramite;
    private int clienteId;
    private String cliente;
    private String estadoId;
    private String estado;
    private String monto;
    private String idAgencia;
    private int mensajesNoLeidos;

    public String getNumeroSolicitud() {
        return numeroSolicitud;
    }

    public void setNumeroSolicitud(String numeroSolicitud) {
        this.numeroSolicitud = numeroSolicitud;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public int getTipoId() {
        return tipoId;
    }

    public void setTipoId(int tipoId) {
        this.tipoId = tipoId;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String Tipo) {
        this.Tipo = Tipo;
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

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getIdAgencia() {
        return idAgencia;
    }

    public void setIdAgencia(String idAgencia) {
        this.idAgencia = idAgencia;
    }

    public int getMensajesNoLeidos() {
        return mensajesNoLeidos;
    }

    public void setMensajesNoLeidos(int mensajesNoLeidos) {
        this.mensajesNoLeidos = mensajesNoLeidos;
    }
    
    // Metodo utilizado para obtener los datos filtrados por diferentes campos
    public ArrayList<Monitoreo> consultar(){
        ArrayList<Monitoreo> result = new ArrayList<>();
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
                + "WHERE s.monto >= 0 ";
        
        if(numeroSolicitud != null){
            if(!numeroSolicitud.isEmpty()){
                instruccion += "AND s.numeroSolicitud = '"+numeroSolicitud+"' ";
            }
        }
        
        if(cif != null){
            if(!cif.isEmpty()){
                instruccion += "AND a.cif = '"+cif+"' ";
            }
        }
        
        if(nombre != null){
            if(!nombre.isEmpty()){
                instruccion += "AND a.nombre LIKE '%"+nombre+"%' ";
            }
        }
        
        if(destinoId != 0){
            instruccion += "AND d.id = "+destinoId+" ";
        }
        
        if(tipoId != 0){
            instruccion += "AND t.id = "+tipoId+" ";
        }
        
        if(tramiteId != 0){
            instruccion += "AND f.id = "+tramiteId+" ";
        }
        
        if(clienteId != 0){
            instruccion += "AND c.id = "+clienteId+" ";
        }
        
        if(!estadoId.equals("-")){
            if(!estadoId.isEmpty()){
                instruccion += "AND e.id = '"+estadoId+"' ";
            }
        }
        
        if(idAgencia != null){
            if(!idAgencia.isEmpty()){
                instruccion += "AND s.idAgencia = '"+idAgencia+"' ";
            }
        }
        
        instruccion += "ORDER BY s.fecha DESC";
        
        Query consulta = em.createQuery(instruccion);
        List<Object[]> resultado = consulta.getResultList();
        
        for(Object[] obj : resultado){
            GcSolicitud s = (GcSolicitud) obj[0];
            GcAsociado a = (GcAsociado) obj[1];
            GcEstado e = (GcEstado) obj[2];
            GcTipocliente c = (GcTipocliente) obj[3];
            GcDestino d = (GcDestino) obj[4];
            GcTipo t = (GcTipo) obj[5];
            GcTramite f = (GcTramite) obj[6];
            
            Monitoreo monitoreo = new Monitoreo();
            monitoreo.setNumeroSolicitud(s.getNumeroSolicitud());
            monitoreo.setAsesorFinanciero(s.getAsesorFinanciero());
            monitoreo.setCif(a.getCif());
            monitoreo.setNombre(a.getNombre());
            monitoreo.setDestino(d.getDescripcion());
            monitoreo.setTipo(t.getDescripcion());
            monitoreo.setTramite(f.getDescripcion());
            monitoreo.setCliente(c.getDescripcion());
            monitoreo.setEstado(e.getDescripcion());
            monitoreo.setMonto(s.getMonto().toString());
            
            Chat chat = new Chat(s.getNumeroSolicitud());
            monitoreo.setMensajesNoLeidos(chat.mensajesNoLeidosGeneral());
            
            result.add(monitoreo);
        }
        
        em.close();
        emf.close();
        
        return result;
    }
 }