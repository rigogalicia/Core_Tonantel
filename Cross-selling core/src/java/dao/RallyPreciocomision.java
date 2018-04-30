/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Rgalicia
 */
@Entity
@Table(name = "rally_preciocomision")
@NamedQueries({
    @NamedQuery(name = "RallyPreciocomision.findAll", query = "SELECT r FROM RallyPreciocomision r")})
public class RallyPreciocomision implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpreciocomision")
    private Integer idpreciocomision;
    @Basic(optional = false)
    @Column(name = "vendedor")
    private float vendedor;
    @Basic(optional = false)
    @Column(name = "gestor")
    private float gestor;
    @Basic(optional = false)
    @Column(name = "estado")
    private Character estado;
    @JoinColumn(name = "rally_producto_idproducto", referencedColumnName = "idproducto")
    @ManyToOne(optional = false)
    private RallyProducto rallyProductoIdproducto;

    public RallyPreciocomision() {
    }

    public RallyPreciocomision(Integer idpreciocomision) {
        this.idpreciocomision = idpreciocomision;
    }

    public RallyPreciocomision(Integer idpreciocomision, float vendedor, float gestor, Character estado) {
        this.idpreciocomision = idpreciocomision;
        this.vendedor = vendedor;
        this.gestor = gestor;
        this.estado = estado;
    }

    public Integer getIdpreciocomision() {
        return idpreciocomision;
    }

    public void setIdpreciocomision(Integer idpreciocomision) {
        this.idpreciocomision = idpreciocomision;
    }

    public float getVendedor() {
        return vendedor;
    }

    public void setVendedor(float vendedor) {
        this.vendedor = vendedor;
    }

    public float getGestor() {
        return gestor;
    }

    public void setGestor(float gestor) {
        this.gestor = gestor;
    }

    public Character getEstado() {
        return estado;
    }

    public void setEstado(Character estado) {
        this.estado = estado;
    }

    public RallyProducto getRallyProductoIdproducto() {
        return rallyProductoIdproducto;
    }

    public void setRallyProductoIdproducto(RallyProducto rallyProductoIdproducto) {
        this.rallyProductoIdproducto = rallyProductoIdproducto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpreciocomision != null ? idpreciocomision.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RallyPreciocomision)) {
            return false;
        }
        RallyPreciocomision other = (RallyPreciocomision) object;
        if ((this.idpreciocomision == null && other.idpreciocomision != null) || (this.idpreciocomision != null && !this.idpreciocomision.equals(other.idpreciocomision))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dao.RallyPreciocomision[ idpreciocomision=" + idpreciocomision + " ]";
    }
    
}
