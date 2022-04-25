package com.example.smart4aviation.service;

import com.example.smart4aviation.model.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightService {

    private ObjectMapper objectMapper = new ObjectMapper();


    //Zwraca wszystkie loty
    public List<Flight> getAll(){
        TypeReference<List<Flight>> typeReference = new TypeReference<List<Flight>>() {};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/json/flight.json");
        try {
            List<Flight> flights = objectMapper.readValue(inputStream,typeReference);
            System.out.println("Flights read");
            return flights;
        }catch (IOException e){
            System.out.println("Unable to read");
        }
        return null;
    }

    //Funkcja zwraca obiekt ResponseCargo, który zawiera całkowitą wagę ładunku, bagaży oraz łączną całkowitą wagę tych rzeczy na pokładzie
    public ResponseCargo getCargo(int flightNumber, Date flightDate){
        List<Flight> flights = getFlightsFromJSON();
        int totalCargoWeights=0;
        int totalBaggageWeights=0;
        int totalWeights=0;
        if(flights.size()>0){
            flights = flights
                    .stream().filter(flight -> flight.getFlightNumber()==flightNumber)
                    .filter(flight -> compareDate(flight.getDepartureDate(),flightDate))
                    .collect(Collectors.toList());

        }
        totalCargoWeights = getTotalWeight(flights,"cargo");
        totalBaggageWeights = getTotalWeight(flights,"baggage");
        totalWeights = totalBaggageWeights + totalCargoWeights;
        return new ResponseCargo(totalCargoWeights,totalBaggageWeights,totalWeights);
    }



    //Zwraca obiekt ResponseFlights, który zawiera ilość lotów przylatujących oraz odlatujących z danego portu oraz tak samo ilość bagaży przylatujących i odlatujących z portu.
    public ResponseFlights getFlight(String iataCode, Date flightDate){
        List<Flight> flights = getFlightsFromJSON();
        int flightsArriving=0;
        int flightsDeparting=0;
        int baggageArriving=0;
        int baggageDeparting=0;

        List<Flight> flightsArr = flights.stream().filter(flight -> flight.getArrivalAirportIATACode().equals(iataCode))
                .filter(flight -> compareDate(flight.getDepartureDate(),flightDate))
                .collect(Collectors.toList());
        List<Flight> flightsDep = flights.stream().filter(flight -> flight.getDepartureAirportIATACode().equals(iataCode))
                .filter(flight -> compareDate(flight.getDepartureDate(),flightDate))
                .collect(Collectors.toList());
        flightsArriving = flightsArr.size();
        flightsDeparting = flightsDep.size();

        baggageArriving= getBaggage(flightsArr);
        baggageDeparting= getBaggage(flightsDep);
        return new ResponseFlights(flightsDeparting,flightsArriving,baggageArriving,baggageDeparting);
    }



    //Zwraca ilość wszystkich bagaży
    private int getBaggage(List<Flight> flights){
        TypeReference<List<CargoEntity>> typeReference = new TypeReference<List<CargoEntity>>() {};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/json/cargo.json");
        List<CargoEntity> cargoEntities = new ArrayList<>();
        int result=0;
        try {
            cargoEntities = objectMapper.readValue(inputStream,typeReference);
        }catch (IOException e){
            System.out.println("Unable to read");
        }
        cargoEntities = cargoEntities.stream().filter(f -> {
            for (int i=0; i<flights.size();i++){
                if (flights.get(i).getFlightId()==f.getFlightId()){
                    return true;
                }
            }
            return false;
        }).collect(Collectors.toList());

        for(int i=0;i<cargoEntities.size();i++){
            List<Baggage> baggages = cargoEntities.get(i).getBaggage();
            for (int j=0;j<baggages.size();j++){
                result+=baggages.get(j).getPieces();
            }
        }
        return result;

    }


    //Zwraca całkowitą wagę wszystkich bagaży lub ładunku danego lotu
    private int getTotalWeight(List<Flight> flights, String type){
        TypeReference<List<CargoEntity>> typeReference = new TypeReference<List<CargoEntity>>() {};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/json/cargo.json");
        List<CargoEntity> cargoEntities = new ArrayList<>();
        int result=0;
        try {
            cargoEntities = objectMapper.readValue(inputStream,typeReference);
        }catch (IOException e){
            System.out.println("Unable to read");
        }
        cargoEntities = cargoEntities.stream().filter(f -> {
            for (int i=0; i<flights.size();i++){
                if (flights.get(i).getFlightId()==f.getFlightId()){
                    return true;
                }
            }
            return false;
        }).collect(Collectors.toList());

        String unit="";
        for (int i=0;i<cargoEntities.size();i++){
        if (type=="cargo"){
            List<Cargo> cargos = cargoEntities.get(i).getCargo();
            for (int j=0;j<cargos.size();j++){
                if(cargos.get(j).getWeightUnit().equals("lb")){
                    result+=cargos.get(j).getWeight()*0.45;
                }else {
                    result+=cargos.get(j).getWeight();
                }
            }
        } else if(type=="baggage"){
            List<Baggage> baggages = cargoEntities.get(i).getBaggage();
            for( int j=0;j<baggages.size();j++){
                if(baggages.get(j).getWeightUnit().equals("lb")){
                    result+=baggages.get(j).getWeight()*0.45;
                }else {
                    result+=baggages.get(j).getWeight();
                }
            }
        }
        }
        return result;
    }

    //Pobiera wszystkie loty z pliku Json
    private List<Flight> getFlightsFromJSON() {
        TypeReference<List<Flight>> typeReference = new TypeReference<List<Flight>>() {};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/json/flight.json");
        List<Flight> flights = new ArrayList<>();
        try {
            flights = objectMapper.readValue(inputStream,typeReference);
            return flights;
        }catch (IOException e){
            System.out.println("Unable to read");
        }
        return flights;
    }


    //Porownuje daty
    private boolean compareDate(Date dateJson, Date dateReq){
        if(dateJson.getDate()==dateReq.getDate() && dateJson.getMonth()==dateReq.getMonth() && dateJson.getYear()==dateReq.getYear()){
            return true;
        }else
            return false;
    }
}
