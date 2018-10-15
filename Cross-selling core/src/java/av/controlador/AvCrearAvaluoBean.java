
package av.controlador;

import admin.modelo.ReportConfig;
import av.modelo.CrearAvaluo;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.Part;

@ManagedBean(name = "av_crearavaluo")
@ViewScoped
public class AvCrearAvaluoBean {
    private String numeroSolicitud;
    private CrearAvaluo crearAvaluo = new CrearAvaluo();
    
    private String msjColindantes;
    private String msjDetalle;
    private String msjConstruccion;
    
    private Part file;
    private String pathAnexo = "";
    private boolean upladed;
    private String nombreImagen = "";
    private int num = 1;

    public AvCrearAvaluoBean() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Map params = facesContext.getExternalContext().getRequestParameterMap();
        numeroSolicitud = params.get("numeroSolicitud").toString();
        crearAvaluo.getSolicitud().setNumeroSolicitud(numeroSolicitud);
        crearAvaluo.consultarSolicitud();
        
    }

    public String getNumeroSolicitud() {
        return numeroSolicitud;
    }

    public void setNumeroSolicitud(String numeroSolicitud) {
        this.numeroSolicitud = numeroSolicitud;
    }

    public CrearAvaluo getCrearAvaluo() {
        return crearAvaluo;
    }

    public void setCrearAvaluo(CrearAvaluo crearAvaluo) {
        this.crearAvaluo = crearAvaluo;
    }

    public String getMsjColindantes() {
        return msjColindantes;
    }

    public void setMsjColindantes(String msjColindantes) {
        this.msjColindantes = msjColindantes;
    }

    public String getMsjDetalle() {
        return msjDetalle;
    }

    public void setMsjDetalle(String msjDetalle) {
        this.msjDetalle = msjDetalle;
    }

    public String getMsjConstruccion() {
        return msjConstruccion;
    }

    public void setMsjConstruccion(String msjConstruccion) {
        this.msjConstruccion = msjConstruccion;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public String getPathAnexo() {
        return pathAnexo;
    }

    public void setPathAnexo(String pathAnexo) {
        this.pathAnexo = pathAnexo;
    }

    public boolean isUpladed() {
        return upladed;
    }

    public void setUpladed(boolean upladed) {
        this.upladed = upladed;
    }
    
    
    
    //Metodo utilizado para validar el Array de Colindantes
    public boolean isComplit(){
        boolean resultado = false;
        msjColindantes = null;
        msjDetalle = null;
        msjConstruccion = null;
        if(crearAvaluo.getColindantes().isEmpty()){
            msjColindantes = "Ingrese los Datos de Colindantes";
        }

        else if(crearAvaluo.getDetalleAvaluo().isEmpty()){
            msjDetalle = "Ingrese los Datos del Detalle";
        }
        else{
            resultado = true;
        }
        return resultado;
    }
    
    //Metodo para calcular el total del avaluo
    public void calcularTotal(){
        crearAvaluo.total();
    }

    public void insertarDatos(ActionEvent e){
        if(isComplit()){
            crearAvaluo.insert();
        }
    }
    //Metodo para cargar imagenes
    public void subirAnexos(){
        nombreImagen = numeroSolicitud + "_" + num++;
        try {
            pathAnexo = "C:\\Users\\Desarrollo\\Documents\\Reportes\\" + nombreImagen +".jpg";
            
            InputStream input = file.getInputStream();
            File f = new File(pathAnexo);
            
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
            
            crearAvaluo.agregarAnexos(pathAnexo, nombreImagen);
            
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
    
}
