package edu.ec.istdab.service;

import java.util.List;

import edu.ec.istdab.model.Rol;
import edu.ec.istdab.model.Usuario;

public interface IRolService extends IService<Rol>{
	Integer asignar(Usuario us, List<Rol> roles);

}
