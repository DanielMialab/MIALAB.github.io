package edu.ec.istdab.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Named;


import edu.ec.istdab.dao.IFacturaDAO;
import edu.ec.istdab.model.Cliente;
import edu.ec.istdab.model.Factura;
import edu.ec.istdab.service.IFacturaService;

@Named
public class FacturaServiceImpl implements IFacturaService, Serializable{

	@EJB
	private IFacturaDAO dao;

	@Override
	public Integer registrar(Factura t) throws Exception {
		return  dao.registrar(t);
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
	
	public List<Factura> obtenerFaturasPorCliente(Cliente cliente){
		
		return dao.obtenerFacturasPorCliente(cliente);
		
	}

	@Override
	public Long obtenerMaximaSecuencia() {
		return dao.getMaxSecuenciaFactura()+new Long(1);
	}

}
