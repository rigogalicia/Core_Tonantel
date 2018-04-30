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
@Table(name = "rally_producto")
@NamedQueries({
    @NamedQuery(name = "RallyProducto.findAll", query = "SELECT r FROM RallyProducto r")})
public class RallyProducto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idproducto")
    private Integer idproducto;
    @Basic(optional = false)
    @Column(name = "nombre_producto")
    private String nombreProducto;
    @Basic(optional = false)
    @Column(name = "tipo")
    private Character tipo;
    @Basic(optional = false)
    @Column(name = "estado")
    private Character estado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rallyProductoIdproducto")
    private List<RallyPreciocomision> rallyPreciocomisionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rallyProductoIdproducto")
    private List<RallyReferencia> rallyReferenciaList;

    public RallyProducto() {
    }

    public RallyProducto(Integer idproducto) {
        this.idproducto = idproducto;
    }

    public RallyProducto(Integer idproducto, String nombreProducto, Character tipo, Character estado) {
        this.idproducto = idproducto;
        this.nombreProducto = nombreProducto;
        this.tipo = tipo;
        this.estado = estado;
    }

    public Integer getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(Integer idproducto) {
        this.idproducto = idproducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public Character getTipo() {
        return tipo;
    }

    public void setTipo(Character tipo) {
        this.tipo = tipo;
    }

    public Character getEstado() {
        return estado;
    }

    public void setEstado(Character estado) {
        this.estado = estado;
    }

    public List<RallyPreciocomision> getRallyPreciocomisionList() {
        return rallyPreciocomisionList;
    }

    public void setRallyPreciocomisionList(List<RallyPreciocomision> rallyPreciocomisionList) {
        this.rallyPreciocomisionList = rallyPreciocomisionList;
    }

    public List<RallyReferencia> getRallyReferenciaList() {
        return rallyReferenciaList;
    }

    public void setRallyReferenciaList(List<RallyReferencia> rallyReferenciaList) {
        this.rallyReferenciaList = rallyReferenciaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idproducto != null ? idproducto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RallyProducto)) {
            return false;
        }
        RallyProducto other = (RallyProducto) object;
        if ((this.idproducto == null && other.idproducto != null) || (this.idproducto != null && !this.idproducto.equals(other.idproducto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dao.RallyProducto[ idproducto=" + idproducto + " ]";
    }
    
}
