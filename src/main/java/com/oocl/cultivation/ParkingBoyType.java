package com.oocl.cultivation;

import java.util.ArrayList;
import java.util.List;

public abstract class ParkingBoyType  {
    private ParkingLot parkingLot;
    private List<ParkingLot> parkingLotList = new ArrayList<>();

    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
        if(this.parkingLotList.isEmpty()) {
            this.parkingLotList.add(parkingLot);
        }
    }

    public List<ParkingLot> getParkingLotList() {
        return parkingLotList;
    }

    public void setParkingLotList(List<ParkingLot> parkingLotList) {
        this.parkingLotList = parkingLotList;
        if(parkingLot == null) {
            this.parkingLot = parkingLotList.get(0);
        }
    }

    public ParkingTicket park(Car car) {
        return parkingLot.parkCar(car);
    }

    public abstract void chooseParkingLot();

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
}
