
package admin.controlador;

import admin.modelo.Agencia;
import admin.modelo.Colaborador;
import admin.modelo.Departamento;
import admin.modelo.Puesto;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

@ManagedBean(name = "colaboradores")
@ViewScoped
public class ColaboradoresBean {
    private Colaborador colaborador = new Colaborador();
    private ArrayList<Colaborador> colaboradores = new ArrayList<>();
    private ArrayList<SelectItem> itemsAgencias = new ArrayList<>();
    private ArrayList<SelectItem> itemsDepartamentos = new ArrayList<>();
    private ArrayList<SelectItem> itemsPuestos = new ArrayList<>();
    private boolean select = false;
    
    public ColaboradoresBean(){
        consultar();
    }

    public Colaborador getColaborador() {
        return colaborador;
    }

    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }

    public ArrayList<Colaborador> getColaboradores() {
        return colaboradores;
    }

    public void setColaboradores(ArrayList<Colaborador> colaboradores) {
        this.colaboradores = colaboradores;
    }

    public ArrayList<SelectItem> getItemsAgencias() {
        itemsAgencias.clear();
        for(Agencia a : new Agencia().mostrarAgencias()){
            SelectItem itemAgencia = new SelectItem(a.getId(), a.getNombre());
            itemsAgencias.add(itemAgencia);
        }
        return itemsAgencias;
    }

    public void setItemsAgencias(ArrayList<SelectItem> itemsAgencias) {
        this.itemsAgencias = itemsAgencias;
    }

    public ArrayList<SelectItem> getItemsDepartamentos() {
        itemsDepartamentos.clear();
        for(Departamento d : new Departamento().mostrarDepartamentos()){
            SelectItem itemDep = new SelectItem(d.getId(), d.getNombre());
            itemsDepartamentos.add(itemDep);
        }
        return itemsDepartamentos;
    }

    public void setItemsDepartamentos(ArrayList<SelectItem> itemsDepartamentos) {
        this.itemsDepartamentos = itemsDepartamentos;
    }

    public ArrayList<SelectItem> getItemsPuestos() {
        return itemsPuestos;
    }

    public void setItemsPuestos(ArrayList<SelectItem> itemsPuestos) {
        this.itemsPuestos = itemsPuestos;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }
    
    private void consultar(){
        colaboradores = colaborador.mostrarColaboradores();
    }
    
    /* Metodo utilizado para filtrar los registros de puestos por departamento */
    public void filtrarPuesto(ValueChangeEvent e){
        Puesto p = new Puesto();
        p.setIdDepartamento(e.getNewValue().toString());
        itemsPuestos.clear();
        for(Puesto puesItem : p.puestosPorDepartamento()){
            SelectItem itemPuesto = new SelectItem(puesItem.getId(), puesItem.getNombre());
            itemsPuestos.add(itemPuesto);
        }
    }
    
    /* Metodo utilizado para insertar un registro de colaboradores */
    public void insertar(ActionEvent event){
        colaborador.insertar();
        consultar();
        colaborador = new Colaborador();
    }
    
    /* Metodo para seleccionar un registro de colaborador */
    public void seleccionar(Colaborador c){
        colaborador = c;
        select = true;
    }
    
    /* Metodo utilizado para modificar un registro de colaborador */
    public void update(ActionEvent event){
        colaborador.update();
        consultar();
        select = false;
        colaborador = new Colaborador();
    }
    
    /* Metodo para cancelar la opcion de editar */
    public void cancelar(ActionEvent event){
        colaborador = new Colaborador();
        select = false;
        colaboradores.clear();
    }
}
