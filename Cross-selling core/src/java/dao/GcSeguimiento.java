/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
 * @author Desarrollo
 */
@Entity
@Table(name = "gc_seguimiento")
@NamedQueries({
    @NamedQuery(name = "GcSeguimiento.findAll", query = "SELECT g FROM GcSeguimiento g")})
public class GcSeguimiento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Basic(optional = false)
    @Column(name = "comentario")
    private String comentario;
    @JoinColumn(name = "solicitud_numero_solicitud", referencedColumnName = "numero_solicitud")
    @ManyToOne(optional = false)
    private GcSolicitud solicitudNumeroSolicitud;

    public GcSeguimiento() {
    }

    public GcSeguimiento(Integer id) {
        this.id = id;
    }

    public GcSeguimiento(Integer id, Date fecha, String comentario) {
        this.id = id;
        this.fecha = fecha;
        this.comentario = comentario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public GcSolicitud getSolicitudNumeroSolicitud() {
        return solicitudNumeroSolicitud;
    }

    public void setSolicitudNumeroSolicitud(GcSolicitud solicitudNumeroSolicitud) {
        this.solicitudNumeroSolicitud = solicitudNumeroSolicitud;
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
        if (!(object instanceof GcSeguimiento)) {
            return false;
        }
        GcSeguimiento other = (GcSeguimiento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dao.GcSeguimiento[ id=" + id + " ]";
    }
    
}
