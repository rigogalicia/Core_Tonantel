/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author r29galicia
 */
@Entity
@Table(name = "av_avaluo")
@NamedQueries({
    @NamedQuery(name = "AvAvaluo.findAll", query = "SELECT a FROM AvAvaluo a")})
public class AvAvaluo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "fechahora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechahora;
    @Basic(optional = false)
    @Column(name = "valor_bancario")
    private int valorBancario;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "valor_redondeado")
    private BigDecimal valorRedondeado;
    @JoinColumn(name = "asignacion_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private AvAsignacion asignacionId;
    @JoinColumn(name = "inmueble_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private AvInmueble inmuebleId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "avaluoId")
    private List<AvDetalle> avDetalleList;

    public AvAvaluo() {
    }

    public AvAvaluo(Integer id) {
        this.id = id;
    }

    public AvAvaluo(Integer id, Date fechahora, int valorBancario, BigDecimal valorRedondeado) {
        this.id = id;
        this.fechahora = fechahora;
        this.valorBancario = valorBancario;
        this.valorRedondeado = valorRedondeado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechahora() {
        return fechahora;
    }

    public void setFechahora(Date fechahora) {
        this.fechahora = fechahora;
    }

    public int getValorBancario() {
        return valorBancario;
    }

    public void setValorBancario(int valorBancario) {
        this.valorBancario = valorBancario;
    }

    public BigDecimal getValorRedondeado() {
        return valorRedondeado;
    }

    public void setValorRedondeado(BigDecimal valorRedondeado) {
        this.valorRedondeado = valorRedondeado;
    }

    public AvAsignacion getAsignacionId() {
        return asignacionId;
    }

    public void setAsignacionId(AvAsignacion asignacionId) {
        this.asignacionId = asignacionId;
    }

    public AvInmueble getInmuebleId() {
        return inmuebleId;
    }

    public void setInmuebleId(AvInmueble inmuebleId) {
        this.inmuebleId = inmuebleId;
    }

    public List<AvDetalle> getAvDetalleList() {
        return avDetalleList;
    }

    public void setAvDetalleList(List<AvDetalle> avDetalleList) {
        this.avDetalleList = avDetalleList;
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
        if (!(object instanceof AvAvaluo)) {
            return false;
        }
        AvAvaluo other = (AvAvaluo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dao.AvAvaluo[ id=" + id + " ]";
    }
    
}
