package com.peru21.backend.apirest.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.peru21.backend.apirest.entity.Pago;

public interface IPagoDao extends JpaRepository<Pago, Long >{

}
