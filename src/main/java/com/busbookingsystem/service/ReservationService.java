package com.busbookingsystem.service;

import java.util.List;

import com.busbookingsystem.dto.ReservationDTO;
import com.busbookingsystem.exceptions.AdminException;
import com.busbookingsystem.exceptions.BusException;
import com.busbookingsystem.exceptions.ReservationException;
import com.busbookingsystem.exceptions.UserException;
import com.busbookingsystem.model.Reservation;

public interface ReservationService {


public Reservation addReservation(ReservationDTO reservationDTO, String key) throws ReservationException , BusException,UserException ;
	
public Reservation deleteReservation(Integer reservationId, String key) throws ReservationException, BusException, UserException;

public Reservation viewReservation(Integer reservationId,String key) throws ReservationException, AdminException;

public List<Reservation> viewAllReservation(String key)throws ReservationException;

public List<Reservation> viewReservationByUser(String key) throws ReservationException, UserException;
}
