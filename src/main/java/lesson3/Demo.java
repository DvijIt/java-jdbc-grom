package lesson3;

public class Demo {
    public static void main(String[] args) {
        ProductDAO productDAO = new ProductDAO();

        Product product = new Product(11, "TestName", "Test Description", 1017);
        Product product2 = new Product(12, "TestName2", "Test Description44", 3017);

        productDAO.save(product);
        productDAO.save(product2);

        System.out.println(productDAO.getProducts());

        productDAO.delete(1);

        product2.setName("New name");

        productDAO.update(product2);

    }
}
