package edu.ec.istdab.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.transaction.Transactional;

import org.mindrot.jbcrypt.BCrypt;

import edu.ec.istdab.model.Cliente;
import edu.ec.istdab.model.Rol;
import edu.ec.istdab.model.Usuario;
import edu.ec.istdab.model.UsuarioRol;

@Named
@ViewScoped
public class InicioBean implements Serializable{

	private Usuario usuario;
	private Rol rol;
	private Cliente cliente;
	private UsuarioRol usuarioRol;
	
	@PostConstruct
	private void init() {
		this.usuario=new Usuario();
	}
	
	@Transactional
	public void admin() {
		String clave="admin";
		String claveHash = BCrypt.hashpw(clave, BCrypt.gensalt());
		this.usuario.setClave(claveHash);
		Usuario usuario = new Usuario();
		usuario.setEstado("A");
		usuario.setIdUsuario(1);
		usuario.setUsuario("admin");	
		
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	
	
}
