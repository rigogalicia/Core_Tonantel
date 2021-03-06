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
@Table(name = "ptm_colaborador")
@NamedQueries({
    @NamedQuery(name = "PtmColaborador.findAll", query = "SELECT p FROM PtmColaborador p")})
public class PtmColaborador implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "dpi")
    private String dpi;
    @Column(name = "nit")
    private String nit;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "domicilio")
    private String domicilio;
    @Basic(optional = false)
    @Column(name = "edad")
    private int edad;
    @Basic(optional = false)
    @Column(name = "telefono1")
    private String telefono1;
    @Column(name = "telefono2")
    private String telefono2;
    @Basic(optional = false)
    @Column(name = "estado_civil")
    private Character estadoCivil;
    @Column(name = "conyuge")
    private String conyuge;
    @Basic(optional = false)
    @Column(name = "dependientes")
    private int dependientes;
    @Column(name = "departamento")
    private String departamento;
    @Column(name = "profesion")
    private String profesion;
    @Column(name = "gradoactual")
    private String gradoactual;
    @Column(name = "carrera")
    private String carrera;
    @Column(name = "centroestudio")
    private String centroestudio;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ptmColaboradorDpi")
    private List<PtmCurso> ptmCursoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ptmColaborador")
    private List<PtmEstadopatrimonial> ptmEstadopatrimonialList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ptmColaboradorDpi")
    private List<PtmHijo> ptmHijoList;

    public PtmColaborador() {
    }

    public PtmColaborador(String dpi) {
        this.dpi = dpi;
    }

    public PtmColaborador(String dpi, String nombre, String domicilio, int edad, String telefono1, Character estadoCivil, int dependientes) {
        this.dpi = dpi;
        this.nombre = nombre;
        this.domicilio = domicilio;
        this.edad = edad;
        this.telefono1 = telefono1;
        this.estadoCivil = estadoCivil;
        this.dependientes = dependientes;
    }

    public String getDpi() {
        return dpi;
    }

    public void setDpi(String dpi) {
        this.dpi = dpi;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getTelefono1() {
        return telefono1;
    }

    public void setTelefono1(String telefono1) {
        this.telefono1 = telefono1;
    }

    public String getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    public Character getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(Character estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getConyuge() {
        return conyuge;
    }

    public void setConyuge(String conyuge) {
        this.conyuge = conyuge;
    }

    public int getDependientes() {
        return dependientes;
    }

    public void setDependientes(int dependientes) {
        this.dependientes = dependientes;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public String getGradoactual() {
        return gradoactual;
    }

    public void setGradoactual(String gradoactual) {
        this.gradoactual = gradoactual;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getCentroestudio() {
        return centroestudio;
    }

    public void setCentroestudio(String centroestudio) {
        this.centroestudio = centroestudio;
    }

    public List<PtmCurso> getPtmCursoList() {
        return ptmCursoList;
    }

    public void setPtmCursoList(List<PtmCurso> ptmCursoList) {
        this.ptmCursoList = ptmCursoList;
    }

    public List<PtmEstadopatrimonial> getPtmEstadopatrimonialList() {
        return ptmEstadopatrimonialList;
    }

    public void setPtmEstadopatrimonialList(List<PtmEstadopatrimonial> ptmEstadopatrimonialList) {
        this.ptmEstadopatrimonialList = ptmEstadopatrimonialList;
    }

    public List<PtmHijo> getPtmHijoList() {
        return ptmHijoList;
    }

    public void setPtmHijoList(List<PtmHijo> ptmHijoList) {
        this.ptmHijoList = ptmHijoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dpi != null ? dpi.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PtmColaborador)) {
            return false;
        }
        PtmColaborador other = (PtmColaborador) object;
        if ((this.dpi == null && other.dpi != null) || (this.dpi != null && !this.dpi.equals(other.dpi))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dao.PtmColaborador[ dpi=" + dpi + " ]";
    }
    
}
