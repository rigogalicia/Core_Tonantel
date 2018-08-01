package cc.controlador;

import cc.modelo.Registrar;
import dao.CcBanco;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

@ManagedBean(name = "cc_registrar")
@ViewScoped
public class CcRegistrarBean {
    private Registrar registrar = new Registrar();
    private ArrayList<SelectItem> bancos = new ArrayList<>();
    
    public CcRegistrarBean() {
        
    }

    public Registrar getRegistrar() {
        return registrar;
    }

    public void setRegistrar(Registrar registrar) {
        this.registrar = registrar;
    }

    public ArrayList<SelectItem> getBancos() {
        bancos.clear();
        Registrar reg = new Registrar();
        for(CcBanco b : reg.bancos()){
            SelectItem itemBanco = new SelectItem(b.getId(), b.getDescripcion());
            bancos.add(itemBanco);
        }
        return bancos;
    }

    public void setBancos(ArrayList<SelectItem> bancos) {
        this.bancos = bancos;
    }
    
    /* Metodo para insertar un nuevo registro */
    public void insertar(ActionEvent e){
        registrar.insertar();
    }
}
