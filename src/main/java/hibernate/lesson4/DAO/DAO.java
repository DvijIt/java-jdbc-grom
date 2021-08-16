package hibernate.lesson4.DAO;



public abstract class DAO<T> {

    public T save(T t) {
        return null;
    }

    public abstract void delete(long id);

    public abstract T update(T t);

    public abstract T findById(long id);

}
