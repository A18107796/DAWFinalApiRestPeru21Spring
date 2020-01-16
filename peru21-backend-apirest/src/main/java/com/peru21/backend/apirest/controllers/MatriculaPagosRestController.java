package com.peru21.backend.apirest.controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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

import com.peru21.backend.apirest.entity.Factura;
import com.peru21.backend.apirest.entity.Matricula;
import com.peru21.backend.apirest.entity.MatriculaPagos;
import com.peru21.backend.apirest.entity.Pago;
import com.peru21.backend.apirest.services.MatriculaPagosServiceImpl;
import com.peru21.backend.apirest.services.PagoServiceImpl;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class MatriculaPagosRestController {

	@Autowired
	private MatriculaPagosServiceImpl servicioMatriculasP;

	@Autowired
	private PagoServiceImpl servicePago;

	@GetMapping("/matriculasPagos")
	public List<MatriculaPagos> index() {
		return servicioMatriculasP.findAll();
	}

	@GetMapping("/matriculasPagos/{id}")
	public ResponseEntity<?> showById(@PathVariable Long id) {

		MatriculaPagos matriculaPagos = null;
		Map<String, Object> response = new HashMap<>();

		try {
			matriculaPagos = servicioMatriculasP.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (matriculaPagos == null) {
			response.put("mensaje",
					"La matricula con ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<MatriculaPagos>(matriculaPagos, HttpStatus.OK);
	}

	@PostMapping("/matriculasPagos")
	public ResponseEntity<?> create(@Valid @RequestBody MatriculaPagos matricula, BindingResult result) {
		MatriculaPagos newMatricula = null;
		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {

			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo: '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		try {

			// matricula.setEmpleado(serviceEmpleado.findById((long) 1));

			newMatricula = servicioMatriculasP.save(matricula);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el registro en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La cuenta a sido registrada");
		response.put("matricula", newMatricula);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@PutMapping("/matriculasPagos/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody MatriculaPagos matricula, BindingResult result,
			@PathVariable Long id) {
		MatriculaPagos matriculaActual = servicioMatriculasP.findById(id);
		MatriculaPagos matriculaActualizada = null;

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

			matriculaActual.setFecha_pago(new Date());

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El estudiante ha sido actualizado con exito!");
		response.put("matricula", matriculaActualizada);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

	}

	@DeleteMapping("/matriculasPagos/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {

		Map<String, Object> response = new HashMap<>();

		try {
			servicioMatriculasP.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La matricula se ha sido eliminado con exito!");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@PostMapping("/matriculasPagos/insertPagos")
	public ResponseEntity<?> insertPagos(@Valid @RequestBody MatriculaPagos matriculas, BindingResult result) {
		List<MatriculaPagos> pagosMatricula = new ArrayList<>();
		List<MatriculaPagos> newMatricula = null;
		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {

			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo: '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		try {  
			int cont = 1;
			for (int i = 1; i < 12; i++) {
				MatriculaPagos matricula = new MatriculaPagos();
				matricula.setMatricula(matriculas.getMatricula());
				
				if (i != 0) {
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(matriculas.getMatricula().getPeriodo().getFecha_inicio());
					if(i == 1) {
					  calendar.add(Calendar.DAY_OF_YEAR, 7);
					}else {
					  calendar.add(Calendar.DAY_OF_YEAR, cont * 29);
					  cont = cont + 1;
					}
					matricula.setPago(servicePago.findById((long) i));
					matricula.setFecha_pago(null);
					matricula.setFecha_venc(calendar.getTime());
					matricula.setEstado("Pendiente");
					pagosMatricula.add(matricula);
				}
			}
			//pagosMatricula.remove(0);
			newMatricula = servicioMatriculasP.insertPensiones(pagosMatricula);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el registro en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La matricula a sido registrada");
		response.put("matricula", newMatricula);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	
	}
	
	
	@GetMapping(value ="/matriculasPagos/ganancias/{estado}")
	public ResponseEntity<?> getGanancias(@PathVariable String estado) {
		Double ganancias;
		Map<String, Double> response = new HashMap<>();
		ganancias = servicioMatriculasP.findGanancias(estado);
		if(ganancias == null) {
			ganancias = 0.0;
		}
		
		response.put("ganancias", ganancias);
		return new ResponseEntity<Map<String, Double>>(response, HttpStatus.OK);
	}
	
	
	@PutMapping("/matriculasPagos/anular/{id}")
	public ResponseEntity<?> anularPagoMatricula(@Valid @RequestBody MatriculaPagos matriculaP, BindingResult result,
			@PathVariable Long id) {
		MatriculaPagos matriculapActual = servicioMatriculasP.findById(id);
		MatriculaPagos matriculaPAnulada = null;

		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {

			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo: '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		if (matriculapActual == null) {
			response.put("mensaje", "No se puede anular, la matriculaP ID: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		try {
			
			matriculapActual.setEstado("Pendiente");
			matriculaPAnulada = servicioMatriculasP.save(matriculapActual);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al anular factura en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La factura a sido anulada");
		response.put("matriculap", matriculaPAnulada);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

	}
	
	@GetMapping("/matriculasPagos/fechas/{id}-{estado}")
	public MatriculaPagos findLastDeuda(@PathVariable("id") Long id, @PathVariable("estado") String estado ) {
		
		return servicioMatriculasP.findLastDeuda(id, estado);
	}
	

}
