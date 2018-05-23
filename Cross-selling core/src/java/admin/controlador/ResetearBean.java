/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.controlador;

import admin.modelo.Colaborador;
import admin.modelo.Resetear;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Desarrollo
 */
@ManagedBean(name = "resetear")
@RequestScoped
public class ResetearBean {
    private Resetear  resetear = new Resetear();
    private Colaborador colaborador = new Colaborador();

    public ResetearBean() {
        
    }

    public Resetear getResetear() {
        return resetear;
    }

    public void setResetear(Resetear resetear) {
        this.resetear = resetear;
    }

    public Colaborador getColaborador() {
        return colaborador;
    }

    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }
    
    public void consultarUsuraio(){
        System.out.println("Me hizo Click");
        resetear.ConsultarDatos();
    }

}
