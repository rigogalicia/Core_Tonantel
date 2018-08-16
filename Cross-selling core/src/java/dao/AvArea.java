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
 * @author Rgalicia
 */
@Entity
@Table(name = "av_area")
@NamedQueries({
    @NamedQuery(name = "AvArea.findAll", query = "SELECT a FROM AvArea a")})
public class AvArea implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "registrada")
    private String registrada;
    @Basic(optional = false)
    @Column(name = "fisica")
    private String fisica;
    @Basic(optional = false)
    @Column(name = "avaluar")
    private String avaluar;
    @Basic(optional = false)
    @Column(name = "construida")
    private String construida;
    @Basic(optional = false)
    @Column(name = "exceso")
    private String exceso;
    @Basic(optional = false)
    @Column(name = "frenteyfondo")
    private String frenteyfondo;
    @JoinColumn(name = "inmueble_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private AvInmueble inmuebleId;

    public AvArea() {
    }

    public AvArea(Integer id) {
        this.id = id;
    }

    public AvArea(Integer id, String registrada, String fisica, String avaluar, String construida, String exceso, String frenteyfondo) {
        this.id = id;
        this.registrada = registrada;
        this.fisica = fisica;
        this.avaluar = avaluar;
        this.construida = construida;
        this.exceso = exceso;
        this.frenteyfondo = frenteyfondo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRegistrada() {
        return registrada;
    }

    public void setRegistrada(String registrada) {
        this.registrada = registrada;
    }

    public String getFisica() {
        return fisica;
    }

    public void setFisica(String fisica) {
        this.fisica = fisica;
    }

    public String getAvaluar() {
        return avaluar;
    }

    public void setAvaluar(String avaluar) {
        this.avaluar = avaluar;
    }

    public String getConstruida() {
        return construida;
    }

    public void setConstruida(String construida) {
        this.construida = construida;
    }

    public String getExceso() {
        return exceso;
    }

    public void setExceso(String exceso) {
        this.exceso = exceso;
    }

    public String getFrenteyfondo() {
        return frenteyfondo;
    }

    public void setFrenteyfondo(String frenteyfondo) {
        this.frenteyfondo = frenteyfondo;
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
        if (!(object instanceof AvArea)) {
            return false;
        }
        AvArea other = (AvArea) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dao.AvArea[ id=" + id + " ]";
    }
    
}
