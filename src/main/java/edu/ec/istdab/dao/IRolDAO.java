package edu.ec.istdab.dao;

import java.util.List;

import javax.ejb.Local;

import edu.ec.istdab.model.Rol;
import edu.ec.istdab.model.Usuario;
import edu.ec.istdab.model.UsuarioRol;

@Local
public interface IRolDAO extends ICRUD<Rol>{
	Integer asignar(Usuario us, List<UsuarioRol> roles);
	
}
