package com.busbookingsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.busbookingsystem.model.Bus;
@Repository
public interface BusDao extends JpaRepository<Bus, Integer>{

	public List<Bus> findByBusType(String busType);
}