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
import com.peru21.backend.apirest.entity.Estudiante;
import com.peru21.backend.apirest.services.EstudianteServiceImpl;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class EstudianteRestController {

	@Autowired
	private EstudianteServiceImpl estudianteService;
	
	
	@GetMapping("/estudiantes")
	public List<Estudiante> index() {
		return estudianteService.findAll();
	}
	
	@GetMapping("/estudiantes/estado={estado}")
	public List<Estudiante> index(@PathVariable("estado") String estado) {
		return estudianteService.findAll(estado);
	}	
	
	@GetMapping("/estudiantes/dni={dni}")
	public ResponseEntity<?> findByDNI(@PathVariable("dni") String dni) {
		
		Estudiante estudiante = null;
		Map<String, Object> response = new HashMap<>();
		
		
		try {
			estudiante = estudianteService.findByDNI(dni);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (estudiante == null) {
			response.put("mensaje", "El estudiante con DNI: ".concat(dni.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Estudiante>(estudiante, HttpStatus.OK);
	}		
	
	@GetMapping("/estudiantes/page/{page}")
	public Page<Estudiante> index(@PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page, 5);
		return estudianteService.findAll(pageable);
	}
	
	@GetMapping("/estudiantes/{id}")
	public ResponseEntity<?> showById(@PathVariable Long id) {

		Estudiante estudiante = null;
		Map<String, Object> response = new HashMap<>();

		try {
			estudiante = estudianteService.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (estudiante == null) {
			response.put("mensaje", "El estudiante ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Estudiante>(estudiante, HttpStatus.OK);
	}
	

	@Secured({"ROLE_ADMIN", "ROLE_VENTAS"})
	@PostMapping("/estudiantes")
	// requeesbody llega el empleado en formato Json y se transforma a objeto
	// empleado
	public ResponseEntity<?> create(@Valid @RequestBody Estudiante estudiante, BindingResult result) {
		Estudiante newEstudiante = null;
		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {

			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo: '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(estudiante.getFecha_nac());
			calendar.add(Calendar.DAY_OF_YEAR, 2);
			estudiante.setFecha_nac(calendar.getTime());
			estudiante.setFecha_reg(new Date());
			estudiante.setEstado("Pendiente");
			
			newEstudiante = estudianteService.save(estudiante);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el registro en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El estudiante ha sido creado con exito!");
		response.put("estudiante", newEstudiante);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	

	@Secured({"ROLE_ADMIN", "ROLE_VENTAS"})
	@PutMapping("/estudiantes/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Estudiante estudiante, BindingResult result,
			@PathVariable Long id) {
		Estudiante estudianteActual = estudianteService.findById(id);
		Estudiante estudianteActualizado = null;

		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {

			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo: '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		if (estudianteActual == null) {
			response.put("mensaje", "No se puede editar, el emplado ID: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(estudiante.getFecha_nac());
			calendar.add(Calendar.DAY_OF_YEAR, 2);

			estudianteActual.setNombres(estudiante.getNombres());
			estudianteActual.setApellido_pa(estudiante.getApellido_pa());
			estudianteActual.setApellido_ma(estudiante.getApellido_ma());
			estudianteActual.setDni(estudiante.getDni());
			estudianteActual.setSexo(estudiante.getSexo());
			estudianteActual.setDireccion(estudiante.getDireccion());
			estudianteActual.setTelefono(estudiante.getTelefono());
			estudianteActual.setFecha_nac(calendar.getTime());
			estudianteActual.setDistrito(estudiante.getDistrito());
			estudianteActual.setFecha_reg(new Date());
			estudianteActualizado = estudianteService.save(estudianteActual);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El estudiante ha sido actualizado con exito!");
		response.put("estudiante", estudianteActualizado);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

	}

	@Secured({"ROLE_ADMIN"})
	@DeleteMapping("/estudiantes/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {

		Map<String, Object> response = new HashMap<>();

		try {
			/* Borrar Foto antes de eliminar cliente 
			Estuudiante empleado = empleadoService.findById(id);
			String nombreFotoAnterior = empleado.getFoto();

			uploadService.eliminar(nombreFotoAnterior);
			 */
			estudianteService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El estudiante ha sido eliminado con exito!");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	

	@Secured({"ROLE_ADMIN", "ROLE_VENTAS"})
	@GetMapping("/estudiantes/distritos")
	public List<Distrito> listarDistritos(){
		return estudianteService.findAllDistritos();
	}

	@GetMapping(value ="/estudiantes/nregistros")
	public ResponseEntity<?> getDeudas() {
		Long nfunciones;
		Map<String, Long> response = new HashMap<>();
		nfunciones = estudianteService.findNEstudiantes();
		response.put("registros", nfunciones);
		return new ResponseEntity<Map<String, Long>>(response, HttpStatus.OK);
	}


}
