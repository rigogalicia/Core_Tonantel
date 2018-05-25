/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.controlador;

import admin.modelo.Colaborador;
import admin.modelo.Password;
import admin.modelo.Resetear;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import javax.faces.event.ActionEvent;


/**
 *
 * @author Desarrollo
 */
@ManagedBean(name = "resetear")
@RequestScoped
public class ResetearBean {
    private Resetear  resetear = new Resetear();
    private Password pas = new Password();
    private Colaborador colaborador = new Colaborador();

    private String NuevaClave;

    public ResetearBean() {
    }

    public Resetear getResetear() {
        return resetear;
    }

    public void setResetear(Resetear resetear) {
        this.resetear = resetear;
    }

    public Password getPas() {
        return pas;
    }

    public void setPas(Password pas) {
        this.pas = pas;
    }

    public Colaborador getColaborador() {
        return colaborador;
    }

    public String getNuevaClave() {
        return NuevaClave;
    }

    public void setNuevaClave(String NuevaClave) {
        this.NuevaClave = NuevaClave;
    }
    

    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }

    public void consultarDatos(ActionEvent event){
        resetear.ConsultarDatos();
    }
    public void cancelar(ActionEvent event){
        resetear = new Resetear();
        consultarDatos(event);
    }

    public void actualizarClave(ActionEvent event){
          resetear.update();

    }

}
