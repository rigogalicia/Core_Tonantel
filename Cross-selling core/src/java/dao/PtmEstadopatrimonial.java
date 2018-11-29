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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
 * @author r29galicia
 */
@Entity
@Table(name = "ptm_estadopatrimonial")
@NamedQueries({
    @NamedQuery(name = "PtmEstadopatrimonial.findAll", query = "SELECT p FROM PtmEstadopatrimonial p")})
public class PtmEstadopatrimonial implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PtmEstadopatrimonialPK ptmEstadopatrimonialPK;
    @Column(name = "estado")
    private Character estado;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @Column(name = "usuario")
    private String usuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ptmEstadopatrimonial")
    private List<PtmBienesmuebles> ptmBienesmueblesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ptmEstadopatrimonial")
    private List<PtmTarjetacredito> ptmTarjetacreditoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ptmEstadopatrimonial")
    private List<PtmPrestamo> ptmPrestamoList;
    @JoinColumn(name = "ptm_activo_idactivo", referencedColumnName = "idactivo")
    @ManyToOne(optional = false)
    private PtmActivo ptmActivoIdactivo;
    @JoinColumn(name = "colaborador_dpi", referencedColumnName = "dpi", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private PtmColaborador ptmColaborador;
    @JoinColumn(name = "ptm_pasivo_idpasivo", referencedColumnName = "idpasivo")
    @ManyToOne(optional = false)
    private PtmPasivo ptmPasivoIdpasivo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ptmEstadopatrimonial")
    private List<PtmBienesinmuebles> ptmBienesinmueblesList;

    public PtmEstadopatrimonial() {
    }

    public PtmEstadopatrimonial(PtmEstadopatrimonialPK ptmEstadopatrimonialPK) {
        this.ptmEstadopatrimonialPK = ptmEstadopatrimonialPK;
    }

    public PtmEstadopatrimonial(PtmEstadopatrimonialPK ptmEstadopatrimonialPK, String usuario) {
        this.ptmEstadopatrimonialPK = ptmEstadopatrimonialPK;
        this.usuario = usuario;
    }

    public PtmEstadopatrimonial(int anio, String colaboradorDpi) {
        this.ptmEstadopatrimonialPK = new PtmEstadopatrimonialPK(anio, colaboradorDpi);
    }

    public PtmEstadopatrimonialPK getPtmEstadopatrimonialPK() {
        return ptmEstadopatrimonialPK;
    }

    public void setPtmEstadopatrimonialPK(PtmEstadopatrimonialPK ptmEstadopatrimonialPK) {
        this.ptmEstadopatrimonialPK = ptmEstadopatrimonialPK;
    }

    public Character getEstado() {
        return estado;
    }

    public void setEstado(Character estado) {
        this.estado = estado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public List<PtmBienesmuebles> getPtmBienesmueblesList() {
        return ptmBienesmueblesList;
    }

    public void setPtmBienesmueblesList(List<PtmBienesmuebles> ptmBienesmueblesList) {
        this.ptmBienesmueblesList = ptmBienesmueblesList;
    }

    public List<PtmTarjetacredito> getPtmTarjetacreditoList() {
        return ptmTarjetacreditoList;
    }

    public void setPtmTarjetacreditoList(List<PtmTarjetacredito> ptmTarjetacreditoList) {
        this.ptmTarjetacreditoList = ptmTarjetacreditoList;
    }

    public List<PtmPrestamo> getPtmPrestamoList() {
        return ptmPrestamoList;
    }

    public void setPtmPrestamoList(List<PtmPrestamo> ptmPrestamoList) {
        this.ptmPrestamoList = ptmPrestamoList;
    }

    public PtmActivo getPtmActivoIdactivo() {
        return ptmActivoIdactivo;
    }

    public void setPtmActivoIdactivo(PtmActivo ptmActivoIdactivo) {
        this.ptmActivoIdactivo = ptmActivoIdactivo;
    }

    public PtmColaborador getPtmColaborador() {
        return ptmColaborador;
    }

    public void setPtmColaborador(PtmColaborador ptmColaborador) {
        this.ptmColaborador = ptmColaborador;
    }

    public PtmPasivo getPtmPasivoIdpasivo() {
        return ptmPasivoIdpasivo;
    }

    public void setPtmPasivoIdpasivo(PtmPasivo ptmPasivoIdpasivo) {
        this.ptmPasivoIdpasivo = ptmPasivoIdpasivo;
    }

    public List<PtmBienesinmuebles> getPtmBienesinmueblesList() {
        return ptmBienesinmueblesList;
    }

    public void setPtmBienesinmueblesList(List<PtmBienesinmuebles> ptmBienesinmueblesList) {
        this.ptmBienesinmueblesList = ptmBienesinmueblesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ptmEstadopatrimonialPK != null ? ptmEstadopatrimonialPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PtmEstadopatrimonial)) {
            return false;
        }
        PtmEstadopatrimonial other = (PtmEstadopatrimonial) object;
        if ((this.ptmEstadopatrimonialPK == null && other.ptmEstadopatrimonialPK != null) || (this.ptmEstadopatrimonialPK != null && !this.ptmEstadopatrimonialPK.equals(other.ptmEstadopatrimonialPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dao.PtmEstadopatrimonial[ ptmEstadopatrimonialPK=" + ptmEstadopatrimonialPK + " ]";
    }
    
}
