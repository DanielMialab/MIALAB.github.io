package edu.ec.istdab.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import org.mindrot.jbcrypt.BCrypt;

import edu.ec.istdab.model.Cliente;
import edu.ec.istdab.model.Rol;
import edu.ec.istdab.model.Usuario;
import edu.ec.istdab.service.IClienteService;
import edu.ec.istdab.service.IRolService;

@Named
@ViewScoped
public class RegistroBean implements Serializable{

	@Inject
	private IClienteService clienteService;
	
	@Inject
	private IRolService rolService;
	
	private Cliente cliente;
	private Usuario usuario;
	
	@PostConstruct
	private void init() {
		this.cliente=new Cliente();
		this.usuario=new Usuario();
	}
	
	//metodos
	@Transactional
	public String registrar() {
		String redireccion="";
		try {
			String clave=this.usuario.getClave();
			String claveHash = BCrypt.hashpw(clave, BCrypt.gensalt());
			this.usuario.setClave(claveHash);
			this.usuario.setCliente(cliente);
			this.cliente.setUsu(usuario);
			this.clienteService.registrar(cliente);
			
			List<Rol> roles = new ArrayList<>();
			Rol r = new Rol();
			r.setIdrol(1);
			roles.add(r);
			
			rolService.asignar(this.usuario, roles);
			redireccion="index?faces-redirect=true";
		} catch (Exception e) {
			
		}
		return redireccion;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	
}
