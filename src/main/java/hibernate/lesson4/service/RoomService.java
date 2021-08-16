package hibernate.lesson4.service;

import hibernate.lesson4.DAO.RoomDAO;
import hibernate.lesson4.entity.Filter;
import hibernate.lesson4.entity.Room;

import java.util.Date;
import java.util.Set;

public class RoomService {
    private RoomDAO roomDAO = new RoomDAO();

    public Set<Room> findRooms(Filter filter){
        return roomDAO.findRooms(filter);
    }

    public void bookRoom(long roomId, long userId, Date dateFrom, Date dateTo)throws Exception{
        roomDAO.bookRoom(roomId, userId, dateFrom, dateTo);
    }

    public void cancelReservation(long roomId, long userId)throws Exception{
        roomDAO.cancelReservation(roomId, userId);
    }
}
