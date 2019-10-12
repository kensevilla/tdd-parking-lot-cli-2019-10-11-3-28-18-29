package com.oocl.cultivation;

import java.util.ArrayList;
import java.util.List;

public class ParkingBoy {

    private ParkingLot parkingLot;
    private List<ParkingLot> parkingLotList = new ArrayList<>();

    public ParkingBoy(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
        this.parkingLotList.add(parkingLot);
    }

    public ParkingBoy(List<ParkingLot> parkingLotList) {
        this.parkingLotList = parkingLotList;
        this.parkingLot = parkingLotList.get(0);
    }

    public ParkingTicket park(Car car) {
        chooseParkingLot();
        return parkingLot.parkCar(car);
    }

    private void chooseParkingLot() {
        this.parkingLot = parkingLotList.stream()
                .filter(tempParkingLot -> tempParkingLot.getAvailableParkingPosition()>0)
                .findFirst()
                .orElse(this.parkingLot);
    }

    public Car fetch(ParkingTicket ticket) {
        for(ParkingLot tempParkingLot : parkingLotList){
            Car car = tempParkingLot.fetchCar(ticket);
            if(car != null){
                return car;
            }
        }
        return null;
    }

    public String getLastErrorMessage() {
        return this.parkingLot.getAlertMessage();
    }

    public List<ParkingLot> getParkingLotList() {
        return this.parkingLotList;
    }
}
