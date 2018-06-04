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
@Table(name = "gc_estado")
@NamedQueries({
    @NamedQuery(name = "GcEstado.findAll", query = "SELECT g FROM GcEstado g")})
public class GcEstado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private String id;
    @Column(name = "descripcion")
    private String descripcion;
    @OneToMany(mappedBy = "estadoId")
    private List<GcGestion> gcGestionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estadoId")
    private List<GcSolicitud> gcSolicitudList;

    public GcEstado() {
    }

    public GcEstado(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<GcGestion> getGcGestionList() {
        return gcGestionList;
    }

    public void setGcGestionList(List<GcGestion> gcGestionList) {
        this.gcGestionList = gcGestionList;
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
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GcEstado)) {
            return false;
        }
        GcEstado other = (GcEstado) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dao.GcEstado[ id=" + id + " ]";
    }
    
}
