package com.busbookingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.busbookingsystem.model.CurrentUserSession;
@Repository
public interface UserSessionDao extends JpaRepository<CurrentUserSession,Integer >{
public CurrentUserSession findByUuid(String uuid);
}
