package com.busbookingsystem.dto;

import java.time.LocalDate;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class BusDTO {
	private Integer busId;
	private String routeFrom;
	private String routeTo;
	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate busJourneyDate;

}
