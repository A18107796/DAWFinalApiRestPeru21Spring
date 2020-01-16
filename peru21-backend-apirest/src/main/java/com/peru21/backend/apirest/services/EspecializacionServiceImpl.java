package com.peru21.backend.apirest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.peru21.backend.apirest.dao.IEspecializacionDao;
import com.peru21.backend.apirest.entity.Curso;
import com.peru21.backend.apirest.entity.Especializacion;

@Service
public class EspecializacionServiceImpl implements IEspecializacionService{

	@Autowired
	private IEspecializacionDao especializacionDao;
	
	@Override
	public Especializacion findById(Long id) {
		return especializacionDao.findById(id).orElse(null);
	}

	@Override
	public Especializacion save(Especializacion especializacion) {
		return especializacionDao.save(especializacion);
	}

	@Override
	public void delete(Long id) {
		especializacionDao.deleteById(id);
		
	}

	@Override
	public List<Especializacion> findAll() {
		return especializacionDao.findAll();
	}

	@Override
	public Especializacion deleteLogical(Especializacion especializacion) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Curso> findAllCursos() {
		return especializacionDao.findAllCursos();
	}

	
}
