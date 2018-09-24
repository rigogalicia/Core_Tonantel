
package av.controlador;

import av.modelo.AutorizarAvaluo;
import dao.AvSolicitud;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


@ManagedBean(name = "av_autorizar")
@ViewScoped
public class AvAutorizarBean {
    private ArrayList<AutorizarAvaluo> listAutorizar = new ArrayList<>();
    private AutorizarAvaluo autorizar = new AutorizarAvaluo();
    private char estado;
    
        public AvAutorizarBean() {
        autorizar.setEst('c');
        listAutorizar = autorizar.consultarAvaluo();
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
    
    //Metodo para autorizar Estado
    public void autorizar(){
        System.out.println("Autorizar ............................");
        estado = 'd';
        cambiarEstadoAvaluo();
    }
    
    //Metodo para rechazar avaluo
    public void rechazar(){
        System.out.println("Rechar ................................");
        estado = 'e';
        cambiarEstadoAvaluo();
    }
    
    //Metodo para autorizar o rechazar avaluo
    public void cambiarEstadoAvaluo(){
        
        EntityManagerFactory emf = null;
        EntityManager em = null;
        try {
            emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
            em = emf.createEntityManager();
            
            em.getTransaction().begin();
            
            AvSolicitud solicitud = em.find(AvSolicitud.class, autorizar.getNumeroSolicitud());
            solicitud.setEstado(estado);
            
            em.getTransaction().commit();
            

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
    
}
