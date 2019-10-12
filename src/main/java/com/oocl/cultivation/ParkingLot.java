package com.oocl.cultivation;

import java.util.HashMap;
import java.util.Map;

public class ParkingLot {
    private final int capacity;
    private Map<ParkingTicket, Car> cars = new HashMap<>();

    public ParkingLot() {
        this(10);
    }

    public ParkingLot(int capacity) {
        this.capacity = capacity;
    }

    public int getAvailableParkingPosition() {
        return capacity - cars.size();
    }

    public ParkingTicket parkCar(Car car){
        if(getAvailableParkingPosition()>0) {
            ParkingTicket parkingTicket = new ParkingTicket();
            cars.put(parkingTicket, car);
            return parkingTicket;
        }
        else{
            System.out.append("Not enough position.");
        }
        return null;
    }

    public Car fetchCar(ParkingTicket parkingTicket){
        Car car = new Car();
        if(parkingTicket != null) {
             car = cars.get(parkingTicket);
            if (car != null) {
                cars.remove(parkingTicket);
            } else {
                System.out.append("Unrecognized parking ticket");
            }
        }
        else{
            System.out.append("Please provide your parking ticket.");
        }
        return car;
    }

    public Map<ParkingTicket, Car> getCars(){
        return cars;
    }
}
