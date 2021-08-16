package hibernate.lesson4.controller;

import hibernate.lesson4.entity.Hotel;
import hibernate.lesson4.service.HotelService;


public class HotelController {
    private static HotelService HOTEL_SERVICE = new HotelService();

    public static Hotel findHotelByCity(String city) {
        return HOTEL_SERVICE.findHotelByName(city);
    }

    public static Hotel findHotelByName(String name) {
        return HOTEL_SERVICE.findHotelByName(name);
    }

}
