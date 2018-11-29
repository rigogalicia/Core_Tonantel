/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import javax.persistence.Basic;
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
 * @author Desarrollo
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
    @Column(name = "caja")
    private BigInteger caja;
    @Column(name = "cuenta_ahorro_bancos")
    private BigInteger cuentaAhorroBancos;
    @Column(name = "cuenta_ahorro_tonantel")
    private BigInteger cuentaAhorroTonantel;
    @Column(name = "cuenta_aportaciones_tonantel")
    private BigInteger cuentaAportacionesTonantel;
    @Column(name = "cuenta_cheques_bancos")
    private BigInteger cuentaChequesBancos;
    @Column(name = "cuenta_fondo_retiro")
    private BigInteger cuentaFondoRetiro;
    @Column(name = "cuenta_plazo_fijo_tonantel")
    private BigInteger cuentaPlazoFijoTonantel;
    @Column(name = "cuenta_por_cobrar")
    private BigInteger cuentaPorCobrar;
    @Column(name = "cultivos")
    private BigInteger cultivos;
    @Column(name = "ganado")
    private BigInteger ganado;
    @Column(name = "maquinaria_equipo")
    private BigInteger maquinariaEquipo;
    @Column(name = "materia_prima")
    private BigInteger materiaPrima;
    @Column(name = "menaje_cristaleria")
    private BigInteger menajeCristaleria;
    @Column(name = "mercaderia")
    private BigInteger mercaderia;
    @Column(name = "mipromesa")
    private BigInteger mipromesa;
    @Column(name = "mobiliario")
    private BigInteger mobiliario;
    @Column(name = "otros_activos")
    private BigInteger otrosActivos;
    @Column(name = "terrenos")
    private BigInteger terrenos;
    @Column(name = "vehiculos")
    private BigInteger vehiculos;
    @Column(name = "vivienda")
    private BigInteger vivienda;
    @OneToMany(mappedBy = "ptmActivoIdactivo")
    private List<PtmEstadopatrimonial> ptmEstadopatrimonialList;

    public PtmActivo() {
    }

    public PtmActivo(Integer idactivo) {
        this.idactivo = idactivo;
    }

    public Integer getIdactivo() {
        return idactivo;
    }

    public void setIdactivo(Integer idactivo) {
        this.idactivo = idactivo;
    }

    public BigInteger getCaja() {
        return caja;
    }

    public void setCaja(BigInteger caja) {
        this.caja = caja;
    }

    public BigInteger getCuentaAhorroBancos() {
        return cuentaAhorroBancos;
    }

    public void setCuentaAhorroBancos(BigInteger cuentaAhorroBancos) {
        this.cuentaAhorroBancos = cuentaAhorroBancos;
    }

    public BigInteger getCuentaAhorroTonantel() {
        return cuentaAhorroTonantel;
    }

    public void setCuentaAhorroTonantel(BigInteger cuentaAhorroTonantel) {
        this.cuentaAhorroTonantel = cuentaAhorroTonantel;
    }

    public BigInteger getCuentaAportacionesTonantel() {
        return cuentaAportacionesTonantel;
    }

    public void setCuentaAportacionesTonantel(BigInteger cuentaAportacionesTonantel) {
        this.cuentaAportacionesTonantel = cuentaAportacionesTonantel;
    }

    public BigInteger getCuentaChequesBancos() {
        return cuentaChequesBancos;
    }

    public void setCuentaChequesBancos(BigInteger cuentaChequesBancos) {
        this.cuentaChequesBancos = cuentaChequesBancos;
    }

    public BigInteger getCuentaFondoRetiro() {
        return cuentaFondoRetiro;
    }

    public void setCuentaFondoRetiro(BigInteger cuentaFondoRetiro) {
        this.cuentaFondoRetiro = cuentaFondoRetiro;
    }

    public BigInteger getCuentaPlazoFijoTonantel() {
        return cuentaPlazoFijoTonantel;
    }

    public void setCuentaPlazoFijoTonantel(BigInteger cuentaPlazoFijoTonantel) {
        this.cuentaPlazoFijoTonantel = cuentaPlazoFijoTonantel;
    }

    public BigInteger getCuentaPorCobrar() {
        return cuentaPorCobrar;
    }

    public void setCuentaPorCobrar(BigInteger cuentaPorCobrar) {
        this.cuentaPorCobrar = cuentaPorCobrar;
    }

    public BigInteger getCultivos() {
        return cultivos;
    }

    public void setCultivos(BigInteger cultivos) {
        this.cultivos = cultivos;
    }

    public BigInteger getGanado() {
        return ganado;
    }

    public void setGanado(BigInteger ganado) {
        this.ganado = ganado;
    }

    public BigInteger getMaquinariaEquipo() {
        return maquinariaEquipo;
    }

    public void setMaquinariaEquipo(BigInteger maquinariaEquipo) {
        this.maquinariaEquipo = maquinariaEquipo;
    }

    public BigInteger getMateriaPrima() {
        return materiaPrima;
    }

    public void setMateriaPrima(BigInteger materiaPrima) {
        this.materiaPrima = materiaPrima;
    }

    public BigInteger getMenajeCristaleria() {
        return menajeCristaleria;
    }

    public void setMenajeCristaleria(BigInteger menajeCristaleria) {
        this.menajeCristaleria = menajeCristaleria;
    }

    public BigInteger getMercaderia() {
        return mercaderia;
    }

    public void setMercaderia(BigInteger mercaderia) {
        this.mercaderia = mercaderia;
    }

    public BigInteger getMipromesa() {
        return mipromesa;
    }

    public void setMipromesa(BigInteger mipromesa) {
        this.mipromesa = mipromesa;
    }

    public BigInteger getMobiliario() {
        return mobiliario;
    }

    public void setMobiliario(BigInteger mobiliario) {
        this.mobiliario = mobiliario;
    }

    public BigInteger getOtrosActivos() {
        return otrosActivos;
    }

    public void setOtrosActivos(BigInteger otrosActivos) {
        this.otrosActivos = otrosActivos;
    }

    public BigInteger getTerrenos() {
        return terrenos;
    }

    public void setTerrenos(BigInteger terrenos) {
        this.terrenos = terrenos;
    }

    public BigInteger getVehiculos() {
        return vehiculos;
    }

    public void setVehiculos(BigInteger vehiculos) {
        this.vehiculos = vehiculos;
    }

    public BigInteger getVivienda() {
        return vivienda;
    }

    public void setVivienda(BigInteger vivienda) {
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
