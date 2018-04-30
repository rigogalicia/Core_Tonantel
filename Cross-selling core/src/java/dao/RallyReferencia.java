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
@Table(name = "rally_referencia")
@NamedQueries({
    @NamedQuery(name = "RallyReferencia.findAll", query = "SELECT r FROM RallyReferencia r")})
public class RallyReferencia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idreferencia")
    private Integer idreferencia;
    @Basic(optional = false)
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;
    @Column(name = "comentario")
    private String comentario;
    @Basic(optional = false)
    @Column(name = "estado")
    private Character estado;
    @Basic(optional = false)
    @Column(name = "agencia_cercana")
    private String agenciaCercana;
    @Basic(optional = false)
    @Column(name = "usuario")
    private String usuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rallyReferenciaIdreferencia")
    private List<RallyAsignacion> rallyAsignacionList;
    @JoinColumn(name = "rally_asociado_idasociado", referencedColumnName = "idasociado")
    @ManyToOne(optional = false)
    private RallyAsociado rallyAsociadoIdasociado;
    @JoinColumn(name = "rally_producto_idproducto", referencedColumnName = "idproducto")
    @ManyToOne(optional = false)
    private RallyProducto rallyProductoIdproducto;

    public RallyReferencia() {
    }

    public RallyReferencia(Integer idreferencia) {
        this.idreferencia = idreferencia;
    }

    public RallyReferencia(Integer idreferencia, Date fechaCreacion, Character estado, String agenciaCercana, String usuario) {
        this.idreferencia = idreferencia;
        this.fechaCreacion = fechaCreacion;
        this.estado = estado;
        this.agenciaCercana = agenciaCercana;
        this.usuario = usuario;
    }

    public Integer getIdreferencia() {
        return idreferencia;
    }

    public void setIdreferencia(Integer idreferencia) {
        this.idreferencia = idreferencia;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Character getEstado() {
        return estado;
    }

    public void setEstado(Character estado) {
        this.estado = estado;
    }

    public String getAgenciaCercana() {
        return agenciaCercana;
    }

    public void setAgenciaCercana(String agenciaCercana) {
        this.agenciaCercana = agenciaCercana;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public List<RallyAsignacion> getRallyAsignacionList() {
        return rallyAsignacionList;
    }

    public void setRallyAsignacionList(List<RallyAsignacion> rallyAsignacionList) {
        this.rallyAsignacionList = rallyAsignacionList;
    }

    public RallyAsociado getRallyAsociadoIdasociado() {
        return rallyAsociadoIdasociado;
    }

    public void setRallyAsociadoIdasociado(RallyAsociado rallyAsociadoIdasociado) {
        this.rallyAsociadoIdasociado = rallyAsociadoIdasociado;
    }

    public RallyProducto getRallyProductoIdproducto() {
        return rallyProductoIdproducto;
    }

    public void setRallyProductoIdproducto(RallyProducto rallyProductoIdproducto) {
        this.rallyProductoIdproducto = rallyProductoIdproducto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idreferencia != null ? idreferencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RallyReferencia)) {
            return false;
        }
        RallyReferencia other = (RallyReferencia) object;
        if ((this.idreferencia == null && other.idreferencia != null) || (this.idreferencia != null && !this.idreferencia.equals(other.idreferencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dao.RallyReferencia[ idreferencia=" + idreferencia + " ]";
    }
    
}
