package com.peru21.backend.apirest.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
@Table(name = "facturas")
public class Factura implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String ruc;

	private String n_factura;

	private String observaciones;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_estudiante")
	@JsonIgnoreProperties( value = {"facturas","matriculas","hibernateLazyInitializer", "handler" }, allowSetters = true)
	private Estudiante estudiante;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_empleado")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Empleado empleado;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_moneda")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Moneda moneda;

	@Temporal(TemporalType.DATE)
	private Date fechaPago;

	private String estado;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_factura")
	@JsonIgnoreProperties({"facturas","hibernateLazyInitializer", "handler"})
	private List<FacturaDetalle> facturaDetalles;
	
	
	public List<FacturaDetalle> getFacturaDetalles() {
		return facturaDetalles;
	}

	public void setFacturaDetalles(List<FacturaDetalle> facturaDetalles) {
		this.facturaDetalles = facturaDetalles;
	}

	public Factura() {
		this.facturaDetalles  = new ArrayList<>();
	}
	
	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getRuc() {
		return ruc;
	}



	public void setRuc(String ruc) {
		this.ruc = ruc;
	}



	public String getN_factura() {
		return n_factura;
	}



	public void setN_factura(String n_factura) {
		this.n_factura = n_factura;
	}



	public String getObservaciones() {
		return observaciones;
	}



	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}



	public Estudiante getEstudiante() {
		return estudiante;
	}



	public void setEstudiante(Estudiante estudiante) {
		this.estudiante = estudiante;
	}



	public Empleado getEmpleado() {
		return empleado;
	}



	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}



	public Moneda getMoneda() {
		return moneda;
	}



	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}



	public Date getFechaPago() {
		return fechaPago;
	}



	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
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
