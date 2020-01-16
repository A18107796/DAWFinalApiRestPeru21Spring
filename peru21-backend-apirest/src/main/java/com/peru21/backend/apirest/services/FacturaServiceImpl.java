package com.peru21.backend.apirest.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import com.peru21.backend.apirest.dao.IFacturaDao;
import com.peru21.backend.apirest.entity.Empleado;
import com.peru21.backend.apirest.entity.Factura;
import com.peru21.backend.apirest.entity.Moneda;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class FacturaServiceImpl implements IFacturaService{
	
	@Autowired
	private IFacturaDao facturaDao;
	
	
	@Override
	@Transactional(readOnly = true)
	public List<Factura> findAll() {
		return (List<Factura>) facturaDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Factura findById(Long id) {
		return facturaDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Factura save(Factura factura) {
		return facturaDao.save(factura);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		facturaDao.deleteById(id);
	}

	@Override
	@Transactional
	public Factura deleteLogical(Factura factura) {
		factura.setEstado("Inactivo");
		return facturaDao.save(factura);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Moneda> findAllMonedas() {
		return (List<Moneda>) facturaDao.findAllMonedas();
	}

	@Override
	public String exportReport(String format) throws FileNotFoundException, JRException {
		Factura factura = facturaDao.findById((long) 1).orElse(null);
		
		String path = "src/main/resources/static/images";
		File file = ResourceUtils.getFile("classpath:factura.jrxml");
		JasperReport jasper = JasperCompileManager.compileReport(file.getAbsolutePath());

		JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource((Collection<?>) factura);

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("gain java", "knowedge");

		JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, parameters, ds);

		if (format.equalsIgnoreCase("html")) {
			JasperExportManager.exportReportToHtmlFile(jasperPrint, path +  "/factura.html");
		}
		if (format.equalsIgnoreCase("pdf")) {	
			JasperExportManager.exportReportToPdfFile(jasperPrint, path +  "/factura.pdf");
		}
		
		return "path : " + path;
	}
	
	

}
