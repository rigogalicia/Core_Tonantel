
package admin.controlador;

import admin.modelo.Departamento;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

@ManagedBean(name = "colaboradores")
@ViewScoped
public class ColaboradoresBean {
    private  List<SelectItem> listaDepartamentos = new ArrayList<>();

    public ColaboradoresBean() {
        
    }

    public List<SelectItem> getListaDepartamentos() {
        return listaDepartamentos;
    }

    public void setListaDepartamentos(List<SelectItem> listaDepartamentos) {
        this.listaDepartamentos = listaDepartamentos;
    }
    
}
