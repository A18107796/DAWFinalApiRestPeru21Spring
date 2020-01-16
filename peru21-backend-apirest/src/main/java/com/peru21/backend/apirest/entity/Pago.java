package com.peru21.backend.apirest.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pagos")
public class Pago implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String cod_pago;
	
	private Double monto; 

	private String descripcion;
	
	
	
	
	
	public String getCod_pago() {
		return cod_pago;
	}





	public void setCod_pago(String cod_pago) {
		this.cod_pago = cod_pago;
	}





	public Long getId() {
		return id;
	}





	public void setId(Long id) {
		this.id = id;
	}





	public Double getMonto() {
		return monto;
	}





	public void setMonto(Double monto) {
		this.monto = monto;
	}





	public String getDescripcion() {
		return descripcion;
	}





	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}





	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
}
