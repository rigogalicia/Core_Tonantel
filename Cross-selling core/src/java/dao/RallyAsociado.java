/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Rgalicia
 */
@Entity
@Table(name = "rally_asociado")
@NamedQueries({
    @NamedQuery(name = "RallyAsociado.findAll", query = "SELECT r FROM RallyAsociado r")})
public class RallyAsociado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idasociado")
    private Integer idasociado;
    @Column(name = "cif")
    private String cif;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "apellido")
    private String apellido;
    @Basic(optional = false)
    @Column(name = "direccion")
    private String direccion;
    @Basic(optional = false)
    @Column(name = "telefonomovil")
    private String telefonomovil;
    @Column(name = "telefonocasa")
    private String telefonocasa;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rallyAsociadoIdasociado")
    private List<RallyReferencia> rallyReferenciaList;

    public RallyAsociado() {
    }

    public RallyAsociado(Integer idasociado) {
        this.idasociado = idasociado;
    }

    public RallyAsociado(Integer idasociado, String nombre, String apellido, String direccion, String telefonomovil) {
        this.idasociado = idasociado;
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        this.telefonomovil = telefonomovil;
    }

    public Integer getIdasociado() {
        return idasociado;
    }

    public void setIdasociado(Integer idasociado) {
        this.idasociado = idasociado;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefonomovil() {
        return telefonomovil;
    }

    public void setTelefonomovil(String telefonomovil) {
        this.telefonomovil = telefonomovil;
    }

    public String getTelefonocasa() {
        return telefonocasa;
    }

    public void setTelefonocasa(String telefonocasa) {
        this.telefonocasa = telefonocasa;
    }

    public List<RallyReferencia> getRallyReferenciaList() {
        return rallyReferenciaList;
    }

    public void setRallyReferenciaList(List<RallyReferencia> rallyReferenciaList) {
        this.rallyReferenciaList = rallyReferenciaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idasociado != null ? idasociado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RallyAsociado)) {
            return false;
        }
        RallyAsociado other = (RallyAsociado) object;
        if ((this.idasociado == null && other.idasociado != null) || (this.idasociado != null && !this.idasociado.equals(other.idasociado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dao.RallyAsociado[ idasociado=" + idasociado + " ]";
    }
    
}
