package com.peru21.backend.apirest.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "periodos")
public class Periodo implements Serializable{


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_periodo;
	
	private String nombre_periodo;

	@Temporal(TemporalType.DATE)
	private Date fecha_inicio;
	
	


	public Date getFecha_inicio() {
		return fecha_inicio;
	}

	public void setFecha_inicio(Date fecha_inicio) {
		this.fecha_inicio = fecha_inicio;
	}

	public Long getId_periodo() {
		return id_periodo;
	}

	public void setId_periodo(Long id_periodo) {
		this.id_periodo = id_periodo;
	}


	public String getNombre_periodo() {
		return nombre_periodo;
	}

	public void setNombre_periodo(String nombre_periodo) {
		this.nombre_periodo = nombre_periodo;
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
