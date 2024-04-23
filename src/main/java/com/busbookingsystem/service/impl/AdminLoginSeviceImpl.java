package com.busbookingsystem.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busbookingsystem.dto.AdminLoginDTO;
import com.busbookingsystem.exceptions.LoginException;
import com.busbookingsystem.model.Admin;
import com.busbookingsystem.model.CurrentAdminSession;
import com.busbookingsystem.repository.AdminDao;
import com.busbookingsystem.repository.AdminSessionDao;
import com.busbookingsystem.service.AdminLoginService;

import net.bytebuddy.utility.RandomString;

@Service
public class AdminLoginSeviceImpl implements AdminLoginService {
	@Autowired
	private AdminDao adminDao;
	@Autowired
	private AdminSessionDao adminSessionDao;
	

	@Override
	public CurrentAdminSession loginToAdminAccount(AdminLoginDTO dto) throws LoginException {
		Admin currentAdmin = adminDao.findByPhoneNumber(dto.getPhoneNumber());

		if (currentAdmin == null) {
			throw new LoginException("Please enter valid number");
		}

		Optional<CurrentAdminSession> validAdminSession = adminSessionDao.findById(currentAdmin.getAdminId());
		if (validAdminSession.isPresent()) {
			throw new LoginException("Admin already Logged-In with this number");
		}

		if (currentAdmin.getAdminPassword().equals(dto.getAdminPassword())) {
			String key = RandomString.make(6);
			CurrentAdminSession currentAdminSession = new CurrentAdminSession(currentAdmin.getAdminId(), key,
					LocalDateTime.now());
			adminSessionDao.save(currentAdminSession);
return currentAdminSession;
		}
		else {
			throw new LoginException("Please Enter a valid password");
		}
	}

	@Override
	public String logOutFromAdminAccount(String key) throws LoginException {
		CurrentAdminSession validAdminSession = adminSessionDao.findByUuid(key);
		
		if (validAdminSession==null) {
			throw new LoginException("Admin not logged in with this number");
		}
		adminSessionDao.delete(validAdminSession);
		return "Admin logged out";
	}

}
