package com.busbookingsystem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busbookingsystem.exceptions.AdminException;
import com.busbookingsystem.model.Admin;
import com.busbookingsystem.model.CurrentAdminSession;
import com.busbookingsystem.repository.AdminDao;
import com.busbookingsystem.repository.AdminSessionDao;
import com.busbookingsystem.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
	private AdminDao adminDao;

	@Autowired
	private AdminSessionDao adminSessionDao;

	@Override
	public Admin createAdmin(Admin admin) throws AdminException {
		Admin currentAdmin = adminDao.findByPhoneNumber(admin.getPhoneNumber());

		if (currentAdmin != null)

			throw new AdminException("Admin already registered with this mobile number");
		return adminDao.save(admin);

	}

	@Override
	public Admin updateAdmin(Admin admin, String key) throws AdminException {
		CurrentAdminSession loggedInAdmin = adminSessionDao.findByUuid(key);
		if (loggedInAdmin == null) {
			throw new AdminException("Please provide a valid key");
		}
		if (admin.getAdminId() == loggedInAdmin.getAdminId()) {
			return adminDao.save(admin);
		} else {

			throw new AdminException("Invalid Admin Details,Login First");
		}
	}

}
