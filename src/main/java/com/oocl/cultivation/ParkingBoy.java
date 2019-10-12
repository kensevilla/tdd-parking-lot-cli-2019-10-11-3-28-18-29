package com.oocl.cultivation;

import java.util.ArrayList;
import java.util.List;

public class ParkingBoy {

    private ParkingLot parkingLot;
    private List<ParkingLot> parkingLotList = new ArrayList<>();

    public ParkingBoy(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    public ParkingBoy(List<ParkingLot> parkingLotList) {
        this.parkingLotList = parkingLotList;
    }

    public ParkingTicket park(Car car) {
        return parkingLot.parkCar(car);
    }

    public Car fetch(ParkingTicket ticket) {
        return parkingLot.fetchCar(ticket);
    }

    public String getLastErrorMessage() {
        return this.parkingLot.getAlertMessage();
    }

    public List<ParkingLot> getParkingLotList() {
        return this.parkingLotList;
    }
}
