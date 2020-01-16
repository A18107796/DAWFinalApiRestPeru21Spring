package com.peru21.backend.apirest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.peru21.backend.apirest.dao.IMatriculaDao;
import com.peru21.backend.apirest.entity.Matricula;
import com.peru21.backend.apirest.entity.Periodo;

@Service
public class MatriculaServiceImpl implements IMatriculaService{
	
	@Autowired
	private IMatriculaDao matriculaDao;
	
	
	@Override
	@Transactional(readOnly = true)
	public List<Matricula> findAll() {
		return (List<Matricula>)matriculaDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Matricula> findAll(Pageable pageable) {
		return matriculaDao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Matricula findById(Long id) {
		return matriculaDao.findById(id).orElse(null);
	}

	@Override
	public Matricula save(Matricula matricula) {

		return matriculaDao.save(matricula);
	}

	@Override
	public void delete(Long id) {
		matriculaDao.deleteById(id);
		
	}

	@Override
	public Matricula deleteLogical(Matricula matricula) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Periodo> findAllPeriodos() {
		return matriculaDao.findAllPeriodos();
	}
	

	@Override
	public Long CuentasXAlumno(Long id) {
		return matriculaDao.CuentasXAlumno(id);
	}

	@Override
	public Long findNMatriculas() {
		return matriculaDao.findNMatriculas();
	}


}
