package com.peru21.backend.apirest.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.peru21.backend.apirest.entity.Matricula;
import com.peru21.backend.apirest.entity.Periodo;

public interface IMatriculaService {
		
	public List<Matricula> findAll();
	
	public Page<Matricula> findAll(Pageable pageable);
	
	public Matricula findById(Long id);
	
	public Matricula save(Matricula matricula);
	
	public void delete(Long id);
	
	public Matricula deleteLogical(Matricula matricula);
	
	public List<Periodo> findAllPeriodos();
	

	public Long CuentasXAlumno(Long id);
	
	public Long findNMatriculas();

}
