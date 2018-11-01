package com.apap.tutorial7.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.apap.tutorial7.model.FlightModel;
import com.apap.tutorial7.service.FlightService;
import com.apap.tutorial7.service.PilotService;

/**
 * FlightController
 */
@RestController
@RequestMapping("/flight")
public class FlightController {
    @Autowired
    private FlightService flightService;
    
    @Autowired
    private PilotService pilotService;

    @PostMapping(value = "/add")
    private FlightModel add(@RequestBody FlightModel flight) {
        return flightService.addFlight(flight);
    }
    
    @GetMapping(value = "/view/{flightNumber}")
    private @ResponseBody FlightModel view(@PathVariable("flightNumber") String flightNumber) {
        FlightModel flight = flightService.getFlightDetailByFlightNumber(flightNumber).get();
        return flight;
    }
    
    @DeleteMapping(value = "/delete/{flightId}")
    private String delete(@PathVariable(value="flightId") long flightId) {
        FlightModel flight = flightService.getFlightById(flightId).get();
        flightService.deleteFlight(flight);
        return "flight has been deleted";
    }

    @PutMapping(value = "/update/{flightId}")
    private String updatePilotSubmit(
    		@PathVariable("flightId") long flightId,
    		@RequestParam(value="destination", required=false) String destination,
    		@RequestParam(value="origin", required=false) String origin,
    		@RequestParam(value="time", required=false) String time) throws ParseException {			//format tanggal String "dd-MM-yyyy" untuk memudahkan handle tanggal kosong
    	
        FlightModel flight = flightService.getFlightById(flightId).get();
        if(flight.equals(null)) return "Couldn't find your flight";
        
        if(origin.equals("")) {
        	//do nothing
        }
        else {
        	flight.setOrigin(origin);
        }
        if(destination.equals("")) {
        	//do nothing
        }
        else {
        	flight.setDestination(destination);
        }
        if(time.equals("")) {
        	// do nothing
        }
        else {
        	// convert String into java.sql.Date
        	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        	java.util.Date date = sdf.parse(time);
        	java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        	flight.setTime(sqlDate);
        }
        flightService.updateFlight(flightId, flight);
        return "flight update success";
    }
    
    @GetMapping(value="/all")
    public @ResponseBody List<FlightModel> viewAllFlight(){
    	List<FlightModel> listOfFlight = flightService.getAll();
    	return listOfFlight;
    }
}