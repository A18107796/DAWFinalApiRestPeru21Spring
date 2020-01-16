package com.peru21.backend.apirest.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "cursos")
public class Curso implements Serializable{


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String cod_curso;
	
	private String nombre_curso;
	
	
	
	
	public Long getId() {
		return id;
	}




	public void setId(Long id) {
		this.id = id;
	}




	public String getCod_curso() {
		return cod_curso;
	}




	public void setCod_curso(String cod_curso) {
		this.cod_curso = cod_curso;
	}




	public String getNombre_curso() {
		return nombre_curso;
	}




	public void setNombre_curso(String nombre_curso) {
		this.nombre_curso = nombre_curso;
	}




	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
}
