package com.peru21.backend.apirest.controllers;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.peru21.backend.apirest.entity.Empleado;
import com.peru21.backend.apirest.entity.Estudiante;
import com.peru21.backend.apirest.entity.Factura;
import com.peru21.backend.apirest.entity.FacturaDetalle;
import com.peru21.backend.apirest.entity.Matricula;
import com.peru21.backend.apirest.entity.MatriculaPagos;
import com.peru21.backend.apirest.entity.Moneda;
import com.peru21.backend.apirest.services.EstudianteServiceImpl;
import com.peru21.backend.apirest.services.FacturaServiceImpl;
import com.peru21.backend.apirest.services.UploadFileServiceImpl;

import net.sf.jasperreports.engine.JRException;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class FacturaRestController {

	private final Logger log = LoggerFactory.getLogger(UploadFileServiceImpl.class);

	@Autowired
	private FacturaServiceImpl facturaService;

	@GetMapping("/facturas")
	public List<Factura> index() {
		return facturaService.findAll();
	}
	
	@GetMapping("/facturas/export/{format}")
	public String exportReport(@PathVariable String format) throws FileNotFoundException, JRException {
		return facturaService.exportReport(format);
	} 

	@GetMapping("/facturas/monedas")
	public List<Moneda> monedas() {
		return facturaService.findAllMonedas();
	}

	@GetMapping("/facturas/{id}")
	public ResponseEntity<?> showById(@PathVariable Long id) {

		Factura factura = null;
		Map<String, Object> response = new HashMap<>();

		try {
			factura = facturaService.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (factura == null) {
			response.put("mensaje", "La factura ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Factura>(factura, HttpStatus.OK);
	}

	@Secured({ "ROLE_ADMIN", "ROLE_VENTAS" })
	@PostMapping("/facturas")
	public ResponseEntity<?> create(@Valid @RequestBody Factura factura, BindingResult result) {
		Factura newFactura = null;
		Map<String, Object> response = new HashMap<>();
		List<FacturaDetalle> detalles = new ArrayList<FacturaDetalle>();
		if (result.hasErrors()) {

			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo: '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		try {

			factura.setFechaPago(new Date());
			factura.setEstado("Pagada");
			newFactura = facturaService.save(factura);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el registro en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La factura ha sido registrada");
		response.put("factura", newFactura);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@Secured({ "ROLE_ADMIN", "ROLE_VENTAS" })
	@PutMapping("/facturas/anular/{id}")
	public ResponseEntity<?> anularFactura(@Valid @RequestBody Factura factura, BindingResult result,
			@PathVariable Long id) {
		Factura facturaActual = facturaService.findById(id);
		Factura facturaAnulada = null;

		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {

			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo: '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		if (facturaActual == null) {
			response.put("mensaje", "No se puede anular, la factura ID: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		try {
			
			facturaActual.setEstado("Anulada");
			facturaAnulada = facturaService.save(facturaActual);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al anular factura en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La factura a sido anulada");
		response.put("factura", facturaAnulada);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

	}

}
