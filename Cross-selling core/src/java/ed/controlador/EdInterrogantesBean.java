package ed.controlador;

import admin.modelo.Puesto;
import ed.modelo.Interrogante;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

@ManagedBean(name = "ed_interrogantes")
@ViewScoped
public class EdInterrogantesBean {
    private String userConect;
    private Interrogante interrogante = new Interrogante();
    private ArrayList<SelectItem> conductaSelect = new ArrayList<>();
    private ArrayList<SelectItem> puestoSelect = new ArrayList<>();
    private ArrayList<Interrogante> interrogantes = new ArrayList<>();
    
    public EdInterrogantesBean() {
        // Metodo constructor de la clase
        HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        if(sesion.getAttribute("userConect") != null){
            userConect = sesion.getAttribute("userConect").toString();
            FacesContext facesContext = FacesContext.getCurrentInstance();
            Map params = facesContext.getExternalContext().getRequestParameterMap();
            interrogante.setEvaluacion(params.get("Evaluacion").toString());
            interrogante.setLider("NO");
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

    public Interrogante getInterrogante() {
        return interrogante;
    }

    public void setInterrogante(Interrogante interrogante) {
        this.interrogante = interrogante;
    }

    public ArrayList<SelectItem> getConductaSelect() {
        conductaSelect.clear();
        conductaSelect.clear();
        conductaSelect.add(new SelectItem("Conductas Genéricas", "Conductas Genéricas"));
        conductaSelect.add(new SelectItem("Conductas Específicas", "Conductas Específicas"));
        conductaSelect.add(new SelectItem("Conductas Complementarias", "Conductas Complementarias"));
        return conductaSelect;
    }

    public void setConductaSelect(ArrayList<SelectItem> conductaSelect) {
        this.conductaSelect = conductaSelect;
    }

    public ArrayList<SelectItem> getPuestoSelect() {
        puestoSelect.clear();
        for(Puesto p : new Puesto().mostrarPuesto()){
            puestoSelect.add(new SelectItem(p.getId(), p.getNombre()));
        }
        return puestoSelect;
    }

    public void setPuestoSelect(ArrayList<SelectItem> puestoSelect) {
        this.puestoSelect = puestoSelect;
    }

    public ArrayList<Interrogante> getInterrogantes() {
        return interrogantes;
    }

    public void setInterrogantes(ArrayList<Interrogante> interrogantes) {
        this.interrogantes = interrogantes;
    }
    
    // Este metodo se utiliza para insertar un registro en interrogante
    public void insertRegistro() {
        interrogante.insert();
        interrogantes = interrogante.mostrarInterrogantesFiller();
        interrogante.setDescripcion("");
    }
    
    // Metodo para consultar las interrogantes por conducta
    public void consultarPorConducta(ValueChangeEvent e) {
        interrogante.setConducta(e.getNewValue().toString());
        interrogantes = interrogante.mostrarInterrogantesFiller();
    }
    
    // Metodo para consultar las interrogantes por puesto
    public void consultarPorPuesto(ValueChangeEvent e) {
        interrogante.setPuesto(e.getNewValue().toString());
        interrogantes = interrogante.mostrarInterrogantesFiller();
    }
    
    // Metodo para consultar las interrogantes por lider
    public void consultarPorLider(ValueChangeEvent e) {
        interrogante.setLider(e.getNewValue().toString());
        interrogantes = interrogante.mostrarInterrogantesFiller();
    }
    
    // Metodo para eliminar un registro de interrogante
    public void delete(Interrogante i) {
        interrogante.delete(i);
        interrogantes = interrogante.mostrarInterrogantesFiller();
    }
}
