package com.oocl.cultivation.test;

import com.oocl.cultivation.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParkingBoyFacts {
    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    public void setup() {
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void should_return_parking_ticket_when_car_is_park_by_parking_boy_in_parking_lot(){
        ParkingLot parkingLot = new ParkingLot();
        ParkingBoyType parkingBoy = new ParkingBoy(parkingLot);
        Car car = new Car();

        ParkingTicket parkingTicket = parkingBoy.park(car);

        assertNotNull(parkingTicket);
    }

    @Test
    void should_fetch_car_by_parking_boy_given_parking_ticket(){
        ParkingLot parkingLot = new ParkingLot();
        ParkingBoyType parkingBoy = new ParkingBoy(parkingLot);
        Car car = new Car();

        ParkingTicket parkingTicket = parkingBoy.park(car);
        Car fetchedCar = parkingBoy.fetch(parkingTicket);

        assertNotNull(fetchedCar);
    }

    @Test
    void should_return_multiple_cars_when_parking_boy_parks_multiple_cars(){
        Car car = new Car();
        Car otherCar = new Car();
        ParkingLot parkingLot = new ParkingLot();
        ParkingBoyType parkingBoy = new ParkingBoy(parkingLot);

        parkingBoy.park(car);
        parkingBoy.park(otherCar);

        assertEquals(2, parkingLot.getCars().size());
    }

    @Test
    void should_return_correct_car_given_corresponding_ticket(){
        Car car = new Car();
        Car otherCar = new Car();
        ParkingLot parkingLot = new ParkingLot();
        ParkingBoyType parkingBoy = new ParkingBoy(parkingLot);

        ParkingTicket parkingTicket = parkingBoy.park(car);
        ParkingTicket otherParkingTicket = parkingBoy.park(otherCar);

        assertEquals(otherCar, parkingBoy.fetch(otherParkingTicket));
        assertEquals(car, parkingBoy.fetch(parkingTicket));
    }

    @Test
    void should_return_no_car_given_wrong_ticket(){
        ParkingLot parkingLot = new ParkingLot();
        ParkingBoyType parkingBoy = new ParkingBoy(parkingLot);

        ParkingTicket parkingTicket = new ParkingTicket();

        assertNull(parkingBoy.fetch(parkingTicket));
    }

    @Test
    void should_return_incorrect_car_given_incorrect_ticket(){
        Car car = new Car();
        Car otherCar = new Car();
        ParkingLot parkingLot = new ParkingLot();
        ParkingBoyType parkingBoy = new ParkingBoy(parkingLot);

        ParkingTicket parkingTicket = parkingBoy.park(car);
        ParkingTicket otherParkingTicket = parkingBoy.park(otherCar);

        assertNotEquals(car, parkingBoy.fetch(otherParkingTicket));
    }

    @Test
    void should_return_no_car_when_ticket_already_used(){
        ParkingLot parkingLot = new ParkingLot();
        ParkingBoyType parkingBoy = new ParkingBoy(parkingLot);
        Car car = new Car();

        ParkingTicket parkingTicket = parkingBoy.park(car);
        Car fetchedCar = parkingBoy.fetch(parkingTicket);
        assertNotNull(fetchedCar);
        fetchedCar = parkingBoy.fetch(parkingTicket);
        assertNull(fetchedCar);
    }

    @Test
    void should_return_no_ticket_when_parking_lot_capacity_is_reached(){
        int ctr = 0;
        ParkingLot parkingLot = new ParkingLot();
        ParkingBoyType parkingBoy = new ParkingBoy(parkingLot);

        do {
            parkingBoy.park(new Car());
            ctr++;
        }while (ctr<10);

        ParkingTicket parkingTicket = parkingBoy.park(new Car());
        assertNull(parkingTicket);
    }

    @Test
    void should_return_alert_when_ticket_already_used(){
        ParkingLot parkingLot = new ParkingLot();
        ParkingBoyType parkingBoy = new ParkingBoy(parkingLot);
        Car car = new Car();

        ParkingTicket parkingTicket = parkingBoy.park(car);
        parkingBoy.fetch(parkingTicket);
        parkingBoy.fetch(parkingTicket);
        assertEquals("Unrecognized parking ticket", parkingBoy.getLastErrorMessage());
    }

    @Test
    void should_return_alert_when_ticket_is_null(){
        ParkingLot parkingLot = new ParkingLot();
        ParkingBoyType parkingBoy = new ParkingBoy(parkingLot);
        Car car = new Car();

        ParkingTicket parkingTicket = null;
        parkingBoy.fetch(parkingTicket);

        assertEquals("Please provide your parking ticket.", parkingBoy.getLastErrorMessage());
    }

    @Test
    void should_return_alert_when_parking_without_available_position(){
        int ctr = 0;
        ParkingLot parkingLot = new ParkingLot();
        ParkingBoyType parkingBoy = new ParkingBoy(parkingLot);

        do {
            parkingBoy.park(new Car());
            ctr++;
        }while (ctr<10);

        parkingBoy.park(new Car());
        assertEquals("Not enough position.", parkingBoy.getLastErrorMessage());
    }

    @Test
    void should_return_multiple_parking_lot_when_added_parking_lots_to_parking_boy(){
        List<ParkingLot> parkingLotList = new ArrayList<>();
        ParkingLot parkingLot = new ParkingLot();
        ParkingLot otherParkingLot = new ParkingLot();
        parkingLotList.add(parkingLot);
        parkingLotList.add(otherParkingLot);
        ParkingBoy parkingBoy = new ParkingBoy(parkingLotList);

        assertEquals(2, parkingBoy.getParkingLotList().size());
    }

    @Test
    void should_park_to_other_parking_lot_when_first_parking_lot_is_full(){
        List<ParkingLot> parkingLotList = new ArrayList<>();
        ParkingLot parkingLot = new ParkingLot();
        ParkingLot otherParkingLot = new ParkingLot();
        parkingLotList.add(parkingLot);
        parkingLotList.add(otherParkingLot);
        ParkingBoy parkingBoy = new ParkingBoy(parkingLotList);

        int ctr = 0;
        do {
            parkingBoy.park(new Car());
            ctr++;
        }while (ctr<10 + 1);

        assertEquals(1, otherParkingLot.getCars().size());
    }

    @Test
    void should_park_to_third_parking_lot_when_first_and_second_parking_lot_is_full(){
        List<ParkingLot> parkingLotList = new ArrayList<>();
        ParkingLot parkingLot = new ParkingLot();
        ParkingLot secondParkingLot = new ParkingLot();
        ParkingLot thirdParkingLot = new ParkingLot();
        parkingLotList.add(parkingLot);
        parkingLotList.add(secondParkingLot);
        parkingLotList.add(thirdParkingLot);
        ParkingBoy parkingBoy = new ParkingBoy(parkingLotList);

        int ctr = 0;
        do {
            parkingBoy.park(new Car());
            ctr++;
        }while (ctr<20 + 3);

        assertEquals(3, thirdParkingLot.getCars().size());
    }

    @Test
    void should_return_correct_car_when_park_on_third_parking_lot(){
        List<ParkingLot> parkingLotList = new ArrayList<>();
        ParkingLot parkingLot = new ParkingLot();
        ParkingLot secondParkingLot = new ParkingLot();
        ParkingLot thirdParkingLot = new ParkingLot();
        parkingLotList.add(parkingLot);
        parkingLotList.add(secondParkingLot);
        parkingLotList.add(thirdParkingLot);
        ParkingBoy parkingBoy = new ParkingBoy(parkingLotList);

        int ctr = 0;
        do {
            parkingBoy.park(new Car());
            ctr++;
        }while (ctr<10 + 10);
        Car car = new Car();
        ParkingTicket parkingTicket = parkingBoy.park(car);
        Car fetchedCar = parkingBoy.fetch(parkingTicket);
        assertEquals(car, fetchedCar);
    }

    @Test
    void should_return_both_parking_lots_with_equal_available_postion_when_park_by_smart_parking_boy(){
        List<ParkingLot> parkingLotList = new ArrayList<>();
        ParkingLot parkingLot = new ParkingLot();
        ParkingLot otherParKingLot = new ParkingLot();
        parkingLotList.add(parkingLot);
        parkingLotList.add(otherParKingLot);
        ParkingBoyType smartParkingBoy = new SmartParkingBoy(parkingLotList);

        int ctr = 0;
        do {
            smartParkingBoy.park(new Car());
            ctr++;
        }while (ctr<2 + 2);

        assertEquals(smartParkingBoy.getParkingLotList().get(0).getAvailableParkingPosition(), smartParkingBoy.getParkingLotList().get(1).getAvailableParkingPosition());
    }

    @Test
    void should_return_1st_parking_lot_with_9_and_2nd_parking_lot_with_8_available_postion_given_3_cars_when_park_by_smart_parking_boy(){
        List<ParkingLot> parkingLotList = new ArrayList<>();
        ParkingLot parkingLot = new ParkingLot();
        ParkingLot otherParKingLot = new ParkingLot();
        parkingLotList.add(parkingLot);
        parkingLotList.add(otherParKingLot);
        ParkingBoyType smartParkingBoy = new SmartParkingBoy(parkingLotList);

        int ctr = 0;
        do {
            smartParkingBoy.park(new Car());
            ctr++;
        }while (ctr<2 + 1);

        assertEquals(smartParkingBoy.getParkingLotList().get(0).getAvailableParkingPosition(), 9);
        assertEquals(smartParkingBoy.getParkingLotList().get(1).getAvailableParkingPosition(), 8);
    }

    @Test
    void should_park_on_the_second_parking_lot_given_larger_position_rate_when_4_cars_park_by_super_smart_parking_boy(){
        List<ParkingLot> parkingLotList = new ArrayList<>();
        ParkingLot parkingLot = new ParkingLot(20);
        ParkingLot otherParKingLot = new ParkingLot(50);
        parkingLotList.add(parkingLot);
        parkingLotList.add(otherParKingLot);
        ParkingBoyType superSmartParkingBoy = new SuperSmartParkingBoy(parkingLotList);

        int ctr = 0;
        do {
            superSmartParkingBoy.park(new Car());
            ctr++;
        }while (ctr<4);

        assertEquals(superSmartParkingBoy.getParkingLotList().get(0).getAvailableParkingPosition(), 19);
        assertEquals(superSmartParkingBoy.getParkingLotList().get(1).getAvailableParkingPosition(), 47);
    }

    @Test
    void should_return_fetched_car_when_service_manager_asks_parking_boys_to_park_and_fetch(){
        ParkingLot parkingLot = new ParkingLot();
        ParkingServiceManager serviceManager = new ParkingServiceManager(parkingLot);
        List<ParkingBoyType> parkingBoyList = new ArrayList<>();
        ParkingBoyType parkingBoy = new ParkingBoy(parkingLot);
        ParkingBoyType smartParkingBoy = new SmartParkingBoy(parkingLot);
        parkingBoyList.add(parkingBoy);
        parkingBoyList.add(smartParkingBoy);
        serviceManager.setManagementList(parkingBoyList);

        Car car = new Car();
        ParkingTicket parkingTicket = serviceManager.askToPark(parkingBoy, car);
        Car fetchedCar = serviceManager.askToFetch(smartParkingBoy, parkingTicket);

        assertEquals(car, fetchedCar);
    }

    @Test
    void should_return_fetched_car_when_service_manager_park_and_fetch(){
        ParkingLot parkingLot = new ParkingLot();
        ParkingServiceManager serviceManager = new ParkingServiceManager(parkingLot);

        Car car = new Car();
        ParkingTicket parkingTicket = serviceManager.park(car);
        Car fetchedCar = serviceManager.fetch(parkingTicket);

        assertEquals(car, fetchedCar);
    }

    @Test
    void should_return_alert_message_when_false_ticket_and_park_by_service_manager(){
        ParkingLot parkingLot = new ParkingLot();
        ParkingServiceManager serviceManager = new ParkingServiceManager(parkingLot);

        Car car = new Car();
        ParkingTicket parkingTicket = serviceManager.park(car);
        ParkingTicket falseParkingTicket = new ParkingTicket();
        serviceManager.fetch(falseParkingTicket);

        assertEquals("Unrecognized parking ticket", serviceManager.getLastErrorMessage());
    }

    @Test
    void should_return_alert_message_when_null_ticket_and_park_by_service_manager(){
        ParkingLot parkingLot = new ParkingLot();
        ParkingServiceManager serviceManager = new ParkingServiceManager(parkingLot);

        Car car = new Car();
        ParkingTicket parkingTicket = serviceManager.park(car);
        ParkingTicket nullParkingTicket = null;
        serviceManager.fetch(nullParkingTicket);

        assertEquals("Please provide your parking ticket.", serviceManager.getLastErrorMessage());
    }

    @Test
    void should_return_alert_message_when_no_more_capacity_and_park_by_service_manager(){
        ParkingLot parkingLot = new ParkingLot();
        ParkingServiceManager serviceManager = new ParkingServiceManager(parkingLot);
        int ctr = 0;
        do {
            serviceManager.park(new Car());
            ctr++;
        }while(ctr<10 + 1);

        assertEquals("Not enough position.", serviceManager.getLastErrorMessage());
    }
}
