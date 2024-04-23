package com.busbookingsystem.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class AdminLoginDTO {
	@NotNull(message = "Phone number shouldn't be null")
	private String phoneNumber;

	@NotNull(message = "Password shouldn't be null")
	private String adminPassword;
}
