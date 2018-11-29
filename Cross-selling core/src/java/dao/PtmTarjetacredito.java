/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Desarrollo
 */
@Entity
@Table(name = "ptm_tarjetacredito")
@NamedQueries({
    @NamedQuery(name = "PtmTarjetacredito.findAll", query = "SELECT p FROM PtmTarjetacredito p")})
public class PtmTarjetacredito implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idtarjetacredito")
    private Integer idtarjetacredito;
    @Column(name = "institucion")
    private String institucion;
    @Column(name = "limite")
    private BigInteger limite;
    @JoinColumns({
        @JoinColumn(name = "estadopatrimonial_colaborador_dpi", referencedColumnName = "colaborador_dpi"),
        @JoinColumn(name = "estadopatrimonial_anio", referencedColumnName = "anio")})
    @ManyToOne
    private PtmEstadopatrimonial ptmEstadopatrimonial;

    public PtmTarjetacredito() {
    }

    public PtmTarjetacredito(Integer idtarjetacredito) {
        this.idtarjetacredito = idtarjetacredito;
    }

    public Integer getIdtarjetacredito() {
        return idtarjetacredito;
    }

    public void setIdtarjetacredito(Integer idtarjetacredito) {
        this.idtarjetacredito = idtarjetacredito;
    }

    public String getInstitucion() {
        return institucion;
    }

    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }

    public BigInteger getLimite() {
        return limite;
    }

    public void setLimite(BigInteger limite) {
        this.limite = limite;
    }

    public PtmEstadopatrimonial getPtmEstadopatrimonial() {
        return ptmEstadopatrimonial;
    }

    public void setPtmEstadopatrimonial(PtmEstadopatrimonial ptmEstadopatrimonial) {
        this.ptmEstadopatrimonial = ptmEstadopatrimonial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtarjetacredito != null ? idtarjetacredito.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PtmTarjetacredito)) {
            return false;
        }
        PtmTarjetacredito other = (PtmTarjetacredito) object;
        if ((this.idtarjetacredito == null && other.idtarjetacredito != null) || (this.idtarjetacredito != null && !this.idtarjetacredito.equals(other.idtarjetacredito))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dao.PtmTarjetacredito[ idtarjetacredito=" + idtarjetacredito + " ]";
    }
    
}
