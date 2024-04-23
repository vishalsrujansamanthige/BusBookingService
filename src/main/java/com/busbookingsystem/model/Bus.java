package com.busbookingsystem.model;

import javax.persistence.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenerationTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class Bus {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	private Integer busId;

	@NotNull(message = "Bus name can't be null")
	private String busName;

	@NotNull(message = "Bus driver name can't be null")
	private String driverName;
	private String busType;

	@NotNull(message = "Bus registration number can't be null")
	private String busRegisterNo;

	@NotNull(message = "Start point can't be null")

	@NotBlank(message = "Start point can't be blank")
	private String routeFrom;

	@NotNull(message = "Destination point can't be null")

	@NotBlank(message = "Destination point can't be blank")
	private String routeTo;

	@NotNull(message = "Arrival time can't be null")
	@DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
	private LocalTime arrivalTime;

	@NotNull(message = "Departure time can't be null")
	@DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
	private LocalTime departureTime;

	@NotNull(message = "Total seats can't be null")
	private Integer totalSeats;
	
	
	@NotNull(message = "Available seats can't be null")
	private Integer availableSeats;
	
	
	@NotNull(message = "Price seats can't be null")
	private Integer pricePerSeat;
	
	@NotNull(message = "Bus journey date can't be null")
	@JsonFormat(pattern = "dd-MM-yyyy")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate busJourneyDate;

	@ManyToOne
	private Route route;
}
