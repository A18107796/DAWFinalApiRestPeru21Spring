package com.peru21.backend.apirest.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.peru21.backend.apirest.entity.FacturaDetalle;

public interface IFacturaDetalleDao extends JpaRepository<FacturaDetalle, Long>{

}
