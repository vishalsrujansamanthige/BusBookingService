package com.busbookingsystem.service.impl;



import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busbookingsystem.dto.UserLoginDTO;
import com.busbookingsystem.exceptions.LoginException;
import com.busbookingsystem.model.CurrentUserSession;
import com.busbookingsystem.model.User;
import com.busbookingsystem.repository.UserDao;
import com.busbookingsystem.repository.UserSessionDao;
import com.busbookingsystem.service.UserLoginService;

import net.bytebuddy.utility.RandomString;

@Service
public class UserLoginServiceImpl implements UserLoginService{
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserSessionDao userSessionDao;

	@Override
	public CurrentUserSession logIntoUserAccount(UserLoginDTO dto) throws LoginException {

		User existingUser= userDao.findByMobileNumber(dto.getMobileNumber());
		
		if(existingUser == null) throw new LoginException("Please Enter a valid mobile number");
		
		
		Optional<CurrentUserSession> validUserSessionOpt =  userSessionDao.findById(existingUser.getUserId());
		
		
		if(validUserSessionOpt.isPresent()) {
			
			throw new LoginException("User already Logged In with this number");
		}
		
		if(existingUser.getPassword().equals(dto.getPassword())) {
			
			String key= RandomString.make(6);
			
			CurrentUserSession currentUserSession = new CurrentUserSession(existingUser.getUserId(),key,LocalDateTime.now());
			
			userSessionDao.save(currentUserSession);

			return currentUserSession;
		}
		else
			throw new LoginException("Please Enter a valid password!");
	}

	@Override
	public String logOutFromUserAccount(String key) throws LoginException {
				
		CurrentUserSession validUserSession = userSessionDao.findByUuid(key);
		
		if(validUserSession == null) {
			throw new LoginException("User Not Logged In with this number");
			
		}
		
		userSessionDao.delete(validUserSession);
		
		return "User Logged Out !";
	}

}