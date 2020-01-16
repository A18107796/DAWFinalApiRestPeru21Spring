package com.peru21.backend.apirest.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "especializaciones")
public class Especializacion implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String cod_especializacion;
	
	private String nombre;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_especializacion")
	@JsonIgnoreProperties({"especializaciones","hibernateLazyInitializer", "handler"})
	private List<EspecializacionCursos> cursos;
	
	public Especializacion() {
		this.cursos  = new ArrayList<>();
	}

	public List<EspecializacionCursos> getCursos() {
		return cursos;
	}



	public void setCursos(List<EspecializacionCursos> cursos) {
		this.cursos = cursos;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getCod_especializacion() {
		return cod_especializacion;
	}



	public void setCod_especializacion(String cod_especializacion) {
		this.cod_especializacion = cod_especializacion;
	}



	public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
