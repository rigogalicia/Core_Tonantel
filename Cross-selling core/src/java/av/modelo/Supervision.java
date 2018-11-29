
package av.modelo;

import admin.modelo.Agencia;
import admin.modelo.Colaborador;
import dao.AvAsignacion;
import dao.AvAsociado;
import dao.AvInmueble;
import dao.AvSolicitud;
import gc.controlador.Correo;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/*Autor Perez*/
public class Supervision {
    //Atributos para llenar el array que me devuelve todas las solicitudes
    private String numSolicitud;
    private String solicitante;
    private String direccion;
    private String agencia;
    private String asesor;
    private String asesorNombre;
    private String fechaSolicitud;
    private String estado;
    private char est;
    
    //Atributos para llenar la asignacion
    private String valuador;
    private Date fechaVisita;
    private String observaciones;
    private String msjFecha;
    private String error;
    private Supervision supervision; 
    private AvAsignacion asignacion;

    public String getNumSolicitud() {
        return numSolicitud;
    }

    public void setNumSolicitud(String numSolicitud) {
        this.numSolicitud = numSolicitud;
    }

    public String getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(String solicitante) {
        this.solicitante = solicitante;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getAsesor() {
        return asesor;
    }

    public void setAsesor(String asesor) {
        this.asesor = asesor;
    }

    public String getAsesorNombre() {
        return asesorNombre;
    }

    public void setAsesorNombre(String asesorNombre) {
        this.asesorNombre = asesorNombre;
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

    public String getValuador() {
        return valuador;
    }

    public void setValuador(String valuador) {
        this.valuador = valuador;
    }

    public Date getFechaVisita() {
        return fechaVisita;
    }

    public void setFechaVisita(Date fechaVisita) {
        this.fechaVisita = fechaVisita;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getMsjFecha() {
        return msjFecha;
    }

    public void setMsjFecha(String msjFecha) {
        this.msjFecha = msjFecha;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
    
    //Metodo utilizado para consultar todas las solicitudes generadas
    public ArrayList<Supervision> mostrarSolicitud(){
        ArrayList<Supervision> result = new ArrayList<>();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
        EntityManager em = emf.createEntityManager();
        
        String instruccion = "SELECT s, a, i "
                +"FROM AvSolicitud s "
                +"JOIN s.asociadoCif a "
                +"JOIN s.inmuebleId i "
                +"WHERE s.estado = :est ";
        
        Query consulta = em.createQuery(instruccion);
        consulta.setParameter("est", est);
        
        List<Object[]> resultado = consulta.getResultList();
        
        for(Object[] obj: resultado){
            AvSolicitud s = (AvSolicitud) obj[0];
            AvAsociado a = (AvAsociado) obj[1];
            AvInmueble i = (AvInmueble) obj[2];
            
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            
            Supervision sup = new Supervision();
            sup.setNumSolicitud(s.getNumeroSolicitud());
            sup.setSolicitante(a.getNombre());
            sup.setDireccion(i.getDireccionRegistrada());
            sup.setAgencia(Agencia.descripcionAgencia(s.getAgencia()));
            sup.setAsesorNombre(Colaborador.datosColaborador(s.getUsuario()).getNombre());
            sup.setAsesor(s.getUsuario());
            sup.setFechaSolicitud(formato.format(s.getFechahora()));
            sup.setEstado(EstadoAvaluo.convert(s.getEstado()));
            sup.setEst(s.getEstado());
            
            result.add(sup);

        }
        em.close();
        emf.close();
        
        return result;
    }
    
    
    //Metodo para asignar un avaluo
    public void asiganrAvaluo(){
       error = null;
       Date fecha;
       fecha = agregarDiaFecha(fechaVisita);
       if(isGenerada(numSolicitud) && fecha != null && fecha.compareTo(new Date()) == 1){
           EntityManagerFactory emf = null;
           EntityManager em = null;
           
           try {
               emf =  Persistence.createEntityManagerFactory("Cross-selling_corePU");
               em = emf.createEntityManager();
               
               em.getTransaction().begin();
               
               asignacion = new AvAsignacion();
               asignacion.setUsuario(valuador);
               asignacion.setFirma(Colaborador.urlImagen(valuador));
               asignacion.setFechaVisita(fecha);
               asignacion.setFechahora(new Date());
               asignacion.setSolicitudNumeroSolicitud(new AvSolicitud(numSolicitud));
               
               AvSolicitud solicitud = em.find(AvSolicitud.class, numSolicitud);
               solicitud.setEstado('b');
               
               em.persist(asignacion);
               em.getTransaction().commit();

               FacesContext.getCurrentInstance().getExternalContext().redirect("/Cross-selling_core/faces/vista/av/av_supervision.xhtml");
               enviarCorreoAsesor();
               enviarCorreoValuador();

                
           } catch (Exception e) {
               e.printStackTrace(System.out);
           }
           finally{
                if(emf != null && em != null){
                    em.close();
                    emf.close();
                }
            }
           
        }
        else{
            if(fecha == null){
                msjFecha = "Ingrese fecha en que realizara la visita";
            }
            if(fecha.compareTo(new Date()) == -1){
                msjFecha = "La fecha de visita tiene que ser mayor o igual a la fecha actual";
            }
        }
    }
    
    //Metodo para consultar si ya fue tomado el avaluo
    public boolean isGenerada(String numeroSolicitud){
        boolean result = false;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
        EntityManager em = emf.createEntityManager();

        String instruccion = "SELECT s "
                + "FROM AvSolicitud s "
                + "WHERE s.numeroSolicitud = :numSolicitud";
        
        Query consulta = em.createQuery(instruccion);
        consulta.setParameter("numSolicitud", numeroSolicitud);
        List<AvSolicitud> resultado = consulta.getResultList();
        
        for(AvSolicitud s : resultado){
            if(s.getEstado() == 'a'){
                result = true;
            }
            else{
                error = "! La solicitud ya fue tomada por otro Valuador";

            }
        }

        em.close();
        emf.close();
        return result;
    }
    
    //Metodo utilizado para consultar la solicitu
    public Supervision consultarSolicitud(){
        supervision = new Supervision();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
        EntityManager em = emf.createEntityManager();
        
        String instruccion ="SELECT s, a "
                + "FROM AvSolicitud s "
                + "JOIN s.asociadoCif a "
                + "WHERE s.numeroSolicitud = :numSolicitud ";
        
        Query consulta = em.createQuery(instruccion);
        consulta.setParameter("numSolicitud", numSolicitud);
        
        List<Object[]> resultado = consulta.getResultList();
        for(Object[] obj: resultado){
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            AvSolicitud s = (AvSolicitud) obj[0];
            AvAsociado a = (AvAsociado) obj[1];
            
            supervision.setNumSolicitud(s.getNumeroSolicitud());
            supervision.setAsesorNombre(Colaborador.datosColaborador(s.getUsuario()).getNombre());
            supervision.setAsesor(s.getUsuario());
            supervision.setFechaSolicitud(formato.format(s.getFechahora()));
            supervision.setAgencia(Agencia.descripcionAgencia(s.getAgencia()));
            supervision.setSolicitante(a.getNombre());
            
                       
        }
        em.close();
        emf.close();
        
        return supervision;
    }

    //Metodo para enviar el correo que notifica a quien fue asignada la solicitud
    private void enviarCorreoAsesor(){
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            if(supervision.getNumSolicitud().equals(numSolicitud)){
               String msj = "La solicitud de Avalúo número "+ supervision.getNumSolicitud() +" generada el "+ supervision.getFechaSolicitud() +"\n"
                + "que pertenece al asociado "+ supervision.getSolicitante() +", fue asignada a\n" + Colaborador.datosColaborador(asignacion.getUsuario()).getNombre() +"\n"
                + "el cual programó la visita para el día " + formato.format(asignacion.getFechaVisita()) + ", para\n"
                + "un mejor seguimiento ingresa a la aplicación Crosselling Core.\n\n "
                + "Copyright © Investigación y Desarrollo de Tecnología Cooperativa Tonantel R.L";
               

         Correo correo = new Correo(Colaborador.correoColaborador(supervision.getAsesor()), "Numero solicitud " + supervision.getNumSolicitud(), msj);
         correo.enviar(); 
         } 
    }
  
    //Metodo para enviar correo a valuador
    public void enviarCorreoValuador(){
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

        if(supervision.getNumSolicitud().equals(numSolicitud)){
            String msjValuador ="La solicutd de Avalúo número " + supervision.getNumSolicitud() + " generada el " + supervision.getFechaSolicitud() + "\n"
               + "por el asesor " + Colaborador.datosColaborador(supervision.getAsesor()).getNombre() + " de la agenica " + supervision.getAgencia() + "\n"
               + "que pertenece al asociado " + supervision.getSolicitante() + "\n"
               + "se te fue asignada por el encargado de creditos, el cual programo la fehca de visita " + "\n"
               + "para el " + formato.format(asignacion.getFechaVisita()) + "\n"
               + "para un mejor seguimiento ingresa a Crosselling Core.\n\n "
               + "Copyright © Investigación y Desarrollo de Tecnología Cooperativa Tonantel R.L";
            
            Correo correoAvaluo = new Correo(Colaborador.correoColaborador(asignacion.getUsuario()), "Numero solicitud " + supervision.getNumSolicitud(), msjValuador);
            correoAvaluo.enviar();
        }
    }
    
    // Funcion para agregar un dia a fecha de visita
    private Date agregarDiaFecha(Date fecha){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.DATE, 1);
        return calendar.getTime();
    }
}
