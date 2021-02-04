package edu.ec.istdab.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.DualListModel;

import edu.ec.istdab.model.Cliente;
import edu.ec.istdab.model.Rol;
import edu.ec.istdab.model.Usuario;
import edu.ec.istdab.service.IClienteService;
import edu.ec.istdab.service.IRolService;

@Named
@ViewScoped
public class AsignarBean implements Serializable{
	
	private List<Cliente> clientes;
	private Cliente cliente;
	private DualListModel<Rol> dual;
	
	@Inject
	private IClienteService serviceCliente;
	
	@Inject
	private IRolService serviceRol;
	
	@PostConstruct
	public void init() {
		this.listarClientes();
		this.listarRoles();
	}
	
	public void listarClientes() {
		try {
			this.clientes = this.serviceCliente.listar();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void listarRoles() {
		try {
			List<Rol> roles = this.serviceRol.listar();
			this.dual=new DualListModel<Rol>(roles, new ArrayList<Rol>());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void asignar() {
		try {
			Usuario us = new Usuario();
			us.setIdUsuario(this.cliente.getIdCliente());
			us.setCliente(this.cliente);
			serviceRol.asignar(us, this.dual.getTarget());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	
	
	
	
	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public DualListModel<Rol> getDual() {
		return dual;
	}

	public void setDual(DualListModel<Rol> dual) {
		this.dual = dual;
	}
	
	
}
