package gc.modelo;

import admin.modelo.Colaborador;
import dao.GcAsociado;
import dao.GcDestino;
import dao.GcDesventajas;
import dao.GcEstado;
import dao.GcFichanegocio;
import dao.GcRiesgo;
import dao.GcSolicitud;
import dao.GcTipo;
import dao.GcTipocliente;
import dao.GcTramite;
import dao.GcVentajas;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;

public class Solicitud {
    private GcAsociado asociadoGc = new GcAsociado();
    private GcSolicitud solicitudGc = new GcSolicitud();
    private GcDestino destinoGc = new GcDestino();
    private GcTipo tipoGc = new GcTipo();
    private GcTramite tramiteGc = new GcTramite();
    private GcTipocliente clienteGc = new GcTipocliente();
    private String userConect;
    
    // Campos para crear la ficha de negociacion
    private GcFichanegocio fichaNegocio = new GcFichanegocio();
    private ArrayList<String> ventajas = new ArrayList<>();
    private ArrayList<String> desventajas = new ArrayList<>();
    
    // Campo para manejo de mensaje de error
    String error = null;
    
    public Solicitud(){
        HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        if(sesion.getAttribute("userConect") != null){
            userConect = sesion.getAttribute("userConect").toString();
        }
        else{
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/Cross-selling_core/faces/index.xhtml");
            } catch (IOException ex) {
                ex.printStackTrace(System.out);
            }
        }
    }

    public GcAsociado getAsociadoGc() {
        return asociadoGc;
    }

    public void setAsociadoGc(GcAsociado asociadoGc) {
        this.asociadoGc = asociadoGc;
    }

    public GcSolicitud getSolicitudGc() {
        return solicitudGc;
    }

    public void setSolicitudGc(GcSolicitud solicitudGc) {
        this.solicitudGc = solicitudGc;
    }

    public GcDestino getDestinoGc() {
        return destinoGc;
    }

    public void setDestinoGc(GcDestino destinoGc) {
        this.destinoGc = destinoGc;
    }

    public GcTipo getTipoGc() {
        return tipoGc;
    }

    public void setTipoGc(GcTipo tipoGc) {
        this.tipoGc = tipoGc;
    }

    public GcTramite getTramiteGc() {
        return tramiteGc;
    }

    public void setTramiteGc(GcTramite tramiteGc) {
        this.tramiteGc = tramiteGc;
    }

    public GcTipocliente getClienteGc() {
        return clienteGc;
    }

    public void setClienteGc(GcTipocliente clienteGc) {
        this.clienteGc = clienteGc;
    }

    public GcFichanegocio getFichaNegocio() {
        return fichaNegocio;
    }

    public void setFichaNegocio(GcFichanegocio fichaNegocio) {
        this.fichaNegocio = fichaNegocio;
    }

    public ArrayList<String> getVentajas() {
        return ventajas;
    }

    public void setVentajas(ArrayList<String> ventajas) {
        this.ventajas = ventajas;
    }

    public ArrayList<String> getDesventajas() {
        return desventajas;
    }

    public void setDesventajas(ArrayList<String> desventajas) {
        this.desventajas = desventajas;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    /* Metodo utilizado para generar una solicitud */
    public void generar(boolean isNegociacion){
        EntityManagerFactory emf = null;
        EntityManager em = null;
        try{
            emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
            em = emf.createEntityManager();
            
            em.getTransaction().begin();
        
            Colaborador colaborador = Colaborador.datosColaborador(userConect);
            solicitudGc.setAsesorFinanciero(userConect);
            solicitudGc.setIdAgencia(colaborador.getAgencia());
            solicitudGc.setFecha(new Date());
            GcEstado est = new GcEstado("a");
            solicitudGc.setEstadoId(est);
            solicitudGc.setAsociadoCif(asociadoGc);
            solicitudGc.setDestinoId(destinoGc);
            solicitudGc.setTipoId(tipoGc);
            solicitudGc.setTramiteId(tramiteGc);
            solicitudGc.setTipoclienteId(clienteGc);
            solicitudGc.setRiesgoId(new GcRiesgo(1));
            solicitudGc.setEst(isNegociacion == true ? 'a' : null);
            
            em.merge(asociadoGc);
            em.persist(solicitudGc);
            
            if(isNegociacion){
                fichaNegocio.setSolicitudNumeroSolicitud(solicitudGc);
                em.persist(fichaNegocio);
                
                for(String v : ventajas){
                    GcVentajas gcVentajas = new GcVentajas();
                    gcVentajas.setDescripcion(v);
                    gcVentajas.setFichanegocioId(fichaNegocio);
                    em.persist(gcVentajas);
                }
                
                for(String d : desventajas){
                    GcDesventajas gcDesventajas = new GcDesventajas();
                    gcDesventajas.setDescripcion(d);
                    gcDesventajas.setFichanegocioId(fichaNegocio);
                    em.persist(gcDesventajas);
                }
            }

            em.getTransaction().commit();
            
            FacesContext.getCurrentInstance().getExternalContext().redirect("/Cross-selling_core/faces/vista/gc/gc_generadas.xhtml");
        }
        catch(IOException e){
            e.printStackTrace(System.out);
        }
        catch(Exception e){
            e.printStackTrace(System.out);
            error = "! No se puede cargar la solicitud, el número de solicitud ya existe en la base de datos";
        }finally{
            if(em != null && emf != null){
                em.close();
                emf.close();
            }
        }
    }
   
    /* Metodo utilizado para obtener el nombre del asociado por el cif */
    public void consultarAsociado(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
        EntityManager em = emf.createEntityManager();
        
        String instruccion = "SELECT a "
                + "FROM GcAsociado a "
                + "WHERE a.cif = :miCif";
        Query consulta = em.createQuery(instruccion);
        consulta.setParameter("miCif", asociadoGc.getCif());
        
        try{
            asociadoGc = (GcAsociado) consulta.getSingleResult();
        }
        catch(Exception e){
            e.printStackTrace(System.out);
        }
        
        em.close();
        emf.close();
    }
    
    // Este metodo se utiliza para consultar una solicitud por numero
    public void consultarSolicitud(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
        EntityManager em = emf.createEntityManager();
        
        String instruccion = "SELECT s, a, c, d, t, f "
                + "FROM GcSolicitud s "
                + "JOIN s.asociadoCif a "
                + "JOIN s.tipoclienteId c "
                + "JOIN s.destinoId d "
                + "JOIN s.tipoId t "
                + "JOIN s.tramiteId f "
                + "WHERE s.numeroSolicitud = :numSolicitud";
        Query consulta = em.createQuery(instruccion);
        consulta.setParameter("numSolicitud", solicitudGc.getNumeroSolicitud());
        List<Object[]> resultado = consulta.getResultList();
        
        for(Object[] obj : resultado){
            solicitudGc = (GcSolicitud) obj[0];
            asociadoGc = (GcAsociado) obj[1];
            clienteGc = (GcTipocliente) obj[2];
            destinoGc = (GcDestino) obj[3];
            tipoGc = (GcTipo) obj[4];
            tramiteGc = (GcTramite) obj[5];
        }
        
        em.close();
        emf.close();
    }
    
    // Metodo utilizado para actualizar una solicitud
    public void actualizarSolicitud(){
        EntityManagerFactory emf = null;
        EntityManager em = null;
        
        try{
            emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
            em = emf.createEntityManager();
            
            em.getTransaction().begin();
            
            GcAsociado a = em.find(GcAsociado.class, asociadoGc.getCif());
            a.setNombre(asociadoGc.getNombre());
            GcSolicitud s = em.find(GcSolicitud.class, solicitudGc.getNumeroSolicitud());
            s.setMonto(solicitudGc.getMonto());
            s.setAsociadoCif(asociadoGc);
            s.setTipoclienteId(clienteGc);
            s.setDestinoId(destinoGc);
            s.setTipoId(tipoGc);
            s.setTramiteId(tramiteGc);
            
            em.getTransaction().commit();
        }
        catch(Exception e){
            e.printStackTrace(System.out);
        }finally{
            if(em != null && emf != null){
                em.close();
                emf.close();
            }
        }
        
    }
}