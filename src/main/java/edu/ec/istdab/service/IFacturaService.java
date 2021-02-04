package edu.ec.istdab.service;



import java.util.List;

import edu.ec.istdab.model.Cliente;
import edu.ec.istdab.model.Factura;


public interface IFacturaService extends IService<Factura>{
	
	List<Factura> obtenerFaturasPorCliente(Cliente cliente);
	Long obtenerMaximaSecuencia();
}
