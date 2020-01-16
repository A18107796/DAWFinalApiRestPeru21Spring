package com.peru21.backend.apirest.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "facturas_detalles")
public class FacturaDetalle implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_matriculap")
	@JsonIgnoreProperties( value = {"hibernateLazyInitializer", "handler"})
	private MatriculaPagos matriculaPagos;
	
	private Integer cantidad;
	

	
	
	public Long getId() {
		return id;
	}




	public void setId(Long id) {
		this.id = id;
	}




	public MatriculaPagos getMatriculaPagos() {
		return matriculaPagos;
	}




	public void setMatriculaPagos(MatriculaPagos matriculaPagos) {
		this.matriculaPagos = matriculaPagos;
	}




	public Integer getCantidad() {
		return cantidad;
	}




	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}




	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
