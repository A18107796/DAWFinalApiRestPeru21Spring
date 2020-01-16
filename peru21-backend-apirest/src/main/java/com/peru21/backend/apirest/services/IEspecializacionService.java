package com.peru21.backend.apirest.services;


import java.util.List;


import com.peru21.backend.apirest.entity.Curso;
import com.peru21.backend.apirest.entity.Especializacion;


public interface IEspecializacionService {

	
	public List<Especializacion> findAll();
	
	public Especializacion findById(Long id);
	
	public Especializacion save(Especializacion especializacion);
	
	public void delete(Long id);
	
	public Especializacion deleteLogical(Especializacion especializacion);
	
	public List<Curso> findAllCursos();
}
