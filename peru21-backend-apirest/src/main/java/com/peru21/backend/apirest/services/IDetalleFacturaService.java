package com.peru21.backend.apirest.services;

import java.util.List;


import com.peru21.backend.apirest.entity.FacturaDetalle;


public interface IDetalleFacturaService {
	
	public List<FacturaDetalle> findAll();
	
	public FacturaDetalle findById(Long id);
	
	public FacturaDetalle save(FacturaDetalle facturaDetalle);
	
	public void delete(Long id);
	
	public FacturaDetalle deleteLogical(FacturaDetalle facturaDetalle);
	
}
