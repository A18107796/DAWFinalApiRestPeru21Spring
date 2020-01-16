package com.peru21.backend.apirest.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.peru21.backend.apirest.entity.Curso;

public interface ICursoDao  extends JpaRepository<Curso, Long>{

}
