package com.peru21.backend.apirest.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.peru21.backend.apirest.entity.Curso;
import com.peru21.backend.apirest.entity.Especializacion;

public interface IEspecializacionDao  extends JpaRepository<Especializacion, Long>{
	
	@Query("from Curso")
	public List<Curso> findAllCursos();
	
}
