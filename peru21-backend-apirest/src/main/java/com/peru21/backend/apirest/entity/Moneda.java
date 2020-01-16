package com.peru21.backend.apirest.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table( name = "monedas")
public class Moneda implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nombre_moneda;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public String getNombre_moneda() {
		return nombre_moneda;
	}

	public void setNombre_moneda(String nombre_moneda) {
		this.nombre_moneda = nombre_moneda;
	}


	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;

}
