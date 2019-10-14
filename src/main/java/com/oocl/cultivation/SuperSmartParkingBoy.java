package com.oocl.cultivation;

import java.util.List;

public class SuperSmartParkingBoy extends ParkingBoyType{
    public SuperSmartParkingBoy(ParkingLot parkingLot) {
        super.setParkingLot(parkingLot);
    }

    public SuperSmartParkingBoy(List<ParkingLot> parkingLotList) {
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
                .reduce((a, b) -> a.getAvailableParkingPosition()/a.getCapacity() > b.getAvailableParkingPosition()/b.getCapacity() ? a : b)
                .get());
    }
}
