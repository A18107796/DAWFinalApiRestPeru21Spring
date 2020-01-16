package com.peru21.backend.apirest.controllers;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.peru21.backend.apirest.entity.Distrito;
import com.peru21.backend.apirest.entity.Empleado;
import com.peru21.backend.apirest.entity.Especializacion;
import com.peru21.backend.apirest.entity.Estudiante;
import com.peru21.backend.apirest.entity.Matricula;
import com.peru21.backend.apirest.entity.Periodo;
import com.peru21.backend.apirest.services.EmpleadoServiceImpl;
import com.peru21.backend.apirest.services.EspecializacionServiceImpl;
import com.peru21.backend.apirest.services.EstudianteServiceImpl;
import com.peru21.backend.apirest.services.MatriculaServiceImpl;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class MatriculaRestController {
	
	@Autowired
	private MatriculaServiceImpl serviceMatricula;
	
	@Autowired
	private EmpleadoServiceImpl serviceEmpleado;
	
	@Autowired
	private EstudianteServiceImpl estudianteService;
	
	@Autowired
	private EspecializacionServiceImpl espService;
	
	@GetMapping("/matriculas")
	public List<Matricula> index() {
		return serviceMatricula.findAll();
	}
	

	@GetMapping("/matriculas/page/{page}")
	public Page<Matricula> index(@PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page, 5);
		return serviceMatricula.findAll(pageable);
	}
	
	@GetMapping("/matriculas/{id}")
	public ResponseEntity<?> showById(@PathVariable Long id) {

		Matricula matricula = null;
		Map<String, Object> response = new HashMap<>();

		try {
			matricula = serviceMatricula.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (matricula == null) {
			response.put("mensaje", "La matricula con ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Matricula>(matricula, HttpStatus.OK);
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_VENTAS"})
	@PostMapping("/matriculas")
	public ResponseEntity<?> create(@Valid @RequestBody Matricula matricula, BindingResult result) {
		Matricula newMatricula= null;
		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {

			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo: '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		try {
			matricula.setFecha_reg(new Date());
			matricula.setEstado("PENDIENTE");
			//matricula.setEmpleado(serviceEmpleado.findById((long) 1));
			
			newMatricula = serviceMatricula.save(matricula);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el registro en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La matricula ha sido registrada, recuerde informar que tiene 7 dias para realizar el pago!");
		response.put("matricula", newMatricula);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_VENTAS"})
	@PutMapping("/matriculas/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Matricula matricula, BindingResult result,
			@PathVariable Long id) {
		Matricula matriculaActual = serviceMatricula.findById(id);
		Matricula matriculaActualizada = null;

		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {

			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo: '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		if (matriculaActual == null) {
			response.put("mensaje", "No se puede editar, la matricula ID: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		try {

			matriculaActual.setEmpleado(matricula.getEmpleado());
			matriculaActual.setEstudiante(matricula.getEstudiante());
			matriculaActual.setEspecializacion(matricula.getEspecializacion());
			matriculaActual.setFecha_reg(new Date());
			matriculaActual.setEstado(matricula.getEstado());
			matriculaActualizada = serviceMatricula.save(matriculaActual);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El estudiante ha sido actualizado con exito!");
		response.put("matricula", matriculaActualizada);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

	}
	
	@Secured({"ROLE_ADMIN"})
	@DeleteMapping("/matriculas/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {

		Map<String, Object> response = new HashMap<>();

		try {
			serviceMatricula.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La matricula se ha sido eliminado con exito!");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	
	@GetMapping("/matriculas/estudiantes")
	public List<Estudiante> listarEstudiantes(){
		return estudianteService.findAll();
	}
	
	@GetMapping("/matriculas/empleados")
	public List<Empleado> listarEmpleados(){
		return serviceEmpleado.findAll();
	}
	
	@GetMapping("/matriculas/periodos")
	public List<Periodo> listarPeriodoss(){
		return serviceMatricula.findAllPeriodos();
	}	
	
	@GetMapping("/matriculas/especializaciones")
	public List<Especializacion> listarEspecializaciones(){
		return espService.findAll();
	}
	
	@GetMapping(value ="/matriculas/deudas/{id}")
	public ResponseEntity<?>  getDeudas(@PathVariable Long id) {
		Long nfunciones;
		Map<String, Long> response = new HashMap<>();
		nfunciones = serviceMatricula.CuentasXAlumno(id);
		response.put("deudas", nfunciones);
		return new ResponseEntity<Map<String, Long>>(response, HttpStatus.OK);
	}
	
	@GetMapping(value ="/matriculas/nregistros")
	public ResponseEntity<?> getDeudas() {
		Long nfunciones;
		Map<String, Long> response = new HashMap<>();
		nfunciones = serviceMatricula.findNMatriculas();
		response.put("registros", nfunciones);
		return new ResponseEntity<Map<String, Long>>(response, HttpStatus.OK);
	}
	

}
