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
 * @author Rgalicia
 */
@Entity
@Table(name = "av_asociado")
@NamedQueries({
    @NamedQuery(name = "AvAsociado.findAll", query = "SELECT a FROM AvAsociado a")})
public class AvAsociado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "cif")
    private String cif;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "dpi")
    private String dpi;
    @Basic(optional = false)
    @Column(name = "direccion")
    private String direccion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "asociadoCif")
    private List<AvSolicitud> avSolicitudList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "asociadoCif")
    private List<AvTelefono> avTelefonoList;

    public AvAsociado() {
    }

    public AvAsociado(String cif) {
        this.cif = cif;
    }

    public AvAsociado(String cif, String nombre, String dpi, String direccion) {
        this.cif = cif;
        this.nombre = nombre;
        this.dpi = dpi;
        this.direccion = direccion;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDpi() {
        return dpi;
    }

    public void setDpi(String dpi) {
        this.dpi = dpi;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public List<AvSolicitud> getAvSolicitudList() {
        return avSolicitudList;
    }

    public void setAvSolicitudList(List<AvSolicitud> avSolicitudList) {
        this.avSolicitudList = avSolicitudList;
    }

    public List<AvTelefono> getAvTelefonoList() {
        return avTelefonoList;
    }

    public void setAvTelefonoList(List<AvTelefono> avTelefonoList) {
        this.avTelefonoList = avTelefonoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cif != null ? cif.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AvAsociado)) {
            return false;
        }
        AvAsociado other = (AvAsociado) object;
        if ((this.cif == null && other.cif != null) || (this.cif != null && !this.cif.equals(other.cif))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dao.AvAsociado[ cif=" + cif + " ]";
    }
    
}
