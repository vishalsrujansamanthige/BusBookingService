package com.busbookingsystem.service;

import com.busbookingsystem.dto.AdminLoginDTO;
import com.busbookingsystem.exceptions.LoginException;
import com.busbookingsystem.model.CurrentAdminSession;

public interface AdminLoginService {

	
	public CurrentAdminSession loginToAdminAccount(AdminLoginDTO dto) throws LoginException;
	public String logOutFromAdminAccount(String key) throws LoginException;
}
