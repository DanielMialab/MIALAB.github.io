package edu.ec.istdab.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import edu.ec.istdab.model.Examen;
import edu.ec.istdab.model.Usuario;
import edu.ec.istdab.service.IExamenService;

@Named
@ViewScoped
public class ExamenBean implements Serializable {

	@Inject
	private IExamenService service;

	private Examen examen;
	private List<Examen> lista;
	private String tipoDialogo;
	private Usuario us;

	@PostConstruct
	public void init() {
		FacesContext context = FacesContext.getCurrentInstance();
		this.us = (Usuario) context.getExternalContext().getSessionMap().get("usuario");
		this.examen = new Examen();
	}

	public void operar(String accion) {
		try {
			if (accion.equalsIgnoreCase("R")) {
				this.service.registrar(examen);
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Examen Registrado", "Aviso"));
			} else if (accion.equalsIgnoreCase("M")) {
				this.service.modificar(examen);
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Modificado"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Examen> listar() {
		try {

			return this.service.listar();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void mostrarData(Examen e) {
		this.examen = e;
		this.tipoDialogo = "Modificar Examen";
	}

	public void limpiarControles() {
		this.examen = new Examen();
		this.tipoDialogo = "Nuevo Examen";
	}

	public IExamenService getService() {
		return service;
	}

	public void setService(IExamenService service) {
		this.service = service;
	}

	public Examen getExamen() {
		return examen;
	}

	public void setExamen(Examen examen) {
		this.examen = examen;
	}

	public List<Examen> getLista() {
		return lista;
	}

	public void setLista(List<Examen> lista) {
		this.lista = lista;
	}

	public String getTipoDialogo() {
		return tipoDialogo;
	}

	public void setTipoDialogo(String tipoDialogo) {
		this.tipoDialogo = tipoDialogo;
	}

	public Usuario getUs() {
		return us;
	}

	public void setUs(Usuario us) {
		this.us = us;
	}
}
