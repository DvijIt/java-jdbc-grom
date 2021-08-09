package lesson5;

import org.hibernate.Session;

public class Demo {
    public static void main(String[] args) {
        Session session = new HibernateUtils().createSessionFactory().openSession();

        session.getTransaction().begin();

        Product product = new Product(11234, "TestNameAgain", "Test ewfeafDescription", 1017);

        session.save(product);

        session.getTransaction().commit();

        System.out.println("Done");

        session.close();
    }
}
