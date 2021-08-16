package hibernate.lesson4.service;

import hibernate.lesson4.DAO.HotelDAO;
import hibernate.lesson4.DAO.UserDAO;
import hibernate.lesson4.entity.Hotel;

public class HotelService {

    HotelDAO hotelDAO = new HotelDAO();
    UserDAO userDAO = new UserDAO();

    public Hotel findHotelByName(String name) {
        return hotelDAO.findHotelByName(name);
    }

    public Hotel findHotelByCity(String city) {
        return hotelDAO.findHotelByName(city);
    }




}
