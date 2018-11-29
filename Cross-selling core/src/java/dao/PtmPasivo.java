/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import javax.persistence.Basic;
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
 * @author Desarrollo
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
    @Column(name = "creditos_casa_comercial")
    private BigInteger creditosCasaComercial;
    @Column(name = "deuda_particulares")
    private BigInteger deudaParticulares;
    @Column(name = "prestamo_corto_largo_plazo")
    private BigInteger prestamoCortoLargoPlazo;
    @Column(name = "proveedores")
    private BigInteger proveedores;
    @OneToMany(mappedBy = "ptmPasivoIdpasivo")
    private List<PtmEstadopatrimonial> ptmEstadopatrimonialList;

    public PtmPasivo() {
    }

    public PtmPasivo(Integer idpasivo) {
        this.idpasivo = idpasivo;
    }

    public Integer getIdpasivo() {
        return idpasivo;
    }

    public void setIdpasivo(Integer idpasivo) {
        this.idpasivo = idpasivo;
    }

    public BigInteger getCreditosCasaComercial() {
        return creditosCasaComercial;
    }

    public void setCreditosCasaComercial(BigInteger creditosCasaComercial) {
        this.creditosCasaComercial = creditosCasaComercial;
    }

    public BigInteger getDeudaParticulares() {
        return deudaParticulares;
    }

    public void setDeudaParticulares(BigInteger deudaParticulares) {
        this.deudaParticulares = deudaParticulares;
    }

    public BigInteger getPrestamoCortoLargoPlazo() {
        return prestamoCortoLargoPlazo;
    }

    public void setPrestamoCortoLargoPlazo(BigInteger prestamoCortoLargoPlazo) {
        this.prestamoCortoLargoPlazo = prestamoCortoLargoPlazo;
    }

    public BigInteger getProveedores() {
        return proveedores;
    }

    public void setProveedores(BigInteger proveedores) {
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
