package admin.controlador;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "version")
@SessionScoped
public class VersionBean {
    private String institucion = "Cooperativa Tonantel R.L";
    private String autor = "Investigación y Desarrollo de Tecnología";
    private String numero = "1.1";
    
    public VersionBean() {
    }

    public String getInstitucion() {
        return institucion;
    }

    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
    
}
