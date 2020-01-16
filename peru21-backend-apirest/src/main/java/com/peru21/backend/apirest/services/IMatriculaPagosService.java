package com.peru21.backend.apirest.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.peru21.backend.apirest.entity.Matricula;
import com.peru21.backend.apirest.entity.MatriculaPagos;
import com.peru21.backend.apirest.entity.Pago;
import com.peru21.backend.apirest.entity.Periodo;

public interface IMatriculaPagosService {
	
	public List<MatriculaPagos> findAll();
	
	public Page<MatriculaPagos> findAll(Pageable pageable);
	
	public MatriculaPagos findById(Long id);
	
	public MatriculaPagos save(MatriculaPagos matriculaPagos);
	
	public void delete(Long id);
	
	public MatriculaPagos deleteLogical(MatriculaPagos matriculaPagos);
	
	public List<Pago> findAllPagos();
	
	public List<MatriculaPagos> insertPensiones(List<MatriculaPagos> Pensiones);
	
	public Double findGanancias(String estado);
	
	public MatriculaPagos findLastDeuda(Long id, String Estado);
	
}
