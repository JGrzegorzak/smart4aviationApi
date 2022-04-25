package com.example.smart4aviation.controller;

import com.example.smart4aviation.model.CargoEntity;
import com.example.smart4aviation.model.Flight;
import com.example.smart4aviation.model.ResponseCargo;
import com.example.smart4aviation.model.ResponseFlights;
import com.example.smart4aviation.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class FlightController {

    private FlightService flightService;

    @Autowired
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping("/flights")
    public List<Flight> getAllFlights(){
        return flightService.getAll();
    }


    @GetMapping("/baggage")
    public ResponseCargo getWeightsOfCargoAndBaggage(@RequestParam int flightNumber,@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date){
        return flightService.getCargo(flightNumber,date);
    }

    @GetMapping("/flight")
    public ResponseFlights getBaggageAndFlights(@RequestParam String iataCode,@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date){
        return flightService.getFlight(iataCode,date);
    }
}
