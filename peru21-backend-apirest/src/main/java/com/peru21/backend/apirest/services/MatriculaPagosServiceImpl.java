package com.peru21.backend.apirest.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.peru21.backend.apirest.dao.IMatriculaPagosDao;
import com.peru21.backend.apirest.entity.Estudiante;
import com.peru21.backend.apirest.entity.MatriculaPagos;
import com.peru21.backend.apirest.entity.Pago;
@Service
public class MatriculaPagosServiceImpl implements IMatriculaPagosService {
	
	@Autowired
	private IMatriculaPagosDao matriculaPDao;
		
	@Override
	public List<MatriculaPagos> findAll() {
		return (List<MatriculaPagos>)matriculaPDao.findAll();
	}

	@Override
	public Page<MatriculaPagos> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MatriculaPagos findById(Long id) {
		// TODO Auto-generated method stub
		return matriculaPDao.findById(id).orElse(null);
	}

	@Override
	public MatriculaPagos save(MatriculaPagos matriculaPagos) {
		return matriculaPDao.save(matriculaPagos);
	}

	@Override
	public void delete(Long id) {
		matriculaPDao.deleteById(id);
		
	}

	@Override
	public MatriculaPagos deleteLogical(MatriculaPagos matriculaPagos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Pago> findAllPagos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public List<MatriculaPagos> insertPensiones(List<MatriculaPagos> pensiones) {
		return matriculaPDao.saveAll(pensiones);
	}

	@Override
	public Double findGanancias(String estado) {
		return matriculaPDao.findGanancias(estado);
	}

	@Override
	public MatriculaPagos findLastDeuda(Long id, String Estado) {
		return matriculaPDao.findLastDeuda(id, Estado);
	}


}
