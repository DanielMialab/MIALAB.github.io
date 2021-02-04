package edu.ec.istdab.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "factura")
public class Factura implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idFactura;

	@ManyToOne
	private Cliente cliente;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "facturaContenedora",fetch = FetchType.EAGER)
	private List<ItemFactura> detalleFacturacion;

	@Column(name = "subtotal", nullable = false, length = 70)
	private double subtotal;

	@Column(name = "subtotal_iva", nullable = false, length = 70)
	private double subtotalIva;

	@Column(name = "iva", nullable = false, length = 70)
	private double iva;

	@Column(name = "total", nullable = false, length = 70)
	private double total;

	@Temporal(TemporalType.DATE)
	private Date fecha;
	
	private String estado;
	
	private String codigoEstablecimiento;
	
	private String codigoPuntoEmision;
	
	private String secuenciaComprobante;
	private Long secuencia;
	private String claveAcceso;
	
	private String rutaXml; 
	
	
	public Factura() {
		this.detalleFacturacion = new ArrayList<ItemFactura>();
		this.setSubtotal(0.0);
		this.setIva(0.0);
		this.setTotal(0.0);
	}

	public void agregarItemFactura(ItemFactura nuevoItem) {
		if (!this.getDetalleFacturacion().contains(nuevoItem)) {
			nuevoItem.setFacturaContenedora(this);
			nuevoItem.setFechaFacturacion(new Date());
			this.getDetalleFacturacion().add(nuevoItem);
		}
	}

	public List<ItemFactura> getDetalleFacturacion() {
		return detalleFacturacion;
	}

	public void setDetalleFacturacion(List<ItemFactura> detalleFacturacion) {
		this.detalleFacturacion = detalleFacturacion;
	}

	public Integer getIdFactura() {
		return idFactura;
	}

	public void setIdFactura(Integer idFactura) {
		this.idFactura = idFactura;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

	public double getSubtotalIva() {
		return subtotalIva;
	}

	public void setSubtotalIva(double subtotalIva) {
		this.subtotalIva = subtotalIva;
	}

	public double getIva() {
		return iva;
	}

	public void setIva(double iva) {
		this.iva = iva;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	
	

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCodigoEstablecimiento() {
		return codigoEstablecimiento;
	}

	public void setCodigoEstablecimiento(String codigoEstablecimiento) {
		this.codigoEstablecimiento = codigoEstablecimiento;
	}

	public String getCodigoPuntoEmision() {
		return codigoPuntoEmision;
	}

	public void setCodigoPuntoEmision(String codigoPuntoEmision) {
		this.codigoPuntoEmision = codigoPuntoEmision;
	}

	public String getSecuenciaComprobante() {
		return secuenciaComprobante;
	}

	public void setSecuenciaComprobante(String secuenciaComprobante) {
		this.secuenciaComprobante = secuenciaComprobante;
	}

	public String getClaveAcceso() {
		return claveAcceso;
	}

	public void setClaveAcceso(String claveAcceso) {
		this.claveAcceso = claveAcceso;
	}

	public String getRutaXml() {
		return rutaXml;
	}

	public void setRutaXml(String rutaXml) {
		this.rutaXml = rutaXml;
	}
	
	

	public Long getSecuencia() {
		return secuencia;
	}

	public void setSecuencia(Long secuencia) {
		this.secuencia = secuencia;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
		result = prime * result + ((detalleFacturacion == null) ? 0 : detalleFacturacion.hashCode());
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result + ((idFactura == null) ? 0 : idFactura.hashCode());
		long temp;
		temp = Double.doubleToLongBits(iva);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(subtotal);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(subtotalIva);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(total);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Factura other = (Factura) obj;
		if (cliente == null) {
			if (other.cliente != null)
				return false;
		} else if (!cliente.equals(other.cliente))
			return false;
		if (detalleFacturacion == null) {
			if (other.detalleFacturacion != null)
				return false;
		} else if (!detalleFacturacion.equals(other.detalleFacturacion))
			return false;
		if (fecha == null) {
			if (other.fecha != null)
				return false;
		} else if (!fecha.equals(other.fecha))
			return false;
		if (idFactura == null) {
			if (other.idFactura != null)
				return false;
		} else if (!idFactura.equals(other.idFactura))
			return false;
		if (Double.doubleToLongBits(iva) != Double.doubleToLongBits(other.iva))
			return false;
		if (Double.doubleToLongBits(subtotal) != Double.doubleToLongBits(other.subtotal))
			return false;
		if (Double.doubleToLongBits(subtotalIva) != Double.doubleToLongBits(other.subtotalIva))
			return false;
		if (Double.doubleToLongBits(total) != Double.doubleToLongBits(other.total))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Factura [idFactura=" + idFactura + ", cliente=" + cliente + ", detalleFacturacion=" + detalleFacturacion
				+ ", subtotal=" + subtotal + ", subtotalIva=" + subtotalIva + ", iva=" + iva + ", total=" + total
				+ ", fecha=" + fecha + "]";
	}
	
	
	
	
	

}
