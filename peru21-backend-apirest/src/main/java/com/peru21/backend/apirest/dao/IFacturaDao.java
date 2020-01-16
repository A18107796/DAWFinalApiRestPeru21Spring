package com.peru21.backend.apirest.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.peru21.backend.apirest.entity.Estudiante;
import com.peru21.backend.apirest.entity.Factura;
import com.peru21.backend.apirest.entity.Moneda;

public interface IFacturaDao extends JpaRepository<Factura, Long>{
	
	@Query("from Moneda")
	public List<Moneda> findAllMonedas();
	
	@Query( value =  "select * from facturas where id_matricula =?1", nativeQuery = true)
	public Estudiante findByDNI(String dni);

}
