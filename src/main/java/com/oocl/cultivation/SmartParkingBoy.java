package com.oocl.cultivation;

import java.util.List;

public class SmartParkingBoy extends ParkingBoyType {
    public SmartParkingBoy(ParkingLot parkingLot) {
        super.setParkingLot(parkingLot);
    }

    public SmartParkingBoy(List<ParkingLot> parkingLotList) {
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
                .reduce((a, b) -> a.getAvailableParkingPosition() > b.getAvailableParkingPosition() ? a : b)
                .get());
    }
}
