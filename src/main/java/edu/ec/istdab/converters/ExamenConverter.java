package edu.ec.istdab.converters;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import edu.ec.istdab.model.Examen;

@FacesConverter("examenConverter")
public class ExamenConverter implements Converter, Serializable {


	public Examen selectedExamen;

	@Override
	public Object getAsObject(FacesContext fc, UIComponent uic, String string) {

		if (string.trim().equals("")) {
			return null;
		} else {
			
			System.out.println("pppppppppppppppppppppp"+this.getSelectedExamen());

			Examen p = this.getSelectedExamen();

			return p;
		}
	}

	@Override
	public String getAsString(FacesContext fc, UIComponent uic, Object o) {
		if (o == null || o.equals("")) {
			return "";
		}
		if (!(o instanceof Examen)) {
			return "";
		}

		this.setSelectedExamen((Examen) o);
		String dni = String.valueOf(((Examen) o).getNombres());
		return (dni != null) ? dni : null;
	}

	public Examen getSelectedExamen() {
		return selectedExamen;
	}

	public void setSelectedExamen(Examen selectedExamen) {
		this.selectedExamen = selectedExamen;
	}

}