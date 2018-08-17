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
 * @author r29galicia
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
    @Basic(optional = false)
    @Column(name = "fecha_proximo_pago")
    private String fechaProximoPago;
    @Basic(optional = false)
    @Column(name = "numero_prestamo")
    private String numeroPrestamo;
    @Basic(optional = false)
    @Column(name = "indicador_moroso")
    private String indicadorMoroso;
    @Basic(optional = false)
    @Column(name = "codigo_cliente")
    private String codigoCliente;
    @Basic(optional = false)
    @Column(name = "nombre_cliente")
    private String nombreCliente;
    @Basic(optional = false)
    @Column(name = "celular")
    private String celular;
    @Basic(optional = false)
    @Column(name = "dias_mora")
    private int diasMora;
    @Basic(optional = false)
    @Column(name = "monto_original")
    private float montoOriginal;
    @Basic(optional = false)
    @Column(name = "capital_desembolsado")
    private float capitalDesembolsado;
    @Basic(optional = false)
    @Column(name = "saldo_actual")
    private float saldoActual;
    @Basic(optional = false)
    @Column(name = "asesor_financiero")
    private int asesorFinanciero;

    public RallyCartera() {
    }

    public RallyCartera(Integer idcartera) {
        this.idcartera = idcartera;
    }

    public RallyCartera(Integer idcartera, String fechaProximoPago, String numeroPrestamo, String indicadorMoroso, String codigoCliente, String nombreCliente, String celular, int diasMora, float montoOriginal, float capitalDesembolsado, float saldoActual, int asesorFinanciero) {
        this.idcartera = idcartera;
        this.fechaProximoPago = fechaProximoPago;
        this.numeroPrestamo = numeroPrestamo;
        this.indicadorMoroso = indicadorMoroso;
        this.codigoCliente = codigoCliente;
        this.nombreCliente = nombreCliente;
        this.celular = celular;
        this.diasMora = diasMora;
        this.montoOriginal = montoOriginal;
        this.capitalDesembolsado = capitalDesembolsado;
        this.saldoActual = saldoActual;
        this.asesorFinanciero = asesorFinanciero;
    }

    public Integer getIdcartera() {
        return idcartera;
    }

    public void setIdcartera(Integer idcartera) {
        this.idcartera = idcartera;
    }

    public String getFechaProximoPago() {
        return fechaProximoPago;
    }

    public void setFechaProximoPago(String fechaProximoPago) {
        this.fechaProximoPago = fechaProximoPago;
    }

    public String getNumeroPrestamo() {
        return numeroPrestamo;
    }

    public void setNumeroPrestamo(String numeroPrestamo) {
        this.numeroPrestamo = numeroPrestamo;
    }

    public String getIndicadorMoroso() {
        return indicadorMoroso;
    }

    public void setIndicadorMoroso(String indicadorMoroso) {
        this.indicadorMoroso = indicadorMoroso;
    }

    public String getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(String codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public int getDiasMora() {
        return diasMora;
    }

    public void setDiasMora(int diasMora) {
        this.diasMora = diasMora;
    }

    public float getMontoOriginal() {
        return montoOriginal;
    }

    public void setMontoOriginal(float montoOriginal) {
        this.montoOriginal = montoOriginal;
    }

    public float getCapitalDesembolsado() {
        return capitalDesembolsado;
    }

    public void setCapitalDesembolsado(float capitalDesembolsado) {
        this.capitalDesembolsado = capitalDesembolsado;
    }

    public float getSaldoActual() {
        return saldoActual;
    }

    public void setSaldoActual(float saldoActual) {
        this.saldoActual = saldoActual;
    }

    public int getAsesorFinanciero() {
        return asesorFinanciero;
    }

    public void setAsesorFinanciero(int asesorFinanciero) {
        this.asesorFinanciero = asesorFinanciero;
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
