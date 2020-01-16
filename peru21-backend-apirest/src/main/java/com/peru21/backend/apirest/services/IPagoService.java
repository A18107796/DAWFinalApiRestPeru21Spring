package com.peru21.backend.apirest.services;

import com.peru21.backend.apirest.entity.Curso;
import com.peru21.backend.apirest.entity.Pago;

public interface IPagoService {
	
	public Pago findById(Long id);

	public Pago save(Pago pago);

	public void delete(Long id);
	
}
