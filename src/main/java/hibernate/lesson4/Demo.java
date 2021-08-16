package hibernate.lesson4;

import hibernate.lesson3.DAO.HotelDAO;
import hibernate.lesson3.DAO.RoomDAO;
import hibernate.lesson3.entity.Hotel;

public class Demo {
    public static void main(String[] args) {
        HotelDAO hotelDAO = new HotelDAO();
        RoomDAO roomDAO = new RoomDAO();

        long hotelId = 3;
        long roomId = 3;
        //hotel init
        Hotel hotel = new Hotel("hotel123", "Ukraine123", "Kiev123", "street123");
        //room init
//        Room room = new Room(2, 500, 1, 1, new Date(),
//                hotelDAO.findById(hotelId));

        //save +
//        hotelDAO.save(hotel);
//        roomDAO.save(room);

        //findById
        //hotel +
//        System.out.println(hotelDAO.findById(hotelId));
        //room +
//        System.out.println(roomDAO.findById(roomId));

        //update +
//        hotel.setName("Name updated123");
//        hotel.setId(hotelId);
//        hotelDAO.update(hotel);
        System.out.println(hotel.getId());
//
//        //correct value +
//        room.setBreakfastIncluded(0);
//        roomDAO.update(room);
//        //invalid value +
//        Room room1 = roomDAO.findById(roomId);
//        room1.setBreakfastIncluded(6);
//        roomDAO.update(room1);
//
//        //delete +
//        roomDAO.delete(roomId);
//        hotelDAO.delete(hotelId);
    }
}
