package com.busbookingsystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.busbookingsystem.model.Route;

@Repository
public interface RouteDao extends JpaRepository<Route, Integer> {
	public Route findByRouteFromAndRouteTo(String from, String to);



	public Optional<Route> findById(Integer routeId);
}
