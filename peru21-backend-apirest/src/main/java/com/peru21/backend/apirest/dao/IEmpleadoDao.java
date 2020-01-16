package com.peru21.backend.apirest.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.peru21.backend.apirest.entity.Area;
import com.peru21.backend.apirest.entity.Cargo;
import com.peru21.backend.apirest.entity.Distrito;
import com.peru21.backend.apirest.entity.Empleado;

public interface IEmpleadoDao extends JpaRepository<Empleado, Long>{
		

	//@Query(value =  "SELECT * FROM distritos", nativeQuery = true )
	@Query("from Distrito")
	public List<Distrito> findAllDistritos();
	
	@Query("from Cargo")
	public List<Cargo> findAllCargos();
	
	@Query("from Area")
	public List<Area> findAllArea();
	
	
	@Query(value = "select count(*) from empleados", nativeQuery = true)
	public Long findNEmpleados();
	

	@Query(value = "select * from empleados where id_cargo = 4", nativeQuery = true)
	public List<Empleado> findAllDocentes();
	
}	
