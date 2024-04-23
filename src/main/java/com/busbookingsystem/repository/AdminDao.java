package com.busbookingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.busbookingsystem.model.Admin;
@Repository
public interface AdminDao extends JpaRepository<Admin, Integer> {

	
	public Admin findByPhoneNumber(String phoneNumber);
	
}

