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
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "matriculas")
public class Matricula implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_matricula;

	@NotNull(message = "Seleccione Periodo")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_periodo")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Periodo periodo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_empleado")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Empleado empleado;

	@NotNull(message = "Seleccione Estudiante")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_estudiante")
	@JsonIgnoreProperties(value = { "matriculas", "facturas", "hibernateLazyInitializer",
			"handler" }, allowSetters = true)
	private Estudiante estudiante;

	@NotNull(message = "Seleccione Especializacion")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_especializacion")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Especializacion especializacion;

	private String ciclo;

	private String cursosMatriculados;

	private String turno;

	private String creditos;

	private String observaciones;

	@Temporal(TemporalType.DATE)
	private Date fecha_reg;

	private String estado;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "matricula", cascade = CascadeType.ALL)
	@JsonIgnoreProperties(value = { "matricula", "hibernateLazyInitializer", "handler" }, allowSetters = true)
	private List<MatriculaPagos> pagos;

	public Matricula() {
		pagos = new ArrayList<>();
	}

	public List<MatriculaPagos> getPagos() {
		return pagos;
	}

	public void setPagos(List<MatriculaPagos> pagos) {
		this.pagos = pagos;
	}

	public Long getId_matricula() {
		return id_matricula;
	}

	public void setId_matricula(Long id_matricula) {
		this.id_matricula = id_matricula;
	}

	public Periodo getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}

	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	public Estudiante getEstudiante() {
		return estudiante;
	}

	public void setEstudiante(Estudiante estudiante) {
		this.estudiante = estudiante;
	}

	public Especializacion getEspecializacion() {
		return especializacion;
	}

	public void setEspecializacion(Especializacion especializacion) {
		this.especializacion = especializacion;
	}

	public Date getFecha_reg() {
		return fecha_reg;
	}

	public void setFecha_reg(Date fecha_reg) {
		this.fecha_reg = fecha_reg;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCiclo() {
		return ciclo;
	}

	public void setCiclo(String ciclo) {
		this.ciclo = ciclo;
	}

	public String getCursosMatriculados() {
		return cursosMatriculados;
	}

	public void setCursosMatriculados(String cursosMatriculados) {
		this.cursosMatriculados = cursosMatriculados;
	}

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}

	public String getCreditos() {
		return creditos;
	}

	public void setCreditos(String creditos) {
		this.creditos = creditos;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public Double getTotal() {
		Double total = 0.00;
		for (MatriculaPagos pagos : pagos) {
			if (pagos.getEstado().equalsIgnoreCase("Pendiente")) {
				total += pagos.getPago().getMonto();
			}
		}
		return total;
	}

}
