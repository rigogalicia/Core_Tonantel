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
@Table(name = "ptm_hijo")
@NamedQueries({
    @NamedQuery(name = "PtmHijo.findAll", query = "SELECT p FROM PtmHijo p")})
public class PtmHijo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idhijo")
    private Integer idhijo;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "edad")
    private int edad;
    @JoinColumn(name = "ptm_colaborador_dpi", referencedColumnName = "dpi")
    @ManyToOne(optional = false)
    private PtmColaborador ptmColaboradorDpi;

    public PtmHijo() {
    }

    public PtmHijo(Integer idhijo) {
        this.idhijo = idhijo;
    }

    public PtmHijo(Integer idhijo, String nombre, int edad) {
        this.idhijo = idhijo;
        this.nombre = nombre;
        this.edad = edad;
    }

    public Integer getIdhijo() {
        return idhijo;
    }

    public void setIdhijo(Integer idhijo) {
        this.idhijo = idhijo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public PtmColaborador getPtmColaboradorDpi() {
        return ptmColaboradorDpi;
    }

    public void setPtmColaboradorDpi(PtmColaborador ptmColaboradorDpi) {
        this.ptmColaboradorDpi = ptmColaboradorDpi;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idhijo != null ? idhijo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PtmHijo)) {
            return false;
        }
        PtmHijo other = (PtmHijo) object;
        if ((this.idhijo == null && other.idhijo != null) || (this.idhijo != null && !this.idhijo.equals(other.idhijo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dao.PtmHijo[ idhijo=" + idhijo + " ]";
    }
    
}
