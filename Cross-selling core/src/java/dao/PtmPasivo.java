/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * @author r29galicia
 */
@Entity
@Table(name = "ptm_pasivo")
@NamedQueries({
    @NamedQuery(name = "PtmPasivo.findAll", query = "SELECT p FROM PtmPasivo p")})
public class PtmPasivo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpasivo")
    private Integer idpasivo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "creditos_casa_comercial")
    private BigDecimal creditosCasaComercial;
    @Basic(optional = false)
    @Column(name = "deuda_particulares")
    private BigDecimal deudaParticulares;
    @Basic(optional = false)
    @Column(name = "prestamo_corto_largo_plazo")
    private BigDecimal prestamoCortoLargoPlazo;
    @Basic(optional = false)
    @Column(name = "proveedores")
    private BigDecimal proveedores;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ptmPasivoIdpasivo")
    private List<PtmEstadopatrimonial> ptmEstadopatrimonialList;

    public PtmPasivo() {
    }

    public PtmPasivo(Integer idpasivo) {
        this.idpasivo = idpasivo;
    }

    public PtmPasivo(Integer idpasivo, BigDecimal creditosCasaComercial, BigDecimal deudaParticulares, BigDecimal prestamoCortoLargoPlazo, BigDecimal proveedores) {
        this.idpasivo = idpasivo;
        this.creditosCasaComercial = creditosCasaComercial;
        this.deudaParticulares = deudaParticulares;
        this.prestamoCortoLargoPlazo = prestamoCortoLargoPlazo;
        this.proveedores = proveedores;
    }

    public Integer getIdpasivo() {
        return idpasivo;
    }

    public void setIdpasivo(Integer idpasivo) {
        this.idpasivo = idpasivo;
    }

    public BigDecimal getCreditosCasaComercial() {
        return creditosCasaComercial;
    }

    public void setCreditosCasaComercial(BigDecimal creditosCasaComercial) {
        this.creditosCasaComercial = creditosCasaComercial;
    }

    public BigDecimal getDeudaParticulares() {
        return deudaParticulares;
    }

    public void setDeudaParticulares(BigDecimal deudaParticulares) {
        this.deudaParticulares = deudaParticulares;
    }

    public BigDecimal getPrestamoCortoLargoPlazo() {
        return prestamoCortoLargoPlazo;
    }

    public void setPrestamoCortoLargoPlazo(BigDecimal prestamoCortoLargoPlazo) {
        this.prestamoCortoLargoPlazo = prestamoCortoLargoPlazo;
    }

    public BigDecimal getProveedores() {
        return proveedores;
    }

    public void setProveedores(BigDecimal proveedores) {
        this.proveedores = proveedores;
    }

    public List<PtmEstadopatrimonial> getPtmEstadopatrimonialList() {
        return ptmEstadopatrimonialList;
    }

    public void setPtmEstadopatrimonialList(List<PtmEstadopatrimonial> ptmEstadopatrimonialList) {
        this.ptmEstadopatrimonialList = ptmEstadopatrimonialList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpasivo != null ? idpasivo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PtmPasivo)) {
            return false;
        }
        PtmPasivo other = (PtmPasivo) object;
        if ((this.idpasivo == null && other.idpasivo != null) || (this.idpasivo != null && !this.idpasivo.equals(other.idpasivo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dao.PtmPasivo[ idpasivo=" + idpasivo + " ]";
    }
    
}
