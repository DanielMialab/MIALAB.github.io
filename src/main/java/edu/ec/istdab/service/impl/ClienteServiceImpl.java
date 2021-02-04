package edu.ec.istdab.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Named;

import edu.ec.istdab.dao.IClienteDAO;
import edu.ec.istdab.dao.IUsuarioDAO;
import edu.ec.istdab.model.Cliente;
import edu.ec.istdab.service.IClienteService;

@Named
public class ClienteServiceImpl implements IClienteService, Serializable{

	//Para crear la instancia a la interface dao.
	//@Inject
	@EJB
	private IClienteDAO dao;
	/*public PersonaServiceImpl() {
		//dao=new PersonaDAOImpl();
	}*/
	
	@Override
	public Integer registrar(Cliente cli) throws Exception {
		// TODO Auto-generated method stub
		
		return dao.registrar(cli);
	}

	@Override
	public Integer modificar(Cliente cli) throws Exception {
		// TODO Auto-generated method stub
		return dao.modificar(cli);
	}

	@Override
	public Integer eliminar(Cliente cli) throws Exception {
		// TODO Auto-generated method stub
		return dao.eliminar(cli);
	}

	@Override
	public List<Cliente> listar() throws Exception {
		// TODO Auto-generated method stub
		return dao.listar();
	}

	@Override
	public Cliente listarPorId(Cliente cli) throws Exception {
		// TODO Auto-generated method stub
		return dao.listarPorId(cli);
	}

}
