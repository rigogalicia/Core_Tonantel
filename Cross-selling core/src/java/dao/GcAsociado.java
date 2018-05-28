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
@Table(name = "gc_asociado")
@NamedQueries({
    @NamedQuery(name = "GcAsociado.findAll", query = "SELECT g FROM GcAsociado g")})
public class GcAsociado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "cif")
    private String cif;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "asociadoCif")
    private List<GcSolicitud> gcSolicitudList;

    public GcAsociado() {
    }

    public GcAsociado(String cif) {
        this.cif = cif;
    }

    public GcAsociado(String cif, String nombre) {
        this.cif = cif;
        this.nombre = nombre;
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

    public List<GcSolicitud> getGcSolicitudList() {
        return gcSolicitudList;
    }

    public void setGcSolicitudList(List<GcSolicitud> gcSolicitudList) {
        this.gcSolicitudList = gcSolicitudList;
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
        if (!(object instanceof GcAsociado)) {
            return false;
        }
        GcAsociado other = (GcAsociado) object;
        if ((this.cif == null && other.cif != null) || (this.cif != null && !this.cif.equals(other.cif))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dao.GcAsociado[ cif=" + cif + " ]";
    }
    
}
