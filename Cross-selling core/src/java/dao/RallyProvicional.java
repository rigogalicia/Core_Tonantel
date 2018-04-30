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
@Table(name = "rally_provicional")
@NamedQueries({
    @NamedQuery(name = "RallyProvicional.findAll", query = "SELECT r FROM RallyProvicional r")})
public class RallyProvicional implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idprovicional")
    private Integer idprovicional;
    @Basic(optional = false)
    @Column(name = "cif")
    private String cif;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "referencia")
    private String referencia;
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "saldo")
    private BigDecimal saldo;
    @Basic(optional = false)
    @Column(name = "monto")
    private BigDecimal monto;
    @Basic(optional = false)
    @Column(name = "telefono")
    private String telefono;
    @Basic(optional = false)
    @Column(name = "tipo")
    private Character tipo;
    @Basic(optional = false)
    @Column(name = "operador")
    private int operador;

    public RallyProvicional() {
    }

    public RallyProvicional(Integer idprovicional) {
        this.idprovicional = idprovicional;
    }

    public RallyProvicional(Integer idprovicional, String cif, String nombre, String referencia, Date fecha, BigDecimal saldo, BigDecimal monto, String telefono, Character tipo, int operador) {
        this.idprovicional = idprovicional;
        this.cif = cif;
        this.nombre = nombre;
        this.referencia = referencia;
        this.fecha = fecha;
        this.saldo = saldo;
        this.monto = monto;
        this.telefono = telefono;
        this.tipo = tipo;
        this.operador = operador;
    }

    public Integer getIdprovicional() {
        return idprovicional;
    }

    public void setIdprovicional(Integer idprovicional) {
        this.idprovicional = idprovicional;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Character getTipo() {
        return tipo;
    }

    public void setTipo(Character tipo) {
        this.tipo = tipo;
    }

    public int getOperador() {
        return operador;
    }

    public void setOperador(int operador) {
        this.operador = operador;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idprovicional != null ? idprovicional.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RallyProvicional)) {
            return false;
        }
        RallyProvicional other = (RallyProvicional) object;
        if ((this.idprovicional == null && other.idprovicional != null) || (this.idprovicional != null && !this.idprovicional.equals(other.idprovicional))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dao.RallyProvicional[ idprovicional=" + idprovicional + " ]";
    }
    
}
