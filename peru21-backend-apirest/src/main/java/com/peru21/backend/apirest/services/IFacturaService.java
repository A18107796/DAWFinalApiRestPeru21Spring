package com.peru21.backend.apirest.services;

import java.io.FileNotFoundException;
import java.util.List;

import com.peru21.backend.apirest.entity.Factura;
import com.peru21.backend.apirest.entity.Moneda;

import net.sf.jasperreports.engine.JRException;

public interface IFacturaService {
	
	public List<Factura> findAll();
	
	public Factura findById(Long id);
	
	public Factura save(Factura factura);
	
	public void delete(Long id);
	
	public Factura deleteLogical(Factura factura);
	
	public List<Moneda> findAllMonedas();
	

	public String exportReport(String format)  throws FileNotFoundException, JRException ;
}
