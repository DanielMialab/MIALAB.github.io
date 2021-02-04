package edu.ec.istdab.dao;


import java.util.List;

import javax.ejb.Local;

import edu.ec.istdab.model.Cliente;
import edu.ec.istdab.model.Factura;

@Local
public interface IFacturaDAO extends ICRUD<Factura>{
	
	List<Factura> obtenerFacturasPorCliente(Cliente cliente);
	
	Long getMaxSecuenciaFactura();

}
