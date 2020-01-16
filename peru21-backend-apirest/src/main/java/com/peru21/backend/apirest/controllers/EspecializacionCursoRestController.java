	package com.peru21.backend.apirest.controllers;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.peru21.backend.apirest.entity.Curso;
import com.peru21.backend.apirest.entity.Especializacion;
import com.peru21.backend.apirest.services.CursoServiceImpl;
import com.peru21.backend.apirest.services.EspecializacionServiceImpl;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class EspecializacionCursoRestController {

	

	@Autowired
	private CursoServiceImpl cursoService;

	@Autowired
	private EspecializacionServiceImpl especializacionService;

	@GetMapping("/especializaciones")
	public List<Especializacion> index() {
		return especializacionService.findAll();
	}

	@GetMapping("/especializaciones/cursos")
	public List<Curso> listarCursos() {
		return especializacionService.findAllCursos();
	}
	
	@GetMapping("/especializaciones/{id}")
	public Especializacion findById(@PathVariable Long id) {
		return especializacionService.findById(id);
	}

	@PostMapping("/especializaciones/cursos")
	public ResponseEntity<?> createCurso(@Valid @RequestBody Curso curso, BindingResult result) {
		Curso newCurso = null;
		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {

			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo: '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		try {

			newCurso = cursoService.save(curso);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el registro en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El curso ha sido creado con exito!");
		response.put("curso", newCurso);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@PostMapping("/especializaciones/especializaciones")
	public ResponseEntity<?> createEspecializacion(@Valid @RequestBody Especializacion especializacion,
			BindingResult result) {
		Especializacion newEspecializacion = null;
		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {

			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo: '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		try {

			newEspecializacion = especializacionService.save(especializacion);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el registro en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La especializacion ha sido creado con exito!");
		response.put("especializacion", newEspecializacion);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

}
