/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Rgalicia
 */
@Entity
@Table(name = "ptm_prestamo")
@NamedQueries({
    @NamedQuery(name = "PtmPrestamo.findAll", query = "SELECT p FROM PtmPrestamo p")})
public class PtmPrestamo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idprestamo")
    private Integer idprestamo;
    @Basic(optional = false)
    @Column(name = "institucion")
    private String institucion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "monto")
    private BigDecimal monto;
    @Basic(optional = false)
    @Column(name = "saldo")
    private BigDecimal saldo;
    @Column(name = "fecha_vencimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaVencimiento;
    @JoinColumns({
        @JoinColumn(name = "estadopatrimonial_anio", referencedColumnName = "anio"),
        @JoinColumn(name = "estadopatrimonial_colaborador_dpi", referencedColumnName = "colaborador_dpi")})
    @ManyToOne(optional = false)
    private PtmEstadopatrimonial ptmEstadopatrimonial;

    public PtmPrestamo() {
    }

    public PtmPrestamo(Integer idprestamo) {
        this.idprestamo = idprestamo;
    }

    public PtmPrestamo(Integer idprestamo, String institucion, BigDecimal monto, BigDecimal saldo) {
        this.idprestamo = idprestamo;
        this.institucion = institucion;
        this.monto = monto;
        this.saldo = saldo;
    }

    public Integer getIdprestamo() {
        return idprestamo;
    }

    public void setIdprestamo(Integer idprestamo) {
        this.idprestamo = idprestamo;
    }

    public String getInstitucion() {
        return institucion;
    }

    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
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
        hash += (idprestamo != null ? idprestamo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PtmPrestamo)) {
            return false;
        }
        PtmPrestamo other = (PtmPrestamo) object;
        if ((this.idprestamo == null && other.idprestamo != null) || (this.idprestamo != null && !this.idprestamo.equals(other.idprestamo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dao.PtmPrestamo[ idprestamo=" + idprestamo + " ]";
    }
    
}
