package edu.ec.istdab.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import edu.ec.istdab.model.Usuario;

@Named
@ViewScoped
public class PrincipalBean implements Serializable{
	
	@Inject
	private Usuario us;
	
	@PostConstruct
	public void init() {
		FacesContext context = FacesContext.getCurrentInstance();
		this.us = (Usuario)context.getExternalContext().getSessionMap().get("usuario");
	}	
}
