package com.peru21.backend.apirest.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "distritos")
public class Distrito implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_distrito;

	private String nombre_distrito;

	public Long getId_distrito() {
		return id_distrito;
	}

	public void setId_distrito(Long id_distrito) {
		this.id_distrito = id_distrito;
	}

	public String getNombre_distrito() {
		return nombre_distrito;
	}

	public void setNombre_distrito(String nombre_distrito) {
		this.nombre_distrito = nombre_distrito;
	}

	private static final long serialVersionUID = 1L;

}
