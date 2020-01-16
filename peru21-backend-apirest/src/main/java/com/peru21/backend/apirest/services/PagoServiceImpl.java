package com.peru21.backend.apirest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.peru21.backend.apirest.dao.IPagoDao;
import com.peru21.backend.apirest.entity.Pago;

@Service
public class PagoServiceImpl implements IPagoService{
	
	@Autowired
	private IPagoDao servicePago;
	
	
	@Override
	public Pago findById(Long id) {
		return servicePago.findById(id).orElse(null);
	}

	@Override
	public Pago save(Pago pago) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

}
