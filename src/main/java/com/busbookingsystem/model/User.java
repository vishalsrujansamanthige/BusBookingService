package com.busbookingsystem.model;

import java.util.ArrayList;
import java.util.List;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer userId;
	@NotNull(message = "First Name can't be null")
	@NotBlank(message = "First Name can't be empty")
	private String firstName;
	private String lastName;
	
	@Pattern(regexp = "[A-Za-z0-9!@#$%^&*_]{8,15}",message = "Password must contain only 8-15 character including special characters")
	@NotNull(message = "Password can't be null")
	@NotBlank(message = "Password can't be empty")
	private String password;
	
	
	@NotNull(message = "Phone No. can't be null")
	@NotBlank(message = "Phone No. can't be empty")
	@Pattern(regexp = "[6789]{1}[0-9]{9}",message = "Enter only 10 digits valid no.")
	@Size(min = 10,max = 10)
	private String mobileNumber;
	@Email
	  private String email;
	  
	 @JsonIgnore
	 @OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
	 private List<Reservation> reservations = new ArrayList();
	
	

}
