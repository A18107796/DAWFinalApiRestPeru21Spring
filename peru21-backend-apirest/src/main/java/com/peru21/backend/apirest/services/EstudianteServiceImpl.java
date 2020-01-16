package com.peru21.backend.apirest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.peru21.backend.apirest.dao.IEstudianteDao;
import com.peru21.backend.apirest.entity.Distrito;
import com.peru21.backend.apirest.entity.Estudiante;

@Service
public class EstudianteServiceImpl implements IEstudianteService{
	
	@Autowired
	private IEstudianteDao estudianteDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Estudiante> findAll() {
		return (List<Estudiante>)estudianteDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Estudiante> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return estudianteDao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Estudiante findById(Long id) {
		return estudianteDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Estudiante save(Estudiante estudiante) {
		// TODO Auto-generated method stub
		return estudianteDao.save(estudiante);
	}

	@Override
	public void delete(Long id) {
		estudianteDao.deleteById(id);
	}

	@Override
	public Estudiante deleteLogical(Estudiante estudiante) {
		return estudianteDao.save(estudiante);
	}

	@Override
	public List<Distrito> findAllDistritos() {
		return estudianteDao.findAllDistritos();
	}

	@Override
	public List<Estudiante> findAll(String estado) {
		return estudianteDao.findAllEstudiantes(estado);
	}

	@Override
	@Transactional(readOnly = true)
	public Estudiante findByDNI(String dni) {
		return estudianteDao.findByDNI(dni);
	}

	@Override
	public Long findNEstudiantes() {
		return estudianteDao.findNEstudiantes();
	}

}
