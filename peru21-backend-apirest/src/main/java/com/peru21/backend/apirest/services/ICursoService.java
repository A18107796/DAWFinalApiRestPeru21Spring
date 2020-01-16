package com.peru21.backend.apirest.services;

import com.peru21.backend.apirest.entity.Curso;

public interface ICursoService {

	public Curso findById(Long id);

	public Curso save(Curso curso);

	public void delete(Long id);
}
