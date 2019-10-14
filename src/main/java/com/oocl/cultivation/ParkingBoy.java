package com.oocl.cultivation;

import java.util.List;

public class ParkingBoy extends ParkingBoyType{

    public ParkingBoy(ParkingLot parkingLot) {
        super.setParkingLot(parkingLot);
    }

    public ParkingBoy(List<ParkingLot> parkingLotList) {
        super.setParkingLotList(parkingLotList);
    }

    @Override
    public ParkingTicket park(Car car) {
        chooseParkingLot();
        return super.park(car);
    }

    @Override
    public void chooseParkingLot() {
        super.setParkingLot(super.getParkingLotList().stream()
                .filter(tempParkingLot -> tempParkingLot.getAvailableParkingPosition()>0)
                .findFirst()
                .orElse(super.getParkingLot()));
    }
}
