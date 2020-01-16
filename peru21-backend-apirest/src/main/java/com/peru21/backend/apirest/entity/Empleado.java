package com.peru21.backend.apirest.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "empleados")
public class Empleado implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	@Size(min = 1, max = 50)
	@Column(nullable = false)
	private String nombre;

	@NotEmpty
	@Column(nullable = false)
	private String apellido_pa;

	@NotEmpty
	private String apellido_ma;
	
	@NotEmpty
	@Size(min = 8, max = 8)
	private String dni;
	
	@NotEmpty
	@Size(min = 1, max = 1)
	private String sexo;
	
	@NotNull(message = "Seleccione su fecha de nacimiento")
	@Temporal(TemporalType.DATE)
	private Date fecha_nac;

	@Column(nullable = false)
	private String direccion;
 
	private String telefono;
	
	private String foto;
	
	
	@NotNull(message = "Usted debe seleccionar un distrito")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_distrito")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Distrito distrito;
	
	@NotNull(message = "Usted debe seleccionar un cargo")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cargo")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Cargo cargo;
	

	@NotNull(message = "Usted debe seleccionar un area")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_area")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Area area;
	
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido_pa() {
		return apellido_pa;
	}

	public void setApellido_pa(String apellido_pa) {
		this.apellido_pa = apellido_pa;
	}

	public String getApellido_ma() {
		return apellido_ma;
	}

	public void setApellido_ma(String apellido_ma) {
		this.apellido_ma = apellido_ma;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Date getFecha_nac() {
		return fecha_nac;
	}

	public void setFecha_nac(Date fecha_nac) {
		this.fecha_nac = fecha_nac;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	
	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}


	public Distrito getDistrito() {
		return distrito;
	}

	public void setDistrito(Distrito distrito) {
		this.distrito = distrito;
	}
	
	
	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	@Override
	public String toString() {
		return "Empleado [id=" + id + ", nombre=" + nombre + ", apellido_pa=" + apellido_pa + ", apellido_ma="
				+ apellido_ma + ", dni=" + dni + ", sexo=" + sexo + ", fecha_nac=" + fecha_nac + ", direccion="
				+ direccion + ", telefono=" + telefono + ", foto=" + foto + ", distrito=" + distrito + "]";
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
