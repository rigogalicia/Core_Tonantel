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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Desarrollo
 */
@Entity
@Table(name = "rally_cartera")
@NamedQueries({
    @NamedQuery(name = "RallyCartera.findAll", query = "SELECT r FROM RallyCartera r")})
public class RallyCartera implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcartera")
    private Integer idcartera;
    @Column(name = "asesor_financiero")
    private Integer asesorFinanciero;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "capital_desembolsado")
    private Float capitalDesembolsado;
    @Column(name = "celular")
    private String celular;
    @Column(name = "codigo_cliente")
    private String codigoCliente;
    @Column(name = "dias_mora")
    private Integer diasMora;
    @Column(name = "fecha_proximo_pago")
    private String fechaProximoPago;
    @Column(name = "indicador_moroso")
    private String indicadorMoroso;
    @Column(name = "monto_original")
    private Float montoOriginal;
    @Column(name = "nombre_cliente")
    private String nombreCliente;
    @Column(name = "numero_prestamo")
    private String numeroPrestamo;
    @Column(name = "saldo_actual")
    private Float saldoActual;

    public RallyCartera() {
    }

    public RallyCartera(Integer idcartera) {
        this.idcartera = idcartera;
    }

    public Integer getIdcartera() {
        return idcartera;
    }

    public void setIdcartera(Integer idcartera) {
        this.idcartera = idcartera;
    }

    public Integer getAsesorFinanciero() {
        return asesorFinanciero;
    }

    public void setAsesorFinanciero(Integer asesorFinanciero) {
        this.asesorFinanciero = asesorFinanciero;
    }

    public Float getCapitalDesembolsado() {
        return capitalDesembolsado;
    }

    public void setCapitalDesembolsado(Float capitalDesembolsado) {
        this.capitalDesembolsado = capitalDesembolsado;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(String codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public Integer getDiasMora() {
        return diasMora;
    }

    public void setDiasMora(Integer diasMora) {
        this.diasMora = diasMora;
    }

    public String getFechaProximoPago() {
        return fechaProximoPago;
    }

    public void setFechaProximoPago(String fechaProximoPago) {
        this.fechaProximoPago = fechaProximoPago;
    }

    public String getIndicadorMoroso() {
        return indicadorMoroso;
    }

    public void setIndicadorMoroso(String indicadorMoroso) {
        this.indicadorMoroso = indicadorMoroso;
    }

    public Float getMontoOriginal() {
        return montoOriginal;
    }

    public void setMontoOriginal(Float montoOriginal) {
        this.montoOriginal = montoOriginal;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getNumeroPrestamo() {
        return numeroPrestamo;
    }

    public void setNumeroPrestamo(String numeroPrestamo) {
        this.numeroPrestamo = numeroPrestamo;
    }

    public Float getSaldoActual() {
        return saldoActual;
    }

    public void setSaldoActual(Float saldoActual) {
        this.saldoActual = saldoActual;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcartera != null ? idcartera.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RallyCartera)) {
            return false;
        }
        RallyCartera other = (RallyCartera) object;
        if ((this.idcartera == null && other.idcartera != null) || (this.idcartera != null && !this.idcartera.equals(other.idcartera))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dao.RallyCartera[ idcartera=" + idcartera + " ]";
    }
    
}
