/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author r29galicia
 */
@Entity
@Table(name = "ptm_bienesinmuebles")
@NamedQueries({
    @NamedQuery(name = "PtmBienesinmuebles.findAll", query = "SELECT p FROM PtmBienesinmuebles p")})
public class PtmBienesinmuebles implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idbienesinmuebles")
    private Integer idbienesinmuebles;
    @Basic(optional = false)
    @Column(name = "ubicacion")
    private String ubicacion;
    @Basic(optional = false)
    @Column(name = "medidas")
    private String medidas;
    @Basic(optional = false)
    @Column(name = "hipotecado")
    private Character hipotecado;
    @Basic(optional = false)
    @Column(name = "lugar_hipoteca")
    private String lugarHipoteca;
    @Basic(optional = false)
    @Column(name = "tipo")
    private Character tipo;
    @JoinColumns({
        @JoinColumn(name = "estadopatrimonial_anio", referencedColumnName = "anio")
        , @JoinColumn(name = "estadopatrimonial_colaborador_dpi", referencedColumnName = "colaborador_dpi")})
    @ManyToOne(optional = false)
    private PtmEstadopatrimonial ptmEstadopatrimonial;

    public PtmBienesinmuebles() {
    }

    public PtmBienesinmuebles(Integer idbienesinmuebles) {
        this.idbienesinmuebles = idbienesinmuebles;
    }

    public PtmBienesinmuebles(Integer idbienesinmuebles, String ubicacion, String medidas, Character hipotecado, String lugarHipoteca, Character tipo) {
        this.idbienesinmuebles = idbienesinmuebles;
        this.ubicacion = ubicacion;
        this.medidas = medidas;
        this.hipotecado = hipotecado;
        this.lugarHipoteca = lugarHipoteca;
        this.tipo = tipo;
    }

    public Integer getIdbienesinmuebles() {
        return idbienesinmuebles;
    }

    public void setIdbienesinmuebles(Integer idbienesinmuebles) {
        this.idbienesinmuebles = idbienesinmuebles;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getMedidas() {
        return medidas;
    }

    public void setMedidas(String medidas) {
        this.medidas = medidas;
    }

    public Character getHipotecado() {
        return hipotecado;
    }

    public void setHipotecado(Character hipotecado) {
        this.hipotecado = hipotecado;
    }

    public String getLugarHipoteca() {
        return lugarHipoteca;
    }

    public void setLugarHipoteca(String lugarHipoteca) {
        this.lugarHipoteca = lugarHipoteca;
    }

    public Character getTipo() {
        return tipo;
    }

    public void setTipo(Character tipo) {
        this.tipo = tipo;
    }

    public PtmEstadopatrimonial getPtmEstadopatrimonial() {
        return ptmEstadopatrimonial;
    }

    public void setPtmEstadopatrimonial(PtmEstadopatrimonial ptmEstadopatrimonial) {
        this.ptmEstadopatrimonial = ptmEstadopatrimonial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idbienesinmuebles != null ? idbienesinmuebles.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PtmBienesinmuebles)) {
            return false;
        }
        PtmBienesinmuebles other = (PtmBienesinmuebles) object;
        if ((this.idbienesinmuebles == null && other.idbienesinmuebles != null) || (this.idbienesinmuebles != null && !this.idbienesinmuebles.equals(other.idbienesinmuebles))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dao.PtmBienesinmuebles[ idbienesinmuebles=" + idbienesinmuebles + " ]";
    }
    
}
