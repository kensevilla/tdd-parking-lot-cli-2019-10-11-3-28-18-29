package com.oocl.cultivation;

import java.util.ArrayList;
import java.util.List;

public class ParkingServiceManager extends ParkingBoy{
    public void setManagementList(List<ParkingBoyType> managementList) {
        this.managementList = managementList;
    }

    private List<ParkingBoyType> managementList = new ArrayList<>();
    private ParkingLot parkingLot;
    public ParkingServiceManager(ParkingLot parkingLot){
        super(parkingLot);
    }

    public ParkingTicket askToPark(ParkingBoyType parkingBoy, Car car) {
        if(managementList.contains(parkingBoy)) {
            return parkingBoy.park(car);
        }
        return null;
        }

    public Car askToFetch(ParkingBoyType smartParkingBoy, ParkingTicket parkingTicket) {
        if(managementList.contains(smartParkingBoy)) {
            return smartParkingBoy.fetch(parkingTicket);
        }
        else{
            return null;
        }
    }
}
