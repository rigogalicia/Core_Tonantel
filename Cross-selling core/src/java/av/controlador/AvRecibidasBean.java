
package av.controlador;

import admin.modelo.Colaborador;
import av.modelo.SolicitudesRecibidas;
import dao.AvAsignacion;
import dao.AvSolicitud;
import gc.controlador.Correo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;


@ManagedBean(name = "av_Recibidas")
@ViewScoped
public class AvRecibidasBean {
    private ArrayList<SolicitudesRecibidas> listRecibidas = new ArrayList<>();
    private SolicitudesRecibidas recibidas = new SolicitudesRecibidas();
    
    private String userConect;
    private String error;


    public AvRecibidasBean() {
        HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        if(sesion.getAttribute("userConect") != null){
            userConect = sesion.getAttribute("userConect").toString();
            listRecibidas = recibidas.mostrarDato();
        }
        else{
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/Cross-selling_core/faces/index.xhtml");
            } catch (IOException ex) {
                ex.printStackTrace(System.out);
            }
        }
        
    }

    public ArrayList<SolicitudesRecibidas> getListRecibidas() {
        return listRecibidas;
    }

    public void setListRecibidas(ArrayList<SolicitudesRecibidas> listRecibidas) {
        this.listRecibidas = listRecibidas;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
    
    //Metodo para consultar las solicitudes generadas
    public void asignarAvaluo(SolicitudesRecibidas s){
        error = null;
        if(isGenerada(s.getNumeroSolicitud())){
            EntityManagerFactory emf = null;
            EntityManager em = null;
            try {
                emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
                em = emf.createEntityManager();

                em.getTransaction().begin();

                AvAsignacion asignacion = new AvAsignacion();
                asignacion.setUsuario(userConect);
                asignacion.setFirma(Colaborador.urlImagen(userConect));
                asignacion.setFechahora(new Date());
                asignacion.setSolicitudNumeroSolicitud(new AvSolicitud(s.getNumeroSolicitud()));

                AvSolicitud solicitud = em.find(AvSolicitud.class, s.getNumeroSolicitud());
                solicitud.setEstado('b');

                em.persist(asignacion);
                em.getTransaction().commit();

                FacesContext.getCurrentInstance().getExternalContext().redirect("/Cross-selling_core/faces/vista/av/av_enproceso.xhtml");

                String msj = "La solicitud de Avaluo numero "+s.getNumeroSolicitud()+" generada el "+s.getFechaSolicitud()+"\n"
                        + "pertenece al asociado "+ s.getAsociado()+" fue asignada a\n"
                        + "un valuador de créditos para su debido proceso de aprobación, para\n"
                        + "un mejor seguimiento ingresa a la aplicacion Crosselling Core\n"
                        + "que puedes ingresar en el siguiente enlace:\n\n"
                        + "https://core.app-tonantel.com/Cross-selling_core\n\n\n"
                        + "Copyright © Investigación y Desarrollo de Tecnología Cooperativa Tonantel R.L";

                Correo correo = new Correo(Colaborador.correoColaborador(s.getAsesor()), "No.solicitud" + s.getNumeroSolicitud(), msj);
                correo.enviar();

                listRecibidas = recibidas.mostrarDato();

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
            error = "! La solicitud ya fue tomada por otro Valuador";
        }

    }  
    /* Metodo para consultar el estado actual de la solicitud */
    private boolean isGenerada(String numeroSolicitud){
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
        }

        em.close();
        emf.close();

        return result;
    }
}
