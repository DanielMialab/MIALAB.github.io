package edu.ec.istdab.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.RowEditEvent;

import edu.ec.istdab.model.Rol;
import edu.ec.istdab.service.IRolService;

@Named
@RequestScoped
public class RolBean implements Serializable{
	@Inject
	private IRolService service;
	
	private List<Rol> lista;

	public List<Rol> getLista() {
		return lista;
	}

	public void setLista(List<Rol> lista) {
		this.lista = lista;
	}

	@PostConstruct
	public void init() {
		this.listar();
	}
	
	public void listar() {
		try {
			this.lista = this.service.listar();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void onRowEdit(RowEditEvent event) {
		try {
			this.service.modificar((Rol)event.getObject());
			FacesMessage msg = new FacesMessage("Rol editado", ((Rol)event.getObject()).getTipo());
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			// TODO: handle exception
		}
        
    }
		
}


