package edu.ec.istdab.controller;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import edu.ec.istdab.model.Usuario;

@Named
@ViewScoped
public class MasterBean implements Serializable{

	public void verificarSesion() throws Exception{
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			Usuario us= (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
			if (us==null) {
				//redirigir al login
				context.getExternalContext().redirect("./../index.xhtml");
			}
		} catch (Exception e) {
			context.getExternalContext().redirect("./../index.xhtml");
		}
	}
	
	public void cerrarSesion() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	}
}
