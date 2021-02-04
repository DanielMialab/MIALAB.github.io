package edu.ec.istdab.util;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class MensajeManager implements Serializable{

	public void mostrarMensaje(String titulo, String cuerpo, String severidad) {
		switch (severidad) {
		case "INFO":
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, titulo, cuerpo));
			break;

		case "WARN":
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, titulo, cuerpo));
			break;
			
		case "ERROR":
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, titulo, cuerpo));
			break;
			
		case "FATAL":
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, titulo, cuerpo));
			break;
		}
	}
}
