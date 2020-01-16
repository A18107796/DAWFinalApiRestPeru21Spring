package com.peru21.backend.apirest.controllers;

import java.util.ArrayList;
import java.util.Calendar;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.peru21.backend.apirest.entity.Empleado;
import com.peru21.backend.apirest.entity.Estudiante;
import com.peru21.backend.apirest.entity.Role;
import com.peru21.backend.apirest.entity.Usuario;
import com.peru21.backend.apirest.services.UsuarioService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class UsuarioRestController {

	@Autowired
	private UsuarioService servicioUsuario;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@GetMapping("/usuarios")
	public List<Usuario> index() {
		return servicioUsuario.findAll();
	}

	@Secured({ "ROLE_ADMIN", "ROLE_VENTAS" })
	@PostMapping("/usuarios")
	public ResponseEntity<?> create(@Valid @RequestBody Usuario usuario, BindingResult result) {
		Usuario newUsuario = null;
		Map<String, Object> response = new HashMap<>();
		List<Role> roles = new ArrayList<Role>();

		if (result.hasErrors()) {

			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo: '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		try {
			usuario.setUsername(usuario.getEmpleado().getNombre().concat(usuario.getEmpleado().getDni()));
			usuario.setEnabled(true);
			String passwordBcrypt = passwordEncoder.encode(usuario.getEmpleado().getDni());

			if (usuario.getEmpleado().getCargo().getId() == 3) {
				Role role1 = new Role();
				Role role2 = new Role();
				role1.setId((long) 6);
				role2.setId((long) 3);
				roles.add(role1);
				roles.add(role2);
				
			} else if (usuario.getEmpleado().getCargo().getId() == 1) {
				Role role1 = new Role();
				Role role2 = new Role();
				role1.setId((long) 3);
				role2.setId((long) 5);
				roles.add(role1);
				roles.add(role2);
				
			}else if(usuario.getEmpleado().getCargo().getId() == 2) {
				Role role1 = new Role();
				Role role2 = new Role();
				Role role3 = new Role();
				Role role4 = new Role();
				Role role5 = new Role();

				role1.setId((long)1);
				role2.setId((long)3);
				role3.setId((long)4);
				role4.setId((long)5);
				role5.setId((long)6);
				roles.add(role1);
				roles.add(role2);
				roles.add(role3);
				roles.add(role4);
				roles.add(role5);
				
			}
			usuario.setRoles(roles);
			usuario.setPassword(passwordBcrypt);
			newUsuario = servicioUsuario.save(usuario);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el registro en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El empleado ha sido creado con exito!");
		response.put("usuario", newUsuario);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured({ "ROLE_ADMIN", "ROLE_VENTAS" })
	@PostMapping("/usuarios/e")
	public ResponseEntity<?> createUserA(@Valid @RequestBody Usuario usuario, BindingResult result) {
		Usuario newUsuario = null;
		Map<String, Object> response = new HashMap<>();
		List<Role> roles = new ArrayList<Role>();

		if (result.hasErrors()) {

			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo: '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		} 

		try {
			usuario.setUsername(usuario.getEstudiante().getNombres().concat(usuario.getEstudiante().getDni()));
			usuario.setEnabled(true);
			String passwordBcrypt = passwordEncoder.encode(usuario.getEstudiante().getDni());

			Role role1 = new Role();
			role1.setId((long) 2);	
			roles.add(role1);
			usuario.setRoles(roles);
			usuario.setPassword(passwordBcrypt);
			newUsuario = servicioUsuario.save(usuario);
			
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el registro en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El empleado ha sido creado con exito!");
		response.put("usuario", newUsuario);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

}
