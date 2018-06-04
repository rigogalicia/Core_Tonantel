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
@Table(name = "gc_gestion")
@NamedQueries({
    @NamedQuery(name = "GcGestion.findAll", query = "SELECT g FROM GcGestion g")})
public class GcGestion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "analista")
    private String analista;
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gestionId")
    private List<GcSeguimiento> gcSeguimientoList;
    @JoinColumn(name = "solicitud_numero_solicitud", referencedColumnName = "numero_solicitud")
    @ManyToOne(optional = false)
    private GcSolicitud solicitudNumeroSolicitud;
    @JoinColumn(name = "estado_id", referencedColumnName = "id")
    @ManyToOne
    private GcEstado estadoId;

    public GcGestion() {
    }

    public GcGestion(Integer id) {
        this.id = id;
    }

    public GcGestion(Integer id, String analista, Date fecha) {
        this.id = id;
        this.analista = analista;
        this.fecha = fecha;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAnalista() {
        return analista;
    }

    public void setAnalista(String analista) {
        this.analista = analista;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List<GcSeguimiento> getGcSeguimientoList() {
        return gcSeguimientoList;
    }

    public void setGcSeguimientoList(List<GcSeguimiento> gcSeguimientoList) {
        this.gcSeguimientoList = gcSeguimientoList;
    }

    public GcSolicitud getSolicitudNumeroSolicitud() {
        return solicitudNumeroSolicitud;
    }

    public void setSolicitudNumeroSolicitud(GcSolicitud solicitudNumeroSolicitud) {
        this.solicitudNumeroSolicitud = solicitudNumeroSolicitud;
    }

    public GcEstado getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(GcEstado estadoId) {
        this.estadoId = estadoId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GcGestion)) {
            return false;
        }
        GcGestion other = (GcGestion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dao.GcGestion[ id=" + id + " ]";
    }
    
}
