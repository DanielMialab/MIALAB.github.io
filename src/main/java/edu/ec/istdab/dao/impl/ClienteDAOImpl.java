 package edu.ec.istdab.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import edu.ec.istdab.dao.IClienteDAO;
import edu.ec.istdab.model.Cliente;


@Stateful//@Named
public class ClienteDAOImpl implements IClienteDAO, Serializable{

	@PersistenceContext(unitName = "facturaPU")
	private EntityManager em;
	
	@Override
	public Integer registrar(Cliente cli) throws Exception {
		em.persist(cli);
		return cli.getIdCliente();
	}

	@Override
	public Integer modificar(Cliente cli) throws Exception {
		em.merge(cli);
		return 1;
	}

	@Override
	public Integer eliminar(Cliente cli) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Cliente> listar() throws Exception {
		//List<Persona> lista = new ArrayList<>();
		//try {
			Query query = em.createQuery("SELECT c FROM Cliente c");
			List<Cliente> lista = (List<Cliente>)query.getResultList();
			return lista;
		/*} catch (Exception e) {
			//e.printStackTrace();
		}
		
		
		/*Persona persona = new Persona();
		persona.setIdPersona(1);
		persona.setNombres("Daniel");
		persona.setApellidos("Tapia");
		lista.add(persona);
		
		persona = new Persona();
		persona.setIdPersona(1);
		persona.setNombres("Adriana");
		persona.setApellidos("Loaiza");
		lista.add(persona);
		
		persona = new Persona();
		persona.setIdPersona(1);
		persona.setNombres("Nicole");
		persona.setApellidos("Armijos");
		lista.add(persona);*/
		
	}

	@Override
	public Cliente listarPorId(Cliente cli) throws Exception {
		Cliente cliente=new Cliente();
		List<Cliente> lista = new ArrayList<>();
		try {
			Query query = em.createQuery("FROM Cliente c WHERE c.idCliente=?1");
			query.setParameter(1, cli.getIdCliente());
			lista=(List<Cliente>)query.getResultList();
			if (lista!=null&&!lista.isEmpty()) {
				cliente=lista.get(0);
			}
			} catch (Exception e) {
			throw e;
		}
		return cliente;
	}
	
}
