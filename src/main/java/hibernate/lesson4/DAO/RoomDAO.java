package hibernate.lesson4.DAO;

import hibernate.lesson4.entity.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import javax.persistence.NoResultException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static hibernate.lesson4.utils.SessionFactoryCreator.createSessionFactory;

public class RoomDAO extends DAO<Room> {

    @Override
    public Room save(Room room) {
        Transaction tr = null;
        try (Session session = createSessionFactory().openSession()) {
            //create session/tr
            tr = session.getTransaction();
            tr.begin();

            //action
            session.save(room);

            //close session/tr
            tr.commit();
        } catch (HibernateException e) {
            System.err.println("Save is failed");
            System.err.println(e.getMessage());

            if (tr != null) {
                tr.rollback();
            }
        }

        System.out.println("Save is done");

        return room;
    }

    @Override
    public void delete(long id) {
        Transaction tr = null;
        try (Session session = createSessionFactory().openSession()) {
            //create session/tr
            tr = session.getTransaction();
            tr.begin();

            //action
            Room room = session.load(Room.class, id);
            session.delete(room);

            //close session/tr
            tr.commit();
        } catch (HibernateException e) {
            System.err.println("Delete is failed");
            System.err.println(e.getMessage());

            if (tr != null) {
                tr.rollback();
            }
        }

        System.out.println("Delete is done");
    }

    @Override
    public Room update(Room room) {
        Transaction tr = null;
        try (Session session = createSessionFactory().openSession()) {
            //create session/tr
            tr = session.getTransaction();
            tr.begin();

            //action
            session.update(session.load(Room.class, room.getId()));

            //close session/tr
            tr.commit();
        } catch (HibernateException e) {
            System.err.println("Update is failed");
            System.err.println(e.getMessage());

            if (tr != null) {
                tr.rollback();
            }
        }

        System.out.println("Update is done");

        return room;
    }

    @Override
    public Room findById(long id) {
        Transaction tr = null;
        try (Session session = createSessionFactory().openSession()) {
            tr = session.getTransaction();
            tr.begin();

            //action
            Room room = session.find(Room.class, id);

            //close session/tr
            tr.commit();

            return room;
        } catch (HibernateException e) {
            System.err.println("Delete is failed");
            System.err.println(e.getMessage());

            if (tr != null) {
                tr.rollback();
            }
        }

        System.out.println("Delete is done");

        return null;
    }

    public Set<Room> findRooms(Filter filter){
        Set<Room> result = new HashSet<>();

        if(filter == null){
            result.addAll(getAllRooms());
        }else {
            for(Room r : getAllRooms()){
                if(r.getNumberOfGuests() >= filter.getNumberOfGuests()
                        && r.getPrice() >= filter.getPrice()
                        && r.getBreakfastIncluded() == filter.getBreakfastIncluded()
                        && r.getPetsAllowed() == filter.getPetsAllowed()
                        && r.getDateAvailableFrom().getTime() >= filter.getDateAvailableFrom().getTime()){
                    result.add(r);
                }
            }
        }
        return result;
    }

    public void bookRoom(long roomId, long userId, Date dateFrom, Date dateTo)throws Exception{
        Room room = findById(roomId);
        if(room == null){
            throw new Exception("Exception in method RoomDAO.bookRoom. Room with ID: " + roomId + " is not defined in DB.");
        }

        UserDAO userDAO = new UserDAO();
        User user = userDAO.findById(userId);
        if(user == null){
            throw new Exception("Exception in method RoomDAO.bookRoom. User with ID: " + userId + " is not defined in DB.");
        }

        Order order = getOrderByRoomId(roomId);
        if(order != null){
            if(room.getDateAvailableFrom().getTime() >= order.getDateFrom().getTime() &&
                    room.getDateAvailableFrom().getTime() <= order.getDateTo().getTime()){
                throw new Exception("Exception in method RoomDAO.bookRoom. This room with ID: " + roomId + " is booked already.");
            }
        }

        Session session = null;
        Transaction tr = null;
        try{
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            order = new Order();
            order.setUserOrdered(user);
            order.setRoom(room);
            order.setDateFrom(dateFrom);
            order.setDateTo(dateTo);
            order.setMoneyPaid(room.getPrice());

            OrderDAO orderDAO = new OrderDAO();
            orderDAO.save(order);

            room.setDateAvailableFrom(dateTo);
            update(room);

            tr.commit();
        }catch (HibernateException e){
            System.err.println("Exception in method RoomDAO.bookRoom. Room with ID: " + roomId + " is filed to book.");
            System.err.println(e.getMessage());
            if(tr != null) {
                tr.rollback();
            }
        }finally {
            if(session != null){
                session.close();
            }
        }
    }

