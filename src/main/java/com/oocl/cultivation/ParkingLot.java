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
        return cars.size() - capacity;
    }

    public ParkingTicket parkCar(Car car){
        if(cars.size()<capacity) {
            ParkingTicket parkingTicket = new ParkingTicket();
            cars.put(parkingTicket, car);
            return parkingTicket;
        }
        return null;
    }

    public Car fetchCar(ParkingTicket parkingTicket){
        Car car = cars.get(parkingTicket);
        cars.remove(parkingTicket);
        return car;
    }

    public Map<ParkingTicket, Car> getCars(){
        return cars;
    }
}
