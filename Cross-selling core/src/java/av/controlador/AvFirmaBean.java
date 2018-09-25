package av.controlador;

import admin.modelo.Colaborador;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.Part;

@ManagedBean(name = "av_firma")
@ViewScoped
public class AvFirmaBean {
    private int operador;
    private String usuario;
    private String nombre;
    private Part file;
    private String pathFirma = "";
    
    public AvFirmaBean() {
        
    }

    public int getOperador() {
        return operador;
    }

    public void setOperador(int operador) {
        this.operador = operador;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public String getPathFirma() {
        return pathFirma;
    }

    public void setPathFirma(String pathFirma) {
        this.pathFirma = pathFirma;
    }
    
    // Este metodo consuslta los datos del colaborador por colaborador
    public void consultarDatos(){
        Colaborador colaborador = new Colaborador();
        colaborador.setOperador(operador);
        colaborador = colaborador.datosColaborador();
        nombre = colaborador.getNombre();
        usuario = colaborador.getUsuario();
        
        pathFirma = "/Users/r29galicia/Downloads/upload/" + usuario + ".jpg";
    }
    
    // Este metodo sube la firma al servidor
    public void subirArchivo(){
        try{
            InputStream input = file.getInputStream();
            File f = new File(pathFirma);
            
            if(!f.exists()){
                f.createNewFile();
            }
            FileOutputStream output = new FileOutputStream(f);
            byte[] buffer = new byte[1024];
            int length;
            while((length = input.read(buffer)) > 0){
                output.write(buffer, 0, length);
            }
            input.close();
            output.close();
            
        }catch(Exception e){
            e.printStackTrace(System.out);
        }
    }
    
    public void actualizarFirma(){
        Colaborador colaborador = new Colaborador();
        colaborador.setUsuario(usuario);
        colaborador.setFirma(pathFirma);
        colaborador.actualizarFirma();
    }
}
