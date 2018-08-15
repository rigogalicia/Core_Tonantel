/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.Serializable;
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
@Table(name = "av_solicitud")
@NamedQueries({
    @NamedQuery(name = "AvSolicitud.findAll", query = "SELECT a FROM AvSolicitud a")})
public class AvSolicitud implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "numero_solicitud")
    private String numeroSolicitud;
    @Basic(optional = false)
    @Column(name = "monto")
    private String monto;
    @Basic(optional = false)
    @Column(name = "numero_credito")
    private String numeroCredito;
    @Basic(optional = false)
    @Column(name = "usuario")
    private String usuario;
    @Basic(optional = false)
    @Column(name = "agencia")
    private String agencia;
    @Basic(optional = false)
    @Column(name = "fechahora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechahora;
    @Basic(optional = false)
    @Column(name = "estado")
    private Character estado;
    @JoinColumn(name = "asociado_cif", referencedColumnName = "cif")
    @ManyToOne(optional = false)
    private AvAsociado asociadoCif;
    @JoinColumn(name = "inmueble_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private AvInmueble inmuebleId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "solicitudNumeroSolicitud")
    private List<AvAsignacion> avAsignacionList;

    public AvSolicitud() {
    }

    public AvSolicitud(String numeroSolicitud) {
        this.numeroSolicitud = numeroSolicitud;
    }

    public AvSolicitud(String numeroSolicitud, String monto, String numeroCredito, String usuario, String agencia, Date fechahora, Character estado) {
        this.numeroSolicitud = numeroSolicitud;
        this.monto = monto;
        this.numeroCredito = numeroCredito;
        this.usuario = usuario;
        this.agencia = agencia;
        this.fechahora = fechahora;
        this.estado = estado;
    }

    public String getNumeroSolicitud() {
        return numeroSolicitud;
    }

    public void setNumeroSolicitud(String numeroSolicitud) {
        this.numeroSolicitud = numeroSolicitud;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getNumeroCredito() {
        return numeroCredito;
    }

    public void setNumeroCredito(String numeroCredito) {
        this.numeroCredito = numeroCredito;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public Date getFechahora() {
        return fechahora;
    }

    public void setFechahora(Date fechahora) {
        this.fechahora = fechahora;
    }

    public Character getEstado() {
        return estado;
    }

    public void setEstado(Character estado) {
        this.estado = estado;
    }

    public AvAsociado getAsociadoCif() {
        return asociadoCif;
    }

    public void setAsociadoCif(AvAsociado asociadoCif) {
        this.asociadoCif = asociadoCif;
    }

    public AvInmueble getInmuebleId() {
        return inmuebleId;
    }

    public void setInmuebleId(AvInmueble inmuebleId) {
        this.inmuebleId = inmuebleId;
    }

    public List<AvAsignacion> getAvAsignacionList() {
        return avAsignacionList;
    }

    public void setAvAsignacionList(List<AvAsignacion> avAsignacionList) {
        this.avAsignacionList = avAsignacionList;
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
        if (!(object instanceof AvSolicitud)) {
            return false;
        }
        AvSolicitud other = (AvSolicitud) object;
        if ((this.numeroSolicitud == null && other.numeroSolicitud != null) || (this.numeroSolicitud != null && !this.numeroSolicitud.equals(other.numeroSolicitud))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dao.AvSolicitud[ numeroSolicitud=" + numeroSolicitud + " ]";
    }
    
}
