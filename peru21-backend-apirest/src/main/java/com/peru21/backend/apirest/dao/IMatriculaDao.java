package com.peru21.backend.apirest.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.peru21.backend.apirest.entity.Matricula;
import com.peru21.backend.apirest.entity.Periodo;

public interface IMatriculaDao extends JpaRepository<Matricula, Long>{
	
	@Query("from Periodo")
	public List<Periodo> findAllPeriodos();
	
	@Query(value = "select count(*) from matriculas_pagos where matriculas_pagos.id_matricula = ?1  and matriculas_pagos.estado = 'Pendiente' ", nativeQuery = true)
	public Long CuentasXAlumno(Long id);
	
	@Query(value = "select count(*) from matriculas", nativeQuery = true)
	public Long findNMatriculas();
	
}
