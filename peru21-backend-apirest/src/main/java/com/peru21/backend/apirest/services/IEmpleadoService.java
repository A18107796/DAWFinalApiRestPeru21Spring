package com.peru21.backend.apirest.services;

import java.io.FileNotFoundException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.peru21.backend.apirest.entity.Area;
import com.peru21.backend.apirest.entity.Cargo;
import com.peru21.backend.apirest.entity.Distrito;
import com.peru21.backend.apirest.entity.Empleado;

import net.sf.jasperreports.engine.JRException;

public interface IEmpleadoService {
	
	public List<Empleado> findAll();
	
	public Page<Empleado> findAll(Pageable pageable);
	
	public Empleado findById(Long id);
	
	public Empleado save(Empleado empleado);
	
	public void delete(Long id);
	
	public Empleado deleteLogical(Empleado empleado);
	
	public List<Distrito> findAllDistritos();
	
	public List<Cargo> findAllCargos();
	
	public List<Area> findAllAreas();
	
	public Long findNEmpleados();
	
	public List<Empleado> findAllDocentes();
	
	public String exportReport(String format)  throws FileNotFoundException, JRException ;
}
