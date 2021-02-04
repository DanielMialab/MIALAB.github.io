package edu.ec.istdab.dao.impl;

import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import edu.ec.istdab.dao.IFacturaDAO;
import edu.ec.istdab.model.Cliente;
import edu.ec.istdab.model.Factura;

@Stateful
public class FacturaDAOImp implements IFacturaDAO {

	@PersistenceContext(unitName = "facturaPU")
	private EntityManager em;

	@Override
	public Integer registrar(Factura t) throws Exception {
		em.persist(t);
		return t.getIdFactura();
	}

	@Override
	public Integer modificar(Factura t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer eliminar(Factura t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Factura> listar() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Factura listarPorId(Factura t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Factura> obtenerFacturasPorCliente(Cliente cliente) {
		String sentence = "SELECT factura FROM Factura factura WHERE factura.cliente.id = ?1";
		Query query = em.createQuery(sentence);
		query.setParameter(1, cliente.getIdCliente());

		return (List<Factura>) query.getResultList();

	}

	@Override
	public Long getMaxSecuenciaFactura() {
		
		String sentence = "SELECT MAX(factura.secuencia) FROM Factura factura";
		Query query = em.createQuery(sentence);
		return (Long)query.getSingleResult();

	}

}
