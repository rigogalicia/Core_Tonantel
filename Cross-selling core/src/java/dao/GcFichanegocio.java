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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Desarrollo
 */
@Entity
@Table(name = "gc_fichanegocio")
@NamedQueries({
    @NamedQuery(name = "GcFichanegocio.findAll", query = "SELECT g FROM GcFichanegocio g")})
public class GcFichanegocio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "problematica")
    private String problematica;
    @Basic(optional = false)
    @Column(name = "condiciones")
    private String condiciones;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fichanegocioId")
    private List<GcLogprocesonegocio> gcLogprocesonegocioList;
    @JoinColumn(name = "solicitud_numero_solicitud", referencedColumnName = "numero_solicitud")
    @ManyToOne(optional = false)
    private GcSolicitud solicitudNumeroSolicitud;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fichanegocioId")
    private List<GcVentajas> gcVentajasList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fichanegocioId")
    private List<GcDesventajas> gcDesventajasList;

    public GcFichanegocio() {
    }

    public GcFichanegocio(Integer id) {
        this.id = id;
    }

    public GcFichanegocio(Integer id, String problematica, String condiciones) {
        this.id = id;
        this.problematica = problematica;
        this.condiciones = condiciones;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProblematica() {
        return problematica;
    }

    public void setProblematica(String problematica) {
        this.problematica = problematica;
    }

    public String getCondiciones() {
        return condiciones;
    }

    public void setCondiciones(String condiciones) {
        this.condiciones = condiciones;
    }

    public List<GcLogprocesonegocio> getGcLogprocesonegocioList() {
        return gcLogprocesonegocioList;
    }

    public void setGcLogprocesonegocioList(List<GcLogprocesonegocio> gcLogprocesonegocioList) {
        this.gcLogprocesonegocioList = gcLogprocesonegocioList;
    }

    public GcSolicitud getSolicitudNumeroSolicitud() {
        return solicitudNumeroSolicitud;
    }

    public void setSolicitudNumeroSolicitud(GcSolicitud solicitudNumeroSolicitud) {
        this.solicitudNumeroSolicitud = solicitudNumeroSolicitud;
    }

    public List<GcVentajas> getGcVentajasList() {
        return gcVentajasList;
    }

    public void setGcVentajasList(List<GcVentajas> gcVentajasList) {
        this.gcVentajasList = gcVentajasList;
    }

    public List<GcDesventajas> getGcDesventajasList() {
        return gcDesventajasList;
    }

    public void setGcDesventajasList(List<GcDesventajas> gcDesventajasList) {
        this.gcDesventajasList = gcDesventajasList;
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
        if (!(object instanceof GcFichanegocio)) {
            return false;
        }
        GcFichanegocio other = (GcFichanegocio) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dao.GcFichanegocio[ id=" + id + " ]";
    }
    
}
