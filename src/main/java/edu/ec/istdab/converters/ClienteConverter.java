package edu.ec.istdab.converters;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import edu.ec.istdab.model.Cliente;

@FacesConverter("clienteConverter")
public class ClienteConverter implements Converter, Serializable {

	public Cliente selectedCliente;

	@Override
	public Object getAsObject(FacesContext fc, UIComponent uic, String string) {

		if (string.trim().equals("")) {
			return null;
		} else {

			Cliente p = this.getSelectedCliente();

			return p;
		}
	}

	@Override
	public String getAsString(FacesContext fc, UIComponent uic, Object o) {
		if (o == null || o.equals("")) {
			return "";
		}
		if (!(o instanceof Cliente)) {
			return "";
		}

		this.setSelectedCliente((Cliente) o);
		String dni = String.valueOf(((Cliente) o).getNombres() + " " + String.valueOf(((Cliente) o).getApellidos()));
		return (dni != null) ? dni : null;
	}

	public Cliente getSelectedCliente() {
		return selectedCliente;
	}

	public void setSelectedCliente(Cliente selectedCliente) {
		this.selectedCliente = selectedCliente;
	}

}