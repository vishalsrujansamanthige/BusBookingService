package com.busbookingsystem.service;

import java.util.List;

import com.busbookingsystem.exceptions.AdminException;
import com.busbookingsystem.exceptions.BusException;
import com.busbookingsystem.model.Bus;

public interface BusService {

	public Bus addBus(Bus bus, String key) throws BusException, AdminException;

	public Bus updateBus(Bus bus, String key) throws BusException, AdminException;

	public Bus deleteBus( Integer busId,String key) throws BusException, AdminException;

	public Bus viewBus(Integer busId) throws BusException;
	public List<Bus> viewBusType(String busType) throws BusException;
	
	public List<Bus> viewAllBuses() throws BusException;
}
