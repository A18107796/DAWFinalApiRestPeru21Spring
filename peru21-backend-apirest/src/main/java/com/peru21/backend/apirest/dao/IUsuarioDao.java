package com.peru21.backend.apirest.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.peru21.backend.apirest.entity.Empleado;
import com.peru21.backend.apirest.entity.Usuario;

public interface IUsuarioDao extends CrudRepository<Usuario, Long> {

	public Usuario findByUsername(String username);

	@Query("select u from Usuario u where u.username=?1")
	public Usuario findByUserName2(String username);
	
	
	@Query(value = "select empleados.id, empleados.apellido_ma, empleados.apellido_pa, empleados.direccion, empleados.dni, empleados.fecha_nac, empleados.foto, empleados.nombre, empleados.sexo, empleados.telefono, empleados.id_distrito from empleados, usuarios where empleados.id = usuarios.id and usuarios.username = ?1", nativeQuery = true)
	public Empleado findEmpleado(String username);
}
