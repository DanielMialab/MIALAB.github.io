package edu.ec.istdab.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import edu.ec.istdab.model.Cliente;
import edu.ec.istdab.model.Factura;
import edu.ec.istdab.model.Usuario;
import edu.ec.istdab.service.IUsuarioService;

@Named
@ViewScoped
public class IndexBean implements Serializable {

	private Usuario us;

	@Inject
	private IUsuarioService service;

	@Inject

	private FacturaBean facturaService;

	@PostConstruct
	public void init() {
		this.us = new Usuario();
	}

	public String login() {
		String redireccion = "";
		try {
			Usuario usuario = service.login(us);
			if (usuario != null && usuario.getEstado().equalsIgnoreCase("A")) {
				// FacesContext.getCurrentInstance().addMessage(null, new
				// FacesMessage(FacesMessage.SEVERITY_INFO,"Aviso","CORRECTO........"));
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", usuario);
				redireccion = "/protegido/principal?faces-redirect=true";
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "CREDENCIALES INCORRECTAS..."));
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL, "Aviso", e.getMessage()));
		}
		return redireccion;
	}

	public Usuario obtenerUsuarioLogueado() {
		Usuario loginUser = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("usuario");
		System.out.println("--------------------------------------------->" + loginUser.getRole());
		return loginUser;
	}

	public List<Factura> obtenerFaturasClienteLogueado(){
		
		
		System.out.println("------------------------------------->ingresando");
		Usuario logUser=(Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
		Cliente cliente=logUser.getCliente();
		return facturaService.obtenerFaturasClienteLogueado(cliente);
		
		
	}

	public boolean showManager() {

		System.out.println("----------------------------revisa la validacion------------------->"
				+ obtenerUsuarioLogueado().getRole().contains("admin"));

		if (obtenerUsuarioLogueado().getRole().contains("admin")) {
			System.out.println("-------------------------------->siiiiiiiiiiiiiiiiiiiiiiiiiiiiii miuestrfo"
					+ obtenerUsuarioLogueado().getRole());
			return true;

		} else {

			System.out.println(
					"-------------------------------->NO MUESTRO Ni SHIT" + obtenerUsuarioLogueado().getRole());
			return false;
		}

	}

	public Usuario getUs() {
		return us;
	}

	public void setUs(Usuario us) {
		this.us = us;
	}

}
