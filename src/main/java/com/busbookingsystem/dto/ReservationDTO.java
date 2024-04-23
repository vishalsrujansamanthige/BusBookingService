package com.busbookingsystem.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ReservationDTO {

	private String source;
	private String destination;

	private Integer noOfSeatsToBook;
	@NotNull
	@JsonFormat(pattern ="dd-MM-yyyy")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate journeyDate;
	private BusDTO busDTO;
}
