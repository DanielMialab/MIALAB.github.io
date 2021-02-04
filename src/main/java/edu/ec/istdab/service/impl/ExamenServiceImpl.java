package edu.ec.istdab.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Named;

import edu.ec.istdab.dao.IClienteDAO;
import edu.ec.istdab.dao.IExamenDAO;
import edu.ec.istdab.model.Examen;
import edu.ec.istdab.service.IExamenService;

@Named
public class ExamenServiceImpl implements IExamenService, Serializable{

	@EJB
	private IExamenDAO dao;
	
	@Override
	public Integer registrar(Examen ex) throws Exception {
		// TODO Auto-generated method stub
		return dao.registrar(ex);
	}

	@Override
	public Integer modificar(Examen ex) throws Exception {
		// TODO Auto-generated method stub
		return dao.modificar(ex);
	}

	@Override
	public Integer eliminar(Examen ex) throws Exception {
		// TODO Auto-generated method stub
		return dao.eliminar(ex);
	}

	@Override
	public List<Examen> listar() throws Exception {
		// TODO Auto-generated method stub
		return dao.listar();
	}

	@Override
	public Examen listarPorId(Examen ex) throws Exception {
		// TODO Auto-generated method stub
		return dao.listarPorId(ex);
	}

}
