package com.busbookingsystem.service.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busbookingsystem.dto.ReservationDTO;
import com.busbookingsystem.exceptions.AdminException;
import com.busbookingsystem.exceptions.BusException;
import com.busbookingsystem.exceptions.ReservationException;
import com.busbookingsystem.exceptions.UserException;
import com.busbookingsystem.model.Bus;
import com.busbookingsystem.model.CurrentAdminSession;
import com.busbookingsystem.model.CurrentUserSession;
import com.busbookingsystem.model.Reservation;
import com.busbookingsystem.model.User;
import com.busbookingsystem.repository.AdminSessionDao;
import com.busbookingsystem.repository.BusDao;
import com.busbookingsystem.repository.ReservationDao;
import com.busbookingsystem.repository.UserDao;
import com.busbookingsystem.repository.UserSessionDao;
import com.busbookingsystem.service.ReservationService;
@Service
public class ReservationServiceImpl implements ReservationService {

	@Autowired
	private ReservationDao reservationDao;

	@Autowired
	private BusDao busDao;

	@Autowired
	private UserSessionDao userSessionDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private AdminSessionDao adminSessionDao;

	@Override
	public Reservation addReservation(ReservationDTO reservationDTO, String key)
			throws ReservationException, BusException, UserException {

		CurrentUserSession loggedInUser = userSessionDao.findByUuid(key);

		if (loggedInUser == null) {
			throw new UserException("Please provide a valid to reserve seats");
		}

		User user = userDao.findById(loggedInUser.getUserId()).orElseThrow(() -> new UserException("User not found!"));

		Bus bus = busDao.findById(reservationDTO.getBusDTO().getBusId())
				.orElseThrow(() -> new ReservationException("Invalid Bus details"));
		if (reservationDTO.getJourneyDate().isBefore(LocalDate.now())) {
			throw new ReservationException("Please enter future date!");

		}

		if (!bus.getBusJourneyDate().isEqual(reservationDTO.getJourneyDate()))
			throw new ReservationException("Bus is not available on " + reservationDTO.getJourneyDate());

		if (!reservationDTO.getSource().equalsIgnoreCase(bus.getRouteFrom())
				|| !reservationDTO.getDestination().equalsIgnoreCase(bus.getRouteTo()))
			throw new ReservationException("Bus is not available on route : " + reservationDTO.getSource() + " - "
					+ reservationDTO.getDestination());

		int seatsAvailable = bus.getAvailableSeats();
		if (seatsAvailable < reservationDTO.getNoOfSeatsToBook())
			throw new ReservationException("Reservation Failed! Available seats: " + seatsAvailable);
		Reservation reservation = new Reservation();

		bus.setAvailableSeats(seatsAvailable - reservationDTO.getNoOfSeatsToBook());
		Bus updatedBus = busDao.save(bus);

		reservation.setBus(updatedBus);

		reservation.setReservationStatus("Successfull");
		reservation.setReservationDate(LocalDate.now());
		reservation.setReservationTime(LocalTime.now());
		reservation.setSource(bus.getRouteFrom());
		reservation.setDestination(bus.getRouteTo());
		reservation.setNoOfSeatsReserved(reservationDTO.getNoOfSeatsToBook());
		reservation.setPrice(bus.getPricePerSeat() * (reservationDTO.getNoOfSeatsToBook()));
		reservation.setJourneyDate(reservationDTO.getJourneyDate());

		List<Reservation> userReservations = user.getReservations();

		userReservations.add(reservation);

		user.setReservations(userReservations);

		reservation.setUser(user);

		Reservation savedReservation = reservationDao.save(reservation);

		if (savedReservation == null) {
			throw new ReservationException("Could not reserve the seats");
		}
		return savedReservation;

	}

	@Override
	public Reservation deleteReservation(Integer reservationId, String key)
			throws ReservationException, BusException, UserException {
		CurrentUserSession loggedInUser = userSessionDao.findByUuid(key);

		if (loggedInUser == null) {
			throw new UserException("Pleae provide a valid key to reserve seat");
		}

		User user = userDao.findById(loggedInUser.getUserId()).orElseThrow(() -> new UserException("User not found!"));

		List<Reservation> reservationList = user.getReservations();
		boolean validReservationId = false;

		for (int i = 0; i < reservationList.size(); i++) {

			if (reservationList.get(i).getReservationId() == reservationId) {

				validReservationId = true;

				Reservation foundReservation = reservationDao.findById(reservationId)
						.orElseThrow(() -> new ReservationException("No reservation found!"));
				Bus bus = foundReservation.getBus();
				if (foundReservation.getJourneyDate().isBefore(LocalDate.now())) {
					throw new ReservationException("Cannot cancel! Journey completed.");
				}

				bus.setAvailableSeats(bus.getAvailableSeats() + foundReservation.getNoOfSeatsReserved());
				Bus updatedBus = busDao.save(bus);

				reservationList.remove(i);
				reservationDao.delete(foundReservation);
				return foundReservation;

			}

		}

	
			if (!validReservationId) {
				throw new UserException("Reservation Id:" + reservationId + " do not belong to the current user!");}
			return null;
		}
	

	@Override
	public Reservation viewReservation(Integer reservationId, String key) throws ReservationException, AdminException {
		CurrentAdminSession loggedInAdmin = adminSessionDao.findByUuid(key);

		if (loggedInAdmin == null) {
			throw new ReservationException("Please provide a valid key to view reservation!");
		}

		Optional<Reservation> Opt = reservationDao.findById(reservationId);
		Reservation foundReservation = Opt.orElseThrow(() -> new ReservationException("No reservation found!"));
		return foundReservation;
	}

	@Override
	public List<Reservation> viewAllReservation(String key) throws ReservationException {
		CurrentAdminSession loggedInAdmin = adminSessionDao.findByUuid(key);

		if (loggedInAdmin == null) {
			throw new ReservationException("Please provide a valid key to view all reservations!");
		}

		List<Reservation> reservationList = reservationDao.findAll();
		if (reservationList.isEmpty())
			throw new ReservationException("No reservations found!");
		return reservationList;
	}

	@Override
	public List<Reservation> viewReservationByUser(String key) throws ReservationException, UserException {
		CurrentUserSession loggedInUser = userSessionDao.findByUuid(key);

		if (loggedInUser == null) {
			throw new UserException("Please provide a valid key to view reservation!");
		}

		User user = userDao.findById(loggedInUser.getUserId()).orElseThrow(() -> new UserException("User not found!"));

		return user.getReservations();
	}

}
