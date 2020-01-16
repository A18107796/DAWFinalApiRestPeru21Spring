package com.peru21.backend.apirest.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.peru21.backend.apirest.entity.MatriculaPagos;
import com.peru21.backend.apirest.entity.Pago;
import com.peru21.backend.apirest.entity.Periodo;

public interface IMatriculaPagosDao extends JpaRepository<MatriculaPagos, Long>{
	
	@Query(value = "SELECT SUM(pagos.monto) as total FROM matriculas_pagos,pagos WHERE matriculas_pagos.id_pago = pagos.id AND matriculas_pagos.estado = ?1", nativeQuery = true)
	public Double findGanancias(String estado);
	
	@Query(value = "SELECT t1.* FROM (SELECT * FROM  matriculas_pagos WHERE fecha_venc>=CURDATE() and id_matricula = ?1 and estado = ?2 ORDER BY estado, fecha_venc DESC)t1 GROUP BY estado", nativeQuery = true)
	public MatriculaPagos findLastDeuda(Long id, String Estado);
	
	
	
}
