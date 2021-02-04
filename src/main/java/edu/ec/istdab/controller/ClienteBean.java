package edu.ec.istdab.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import edu.ec.istdab.model.Cliente;
import edu.ec.istdab.model.Rol;
import edu.ec.istdab.model.Usuario;
import edu.ec.istdab.model.UsuarioRol;
import edu.ec.istdab.service.IClienteService;
import edu.ec.istdab.service.IRolService;
import edu.ec.istdab.service.IUsuarioService;

@Named
@ViewScoped
public class ClienteBean implements Serializable {

	@Inject
	private IClienteService service;

	@Inject
	private UsuariosBean usuarioBean;

	private Cliente cliente;
	private List<Cliente> lista;
	private String tipoDialogo;
	private Usuario us;
	private UsuarioRol usRol;

	/*
	 * public PersonaBean() { this.persona = new Persona(); //this.service = new
	 * PersonaServiceImpl(); this.listar(); }
	 */

	@PostConstruct
	public void init() {
		FacesContext context = FacesContext.getCurrentInstance();
		this.us = (Usuario) context.getExternalContext().getSessionMap().get("usuario");
		this.cliente = new Cliente();
		this.listar();
	}

	public void operar(String accion) {
//		System.out.println("999999999999999999999999999999999pase por aqui tratando de crear usuario >>>>>"+accion+cliente.getUsu().getUsuario());
		try {
			if (accion.equalsIgnoreCase("R")) {

				try {
//					String clave=this.us.getClave();
//					String claveHash = BCrypt.hashpw(clave, BCrypt.gensalt());
//					this.us.setClave(claveHash);
//					this.us.setCliente(cliente);

					usuarioBean.getUs().setEstado("A");
					usuarioBean.setCliente(this.getCliente());
					this.getCliente().setUsu(usuarioBean.getUs());
					this.getCliente().getUsu().setCliente(cliente);
					System.out.println("00000000000000000000000000000000000000000000000000000>"+cliente.getUsu().getClave());
					this.service.registrar(cliente);

//					List<Rol> roles = new ArrayList<>();
//					Rol r = new Rol();
//					r.setIdrol(3);
//					roles.add(r);
//
//					rolService.asignar(this.us, roles);
				} catch (Exception e) {

				}
				// this.service.registrar(cliente);
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "CLiente Registrada", "Aviso"));
			} else if (accion.equalsIgnoreCase("M")) {
				this.service.modificar(cliente);
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Modificado"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void mostrarData(Cliente c) {
		
		this.cliente = c;
		System.out.println("=======================cliente cargado=====================================>"+this.getCliente().getNombres()+"======================>"+this.getCliente().getUsu().getUsuario());
		this.usuarioBean.setUs(this.cliente.getUsu());
		System.out.println("========================cargado usuario=================================>"+this.usuarioBean.getUs().getUsuario());
		this.tipoDialogo = "Modificar Cliente";

	}

	public void limpiarControles() {
		this.cliente = new Cliente();
		this.tipoDialogo = "Nuevo Cliente";
	}

	public void listar() {
		try {
			this.lista = this.service.listar();
			this.lista.remove(this.us.getCliente());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public IClienteService getService() {
		return service;
	}

	public void setService(IClienteService service) {
		this.service = service;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Cliente> getLista() {
		return lista;
	}

	public void setLista(List<Cliente> lista) {
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

	public UsuarioRol getUsRol() {
		return usRol;
	}

	public void setUsRol(UsuarioRol usRol) {
		this.usRol = usRol;
	}

}
