/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "cc_cheque")
@NamedQueries({
    @NamedQuery(name = "CcCheque.findAll", query = "SELECT c FROM CcCheque c")})
public class CcCheque implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "numero")
    private String numero;
    @Column(name = "agencia")
    private String agencia;
    @Column(name = "beneficiario")
    private String beneficiario;
    @Column(name = "cuenta_bancaria")
    private String cuentaBancaria;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "monto")
    private BigInteger monto;
    @Column(name = "motivo")
    private String motivo;
    @Column(name = "usuario")
    private String usuario;
    @JoinColumn(name = "banco_id", referencedColumnName = "id")
    @ManyToOne
    private CcBanco bancoId;

    public CcCheque() {
    }

    public CcCheque(String numero) {
        this.numero = numero;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getBeneficiario() {
        return beneficiario;
    }

    public void setBeneficiario(String beneficiario) {
        this.beneficiario = beneficiario;
    }

    public String getCuentaBancaria() {
        return cuentaBancaria;
    }

    public void setCuentaBancaria(String cuentaBancaria) {
        this.cuentaBancaria = cuentaBancaria;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigInteger getMonto() {
        return monto;
    }

    public void setMonto(BigInteger monto) {
        this.monto = monto;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public CcBanco getBancoId() {
        return bancoId;
    }

    public void setBancoId(CcBanco bancoId) {
        this.bancoId = bancoId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numero != null ? numero.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CcCheque)) {
            return false;
        }
        CcCheque other = (CcCheque) object;
        if ((this.numero == null && other.numero != null) || (this.numero != null && !this.numero.equals(other.numero))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dao.CcCheque[ numero=" + numero + " ]";
    }
    
}
