/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Desarrollo
 */
@Embeddable
public class PtmEstadopatrimonialPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "colaborador_dpi")
    private String colaboradorDpi;
    @Basic(optional = false)
    @Column(name = "anio")
    private int anio;

    public PtmEstadopatrimonialPK() {
    }

    public PtmEstadopatrimonialPK(String colaboradorDpi, int anio) {
        this.colaboradorDpi = colaboradorDpi;
        this.anio = anio;
    }

    public String getColaboradorDpi() {
        return colaboradorDpi;
    }

    public void setColaboradorDpi(String colaboradorDpi) {
        this.colaboradorDpi = colaboradorDpi;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (colaboradorDpi != null ? colaboradorDpi.hashCode() : 0);
        hash += (int) anio;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PtmEstadopatrimonialPK)) {
            return false;
        }
        PtmEstadopatrimonialPK other = (PtmEstadopatrimonialPK) object;
        if ((this.colaboradorDpi == null && other.colaboradorDpi != null) || (this.colaboradorDpi != null && !this.colaboradorDpi.equals(other.colaboradorDpi))) {
            return false;
        }
        if (this.anio != other.anio) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dao.PtmEstadopatrimonialPK[ colaboradorDpi=" + colaboradorDpi + ", anio=" + anio + " ]";
    }
    
}
