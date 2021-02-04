package edu.ec.istdab.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Named;

import edu.ec.istdab.dao.IRolDAO;
import edu.ec.istdab.model.Rol;
import edu.ec.istdab.model.Usuario;
import edu.ec.istdab.model.UsuarioRol;
import edu.ec.istdab.service.IRolService;

@Named
public class RolServiceImpl implements IRolService, Serializable{

	@EJB
	private IRolDAO dao;
	
	@Override
	public Integer registrar(Rol t) throws Exception {
		// TODO Auto-generated method stub
		return dao.registrar(t);
	}

	@Override
	public Integer modificar(Rol t) throws Exception {
		// TODO Auto-generated method stub
		return dao.modificar(t);
	}

	@Override
	public Integer eliminar(Rol t) throws Exception {
		// TODO Auto-generated method stub
		return dao.eliminar(t);
	}

	@Override
	public List<Rol> listar() throws Exception {
		// TODO Auto-generated method stub
		return dao.listar();
	}

	@Override
	public Rol listarPorId(Rol t) throws Exception {
		// TODO Auto-generated method stub
		return dao.listarPorId(t);
	}

	@Override
	public Integer asignar(Usuario us, List<Rol> roles) {
		List<UsuarioRol> usuario_roles=new ArrayList<UsuarioRol>();
		roles.forEach(r ->{
			UsuarioRol ur = new UsuarioRol();
			ur.setUsuario(us);
			ur.setRol(r);
			usuario_roles.add(ur);
		});
		return dao.asignar(us, usuario_roles);
	}

}
