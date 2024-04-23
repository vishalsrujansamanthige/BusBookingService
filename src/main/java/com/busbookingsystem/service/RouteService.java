package com.busbookingsystem.service;

import java.util.List;

import com.busbookingsystem.exceptions.AdminException;
import com.busbookingsystem.exceptions.RouteException;
import com.busbookingsystem.model.Route;

public interface RouteService {
public Route addRoute(Route route,String key) throws RouteException, AdminException;
	
	public Route updateRoute(Route route,String key) throws RouteException, AdminException;
	
	public Route deleteRoute(int routeId,String key) throws RouteException, AdminException;
	
	public Route viewRoute(int routeId) throws RouteException;
	
	public List<Route> viewAllRoute() throws RouteException;
}
