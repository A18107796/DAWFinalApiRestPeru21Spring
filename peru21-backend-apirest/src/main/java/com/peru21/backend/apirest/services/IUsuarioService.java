package com.peru21.backend.apirest.services;

import java.util.List;

import com.peru21.backend.apirest.entity.Empleado;
import com.peru21.backend.apirest.entity.Usuario;

public interface IUsuarioService {
	
	public Usuario findByUsername(String username);
	
	public Empleado findEmpleados(String username);
	
	public Usuario save(Usuario usuario);
	
	public List<Usuario> findAll();
	
}
