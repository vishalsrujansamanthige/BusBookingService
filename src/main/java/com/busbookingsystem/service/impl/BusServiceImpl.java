package com.busbookingsystem.service.impl;

import java.time.LocalDate;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busbookingsystem.exceptions.AdminException;
import com.busbookingsystem.exceptions.BusException;
import com.busbookingsystem.model.Bus;
import com.busbookingsystem.model.CurrentAdminSession;
import com.busbookingsystem.model.Route;
import com.busbookingsystem.repository.AdminSessionDao;
import com.busbookingsystem.repository.BusDao;
import com.busbookingsystem.repository.RouteDao;
import com.busbookingsystem.service.BusService;

@Service
public class BusServiceImpl implements BusService {

	@Autowired
	private BusDao busDao;

	@Autowired
	private RouteDao routeDao;

	@Autowired
	private AdminSessionDao adminSessionDao;

	@Override
	public Bus addBus(Bus bus, String key) throws BusException, AdminException {

		CurrentAdminSession loggedinAdmin = adminSessionDao.findByUuid(key);
		if (loggedinAdmin == null) {
			throw new AdminException("Please provide a valid key to add bus");
		}

		Route route = routeDao.findByRouteFromAndRouteTo(bus.getRouteFrom(), bus.getRouteTo());
		if (route != null) {
			route.getBusList().add(bus);
			bus.setRoute(route);
			return busDao.save(bus);
		} else {
			throw new BusException("Bus details are incorrect");
		}
	}

	@Override
	public Bus updateBus(Bus bus, String key) throws BusException, AdminException {
		CurrentAdminSession loggedInAdmin = adminSessionDao.findByUuid(key);

		if (loggedInAdmin == null) {
			throw new AdminException("Please provide a valid key to update bus!");
		}

		Optional<Bus> currentBus = busDao.findById(bus.getBusId());
		if (currentBus.isPresent()) {

			Bus existingBus = currentBus.get();

			if (existingBus.getAvailableSeats() != existingBus.getTotalSeats()) {
				throw new BusException("Cannot update already scheduled bus!");

			}

			Route route = routeDao.findByRouteFromAndRouteTo(bus.getRouteFrom(), bus.getRouteTo());
			if (route == null) {
				throw new BusException("Invalid route!");
			}
			bus.setRoute(route);
			return busDao.save(bus);

		} else {
			throw new BusException("Bus doesn't exist with busId : " + bus.getBusId());

		}

	}

	@Override
	public Bus deleteBus(Integer busId, String key) throws BusException, AdminException {
		CurrentAdminSession loggedInAdmin = adminSessionDao.findByUuid(key);

		if (loggedInAdmin == null) {
			throw new AdminException("Please provide a valid key to delete bus!");
		}

		Optional<Bus> bus = busDao.findById(busId);
		if (bus.isPresent()) {
			Bus existingBus = bus.get();

			if (LocalDate.now().isBefore(existingBus.getBusJourneyDate())
					&& existingBus.getAvailableSeats() != existingBus.getTotalSeats()) {
				throw new BusException(
						"Cannot delete as the bus is scheduled and reservations are booked for the bus.");

			}
			busDao.delete(existingBus);
			return existingBus;
		} else {
			throw new BusException("Bus doesn't exist with busId : " + busId);
		}
	}

	@Override
	public List<Bus> viewBusType(String busType) throws BusException {
		List<Bus> findByBusType = busDao.findByBusType(busType);
		if (findByBusType.size() > 0) {
			return findByBusType;
		} else {
			throw new BusException("There is no bus of type " + busType);
		}
	}

	@Override
	public List<Bus> viewAllBuses() throws BusException {
		List<Bus> allBuses = busDao.findAll();
		if (allBuses.size() > 0) {
			return allBuses;
		}
		return null;
	}

	@Override
	public Bus viewBus(Integer busId) throws BusException {
		Optional<Bus> bus = busDao.findById(busId);
		if (bus.isPresent()) {
			Bus bus2 = bus.get();

			return bus2;
		} else {
			throw new BusException("Bus doesn't exist with busId : " + busId);
		}
	}

}
