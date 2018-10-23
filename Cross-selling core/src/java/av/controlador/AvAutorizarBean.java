
package av.controlador;

import admin.modelo.Colaborador;
import av.modelo.AutorizarAvaluo;
import av.modelo.CrearAvaluo;
import dao.AvAvaluo;
import dao.AvSolicitud;
import gc.controlador.Correo;
import java.io.IOException;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpSession;


@ManagedBean(name = "av_autorizar")
@ViewScoped
public class AvAutorizarBean {
    private ArrayList<AutorizarAvaluo> listAutorizar = new ArrayList<>();
    private AutorizarAvaluo autorizar = new AutorizarAvaluo();
    private String userConect = "";

    
    public AvAutorizarBean() {
        HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        if(sesion.getAttribute("userConect") != null){
            userConect = sesion.getAttribute("userConect").toString();
            autorizar.setEst('c');
            listAutorizar = autorizar.consultarAvaluo();
        }
        else
        {
            try {
               FacesContext.getCurrentInstance().getExternalContext().redirect("/Cross-selling_core/faces/index.xhtml"); 
            } catch (IOException ex) {
                ex.printStackTrace(System.out);
            }
        }
    }

    public ArrayList<AutorizarAvaluo> getListAutorizar() {
        return listAutorizar;
    }

    public void setListAutorizar(ArrayList<AutorizarAvaluo> listAutorizar) {
        this.listAutorizar = listAutorizar;
    }

    public AutorizarAvaluo getAutorizar() {
        return autorizar;
    }

    public void setAutorizar(AutorizarAvaluo autorizar) {
        this.autorizar = autorizar;
    }
     
    //Meto para hacer el filtro por estado
    public void filtrarDatos(ValueChangeEvent e){
        char newEst[] = e.getNewValue().toString().toCharArray();
        autorizar.setEst(newEst[0]);
        listAutorizar = autorizar.consultarAvaluo();
    }
    
    //Metodo para Enviar Correos
    public void enviarCorreo(String numeroSolicitud){
        
        for(AutorizarAvaluo a: listAutorizar){
            if(numeroSolicitud.equals(a.getNumeroSolicitud())){
                 String msj = "El avaluo correspondiente al numero de solicitud "+ a.getNumeroSolicitud() +" generada el "+ a.getFechaSolicitud() +"\n"
                        + "perteneciente al asociado "+ a.getAsociado() +" fue autorizado por el jefe de Creditos, \n"
                        + "ya puedes descargar el avaluo ingresando \n"
                        + " a Cross-Selling-Core \n"
                        + "https://core.app-tonantel.com/Cross-selling_core\n\n\n"
                        + "Copyright © Investigación y Desarrollo de Tecnología Cooperativa Tonantel R.L";

                 System.out.println("Se ejecuta" + a.getAsesor());

                 Correo correo = new Correo(Colaborador.correoColaborador(a.getAsesor()), "Numeros solicitud" + a.getNumeroSolicitud(), msj);
                 correo.enviar();
            }
        }
        
    }
    
    // Metodo para autorizar Estado
    public void autorizar(String numeroSolicitud, int idAvaluo){
        cambiarEstadoAvaluo(numeroSolicitud, 'd', idAvaluo);
    }
    
    // Metodo para rechazar avaluo
    public void rechazar(String numeroSolicitud, int idAvaluo){
        cambiarEstadoAvaluo(numeroSolicitud, 'e', idAvaluo);
    }
    
    // Metodo para autorizar o rechazar avaluo
    private void cambiarEstadoAvaluo(String numeroSolicitud, char estado, int idAvaluo){
        
        EntityManagerFactory emf = null;
        EntityManager em = null;
        try {
            emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
            em = emf.createEntityManager();
            
            em.getTransaction().begin();
            
            AvSolicitud solicitud = em.find(AvSolicitud.class, numeroSolicitud);
            solicitud.setEstado(estado);
            
            if(estado == 'd'){
                AvAvaluo avaluo = em.find(AvAvaluo.class, idAvaluo);
                avaluo.setAutorizador(userConect);
                avaluo.setFirmaAutorizador(Colaborador.urlImagen(userConect));
                enviarCorreo(numeroSolicitud);
            }
            
            em.getTransaction().commit();
            
            listAutorizar = autorizar.consultarAvaluo();
            
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
    
    // Metodo para mostrar el detalle del avaluo
    public void detalleAvaluo(String numeroSolicitud){
        CrearAvaluo.detalle(numeroSolicitud, CrearAvaluo.valuadorResponsable(numeroSolicitud), CrearAvaluo.autorizadorResponsable(numeroSolicitud));
    }
}
