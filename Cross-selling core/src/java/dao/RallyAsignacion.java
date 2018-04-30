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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "rally_asignacion")
@NamedQueries({
    @NamedQuery(name = "RallyAsignacion.findAll", query = "SELECT r FROM RallyAsignacion r")})
public class RallyAsignacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idasignacion")
    private Integer idasignacion;
    @Basic(optional = false)
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;
    @Column(name = "fecha_cierre")
    @Temporal(TemporalType.DATE)
    private Date fechaCierre;
    @Column(name = "fecha_proceso")
    @Temporal(TemporalType.DATE)
    private Date fechaProceso;
    @Column(name = "comentario")
    private String comentario;
    @Column(name = "referencia")
    private String referencia;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "monto")
    private Float monto;
    @Column(name = "saldo")
    private Float saldo;
    @Basic(optional = false)
    @Column(name = "estado")
    private Character estado;
    @Basic(optional = false)
    @Column(name = "usuario")
    private String usuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rallyAsignacionIdasignacion")
    private List<RallySeguimiento> rallySeguimientoList;
    @JoinColumn(name = "rally_meta_idmeta", referencedColumnName = "idmeta")
    @ManyToOne(optional = false)
    private RallyMeta rallyMetaIdmeta;
    @JoinColumn(name = "rally_referencia_idreferencia", referencedColumnName = "idreferencia")
    @ManyToOne(optional = false)
    private RallyReferencia rallyReferenciaIdreferencia;

    public RallyAsignacion() {
    }

    public RallyAsignacion(Integer idasignacion) {
        this.idasignacion = idasignacion;
    }

    public RallyAsignacion(Integer idasignacion, Date fechaCreacion, Character estado, String usuario) {
        this.idasignacion = idasignacion;
        this.fechaCreacion = fechaCreacion;
        this.estado = estado;
        this.usuario = usuario;
    }

    public Integer getIdasignacion() {
        return idasignacion;
    }

    public void setIdasignacion(Integer idasignacion) {
        this.idasignacion = idasignacion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public Date getFechaProceso() {
        return fechaProceso;
    }

    public void setFechaProceso(Date fechaProceso) {
        this.fechaProceso = fechaProceso;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public Float getMonto() {
        return monto;
    }

    public void setMonto(Float monto) {
        this.monto = monto;
    }

    public Float getSaldo() {
        return saldo;
    }

    public void setSaldo(Float saldo) {
        this.saldo = saldo;
    }

    public Character getEstado() {
        return estado;
    }

    public void setEstado(Character estado) {
        this.estado = estado;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public List<RallySeguimiento> getRallySeguimientoList() {
        return rallySeguimientoList;
    }

    public void setRallySeguimientoList(List<RallySeguimiento> rallySeguimientoList) {
        this.rallySeguimientoList = rallySeguimientoList;
    }

    public RallyMeta getRallyMetaIdmeta() {
        return rallyMetaIdmeta;
    }

    public void setRallyMetaIdmeta(RallyMeta rallyMetaIdmeta) {
        this.rallyMetaIdmeta = rallyMetaIdmeta;
    }

    public RallyReferencia getRallyReferenciaIdreferencia() {
        return rallyReferenciaIdreferencia;
    }

    public void setRallyReferenciaIdreferencia(RallyReferencia rallyReferenciaIdreferencia) {
        this.rallyReferenciaIdreferencia = rallyReferenciaIdreferencia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idasignacion != null ? idasignacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RallyAsignacion)) {
            return false;
        }
        RallyAsignacion other = (RallyAsignacion) object;
        if ((this.idasignacion == null && other.idasignacion != null) || (this.idasignacion != null && !this.idasignacion.equals(other.idasignacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dao.RallyAsignacion[ idasignacion=" + idasignacion + " ]";
    }
    
}
