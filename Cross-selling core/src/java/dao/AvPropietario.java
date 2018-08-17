/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author r29galicia
 */
@Entity
@Table(name = "av_propietario")
@NamedQueries({
    @NamedQuery(name = "AvPropietario.findAll", query = "SELECT a FROM AvPropietario a")})
public class AvPropietario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "dpi")
    private String dpi;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "propietarioDpi")
    private List<AvInmueble> avInmuebleList;

    public AvPropietario() {
    }

    public AvPropietario(String dpi) {
        this.dpi = dpi;
    }

    public AvPropietario(String dpi, String nombre) {
        this.dpi = dpi;
        this.nombre = nombre;
    }

    public String getDpi() {
        return dpi;
    }

    public void setDpi(String dpi) {
        this.dpi = dpi;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<AvInmueble> getAvInmuebleList() {
        return avInmuebleList;
    }

    public void setAvInmuebleList(List<AvInmueble> avInmuebleList) {
        this.avInmuebleList = avInmuebleList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dpi != null ? dpi.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AvPropietario)) {
            return false;
        }
        AvPropietario other = (AvPropietario) object;
        if ((this.dpi == null && other.dpi != null) || (this.dpi != null && !this.dpi.equals(other.dpi))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dao.AvPropietario[ dpi=" + dpi + " ]";
    }
    
}
