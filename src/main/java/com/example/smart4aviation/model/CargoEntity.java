package com.example.smart4aviation.model;

import java.util.List;

public class CargoEntity {
    private Long flightId;
    private List<Baggage> baggage;
    private List<Cargo> cargo;

    public CargoEntity(Long flightId, List<Baggage> baggage, List<Cargo> cargo) {
        this.flightId = flightId;
        this.baggage = baggage;
        this.cargo = cargo;
    }

    public CargoEntity() {
    }

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public List<Baggage> getBaggage() {
        return baggage;
    }

    public void setBaggage(List<Baggage> baggage) {
        this.baggage = baggage;
    }

    public List<Cargo> getCargo() {
        return cargo;
    }

    public void setCargo(List<Cargo> cargo) {
        this.cargo = cargo;
    }
}
