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
 * @author Rgalicia
 */
@Entity
@Table(name = "rally_seguimiento")
@NamedQueries({
    @NamedQuery(name = "RallySeguimiento.findAll", query = "SELECT r FROM RallySeguimiento r")})
public class RallySeguimiento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idseguimiento")
    private Integer idseguimiento;
    @Column(name = "usuario_refiere")
    private String usuarioRefiere;
    @Basic(optional = false)
    @Column(name = "comentario")
    private String comentario;
    @Basic(optional = false)
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;
    @JoinColumn(name = "rally_asignacion_idasignacion", referencedColumnName = "idasignacion")
    @ManyToOne(optional = false)
    private RallyAsignacion rallyAsignacionIdasignacion;

    public RallySeguimiento() {
    }

    public RallySeguimiento(Integer idseguimiento) {
        this.idseguimiento = idseguimiento;
    }

    public RallySeguimiento(Integer idseguimiento, String comentario, Date fechaCreacion) {
        this.idseguimiento = idseguimiento;
        this.comentario = comentario;
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getIdseguimiento() {
        return idseguimiento;
    }

    public void setIdseguimiento(Integer idseguimiento) {
        this.idseguimiento = idseguimiento;
    }

    public String getUsuarioRefiere() {
        return usuarioRefiere;
    }

    public void setUsuarioRefiere(String usuarioRefiere) {
        this.usuarioRefiere = usuarioRefiere;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public RallyAsignacion getRallyAsignacionIdasignacion() {
        return rallyAsignacionIdasignacion;
    }

    public void setRallyAsignacionIdasignacion(RallyAsignacion rallyAsignacionIdasignacion) {
        this.rallyAsignacionIdasignacion = rallyAsignacionIdasignacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idseguimiento != null ? idseguimiento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RallySeguimiento)) {
            return false;
        }
        RallySeguimiento other = (RallySeguimiento) object;
        if ((this.idseguimiento == null && other.idseguimiento != null) || (this.idseguimiento != null && !this.idseguimiento.equals(other.idseguimiento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dao.RallySeguimiento[ idseguimiento=" + idseguimiento + " ]";
    }
    
}
