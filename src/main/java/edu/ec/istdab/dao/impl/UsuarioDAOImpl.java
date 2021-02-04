package edu.ec.istdab.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import edu.ec.istdab.dao.IUsuarioDAO;
import edu.ec.istdab.model.Usuario;

@Stateless
public class UsuarioDAOImpl implements IUsuarioDAO, Serializable{

	@PersistenceContext(unitName = "facturaPU")
	private EntityManager em;
	@Override
	public Integer registrar(Usuario t) throws Exception {
		em.persist(t);
		return t.getCliente().getIdCliente();
	}

	@Override
	public Integer modificar(Usuario t) throws Exception {
		// TODO Auto-generated method stub
		em.merge(t);
		return t.getCliente().getIdCliente();
	}

	@Override
	public Integer eliminar(Usuario t) throws Exception {
		// TODO Auto-generated method stub
		em.remove(t);
		return 1;
	}

	@Override
	public List<Usuario> listar() throws Exception {
		// TODO Auto-generated method stub
		List<Usuario> lista = new ArrayList<>();
		try {
			Query query = em.createQuery("SELECT u FROM Usuario u");
			lista = (List<Usuario>) query.getResultList();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return lista;
	}

	@Override
	public Usuario listarPorId(Usuario t) throws Exception {
		Usuario us = new Usuario();
		List<Usuario> lista = new ArrayList<>();
		try {
			Query query = em.createQuery("FROM Usuario u WHERE u.id = ?1");
			query.setParameter(1, t.getCliente().getCedula());
			
			lista = (List<Usuario>) query.getResultList();
			
			if (lista != null && !lista.isEmpty()) {
				us = lista.get(0);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return us;
	}

	@Override
	public String traerPassHashed(String us) {
		Usuario usuario = new Usuario();
		try {
			// SQL select clave from usuario where usuario = 'santiago'
			Query query = em.createQuery("FROM Usuario u WHERE u.usuario = ?1");
			query.setParameter(1, us);
			
			List<Usuario> lista = (List<Usuario>) query.getResultList();
			if (!lista.isEmpty()) {
				usuario = lista.get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return usuario != null && usuario.getIdUsuario() != null ? usuario.getClave() : "";
	}

	@Override
	public Usuario leerPorNombreUsuario(String nombre) {
		Usuario usuario = new Usuario();
		try {
			Query query = em.createQuery("FROM Usuario u WHERE u.usuario = ?1");
			query.setParameter(1, nombre);
			
			List<Usuario> lista = (List<Usuario>) query.getResultList();
			if (!lista.isEmpty()) {
				usuario = lista.get(0);
			}
			
		} catch (Exception e) {
			throw e;
		}
		return usuario;
	}

}
