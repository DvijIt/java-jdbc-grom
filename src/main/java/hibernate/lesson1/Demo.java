package hibernate.lesson1;


public class Demo {
    public static void main(String[] args) {
        Product product = new Product(11234, "TestNameAgain", "Test ewfeafDescription", 1017);

        ProductRepository.delete(11);

    }
}
