/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * @author r29galicia
 */
@Entity
@Table(name = "ptm_activo")
@NamedQueries({
    @NamedQuery(name = "PtmActivo.findAll", query = "SELECT p FROM PtmActivo p")})
public class PtmActivo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idactivo")
    private Integer idactivo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "caja")
    private BigDecimal caja;
    @Basic(optional = false)
    @Column(name = "cuenta_ahorro_bancos")
    private BigDecimal cuentaAhorroBancos;
    @Basic(optional = false)
    @Column(name = "cuenta_ahorro_tonantel")
    private BigDecimal cuentaAhorroTonantel;
    @Basic(optional = false)
    @Column(name = "cuenta_aportaciones_tonantel")
    private BigDecimal cuentaAportacionesTonantel;
    @Basic(optional = false)
    @Column(name = "cuenta_cheques_bancos")
    private BigDecimal cuentaChequesBancos;
    @Basic(optional = false)
    @Column(name = "cuenta_fondo_retiro")
    private BigDecimal cuentaFondoRetiro;
    @Basic(optional = false)
    @Column(name = "cuenta_plazo_fijo_tonantel")
    private BigDecimal cuentaPlazoFijoTonantel;
    @Basic(optional = false)
    @Column(name = "cuenta_por_cobrar")
    private BigDecimal cuentaPorCobrar;
    @Basic(optional = false)
    @Column(name = "cultivos")
    private BigDecimal cultivos;
    @Basic(optional = false)
    @Column(name = "mipromesa")
    private BigDecimal mipromesa;
    @Basic(optional = false)
    @Column(name = "ganado")
    private BigDecimal ganado;
    @Basic(optional = false)
    @Column(name = "maquinaria_equipo")
    private BigDecimal maquinariaEquipo;
    @Basic(optional = false)
    @Column(name = "materia_prima")
    private BigDecimal materiaPrima;
    @Basic(optional = false)
    @Column(name = "menaje_cristaleria")
    private BigDecimal menajeCristaleria;
    @Basic(optional = false)
    @Column(name = "mercaderia")
    private BigDecimal mercaderia;
    @Basic(optional = false)
    @Column(name = "mobiliario")
    private BigDecimal mobiliario;
    @Basic(optional = false)
    @Column(name = "otros_activos")
    private BigDecimal otrosActivos;
    @Basic(optional = false)
    @Column(name = "terrenos")
    private BigDecimal terrenos;
    @Basic(optional = false)
    @Column(name = "vehiculos")
    private BigDecimal vehiculos;
    @Basic(optional = false)
    @Column(name = "vivienda")
    private BigDecimal vivienda;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ptmActivoIdactivo")
    private List<PtmEstadopatrimonial> ptmEstadopatrimonialList;

    public PtmActivo() {
    }

    public PtmActivo(Integer idactivo) {
        this.idactivo = idactivo;
    }

    public PtmActivo(Integer idactivo, BigDecimal caja, BigDecimal cuentaAhorroBancos, BigDecimal cuentaAhorroTonantel, BigDecimal cuentaAportacionesTonantel, BigDecimal cuentaChequesBancos, BigDecimal cuentaFondoRetiro, BigDecimal cuentaPlazoFijoTonantel, BigDecimal cuentaPorCobrar, BigDecimal cultivos, BigDecimal mipromesa, BigDecimal ganado, BigDecimal maquinariaEquipo, BigDecimal materiaPrima, BigDecimal menajeCristaleria, BigDecimal mercaderia, BigDecimal mobiliario, BigDecimal otrosActivos, BigDecimal terrenos, BigDecimal vehiculos, BigDecimal vivienda) {
        this.idactivo = idactivo;
        this.caja = caja;
        this.cuentaAhorroBancos = cuentaAhorroBancos;
        this.cuentaAhorroTonantel = cuentaAhorroTonantel;
        this.cuentaAportacionesTonantel = cuentaAportacionesTonantel;
        this.cuentaChequesBancos = cuentaChequesBancos;
        this.cuentaFondoRetiro = cuentaFondoRetiro;
        this.cuentaPlazoFijoTonantel = cuentaPlazoFijoTonantel;
        this.cuentaPorCobrar = cuentaPorCobrar;
        this.cultivos = cultivos;
        this.mipromesa = mipromesa;
        this.ganado = ganado;
        this.maquinariaEquipo = maquinariaEquipo;
        this.materiaPrima = materiaPrima;
        this.menajeCristaleria = menajeCristaleria;
        this.mercaderia = mercaderia;
        this.mobiliario = mobiliario;
        this.otrosActivos = otrosActivos;
        this.terrenos = terrenos;
        this.vehiculos = vehiculos;
        this.vivienda = vivienda;
    }

    public Integer getIdactivo() {
        return idactivo;
    }

    public void setIdactivo(Integer idactivo) {
        this.idactivo = idactivo;
    }

    public BigDecimal getCaja() {
        return caja;
    }

    public void setCaja(BigDecimal caja) {
        this.caja = caja;
    }

    public BigDecimal getCuentaAhorroBancos() {
        return cuentaAhorroBancos;
    }

    public void setCuentaAhorroBancos(BigDecimal cuentaAhorroBancos) {
        this.cuentaAhorroBancos = cuentaAhorroBancos;
    }

    public BigDecimal getCuentaAhorroTonantel() {
        return cuentaAhorroTonantel;
    }

    public void setCuentaAhorroTonantel(BigDecimal cuentaAhorroTonantel) {
        this.cuentaAhorroTonantel = cuentaAhorroTonantel;
    }

    public BigDecimal getCuentaAportacionesTonantel() {
        return cuentaAportacionesTonantel;
    }

    public void setCuentaAportacionesTonantel(BigDecimal cuentaAportacionesTonantel) {
        this.cuentaAportacionesTonantel = cuentaAportacionesTonantel;
    }

    public BigDecimal getCuentaChequesBancos() {
        return cuentaChequesBancos;
    }

    public void setCuentaChequesBancos(BigDecimal cuentaChequesBancos) {
        this.cuentaChequesBancos = cuentaChequesBancos;
    }

    public BigDecimal getCuentaFondoRetiro() {
        return cuentaFondoRetiro;
    }

    public void setCuentaFondoRetiro(BigDecimal cuentaFondoRetiro) {
        this.cuentaFondoRetiro = cuentaFondoRetiro;
    }

    public BigDecimal getCuentaPlazoFijoTonantel() {
        return cuentaPlazoFijoTonantel;
    }

    public void setCuentaPlazoFijoTonantel(BigDecimal cuentaPlazoFijoTonantel) {
        this.cuentaPlazoFijoTonantel = cuentaPlazoFijoTonantel;
    }

    public BigDecimal getCuentaPorCobrar() {
        return cuentaPorCobrar;
    }

    public void setCuentaPorCobrar(BigDecimal cuentaPorCobrar) {
        this.cuentaPorCobrar = cuentaPorCobrar;
    }

    public BigDecimal getCultivos() {
        return cultivos;
    }

    public void setCultivos(BigDecimal cultivos) {
        this.cultivos = cultivos;
    }

    public BigDecimal getMipromesa() {
        return mipromesa;
    }

    public void setMipromesa(BigDecimal mipromesa) {
        this.mipromesa = mipromesa;
    }

    public BigDecimal getGanado() {
        return ganado;
    }

    public void setGanado(BigDecimal ganado) {
        this.ganado = ganado;
    }

    public BigDecimal getMaquinariaEquipo() {
        return maquinariaEquipo;
    }

    public void setMaquinariaEquipo(BigDecimal maquinariaEquipo) {
        this.maquinariaEquipo = maquinariaEquipo;
    }

    public BigDecimal getMateriaPrima() {
        return materiaPrima;
    }

    public void setMateriaPrima(BigDecimal materiaPrima) {
        this.materiaPrima = materiaPrima;
    }

    public BigDecimal getMenajeCristaleria() {
        return menajeCristaleria;
    }

    public void setMenajeCristaleria(BigDecimal menajeCristaleria) {
        this.menajeCristaleria = menajeCristaleria;
    }

    public BigDecimal getMercaderia() {
        return mercaderia;
    }

    public void setMercaderia(BigDecimal mercaderia) {
        this.mercaderia = mercaderia;
    }

    public BigDecimal getMobiliario() {
        return mobiliario;
    }

    public void setMobiliario(BigDecimal mobiliario) {
        this.mobiliario = mobiliario;
    }

    public BigDecimal getOtrosActivos() {
        return otrosActivos;
    }

    public void setOtrosActivos(BigDecimal otrosActivos) {
        this.otrosActivos = otrosActivos;
    }

    public BigDecimal getTerrenos() {
        return terrenos;
    }

    public void setTerrenos(BigDecimal terrenos) {
        this.terrenos = terrenos;
    }

    public BigDecimal getVehiculos() {
        return vehiculos;
    }

    public void setVehiculos(BigDecimal vehiculos) {
        this.vehiculos = vehiculos;
    }

    public BigDecimal getVivienda() {
        return vivienda;
    }

    public void setVivienda(BigDecimal vivienda) {
        this.vivienda = vivienda;
    }

    public List<PtmEstadopatrimonial> getPtmEstadopatrimonialList() {
        return ptmEstadopatrimonialList;
    }

    public void setPtmEstadopatrimonialList(List<PtmEstadopatrimonial> ptmEstadopatrimonialList) {
        this.ptmEstadopatrimonialList = ptmEstadopatrimonialList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idactivo != null ? idactivo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PtmActivo)) {
            return false;
        }
        PtmActivo other = (PtmActivo) object;
        if ((this.idactivo == null && other.idactivo != null) || (this.idactivo != null && !this.idactivo.equals(other.idactivo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dao.PtmActivo[ idactivo=" + idactivo + " ]";
    }
    
}