    public void cancelReservation(long roomId, long userId)throws Exception{
        Room room = findById(roomId);
        if(room == null){
            throw new Exception("Exception in method RoomDAO.cancelReservation. Room with ID: " + roomId + " is not defined in DB.");
        }

        UserDAO userDAO = new UserDAO();
        User user = userDAO.findById(userId);
        if(user == null){
            throw new Exception("Exception in method RoomDAO.cancelReservation. User with ID: " + userId + " is not defined in DB.");
        }

        Order order = getOrderByRoomIdAndUserId(roomId, userId);
        if(order == null){
            throw new Exception("Exception in method RoomDAO.cancelReservation. Order is not defined in DB.");
        }

        Session session = null;
        Transaction tr = null;
        try{
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            OrderDAO orderDAO = new OrderDAO();
            orderDAO.delete(order.getId());

            RoomDAO roomDAO = new RoomDAO();
            room.setDateAvailableFrom(new Date());
            roomDAO.update(room);

            tr.commit();
        }catch (HibernateException e){
            System.err.println("Exception in method RoomDAO.cancelReservation. Room with ID: " + roomId + " is filed to cancelreservation.");
            System.err.println(e.getMessage());
            if(tr != null) {
                tr.rollback();
            }
        }finally {
            if(session != null){
                session.close();
            }
        }
    }

    private Order getOrderByRoomIdAndUserId(long roomId, long userId){
        Session session = null;
        Transaction tr = null;
        Order order;
        try{
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            String sql = "SELECT * FROM ORDERS WHERE ORDER_ROOM_ID = ? AND ORDER_USER_ID = ?";
            NativeQuery query = session.createNativeQuery(sql, Order.class);
            query.setParameter(1, roomId);
            query.setParameter(2, userId);
            order = (Order)query.getSingleResult();
            tr.commit();
            return order;
        }catch (NoResultException ex){
            System.err.println(ex.getMessage());
            if(tr != null) {
                tr.rollback();
            }
            return null;
        }catch (HibernateException e){
            System.err.println(e.getMessage());
            if(tr != null) {
                tr.rollback();
            }
            return null;
        }finally {
            if(session != null){
                session.close();
            }
        }
    }

    private Order getOrderByRoomId(long roomId){
        Session session = null;
        Transaction tr = null;
        Order order;
        try{
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            String sql = "SELECT * FROM ORDERS WHERE ORDER_ROOM_ID = ?";
            NativeQuery query = session.createNativeQuery(sql, Order.class);
            query.setParameter(1, roomId);
            order = (Order)query.getSingleResult();
            tr.commit();
            return order;
        }catch (NoResultException ex){
            System.err.println(ex.getMessage());
            if(tr != null) {
                tr.rollback();
            }
            return null;
        }catch (HibernateException e){
            System.err.println(e.getMessage());
            if(tr != null) {
                tr.rollback();
            }
            return null;
        }finally {
            if(session != null){
                session.close();
            }
        }
    }

    private List<Room> getAllRooms(){
        Session session = null;
        Transaction tr = null;
        List<Room> rooms;
        try{
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            String sql = "SELECT * FROM ROOMS";
            NativeQuery query = session.createNativeQuery(sql, Hotel.class);

            rooms = (List<Room>)query.getResultList();

            tr.commit();
            return rooms;
        }catch (NoResultException ex){
            System.err.println(ex.getMessage());
            if(tr != null) {
                tr.rollback();
            }
            return null;
        }catch (HibernateException e){
            System.err.println(e.getMessage());
            if(tr != null) {
                tr.rollback();
            }
            return null;
        }finally {
            if(session != null){
                session.close();
            }
        }
    }


}
