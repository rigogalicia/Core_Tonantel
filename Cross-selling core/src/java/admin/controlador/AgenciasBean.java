package admin.controlador;

import admin.modelo.Agencia;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

@ManagedBean(name = "agencias")
@ViewScoped
public class AgenciasBean {
    private Agencia agencia = new Agencia();
    private ArrayList<Agencia> listaAgencias = new ArrayList<>();
    private boolean edit = false;
    
    public AgenciasBean() {
        consultarAgencias();
    }

    public Agencia getAgencia() {
        return agencia;
    }

    public void setAgencia(Agencia agencia) {
        this.agencia = agencia;
    }

    public ArrayList<Agencia> getListaAgencias() {
        return listaAgencias;
    }

    public void setListaAgencias(ArrayList<Agencia> listaAgencias) {
        this.listaAgencias = listaAgencias;
    }

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }
    
    /* Este metodo es utilizado para mostrar las agencias registradas 
    en la base de datos */
    private void consultarAgencias(){
        listaAgencias.clear();
        listaAgencias = agencia.mostrarAgencias();
    }
    
    /* Este metodo se utiliza para agregar un nuevo registro a la agencia */
    public void insertarAgencia(ActionEvent event){
        agencia.insertar();
        consultarAgencias();
        agencia = new Agencia();
    }
    
    /* Este metodo se utiliza para actualizar un registro de agencias */
    public void actualizarAgencia(ActionEvent event){
        agencia.update();
        consultarAgencias();
        agencia = new Agencia();
        edit = false;
    }
    
    /* Este metodo realiza la accion de seleccionar un objeto de agencia para 
    luego actualizar el registro*/
    public void seleccionarAgencia(Agencia a){
        edit = true;
        agencia = a;
    }
    
    /* Metodo utilizado para cancelar la accion de actualizar */
    public void cancelarActualizar(ActionEvent event){
        edit = false;
        agencia = new Agencia();
        consultarAgencias();
    }
}
