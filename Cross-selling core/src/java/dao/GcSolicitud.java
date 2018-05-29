/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Rgalicia
 */
@Entity
@Table(name = "gc_solicitud")
@NamedQueries({
    @NamedQuery(name = "GcSolicitud.findAll", query = "SELECT g FROM GcSolicitud g")})
public class GcSolicitud implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "numero_solicitud")
    private String numeroSolicitud;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "monto")
    private BigDecimal monto;
    @Basic(optional = false)
    @Column(name = "asesor_financiero")
    private String asesorFinanciero;
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Basic(optional = false)
    @Column(name = "estado")
    private Character estado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "solicitudNumeroSolicitud")
    private List<GcGestion> gcGestionList;
    @JoinColumn(name = "asociado_cif", referencedColumnName = "cif")
    @ManyToOne(optional = false)
    private GcAsociado asociadoCif;
    @JoinColumn(name = "destino_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private GcDestino destinoId;
    @JoinColumn(name = "tipo_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private GcTipo tipoId;
    @JoinColumn(name = "tramite_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private GcTramite tramiteId;

    public GcSolicitud() {
    }

    public GcSolicitud(String numeroSolicitud) {
        this.numeroSolicitud = numeroSolicitud;
    }

    public GcSolicitud(String numeroSolicitud, BigDecimal monto, String asesorFinanciero, Date fecha, Character estado) {
        this.numeroSolicitud = numeroSolicitud;
        this.monto = monto;
        this.asesorFinanciero = asesorFinanciero;
        this.fecha = fecha;
        this.estado = estado;
    }

    public String getNumeroSolicitud() {
        return numeroSolicitud;
    }

    public void setNumeroSolicitud(String numeroSolicitud) {
        this.numeroSolicitud = numeroSolicitud;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public String getAsesorFinanciero() {
        return asesorFinanciero;
    }

    public void setAsesorFinanciero(String asesorFinanciero) {
        this.asesorFinanciero = asesorFinanciero;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Character getEstado() {
        return estado;
    }

    public void setEstado(Character estado) {
        this.estado = estado;
    }

    public List<GcGestion> getGcGestionList() {
        return gcGestionList;
    }

    public void setGcGestionList(List<GcGestion> gcGestionList) {
        this.gcGestionList = gcGestionList;
    }

    public GcAsociado getAsociadoCif() {
        return asociadoCif;
    }

    public void setAsociadoCif(GcAsociado asociadoCif) {
        this.asociadoCif = asociadoCif;
    }

    public GcDestino getDestinoId() {
        return destinoId;
    }

    public void setDestinoId(GcDestino destinoId) {
        this.destinoId = destinoId;
    }

    public GcTipo getTipoId() {
        return tipoId;
    }

    public void setTipoId(GcTipo tipoId) {
        this.tipoId = tipoId;
    }

    public GcTramite getTramiteId() {
        return tramiteId;
    }

    public void setTramiteId(GcTramite tramiteId) {
        this.tramiteId = tramiteId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numeroSolicitud != null ? numeroSolicitud.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GcSolicitud)) {
            return false;
        }
        GcSolicitud other = (GcSolicitud) object;
        if ((this.numeroSolicitud == null && other.numeroSolicitud != null) || (this.numeroSolicitud != null && !this.numeroSolicitud.equals(other.numeroSolicitud))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dao.GcSolicitud[ numeroSolicitud=" + numeroSolicitud + " ]";
    }
    
}