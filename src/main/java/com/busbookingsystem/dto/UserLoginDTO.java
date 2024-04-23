package com.busbookingsystem.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO {
	@NotNull(message = "Mobile number should not be null")
	private String mobileNumber;
	@NotNull(message = "password should not be null")
	private String password;
}
