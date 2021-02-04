package edu.ec.istdab.service;

import edu.ec.istdab.model.Usuario;

public interface IUsuarioService extends IService<Usuario> {
	Usuario login(Usuario us);
}
