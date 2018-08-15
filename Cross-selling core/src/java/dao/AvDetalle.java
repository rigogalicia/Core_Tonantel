/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "av_detalle")
@NamedQueries({
    @NamedQuery(name = "AvDetalle.findAll", query = "SELECT a FROM AvDetalle a")})
public class AvDetalle implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "medidas")
    private BigDecimal medidas;
    @Basic(optional = false)
    @Column(name = "valor")
    private BigDecimal valor;
    @Basic(optional = false)
    @Column(name = "tipo")
    private Character tipo;
    @JoinColumn(name = "avaluo_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private AvAvaluo avaluoId;

    public AvDetalle() {
    }

    public AvDetalle(Integer id) {
        this.id = id;
    }

    public AvDetalle(Integer id, String descripcion, BigDecimal medidas, BigDecimal valor, Character tipo) {
        this.id = id;
        this.descripcion = descripcion;
        this.medidas = medidas;
        this.valor = valor;
        this.tipo = tipo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getMedidas() {
        return medidas;
    }

    public void setMedidas(BigDecimal medidas) {
        this.medidas = medidas;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Character getTipo() {
        return tipo;
    }

    public void setTipo(Character tipo) {
        this.tipo = tipo;
    }

    public AvAvaluo getAvaluoId() {
        return avaluoId;
    }

    public void setAvaluoId(AvAvaluo avaluoId) {
        this.avaluoId = avaluoId;
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
        if (!(object instanceof AvDetalle)) {
            return false;
        }
        AvDetalle other = (AvDetalle) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dao.AvDetalle[ id=" + id + " ]";
    }
    
}
