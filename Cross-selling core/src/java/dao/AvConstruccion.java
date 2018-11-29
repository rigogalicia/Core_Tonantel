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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Desarrollo
 */
@Entity
@Table(name = "av_construccion")
@NamedQueries({
    @NamedQuery(name = "AvConstruccion.findAll", query = "SELECT a FROM AvConstruccion a")})
public class AvConstruccion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "muro")
    private String muro;
    @Basic(optional = false)
    @Column(name = "techo")
    private String techo;
    @Basic(optional = false)
    @Column(name = "cielo")
    private String cielo;
    @Basic(optional = false)
    @Column(name = "piso")
    private String piso;
    @Basic(optional = false)
    @Column(name = "niveles")
    private String niveles;
    @Basic(optional = false)
    @Column(name = "electricidad")
    private String electricidad;
    @Basic(optional = false)
    @Column(name = "sanitario")
    private String sanitario;
    @Basic(optional = false)
    @Column(name = "ambiente")
    private String ambiente;
    @Basic(optional = false)
    @Column(name = "agua")
    private String agua;
    @Basic(optional = false)
    @Column(name = "destino")
    private String destino;
    @Basic(optional = false)
    @Column(name = "riesgo")
    private String riesgo;
    @Basic(optional = false)
    @Column(name = "factores_positivos")
    private String factoresPositivos;
    @JoinColumn(name = "inmueble_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private AvInmueble inmuebleId;

    public AvConstruccion() {
    }

    public AvConstruccion(Integer id) {
        this.id = id;
    }

    public AvConstruccion(Integer id, String muro, String techo, String cielo, String piso, String niveles, String electricidad, String sanitario, String ambiente, String agua, String destino, String riesgo, String factoresPositivos) {
        this.id = id;
        this.muro = muro;
        this.techo = techo;
        this.cielo = cielo;
        this.piso = piso;
        this.niveles = niveles;
        this.electricidad = electricidad;
        this.sanitario = sanitario;
        this.ambiente = ambiente;
        this.agua = agua;
        this.destino = destino;
        this.riesgo = riesgo;
        this.factoresPositivos = factoresPositivos;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMuro() {
        return muro;
    }

    public void setMuro(String muro) {
        this.muro = muro;
    }

    public String getTecho() {
        return techo;
    }

    public void setTecho(String techo) {
        this.techo = techo;
    }

    public String getCielo() {
        return cielo;
    }

    public void setCielo(String cielo) {
        this.cielo = cielo;
    }

    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public String getNiveles() {
        return niveles;
    }

    public void setNiveles(String niveles) {
        this.niveles = niveles;
    }

    public String getElectricidad() {
        return electricidad;
    }

    public void setElectricidad(String electricidad) {
        this.electricidad = electricidad;
    }

    public String getSanitario() {
        return sanitario;
    }

    public void setSanitario(String sanitario) {
        this.sanitario = sanitario;
    }

    public String getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(String ambiente) {
        this.ambiente = ambiente;
    }

    public String getAgua() {
        return agua;
    }

    public void setAgua(String agua) {
        this.agua = agua;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getRiesgo() {
        return riesgo;
    }

    public void setRiesgo(String riesgo) {
        this.riesgo = riesgo;
    }

    public String getFactoresPositivos() {
        return factoresPositivos;
    }

    public void setFactoresPositivos(String factoresPositivos) {
        this.factoresPositivos = factoresPositivos;
    }

    public AvInmueble getInmuebleId() {
        return inmuebleId;
    }

    public void setInmuebleId(AvInmueble inmuebleId) {
        this.inmuebleId = inmuebleId;
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
        if (!(object instanceof AvConstruccion)) {
            return false;
        }
        AvConstruccion other = (AvConstruccion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dao.AvConstruccion[ id=" + id + " ]";
    }
    
}
