/*
 * Clase utilizada para enviar correos desde la aplicacion
 */
package gc.controlador;

import java.util.Properties;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


public class Correo {
    private String direccionDeCorreo;
    private String asunto;
    private String mensaje;
   
    public Correo(String direccion, String asunto, String mensaje){
        this.direccionDeCorreo = direccion;
        this.asunto = asunto;
        this.mensaje = mensaje;
    }

    public String getDireccionDeCorreo() {
        return direccionDeCorreo;
    }

    public void setDireccionDeCorreo(String direccionDeCorreo) {
        this.direccionDeCorreo = direccionDeCorreo;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    //metodo utilizado para enviar el correo
    public void enviar(){
        try{
            Properties p = new Properties();
            // Datos de configuración
            p.put("mail.smtp.host", "mail.cooperativa-tonantel.com.gt");
            p.setProperty("mail.smtp.starttls.enable","true");
            p.setProperty("mail.smtp.port", "587");
            p.setProperty("mail.smtp.user", "crosseling.core@cooperativa-tonantel.com.gt");
            p.setProperty("mail.smtp.auth", "true");
            
            // Inicializa la session
            Session session = Session.getDefaultInstance(p,null);
            BodyPart texto = new MimeBodyPart();
            texto.setText(mensaje);
            
            MimeMultipart m = new MimeMultipart();
            m.addBodyPart(texto);
            
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("crosseling.core@cooperativa-tonantel.com.gt"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(direccionDeCorreo));
            message.setSubject(asunto);
            message.setContent(m);
            
            Transport t = session.getTransport("smtp"); 
            t.connect("crosseling.core@cooperativa-tonantel.com.gt","Ton@nte1");
            t.sendMessage(message, message.getAllRecipients());
            t.close();
        }   
        catch(Exception e){
            e.printStackTrace(System.out);
        }
    }
    
}
