package com.peru21.backend.apirest.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.peru21.backend.apirest.entity.Distrito;
import com.peru21.backend.apirest.entity.Estudiante;

public interface IEstudianteDao extends JpaRepository<Estudiante, Long>{

	//@Query(value =  "SELECT * FROM distritos", nativeQuery = true )
	@Query("from Distrito")
	public List<Distrito> findAllDistritos();
	
	@Query(value = "select * from estudiantes where estudiantes.estado = ?1", nativeQuery = true)
	public List<Estudiante> findAllEstudiantes(String estado);
	
	@Query("select u from Estudiante u where u.dni=?1")
	public Estudiante findByDNI(String dni);
	
	@Query(value = "select count(*) from estudiantes", nativeQuery = true)
	public Long findNEstudiantes();
	
}
