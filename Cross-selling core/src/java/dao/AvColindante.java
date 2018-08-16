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
@Table(name = "av_colindante")
@NamedQueries({
    @NamedQuery(name = "AvColindante.findAll", query = "SELECT a FROM AvColindante a")})
public class AvColindante implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "metros")
    private String metros;
    @Column(name = "varas")
    private String varas;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "tipo")
    private Character tipo;
    @JoinColumn(name = "puntocardinal_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private AvPuntocardinal puntocardinalId;
    @JoinColumn(name = "inmueble_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private AvInmueble inmuebleId;

    public AvColindante() {
    }

    public AvColindante(Integer id) {
        this.id = id;
    }

    public AvColindante(Integer id, String nombre, Character tipo) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMetros() {
        return metros;
    }

    public void setMetros(String metros) {
        this.metros = metros;
    }

    public String getVaras() {
        return varas;
    }

    public void setVaras(String varas) {
        this.varas = varas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Character getTipo() {
        return tipo;
    }

    public void setTipo(Character tipo) {
        this.tipo = tipo;
    }

    public AvPuntocardinal getPuntocardinalId() {
        return puntocardinalId;
    }

    public void setPuntocardinalId(AvPuntocardinal puntocardinalId) {
        this.puntocardinalId = puntocardinalId;
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
        if (!(object instanceof AvColindante)) {
            return false;
        }
        AvColindante other = (AvColindante) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dao.AvColindante[ id=" + id + " ]";
    }
    
}
