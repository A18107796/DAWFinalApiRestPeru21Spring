package com.peru21.backend.apirest.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import com.peru21.backend.apirest.dao.IEmpleadoDao;
import com.peru21.backend.apirest.entity.Area;
import com.peru21.backend.apirest.entity.Cargo;
import com.peru21.backend.apirest.entity.Distrito;
import com.peru21.backend.apirest.entity.Empleado;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class EmpleadoServiceImpl implements IEmpleadoService {

	@Autowired
	private IEmpleadoDao empleadoDao;

	@Override
	@Transactional(readOnly = true)
	public List<Empleado> findAll() {
		return (List<Empleado>) empleadoDao.findAll();
	}

	public String exportReport(String format) throws FileNotFoundException, JRException {
		
		String path = "uploads";
		Path ruta = Paths.get("uploads").resolve("empleados.html").toAbsolutePath();
		File html = ruta.toFile();
		if(html.exists()) {
			System.out.println("ruta:" + ruta.toString());
			html.delete();
		}
		
		List<Empleado> lista = empleadoDao.findAll();
		
		File file = ResourceUtils.getFile("classpath:empleados.jrxml");
		JasperReport jasper = JasperCompileManager.compileReport(file.getAbsolutePath());

		JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(lista);

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("gain java", "knowedge");

		JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, parameters, ds);

		if (format.equalsIgnoreCase("html")) {
			JasperExportManager.exportReportToHtmlFile(jasperPrint, path +  "/empleados.html");
		}
		if (format.equalsIgnoreCase("pdf")) {	
			JasperExportManager.exportReportToPdfFile(jasperPrint, path +  "/empleados.pdf");
		}
		
		
	
		return path;
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Empleado> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return empleadoDao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Empleado findById(Long id) {
		// TODO Auto-generated method stub
		return empleadoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Empleado save(Empleado empleado) {
		// TODO Auto-generated method stub
		return empleadoDao.save(empleado);
	}

	@Override
	public void delete(Long id) {
		empleadoDao.deleteById(id);
	}

	@Override
	public Empleado deleteLogical(Empleado empleado) {
		return save(empleado);

	}

	@Override
	public List<Distrito> findAllDistritos() {
		return empleadoDao.findAllDistritos();
	}

	@Override
	public Long findNEmpleados() {
		return empleadoDao.findNEmpleados();
	}

	@Override
	public List<Cargo> findAllCargos() {
		return empleadoDao.findAllCargos();
	}

	@Override
	public List<Area> findAllAreas() {
		return empleadoDao.findAllArea();
	}

	@Override
	public List<Empleado> findAllDocentes() {
		return empleadoDao.findAllDocentes();
	}

}
