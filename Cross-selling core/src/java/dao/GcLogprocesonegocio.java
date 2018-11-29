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
@Table(name = "gc_logprocesonegocio")
@NamedQueries({
    @NamedQuery(name = "GcLogprocesonegocio.findAll", query = "SELECT g FROM GcLogprocesonegocio g")})
public class GcLogprocesonegocio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "fecha_hora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHora;
    @Basic(optional = false)
    @Column(name = "est")
    private Character est;
    @Basic(optional = false)
    @Column(name = "usuario")
    private String usuario;
    @Basic(optional = false)
    @Column(name = "comentario")
    private String comentario;
    @JoinColumn(name = "fichanegocio_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private GcFichanegocio fichanegocioId;

    public GcLogprocesonegocio() {
    }

    public GcLogprocesonegocio(Integer id) {
        this.id = id;
    }

    public GcLogprocesonegocio(Integer id, Date fechaHora, Character est, String usuario, String comentario) {
        this.id = id;
        this.fechaHora = fechaHora;
        this.est = est;
        this.usuario = usuario;
        this.comentario = comentario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Character getEst() {
        return est;
    }

    public void setEst(Character est) {
        this.est = est;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public GcFichanegocio getFichanegocioId() {
        return fichanegocioId;
    }

    public void setFichanegocioId(GcFichanegocio fichanegocioId) {
        this.fichanegocioId = fichanegocioId;
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
        if (!(object instanceof GcLogprocesonegocio)) {
            return false;
        }
        GcLogprocesonegocio other = (GcLogprocesonegocio) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dao.GcLogprocesonegocio[ id=" + id + " ]";
    }
    
}
