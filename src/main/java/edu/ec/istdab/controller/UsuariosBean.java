package edu.ec.istdab.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.RowEditEvent;

import edu.ec.istdab.model.Cliente;
import edu.ec.istdab.model.Usuario;
import edu.ec.istdab.service.IClienteService;
import edu.ec.istdab.service.IUsuarioService;


@Named
@ViewScoped
public class UsuariosBean implements Serializable{
	@Inject
	private IUsuarioService service;
	private Usuario us;
	private String tipoDialogo;
	
	@Inject
	private IClienteService serviceCliente;
	private Cliente cliente;
	private List<Cliente> lista;
	private List<Usuario> list;
	
	
	

	
	public List<Usuario> getList() {
		return list;
	}

	public void setList(List<Usuario> list) {
		this.list = list;
	}

	public String getTipoDialogo() {
		return tipoDialogo;
	}

	public void setTipoDialogo(String tipoDialogo) {
		this.tipoDialogo = tipoDialogo;
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

	public Usuario getUs() {
		return us;
	}

	public void setUs(Usuario us) {
		this.us = us;
	}

	@PostConstruct
	public void init() {
		this.us=new Usuario();
		this.us.setRole("cliente");
		this.listar();
		/*FacesContext context = FacesContext.getCurrentInstance();
		this.us = (Usuario) context.getExternalContext().getSessionMap().get("usuario");
		this.listar();	*/
	}
	
	public void listar() {
		try {
			this.list = this.service.listar();
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*try {
			Integer id = this.us.getCliente().getIdCliente();
			Cliente cli = new Cliente();
			cli.setIdCliente(id);
			this.cliente = this.serviceCliente.listarPorId(cli);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
	
	public void operar(String accion) {
		try {
			if (accion.equalsIgnoreCase("M")) {
				
				this.serviceCliente.modificar(cliente);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario Modificado",""));
			} 
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void operarC(String accion) {
		try {
			
			if (accion.equalsIgnoreCase("M")) {
				this.serviceCliente.modificar(cliente);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cliente Modificado",""));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void mostrarData(Usuario u) {
		this.us = u;
		this.tipoDialogo = "Modificar Usuario";
	}
	
	public void onRowEdit(RowEditEvent event) {
		try {
			this.service.modificar((Usuario)event.getObject());
			FacesMessage msg = new FacesMessage("Usuario editado", ((Usuario)event.getObject()).getUsuario());
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			// TODO: handle exception
		}
        
    }
	
	
}
