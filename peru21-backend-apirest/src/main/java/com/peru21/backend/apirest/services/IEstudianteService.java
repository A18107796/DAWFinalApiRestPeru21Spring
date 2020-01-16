package com.peru21.backend.apirest.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.peru21.backend.apirest.entity.Distrito;

import com.peru21.backend.apirest.entity.Estudiante;

public interface IEstudianteService {
	public List<Estudiante> findAll();

	public List<Estudiante> findAll(String estado);

	public Page<Estudiante> findAll(Pageable pageable);

	public Estudiante findByDNI(String dni);

	public Estudiante findById(Long id);

	public Estudiante save(Estudiante estudiante);

	public void delete(Long id);

	public Estudiante deleteLogical(Estudiante estudiante);

	public List<Distrito> findAllDistritos();

	public Long findNEstudiantes();
}
