package com.busbookingsystem.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;
@Data

@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Route {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer routeId;
	@NotNull(message = "Start point cant be null!")
	@NotBlank(message = "Start point cant be blank!")
	private String routeFrom;
	@NotNull(message = "Destination point cant be null!")
	@NotBlank(message = "Destination point cant be blank!")
	private String routeTo;
	private String distance;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "route")
	private List<Bus> busList = new ArrayList<>();

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Route other = (Route) obj;
		return Objects.equals(distance, other.distance)
				&& Objects.equals(routeFrom.toLowerCase(), other.routeFrom.toLowerCase())
				&& Objects.equals(routeId, other.routeId)
				&& Objects.equals(routeTo.toLowerCase(), other.routeTo.toLowerCase());
	}

	@Override
	public int hashCode() {

		return Objects.hash(distance, routeFrom, routeId, routeTo);
	}

	
	
	
	
	
	
	
	
	
	
	
}
