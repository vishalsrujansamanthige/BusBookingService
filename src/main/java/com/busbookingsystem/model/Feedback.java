package com.busbookingsystem.model;

import java.time.LocalDateTime;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Feedback {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer feedbackId;
	private Integer driverRating;
	@Max(value = 5,message = "Rating must be in range of 1-5")
	@Min(value = 1,message = "Rating must be in range of 1-5")
	private Integer serviceRating;
	private Integer overallRating;
	private String comments;
	private LocalDateTime feedbackDateTime;
	@OneToOne
	private User user;
	@OneToOne
	private Bus bus;
}
