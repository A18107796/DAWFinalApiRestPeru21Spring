package com.peru21.backend.apirest.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.stream.Collectors;

import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
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
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.peru21.backend.apirest.entity.Area;
import com.peru21.backend.apirest.entity.Cargo;
import com.peru21.backend.apirest.entity.Distrito;
import com.peru21.backend.apirest.entity.Empleado;
import com.peru21.backend.apirest.entity.Estudiante;
import com.peru21.backend.apirest.services.IEmpleadoService;
import com.peru21.backend.apirest.services.IUploadFileService;

import net.sf.jasperreports.engine.JRException;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class EmpleadoRestController {

	@Autowired
	private IEmpleadoService empleadoService;

	@Autowired
	private IUploadFileService uploadService;


	
	@GetMapping("/empleados")
	public List<Empleado> index() {
		return empleadoService.findAll();
	}
	
	@GetMapping("/empleados/export/{format}")
	public ResponseEntity<?> exportReport(@PathVariable String format) throws FileNotFoundException, JRException {
		
		Map<String, String> response = new HashMap<>();
		String direccion = empleadoService.exportReport(format);
		response.put("path", direccion);
		
		return new ResponseEntity<Map<String, String>>(response, HttpStatus.OK);
		
	} 
	
	@GetMapping("/empleados/page/{page}")
	public Page<Empleado> index(@PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page, 10);
		return empleadoService.findAll(pageable);
	}
	
	
	@Secured({"ROLE_ADMIN", "ROLE_VENTAS","ROLE_COORDINACION"})
	@GetMapping("/empleados/{id}")
	public ResponseEntity<?> showById(@PathVariable Long id) {

		Empleado empleado = null;
		Map<String, Object> response = new HashMap<>();

		try {
			empleado = empleadoService.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (empleado == null) {
			response.put("mensaje", "El cliente ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Empleado>(empleado, HttpStatus.OK);
	}

	
	@Secured({"ROLE_ADMIN", "ROLE_VENTAS","ROLE_COORDINACION"})
	@PostMapping("/empleados")
	// requeesbody llega el empleado en formato Json y se transforma a objeto
	// empleado
	public ResponseEntity<?> create(@Valid @RequestBody Empleado empleado, BindingResult result) {
		Empleado newEmpleado = null;
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
			calendar.setTime(empleado.getFecha_nac());
			calendar.add(Calendar.DAY_OF_YEAR, 2);
			empleado.setFecha_nac(calendar.getTime());
			newEmpleado = empleadoService.save(empleado);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el registro en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El empleado ha sido creado con exito!");
		response.put("empleado", newEmpleado);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@Secured({"ROLE_ADMIN", "ROLE_VENTAS","ROLE_COORDINACION"})
	@PutMapping("/empleados/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Empleado empleado, BindingResult result,
			@PathVariable Long id) {
		Empleado empleadoActual = empleadoService.findById(id);
		Empleado empleadoActualizado = null;

		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {

			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo: '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		if (empleadoActual == null) {
			response.put("mensaje", "No se puede editar, el emplado ID: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(empleado.getFecha_nac());
			calendar.add(Calendar.DAY_OF_YEAR, 2);

			empleadoActual.setNombre(empleado.getNombre());
			empleadoActual.setApellido_pa(empleado.getApellido_pa());
			empleadoActual.setApellido_ma(empleado.getApellido_ma());
			empleadoActual.setDni(empleado.getDni());
			empleadoActual.setSexo(empleado.getSexo());
			empleadoActual.setDireccion(empleado.getDireccion());
			empleadoActual.setTelefono(empleado.getTelefono());
			empleadoActual.setFecha_nac(calendar.getTime());
			empleadoActual.setDistrito(empleado.getDistrito());
			empleadoActual.setCargo(empleado.getCargo());
			empleadoActual.setArea(empleado.getArea());
			empleadoActualizado = empleadoService.save(empleadoActual);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El empleado ha sido actualizado con exito!");
		response.put("empleado", empleadoActualizado);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

	}

	@Secured({"ROLE_ADMIN"})
	@DeleteMapping("/empleados/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {

		Map<String, Object> response = new HashMap<>();

		try {
			/* Borrar Foto antes de eliminar cliente */
			Empleado empleado = empleadoService.findById(id);
			String nombreFotoAnterior = empleado.getFoto();

			uploadService.eliminar(nombreFotoAnterior);

			/* **** */
			empleadoService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El empleado ha sido eliminado con exito!");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_VENTAS","ROLE_COORDINACION"})
	@PostMapping("/empleados/upload")
	public ResponseEntity<?> upload(@RequestParam("archivo") MultipartFile archivo, @RequestParam("id") Long id) {
		Map<String, Object> response = new HashMap<>();

		Empleado empleado = empleadoService.findById(id);

		if (!archivo.isEmpty()) {

			String nombreArchivo = null;
			try {
				nombreArchivo = uploadService.copiarImg(archivo);
			} catch (IOException e) {
				response.put("mensaje", "Error al subir la imagen");
				response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			// Validar si existe foto anterior
			String nombreFotoAnterior = empleado.getFoto();

			uploadService.eliminar(nombreFotoAnterior);

			empleado.setFoto(nombreArchivo);

			empleadoService.save(empleado);
			response.put("empleado", empleado);
			response.put("mensahe", "Has subido correctamente la imagen: " + nombreArchivo);
		}
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@GetMapping("/uploads/img/{nombreFoto:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String nombreFoto) {

		Resource recurso = null;

		try {
			recurso = uploadService.cargar(nombreFoto);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		HttpHeaders cabecera = new HttpHeaders();
		// el 2do campo fuerza a descargar el atributo
		cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"");

		return new ResponseEntity<Resource>(recurso, cabecera, HttpStatus.OK);
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_VENTAS"})
	@GetMapping("/empleados/distritos")
	public List<Distrito> listarDistritos(){
		return empleadoService.findAllDistritos();
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_VENTAS"})
	@GetMapping("/empleados/areas")
	public List<Area> listarCargos(){
		return empleadoService.findAllAreas();
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_VENTAS"})
	@GetMapping("/empleados/cargos")
	public List<Cargo> listarAreas(){
		return empleadoService.findAllCargos();
	}
	
	@GetMapping(value ="/empleados/nregistros")
	public ResponseEntity<?> getDeudas() {
		Long nfunciones;
		Map<String, Long> response = new HashMap<>();
		nfunciones = empleadoService.findNEmpleados();
		response.put("registros", nfunciones);
		return new ResponseEntity<Map<String, Long>>(response, HttpStatus.OK);
	}
	
	@GetMapping("/empleados/docentes")
	public List<Empleado> findAllDocentes() {
		return empleadoService.findAllDocentes();
	}
}
