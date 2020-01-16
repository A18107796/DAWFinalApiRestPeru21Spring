package com.peru21.backend.apirest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.peru21.backend.apirest.dao.IFacturaDetalleDao;
import com.peru21.backend.apirest.entity.FacturaDetalle;

@Service
public class DetalleFacturaServiceImpl implements IDetalleFacturaService {

	@Autowired
	private IFacturaDetalleDao fdetalleDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<FacturaDetalle> findAll() {
		return (List<FacturaDetalle>) fdetalleDao.findAll(); 
	}

	@Override
	@Transactional(readOnly = true)
	public FacturaDetalle findById(Long id) {
		return fdetalleDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public FacturaDetalle save(FacturaDetalle facturaDetalle) {
		return fdetalleDao.save(facturaDetalle);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		fdetalleDao.deleteById(id);
		
	}

	@Override
	@Transactional
	public FacturaDetalle deleteLogical(FacturaDetalle facturaDetalle) {
		// TODO Auto-generated method stub
		return null;
	}

}
