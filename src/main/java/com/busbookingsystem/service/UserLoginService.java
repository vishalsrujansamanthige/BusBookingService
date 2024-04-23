package com.busbookingsystem.service;

import com.busbookingsystem.dto.UserLoginDTO;
import com.busbookingsystem.exceptions.LoginException;
import com.busbookingsystem.model.CurrentUserSession;

public interface UserLoginService {
	
	
	
	public CurrentUserSession logIntoUserAccount(UserLoginDTO dto)throws LoginException;

	public String logOutFromUserAccount(String key)throws LoginException;

}
