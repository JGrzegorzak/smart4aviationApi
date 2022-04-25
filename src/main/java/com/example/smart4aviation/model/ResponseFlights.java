package com.example.smart4aviation.model;

public class ResponseFlights {
    private Integer flightsDeparting;
    private Integer flightsArriving;
    private Integer baggageArriving;
    private Integer baggageDeparting;

    public ResponseFlights(Integer flightsDeparting, Integer flightsArriving, Integer baggageArriving, Integer baggageDeparting) {
        this.flightsDeparting = flightsDeparting;
        this.flightsArriving = flightsArriving;
        this.baggageArriving = baggageArriving;
        this.baggageDeparting = baggageDeparting;
    }

    public ResponseFlights() {
    }

    public Integer getFlightsDeparting() {
        return flightsDeparting;
    }

    public void setFlightsDeparting(Integer flightsDeparting) {
        this.flightsDeparting = flightsDeparting;
    }

    public Integer getFlightsArriving() {
        return flightsArriving;
    }

    public void setFlightsArriving(Integer flightsArriving) {
        this.flightsArriving = flightsArriving;
    }

    public Integer getBaggageArriving() {
        return baggageArriving;
    }

    public void setBaggageArriving(Integer baggageArriving) {
        this.baggageArriving = baggageArriving;
    }

    public Integer getBaggageDeparting() {
        return baggageDeparting;
    }

    public void setBaggageDeparting(Integer baggageDeparting) {
        this.baggageDeparting = baggageDeparting;
    }
}
