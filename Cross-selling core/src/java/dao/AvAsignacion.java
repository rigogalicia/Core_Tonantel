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
 * @author Desarrollo
 */
@Entity
@Table(name = "av_asignacion")
@NamedQueries({
    @NamedQuery(name = "AvAsignacion.findAll", query = "SELECT a FROM AvAsignacion a")})
public class AvAsignacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "usuario")
    private String usuario;
    @Column(name = "firma")
    private String firma;
    @Basic(optional = false)
    @Column(name = "fechahora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechahora;
    @Column(name = "fecha_visita")
    @Temporal(TemporalType.DATE)
    private Date fechaVisita;
    @Column(name = "asignador")
    private String asignador;
    @Column(name = "observacion")
    private String observacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "asignacionId")
    private List<AvAvaluo> avAvaluoList;
    @JoinColumn(name = "solicitud_numero_solicitud", referencedColumnName = "numero_solicitud")
    @ManyToOne(optional = false)
    private AvSolicitud solicitudNumeroSolicitud;

    public AvAsignacion() {
    }

    public AvAsignacion(Integer id) {
        this.id = id;
    }

    public AvAsignacion(Integer id, String usuario, Date fechahora) {
        this.id = id;
        this.usuario = usuario;
        this.fechahora = fechahora;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }

    public Date getFechahora() {
        return fechahora;
    }

    public void setFechahora(Date fechahora) {
        this.fechahora = fechahora;
    }

    public Date getFechaVisita() {
        return fechaVisita;
    }

    public void setFechaVisita(Date fechaVisita) {
        this.fechaVisita = fechaVisita;
    }

    public String getAsignador() {
        return asignador;
    }

    public void setAsignador(String asignador) {
        this.asignador = asignador;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public List<AvAvaluo> getAvAvaluoList() {
        return avAvaluoList;
    }

    public void setAvAvaluoList(List<AvAvaluo> avAvaluoList) {
        this.avAvaluoList = avAvaluoList;
    }

    public AvSolicitud getSolicitudNumeroSolicitud() {
        return solicitudNumeroSolicitud;
    }

    public void setSolicitudNumeroSolicitud(AvSolicitud solicitudNumeroSolicitud) {
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
        if (!(object instanceof AvAsignacion)) {
            return false;
        }
        AvAsignacion other = (AvAsignacion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dao.AvAsignacion[ id=" + id + " ]";
    }
    
}
