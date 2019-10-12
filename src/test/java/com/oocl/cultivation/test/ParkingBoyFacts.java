package com.oocl.cultivation.test;

import com.oocl.cultivation.Car;
import com.oocl.cultivation.ParkingBoy;
import com.oocl.cultivation.ParkingLot;
import com.oocl.cultivation.ParkingTicket;
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
        ParkingBoy parkingBoy = new ParkingBoy(parkingLot);
        Car car = new Car();

        ParkingTicket parkingTicket = parkingBoy.park(car);

        assertNotNull(parkingTicket);
    }

    @Test
    void should_fetch_car_by_parking_boy_given_parking_ticket(){
        ParkingLot parkingLot = new ParkingLot();
        ParkingBoy parkingBoy = new ParkingBoy(parkingLot);
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
        ParkingBoy parkingBoy = new ParkingBoy(parkingLot);

        parkingBoy.park(car);
        parkingBoy.park(otherCar);

        assertEquals(2, parkingLot.getCars().size());
    }

    @Test
    void should_return_correct_car_given_corresponding_ticket(){
        Car car = new Car();
        Car otherCar = new Car();
        ParkingLot parkingLot = new ParkingLot();
        ParkingBoy parkingBoy = new ParkingBoy(parkingLot);

        ParkingTicket parkingTicket = parkingBoy.park(car);
        ParkingTicket otherParkingTicket = parkingBoy.park(otherCar);

        assertEquals(otherCar, parkingBoy.fetch(otherParkingTicket));
        assertEquals(car, parkingBoy.fetch(parkingTicket));
    }

    @Test
    void should_return_no_car_given_wrong_ticket(){
        ParkingLot parkingLot = new ParkingLot();
        ParkingBoy parkingBoy = new ParkingBoy(parkingLot);

        ParkingTicket parkingTicket = new ParkingTicket();

        assertNull(parkingBoy.fetch(parkingTicket));
    }

    @Test
    void should_return_incorrect_car_given_incorrect_ticket(){
        Car car = new Car();
        Car otherCar = new Car();
        ParkingLot parkingLot = new ParkingLot();
        ParkingBoy parkingBoy = new ParkingBoy(parkingLot);

        ParkingTicket parkingTicket = parkingBoy.park(car);
        ParkingTicket otherParkingTicket = parkingBoy.park(otherCar);

        assertNotEquals(car, parkingBoy.fetch(otherParkingTicket));
    }

    @Test
    void should_return_no_car_when_ticket_already_used(){
        ParkingLot parkingLot = new ParkingLot();
        ParkingBoy parkingBoy = new ParkingBoy(parkingLot);
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
        ParkingBoy parkingBoy = new ParkingBoy(parkingLot);

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
        ParkingBoy parkingBoy = new ParkingBoy(parkingLot);
        Car car = new Car();

        ParkingTicket parkingTicket = parkingBoy.park(car);
        parkingBoy.fetch(parkingTicket);
        parkingBoy.fetch(parkingTicket);
        assertEquals("Unrecognized parking ticket", parkingBoy.getLastErrorMessage());
    }

    @Test
    void should_return_alert_when_ticket_is_null(){
        ParkingLot parkingLot = new ParkingLot();
        ParkingBoy parkingBoy = new ParkingBoy(parkingLot);
        Car car = new Car();

        ParkingTicket parkingTicket = null;
        parkingBoy.fetch(parkingTicket);

        assertEquals("Please provide your parking ticket.", parkingBoy.getLastErrorMessage());
    }

    @Test
    void should_return_alert_when_parking_without_available_position(){
        int ctr = 0;
        ParkingLot parkingLot = new ParkingLot();
        ParkingBoy parkingBoy = new ParkingBoy(parkingLot);

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
}
