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
@Table(name = "ptm_curso")
@NamedQueries({
    @NamedQuery(name = "PtmCurso.findAll", query = "SELECT p FROM PtmCurso p")})
public class PtmCurso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcurso")
    private Integer idcurso;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "centroestudio")
    private String centroestudio;
    @JoinColumn(name = "ptm_colaborador_dpi", referencedColumnName = "dpi")
    @ManyToOne(optional = false)
    private PtmColaborador ptmColaboradorDpi;

    public PtmCurso() {
    }

    public PtmCurso(Integer idcurso) {
        this.idcurso = idcurso;
    }

    public PtmCurso(Integer idcurso, String descripcion, String centroestudio) {
        this.idcurso = idcurso;
        this.descripcion = descripcion;
        this.centroestudio = centroestudio;
    }

    public Integer getIdcurso() {
        return idcurso;
    }

    public void setIdcurso(Integer idcurso) {
        this.idcurso = idcurso;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCentroestudio() {
        return centroestudio;
    }

    public void setCentroestudio(String centroestudio) {
        this.centroestudio = centroestudio;
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
        hash += (idcurso != null ? idcurso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PtmCurso)) {
            return false;
        }
        PtmCurso other = (PtmCurso) object;
        if ((this.idcurso == null && other.idcurso != null) || (this.idcurso != null && !this.idcurso.equals(other.idcurso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dao.PtmCurso[ idcurso=" + idcurso + " ]";
    }
    
}
