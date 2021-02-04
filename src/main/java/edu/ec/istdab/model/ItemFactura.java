package edu.ec.istdab.model;

import java.io.Serializable;
import java.lang.Long;
import java.util.Date;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: ItemFactura
 *
 */
@Entity

public class ItemFactura implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	private Factura facturaContenedora;

	@OneToOne
	private Examen examenVinculado;

	@Temporal(TemporalType.DATE)
	private Date fechaFacturacion;
	private Double catindad;
	private Double precioUnitarioActual;
	private Double precioTotalItem;

	public ItemFactura() {
		super();
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Factura getFacturaContenedora() {
		return facturaContenedora;
	}

	public void setFacturaContenedora(Factura facturaContenedora) {
		this.facturaContenedora = facturaContenedora;
	}

	public Examen getExamenVinculado() {
		return examenVinculado;
	}

	public void setExamenVinculado(Examen examenVinculado) {
		this.examenVinculado = examenVinculado;
	}

	public Date getFechaFacturacion() {
		return fechaFacturacion;
	}

	public void setFechaFacturacion(Date fechaFacturacion) {
		this.fechaFacturacion = fechaFacturacion;
	}

	public Double getCatindad() {
		return catindad;
	}

	public void setCatindad(Double catindad) {
		this.catindad = catindad;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((catindad == null) ? 0 : catindad.hashCode());
		result = prime * result + ((examenVinculado == null) ? 0 : examenVinculado.hashCode());
		result = prime * result + ((facturaContenedora == null) ? 0 : facturaContenedora.hashCode());
		result = prime * result + ((fechaFacturacion == null) ? 0 : fechaFacturacion.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		ItemFactura other = (ItemFactura) obj;

		if (this.getExamenVinculado().equals(other.getExamenVinculado())) {
			return true;
		}

		if (catindad == null) {
			if (other.catindad != null)
				return false;
		} else if (!catindad.equals(other.catindad))
			return false;
		if (examenVinculado == null) {
			if (other.examenVinculado != null)
				return false;
		} else if (!examenVinculado.equals(other.examenVinculado))
			return false;
		if (facturaContenedora == null) {
			if (other.facturaContenedora != null)
				return false;
		} else if (!facturaContenedora.equals(other.facturaContenedora))
			return false;
		if (fechaFacturacion == null) {
			if (other.fechaFacturacion != null)
				return false;
		} else if (!fechaFacturacion.equals(other.fechaFacturacion))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return this.getExamenVinculado().getNombres();
	}

	public Double getPrecioUnitarioActual() {
		return precioUnitarioActual;
	}

	public void setPrecioUnitarioActual(Double precioUnitarioActual) {
		this.precioUnitarioActual = precioUnitarioActual;
	}

	public Double getPrecioTotalItem() {
		return precioTotalItem;
	}

	public void setPrecioTotalItem(Double precioTotalItem) {
		this.precioTotalItem = precioTotalItem;
	}

}
