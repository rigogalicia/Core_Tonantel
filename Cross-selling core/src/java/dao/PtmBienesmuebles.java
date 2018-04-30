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
 * @author Rgalicia
 */
@Entity
@Table(name = "ptm_bienesmuebles")
@NamedQueries({
    @NamedQuery(name = "PtmBienesmuebles.findAll", query = "SELECT p FROM PtmBienesmuebles p")})
public class PtmBienesmuebles implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idbienesmuebles")
    private Integer idbienesmuebles;
    @Basic(optional = false)
    @Column(name = "marca")
    private String marca;
    @Basic(optional = false)
    @Column(name = "tipo")
    private String tipo;
    @Basic(optional = false)
    @Column(name = "anio")
    private String anio;
    @Basic(optional = false)
    @Column(name = "modelo")
    private String modelo;
    @JoinColumns({
        @JoinColumn(name = "estadopatrimonial_anio", referencedColumnName = "anio"),
        @JoinColumn(name = "estadopatrimonial_colaborador_dpi", referencedColumnName = "colaborador_dpi")})
    @ManyToOne(optional = false)
    private PtmEstadopatrimonial ptmEstadopatrimonial;

    public PtmBienesmuebles() {
    }

    public PtmBienesmuebles(Integer idbienesmuebles) {
        this.idbienesmuebles = idbienesmuebles;
    }

    public PtmBienesmuebles(Integer idbienesmuebles, String marca, String tipo, String anio, String modelo) {
        this.idbienesmuebles = idbienesmuebles;
        this.marca = marca;
        this.tipo = tipo;
        this.anio = anio;
        this.modelo = modelo;
    }

    public Integer getIdbienesmuebles() {
        return idbienesmuebles;
    }

    public void setIdbienesmuebles(Integer idbienesmuebles) {
        this.idbienesmuebles = idbienesmuebles;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
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
        hash += (idbienesmuebles != null ? idbienesmuebles.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PtmBienesmuebles)) {
            return false;
        }
        PtmBienesmuebles other = (PtmBienesmuebles) object;
        if ((this.idbienesmuebles == null && other.idbienesmuebles != null) || (this.idbienesmuebles != null && !this.idbienesmuebles.equals(other.idbienesmuebles))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dao.PtmBienesmuebles[ idbienesmuebles=" + idbienesmuebles + " ]";
    }
    
}
