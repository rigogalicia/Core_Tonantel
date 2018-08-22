package av.modelo;

import admin.modelo.Colaborador;
import dao.AvAsociado;
import dao.AvColindante;
import dao.AvDocumento;
import dao.AvInmueble;
import dao.AvPropietario;
import dao.AvSolicitud;
import dao.AvTelefono;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpSession;

public class Solicitud {
    AvAsociado asociado = new AvAsociado();
    ArrayList<AvTelefono> telefonos = new ArrayList<>();
    AvDocumento documento = new AvDocumento();
    AvPropietario propietario = new AvPropietario();
    AvInmueble inmueble = new AvInmueble();
    ArrayList<AvColindante> colindantes = new ArrayList<>();
    AvSolicitud solicitud = new AvSolicitud();
    private String userConect;
    
    
    //Metodo contstructor
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

    public AvAsociado getAsociado() {
        return asociado;
    }

    public void setAsociado(AvAsociado asociado) {
        this.asociado = asociado;
    }

    public ArrayList<AvTelefono> getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(ArrayList<AvTelefono> telefonos) {
        this.telefonos = telefonos;
    }

    public AvDocumento getDocumento() {
        return documento;
    }

    public void setDocumento(AvDocumento documento) {
        this.documento = documento;
    }

    public AvPropietario getPropietario() {
        return propietario;
    }

    public void setPropietario(AvPropietario propietario) {
        this.propietario = propietario;
    }

    public AvInmueble getInmueble() {
        return inmueble;
    }

    public void setInmueble(AvInmueble inmueble) {
        this.inmueble = inmueble;
    }

    public ArrayList<AvColindante> getColindantes() {
        return colindantes;
    }

    public void setColindantes(ArrayList<AvColindante> colindantes) {
        this.colindantes = colindantes;
    }

    public AvSolicitud getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(AvSolicitud solicitud) {
        this.solicitud = solicitud;
    }
    
    
    /* Este metodo crea una solicitud, envia los datos a la base de datos
    Por medio de una transaccion*/
    public void crearSolicitud(){
        EntityManagerFactory emf = null;
        EntityManager em = null;
        
        try{
            emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
            em = emf.createEntityManager();
            
            em.getTransaction().begin();
            Colaborador colaborador = Colaborador.datosColaborador(userConect);
            solicitud.setUsuario(userConect);
            solicitud.setAgencia(colaborador.getAgencia());
            solicitud.setFechahora(new Date());
            solicitud.setEstado('a');
            em.merge(asociado);
            for(AvTelefono t : telefonos){
                t.setAsociadoCif(asociado);
                em.merge(t);
            }
            em.merge(propietario);
            em.merge(documento);
            inmueble.setPropietarioDpi(propietario);
            inmueble.setDocumentoId(documento);
            em.merge(inmueble);
            for(AvColindante c : colindantes){
                c.setTipo('a');
                c.setInmuebleId(inmueble);
                em.merge(c);
            }
            solicitud.setAsociadoCif(asociado);
            solicitud.setInmuebleId(inmueble);
            em.merge(solicitud);
            em.getTransaction().commit();
        }
        catch(Exception e){
            e.printStackTrace(System.out);
        }finally{
            if(emf != null && em != null){
                em.close();
                emf.close();
            }
        }
    }
}
