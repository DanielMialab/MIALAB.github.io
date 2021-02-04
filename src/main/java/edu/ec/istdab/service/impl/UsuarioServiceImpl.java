package edu.ec.istdab.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Named;

import org.mindrot.jbcrypt.BCrypt;

import edu.ec.istdab.dao.IUsuarioDAO;
import edu.ec.istdab.model.Usuario;
import edu.ec.istdab.service.IUsuarioService;

@Named
public class UsuarioServiceImpl implements IUsuarioService, Serializable{

	@EJB
	private IUsuarioDAO dao;
	 
	@Override
	public Usuario login(Usuario us) {
		Usuario usuario = null;
		String clave = us.getClave();
		String claveHash = dao.traerPassHashed(us.getUsuario());
		try {
//			if(!claveHash.isEmpty()) {
//				if(BCrypt.checkpw(clave, claveHash)) {
//					return dao.leerPorNombreUsuario(us.getUsuario());
//				}
//			}
			
			
			if(!claveHash.isEmpty()) {
				System.out.println("000000000000000000000000000>clave verificada no vacia");
				if(clave.equals(claveHash)) {
					System.out.println("000000000000000000000000000>clave verificada");
					return dao.leerPorNombreUsuario(us.getUsuario());
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return usuario;
	}

	
	@Override
	public Integer registrar(Usuario t) throws Exception {
		// TODO Auto-generated method stub
		return dao.registrar(t);
	}

	@Override
	public Integer modificar(Usuario t) throws Exception {
		// TODO Auto-generated method stub
		return dao.modificar(t);
	}

	@Override
	public Integer eliminar(Usuario t) throws Exception {
		// TODO Auto-generated method stub
		return dao.eliminar(t);
	}

	@Override
	public List<Usuario> listar() throws Exception {
		// TODO Auto-generated method stub
		return dao.listar();
	}

	@Override
	public Usuario listarPorId(Usuario t) throws Exception {
		// TODO Auto-generated method stub
		return dao.listarPorId(t);
	}
	
	

}
