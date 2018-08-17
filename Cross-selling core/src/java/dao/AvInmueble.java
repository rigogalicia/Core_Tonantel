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
 * @author r29galicia
 */
@Entity
@Table(name = "av_inmueble")
@NamedQueries({
    @NamedQuery(name = "AvInmueble.findAll", query = "SELECT a FROM AvInmueble a")})
public class AvInmueble implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "direccion_registrada")
    private String direccionRegistrada;
    @Column(name = "direccion_fisica")
    private String direccionFisica;
    @Column(name = "coordenadas")
    private String coordenadas;
    @Basic(optional = false)
    @Column(name = "vivienda")
    private Character vivienda;
    @Basic(optional = false)
    @Column(name = "acceso")
    private Character acceso;
    @Basic(optional = false)
    @Column(name = "agua")
    private Character agua;
    @Basic(optional = false)
    @Column(name = "luz")
    private Character luz;
    @Basic(optional = false)
    @Column(name = "drenaje")
    private Character drenaje;
    @Column(name = "numero_catastral")
    private String numeroCatastral;
    @Column(name = "residencial")
    private String residencial;
    @Column(name = "observaciones")
    private String observaciones;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "inmuebleId")
    private List<AvColindante> avColindanteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "inmuebleId")
    private List<AvArea> avAreaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "inmuebleId")
    private List<AvSolicitud> avSolicitudList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "inmuebleId")
    private List<AvAvaluo> avAvaluoList;
    @JoinColumn(name = "documento_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private AvDocumento documentoId;
    @JoinColumn(name = "propietario_dpi", referencedColumnName = "dpi")
    @ManyToOne(optional = false)
    private AvPropietario propietarioDpi;

    public AvInmueble() {
    }

    public AvInmueble(Integer id) {
        this.id = id;
    }

    public AvInmueble(Integer id, String direccionRegistrada, Character vivienda, Character acceso, Character agua, Character luz, Character drenaje) {
        this.id = id;
        this.direccionRegistrada = direccionRegistrada;
        this.vivienda = vivienda;
        this.acceso = acceso;
        this.agua = agua;
        this.luz = luz;
        this.drenaje = drenaje;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDireccionRegistrada() {
        return direccionRegistrada;
    }

    public void setDireccionRegistrada(String direccionRegistrada) {
        this.direccionRegistrada = direccionRegistrada;
    }

    public String getDireccionFisica() {
        return direccionFisica;
    }

    public void setDireccionFisica(String direccionFisica) {
        this.direccionFisica = direccionFisica;
    }

    public String getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(String coordenadas) {
        this.coordenadas = coordenadas;
    }

    public Character getVivienda() {
        return vivienda;
    }

    public void setVivienda(Character vivienda) {
        this.vivienda = vivienda;
    }

    public Character getAcceso() {
        return acceso;
    }

    public void setAcceso(Character acceso) {
        this.acceso = acceso;
    }

    public Character getAgua() {
        return agua;
    }

    public void setAgua(Character agua) {
        this.agua = agua;
    }

    public Character getLuz() {
        return luz;
    }

    public void setLuz(Character luz) {
        this.luz = luz;
    }

    public Character getDrenaje() {
        return drenaje;
    }

    public void setDrenaje(Character drenaje) {
        this.drenaje = drenaje;
    }

    public String getNumeroCatastral() {
        return numeroCatastral;
    }

    public void setNumeroCatastral(String numeroCatastral) {
        this.numeroCatastral = numeroCatastral;
    }

    public String getResidencial() {
        return residencial;
    }

    public void setResidencial(String residencial) {
        this.residencial = residencial;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public List<AvColindante> getAvColindanteList() {
        return avColindanteList;
    }

    public void setAvColindanteList(List<AvColindante> avColindanteList) {
        this.avColindanteList = avColindanteList;
    }

    public List<AvArea> getAvAreaList() {
        return avAreaList;
    }

    public void setAvAreaList(List<AvArea> avAreaList) {
        this.avAreaList = avAreaList;
    }

    public List<AvSolicitud> getAvSolicitudList() {
        return avSolicitudList;
    }

    public void setAvSolicitudList(List<AvSolicitud> avSolicitudList) {
        this.avSolicitudList = avSolicitudList;
    }

    public List<AvAvaluo> getAvAvaluoList() {
        return avAvaluoList;
    }

    public void setAvAvaluoList(List<AvAvaluo> avAvaluoList) {
        this.avAvaluoList = avAvaluoList;
    }

    public AvDocumento getDocumentoId() {
        return documentoId;
    }

    public void setDocumentoId(AvDocumento documentoId) {
        this.documentoId = documentoId;
    }

    public AvPropietario getPropietarioDpi() {
        return propietarioDpi;
    }

    public void setPropietarioDpi(AvPropietario propietarioDpi) {
        this.propietarioDpi = propietarioDpi;
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
        if (!(object instanceof AvInmueble)) {
            return false;
        }
        AvInmueble other = (AvInmueble) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dao.AvInmueble[ id=" + id + " ]";
    }
    
}
