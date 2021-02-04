package edu.ec.istdab.dao;

import edu.ec.istdab.model.Usuario;

public interface IUsuarioDAO extends ICRUD<Usuario>{
	String traerPassHashed(String us);
	Usuario leerPorNombreUsuario(String nombre);
}
