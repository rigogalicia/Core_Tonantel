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
 * @author r29galicia
 */
@Entity
@Table(name = "av_telefono")
@NamedQueries({
    @NamedQuery(name = "AvTelefono.findAll", query = "SELECT a FROM AvTelefono a")})
public class AvTelefono implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "numero")
    private String numero;
    @JoinColumn(name = "asociado_cif", referencedColumnName = "cif")
    @ManyToOne(optional = false)
    private AvAsociado asociadoCif;

    public AvTelefono() {
    }

    public AvTelefono(Integer id) {
        this.id = id;
    }

    public AvTelefono(Integer id, String numero) {
        this.id = id;
        this.numero = numero;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public AvAsociado getAsociadoCif() {
        return asociadoCif;
    }

    public void setAsociadoCif(AvAsociado asociadoCif) {
        this.asociadoCif = asociadoCif;
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
        if (!(object instanceof AvTelefono)) {
            return false;
        }
        AvTelefono other = (AvTelefono) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dao.AvTelefono[ id=" + id + " ]";
    }
    
}
