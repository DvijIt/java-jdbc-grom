package hibernate.lesson1;

import org.hibernate.Session;
import java.util.function.Consumer;

public class ProductRepositoryWithLambda {
    private static final HibernateUtils HIBERNATE_UTILS = new HibernateUtils();

    public Product save(Product product) {
        executeInsideTransaction(session -> session.save(product));
        return product;
    }

    public void delete(long id) {
        Product product = new Product();
        product.setId(id);
        executeInsideTransaction(session -> session.delete(product));
    }

    public Product update(Product product) {
        executeInsideTransaction(session -> session.update(product));
        return product;
    }

    private void executeInsideTransaction(Consumer<Session> action) {
        Session session = HIBERNATE_UTILS.createSessionFactory().openSession();
        session.getTransaction().begin();

        action.accept(session);

        session.getTransaction().commit();
        session.close();
    }
}
