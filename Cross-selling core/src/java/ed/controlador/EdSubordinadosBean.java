package ed.controlador;

import admin.modelo.Colaborador;
import java.io.IOException;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

@ManagedBean(name = "ed_subordinados")
@ViewScoped
public class EdSubordinadosBean {
    private String userConect;
    private ArrayList<SelectItem> listaColaboradores = new ArrayList<>();
    private Colaborador colaborador = new Colaborador();
    private Colaborador subordinado = new Colaborador();
    
    public EdSubordinadosBean() {
        // Metodo constructor de la clase
        HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        if(sesion.getAttribute("userConect") != null){
            userConect = sesion.getAttribute("userConect").toString();
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

    public ArrayList<SelectItem> getListaColaboradores() {
        listaColaboradores.clear();
        for(Colaborador c : new Colaborador().mostrarColaboradores()){
            SelectItem itemColaborador = new SelectItem(c.getUsuario(), c.getNombre());
            listaColaboradores.add(itemColaborador);
        }
        
        return listaColaboradores;
    }

    public void setListaColaboradores(ArrayList<SelectItem> listaColaboradores) {
        this.listaColaboradores = listaColaboradores;
    }

    public Colaborador getSubordinado() {
        return subordinado;
    }

    public void setSubordinado(Colaborador subordinado) {
        this.subordinado = subordinado;
    }

    public Colaborador getColaborador() {
        return colaborador;
    }

    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }
    
    /* Metodo para consultar los datos del colaborador Jefe */
    public void selectColaborador(ValueChangeEvent e){
        colaborador.setUsuario(e.getNewValue().toString());
        colaborador = colaborador.consultarPorUsuario();
    }
    
    /* Metodo para agregar subordinados al objeto de colaborador */
    public void addSubordinados(){
        subordinado = Colaborador.datosColaborador(subordinado.getUsuario());
        colaborador.getSubordinados().add(subordinado);
        colaborador.agregarSubordinados();
    }

    /* Metodo para eliminar un registro de subordinados */
    public void deleteSubordinado(Colaborador c){
        colaborador.getSubordinados().remove(c);
        colaborador.agregarSubordinados();
    }
}
