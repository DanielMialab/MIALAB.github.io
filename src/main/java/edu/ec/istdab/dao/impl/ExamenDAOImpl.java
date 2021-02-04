package edu.ec.istdab.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import edu.ec.istdab.dao.IExamenDAO;
import edu.ec.istdab.model.Cliente;
import edu.ec.istdab.model.Examen;

@Stateful
public class ExamenDAOImpl implements IExamenDAO, Serializable{

	@PersistenceContext(unitName = "facturaPU")
	private EntityManager em;
	
	@Override
	public Integer registrar(Examen ex) throws Exception {
		em.persist(ex);
		return ex.getIdExamen();
	}

	@Override
	public Integer modificar(Examen ex) throws Exception {
		em.merge(ex);
		return 1;
	}

	@Override
	public Integer eliminar(Examen ex) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Examen> listar() throws Exception {
		Query query = em.createQuery("SELECT e FROM Examen e");
		List<Examen> lista = (List<Examen>) query.getResultList();
		return lista;
	}

	@Override
	public Examen listarPorId(Examen ex) throws Exception {
		Examen examen=new Examen();
		List<Examen> lista = new ArrayList<>();
		try {
			Query query = em.createQuery("FROM Examen e WHERE e.idExamen=?1");
			query.setParameter(1, ex.getIdExamen());
			lista=(List<Examen>)query.getResultList();
			if (lista!=null&&!lista.isEmpty()) {
				examen=lista.get(0);
			}
			} catch (Exception e) {
			throw e;
		}
		return examen;
	}
}
