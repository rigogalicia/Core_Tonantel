
package admin.controlador;

import admin.modelo.Departamento;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

@ManagedBean(name = "departamentos")
@ViewScoped
public class DepartamentosBean {
    private Departamento departamento =  new Departamento();
    private ArrayList<Departamento> listaDepartamentos = new ArrayList<>();
    private boolean edit= false;
    private String nombreBuscar;

    public DepartamentosBean() {
        consultarDepartamento();
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public ArrayList<Departamento> getListaDepartamentos() {
        return listaDepartamentos;
    }

    public void setListaDepartamentos(ArrayList<Departamento> listaDepartamentos) {
        this.listaDepartamentos = listaDepartamentos;
    }

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    public String getNombreBuscar() {
        return nombreBuscar;
    }

    public void setNombreBuscar(String nombreBuscar) {
        this.nombreBuscar = nombreBuscar;
    }
    
    
    private void consultarDepartamento(){
        listaDepartamentos.clear();
        listaDepartamentos = departamento.mostrarDepartamentos();
    }
    
    public void insertarDepartamento(ActionEvent event){
        departamento.insertar();
        consultarDepartamento();
        departamento = new Departamento();
    }
    
    public void actualizarDepartamento(ActionEvent event){
        departamento.update();
        consultarDepartamento();
        departamento = new Departamento();
        edit = false;
    }
    
    public void seleccionarDepartamento(Departamento a){
        edit = true;
        departamento = a;
    }
    
    public void cancelarActualizar(ActionEvent event){
        edit = false;
        departamento = new Departamento();
        consultarDepartamento();
    }
    /* Metodo utilizado para filtrar los registros por nombre */
    public void buscarDepartamentos(){
        if(!nombreBuscar.isEmpty()){
            listaDepartamentos.clear();
            listaDepartamentos = Departamento.buscarDepartamentos(nombreBuscar);
        }
        else{
           nombreBuscar = new String();
           consultarDepartamento();
        }
    }

}
    

