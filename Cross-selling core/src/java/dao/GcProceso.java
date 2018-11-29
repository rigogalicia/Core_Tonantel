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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Desarrollo
 */
@Entity
@Table(name = "gc_proceso")
@NamedQueries({
    @NamedQuery(name = "GcProceso.findAll", query = "SELECT g FROM GcProceso g")})
public class GcProceso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "estado")
    private Character estado;
    @JoinColumn(name = "asociado_cif", referencedColumnName = "cif")
    @ManyToOne(optional = false)
    private GcAsociado asociadoCif;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "procesoId")
    private List<GcDetalle> gcDetalleList;

    public GcProceso() {
    }

    public GcProceso(Integer id) {
        this.id = id;
    }

    public GcProceso(Integer id, Character estado) {
        this.id = id;
        this.estado = estado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Character getEstado() {
        return estado;
    }

    public void setEstado(Character estado) {
        this.estado = estado;
    }

    public GcAsociado getAsociadoCif() {
        return asociadoCif;
    }

    public void setAsociadoCif(GcAsociado asociadoCif) {
        this.asociadoCif = asociadoCif;
    }

    public List<GcDetalle> getGcDetalleList() {
        return gcDetalleList;
    }

    public void setGcDetalleList(List<GcDetalle> gcDetalleList) {
        this.gcDetalleList = gcDetalleList;
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
        if (!(object instanceof GcProceso)) {
            return false;
        }
        GcProceso other = (GcProceso) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dao.GcProceso[ id=" + id + " ]";
    }
    
}
