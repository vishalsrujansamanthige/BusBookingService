package com.busbookingsystem.service;

import com.busbookingsystem.exceptions.AdminException;
import com.busbookingsystem.model.Admin;

public interface AdminService {
	public Admin createAdmin(Admin admin) throws AdminException;
public 	Admin updateAdmin(Admin admin,String key) throws AdminException;
}
