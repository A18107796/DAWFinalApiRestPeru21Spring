package com.peru21.backend.apirest.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "matriculas_pagos")
public class MatriculaPagos implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_pago")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Pago pago;
	@Temporal(TemporalType.DATE)
	private Date fecha_pago;
	@Temporal(TemporalType.DATE)
	private Date fecha_venc;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_matricula")
	@JsonIgnoreProperties( value = {"pagos", "hibernateLazyInitializer", "handler" }, allowSetters = true)
	private Matricula matricula;
	
	private String estado;
			
		
	


	public Matricula getMatricula() {
		return matricula;
	}

	public void setMatricula(Matricula matricula) {
		this.matricula = matricula;
	}

	public Pago getPago() {
		return pago;
	}

	public void setPago(Pago pago) {
		this.pago = pago;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFecha_pago() {
		return fecha_pago;
	}

	public void setFecha_pago(Date fecha_pago) {
		this.fecha_pago = fecha_pago;
	}

	public Date getFecha_venc() {
		return fecha_venc;
	}

	public void setFecha_venc(Date fecha_venc) {
		this.fecha_venc = fecha_venc;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
