package gc.controlador;

import dao.GcGestion;
import gc.modelo.Chat;
import gc.modelo.SolicitudesEnproceso;
import gc.modelo.SolicitudesGeneradas;
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

@ManagedBean(name = "gc_chat")
@ViewScoped
public class GcChatBean {
    private boolean isChat;
    private int panel1 = 12;
    private int panel2 = 0;
    private Chat chat = new Chat();
    private ArrayList<Chat> mensajes = new ArrayList<>();
    private String userConect;
    private String nombreUsuario;
    private String nombreReceptor;
    
    public GcChatBean() {
        HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        if(sesion.getAttribute("userConect") != null){
            userConect = sesion.getAttribute("userConect").toString();
            nombreUsuario = sesion.getAttribute("nombreUsuario").toString();
        }
        else{
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/Cross-selling_core/faces/index.xhtml");
            } catch (IOException ex) {
                ex.printStackTrace(System.out);
            }
        }
    }

    public boolean isIsChat() {
        return isChat;
    }

    public void setIsChat(boolean isChat) {
        this.isChat = isChat;
    }

    public int getPanel1() {
        return panel1;
    }

    public void setPanel1(int panel1) {
        this.panel1 = panel1;
    }

    public int getPanel2() {
        return panel2;
    }

    public void setPanel2(int panel2) {
        this.panel2 = panel2;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public ArrayList<Chat> getMensajes() {
        return mensajes;
    }

    public void setMensajes(ArrayList<Chat> mensajes) {
        this.mensajes = mensajes;
    }

    public String getUserConect() {
        return userConect;
    }

    public void setUserConect(String userConect) {
        this.userConect = userConect;
    }

    public String getNombreReceptor() {
        return nombreReceptor;
    }

    public void setNombreReceptor(String nombreReceptor) {
        this.nombreReceptor = nombreReceptor;
    }
    
    /* Metodo para activar chat de solicitudes generadas */
    public void activarChatG(SolicitudesGeneradas g){
        chat.setNumeroSolicitud(g.getNumeroSolicitud());
        mensajes = chat.mostrarMensajes();
        nombreReceptor = chat.nombreColaborador(usuarioAnalista(g.getNumeroSolicitud()));
        chat.setUsuario(userConect);
        chat.marcarComoLeidos();
        isChat = true;
        panel1 = 8;
        panel2 = 4;
    }
    
    /* Metodo utilizado para activar el chat de solicitudes en proceso */
    public void activarChat(SolicitudesEnproceso p){
        chat.setNumeroSolicitud(p.getNumeroSolicitud());
        mensajes = chat.mostrarMensajes();
        nombreReceptor = chat.nombreColaborador(p.getAsesorFinanciero());
        chat.setUsuario(userConect);
        chat.marcarComoLeidos();
        isChat = true;
        panel1 = 8;
        panel2 = 4;
    }
    
    /* Metodo utilizado para desactivar el chat */
    public void desactivarChat(){
        isChat = false;
        panel1 = 12;
        panel2 = 0;
    }
    
    /* Metodo utilizado para enviar un mensaje nuevo */
    public void enviarMensaje(){
        chat.setFecha(new Date());
        chat.setNombreUsuario(nombreUsuario);
        chat.setUsuario(userConect);
        chat.setEstado('a');
        
        chat.enviarMensaje();
        chat.setMensaje(null);
        mensajes = chat.mostrarMensajes();
    }
    
    /* Metodo utilizado para obtener el usuario del analista */
    private String usuarioAnalista(String numeroSolicitud){
        String result = "";
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
        EntityManager em = emf.createEntityManager();
        
        String instruccion = "SELECT DISTINCT g "
                + "FROM GcGestion g "
                + "JOIN g.solicitudNumeroSolicitud s "
                + "WHERE s.numeroSolicitud = :numSol";
        Query consulta = em.createQuery(instruccion);
        consulta.setParameter("numSol", numeroSolicitud);
        
        List<GcGestion> resultado = consulta.getResultList();
        for(GcGestion g : resultado){
            result = g.getAnalista();
        }
        
        em.close();
        emf.close();
        
        return result;
    }
}
