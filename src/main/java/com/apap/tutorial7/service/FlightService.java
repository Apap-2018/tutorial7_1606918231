package com.apap.tutorial7.service;

import java.util.List;
import java.util.Optional;

import com.apap.tutorial7.model.FlightModel;

/**
 * FlightService
 */
public interface FlightService {
    FlightModel addFlight(FlightModel flight);
    
    void deleteByFlightNumber(String flightNumber);

    Optional<FlightModel> getFlightDetailByFlightNumber(String flightNumber);
    
    Optional<FlightModel> getFlightById(long flightId);
    
    void deleteFlight(FlightModel flight);
    
    void updateFlight(long flightId, FlightModel flight);
    
    List<FlightModel> getAll();
}