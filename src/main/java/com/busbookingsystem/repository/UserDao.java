package com.busbookingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.busbookingsystem.model.User;

public interface UserDao extends JpaRepository<User, Integer> {
	public User findByMobileNumber(String mobileNumber);
}
