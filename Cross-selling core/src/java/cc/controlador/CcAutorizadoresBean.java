package cc.controlador;

import admin.modelo.Agencia;
import admin.modelo.Colaborador;
import cc.modelo.Autorizadores;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

@ManagedBean(name = "cc_autorizadores")
@ViewScoped
public class CcAutorizadoresBean {
    private Autorizadores autorizadores = new Autorizadores();
    private ArrayList<Autorizadores> listaAutorizadores = new ArrayList<>();
    private ArrayList<SelectItem> agencias = new ArrayList<>();
    private ArrayList<SelectItem> usuarios = new ArrayList<>();
    
    public CcAutorizadoresBean() {
        consultarAutorizadores();
    }

    public Autorizadores getAutorizadores() {
        return autorizadores;
    }

    public void setAutorizadores(Autorizadores autorizadores) {
        this.autorizadores = autorizadores;
    }

    public ArrayList<Autorizadores> getListaAutorizadores() {
        return listaAutorizadores;
    }

    public void setListaAutorizadores(ArrayList<Autorizadores> listaAutorizadores) {
        this.listaAutorizadores = listaAutorizadores;
    }

    public ArrayList<SelectItem> getAgencias() {
        agencias.clear();
        Agencia ag = new Agencia();
        for(Agencia a : ag.mostrarAgencias()){
            SelectItem itemAgencia = new SelectItem(a.getId(), a.getNombre());
            agencias.add(itemAgencia);
        }
        return agencias;
    }

    public void setAgencias(ArrayList<SelectItem> agencias) {
        this.agencias = agencias;
    }

    public ArrayList<SelectItem> getUsuarios() {
        usuarios.clear();
        Colaborador col = new Colaborador();
        for(Colaborador c : col.mostrarColaboradores()){
            SelectItem itemColaborador = new SelectItem(c.getUsuario(), c.getNombre());
            usuarios.add(itemColaborador);
        }
        return usuarios;
    }

    public void setUsuarios(ArrayList<SelectItem> usuarios) {
        this.usuarios = usuarios;
    }
    
    /* Metodo para insertar un registro de autorizadores */
    public void registrar(){
        autorizadores.insert();
        autorizadores = new Autorizadores();
        consultarAutorizadores();
    }
    
    /* Metodo para consultar los datos de autorizadores registrados */
    private void consultarAutorizadores(){
        listaAutorizadores.clear();
        listaAutorizadores = autorizadores.mostrarAutorizadores();
    }
}
