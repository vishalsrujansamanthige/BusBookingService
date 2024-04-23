package com.busbookingsystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.busbookingsystem.model.CurrentAdminSession;
@Repository
public interface AdminSessionDao extends JpaRepository<CurrentAdminSession, Integer>{
	public CurrentAdminSession findByUuid(String uuid);

	public Optional<CurrentAdminSession> findById(Integer adminId);
}
