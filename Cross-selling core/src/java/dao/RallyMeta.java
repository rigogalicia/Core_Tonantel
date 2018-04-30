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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Rgalicia
 */
@Entity
@Table(name = "rally_meta")
@NamedQueries({
    @NamedQuery(name = "RallyMeta.findAll", query = "SELECT r FROM RallyMeta r")})
public class RallyMeta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idmeta")
    private Integer idmeta;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "cantidad_colocar")
    private int cantidadColocar;
    @Basic(optional = false)
    @Column(name = "monto_colocar")
    private float montoColocar;
    @Basic(optional = false)
    @Column(name = "cantidad_captar")
    private int cantidadCaptar;
    @Basic(optional = false)
    @Column(name = "monto_captar")
    private float montoCaptar;
    @Basic(optional = false)
    @Column(name = "estado")
    private Character estado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rallyMetaIdmeta")
    private List<RallyAsignacion> rallyAsignacionList;

    public RallyMeta() {
    }

    public RallyMeta(Integer idmeta) {
        this.idmeta = idmeta;
    }

    public RallyMeta(Integer idmeta, String descripcion, int cantidadColocar, float montoColocar, int cantidadCaptar, float montoCaptar, Character estado) {
        this.idmeta = idmeta;
        this.descripcion = descripcion;
        this.cantidadColocar = cantidadColocar;
        this.montoColocar = montoColocar;
        this.cantidadCaptar = cantidadCaptar;
        this.montoCaptar = montoCaptar;
        this.estado = estado;
    }

    public Integer getIdmeta() {
        return idmeta;
    }

    public void setIdmeta(Integer idmeta) {
        this.idmeta = idmeta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCantidadColocar() {
        return cantidadColocar;
    }

    public void setCantidadColocar(int cantidadColocar) {
        this.cantidadColocar = cantidadColocar;
    }

    public float getMontoColocar() {
        return montoColocar;
    }

    public void setMontoColocar(float montoColocar) {
        this.montoColocar = montoColocar;
    }

    public int getCantidadCaptar() {
        return cantidadCaptar;
    }

    public void setCantidadCaptar(int cantidadCaptar) {
        this.cantidadCaptar = cantidadCaptar;
    }

    public float getMontoCaptar() {
        return montoCaptar;
    }

    public void setMontoCaptar(float montoCaptar) {
        this.montoCaptar = montoCaptar;
    }

    public Character getEstado() {
        return estado;
    }

    public void setEstado(Character estado) {
        this.estado = estado;
    }

    public List<RallyAsignacion> getRallyAsignacionList() {
        return rallyAsignacionList;
    }

    public void setRallyAsignacionList(List<RallyAsignacion> rallyAsignacionList) {
        this.rallyAsignacionList = rallyAsignacionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idmeta != null ? idmeta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RallyMeta)) {
            return false;
        }
        RallyMeta other = (RallyMeta) object;
        if ((this.idmeta == null && other.idmeta != null) || (this.idmeta != null && !this.idmeta.equals(other.idmeta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dao.RallyMeta[ idmeta=" + idmeta + " ]";
    }
    
}
