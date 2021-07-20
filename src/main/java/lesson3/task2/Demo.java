package lesson3.task2;


public class Demo {
    public static void main(String[] args) throws BadRequestException {
        Solution productDAO = new Solution();

        System.out.println(productDAO.findProductsByPrice(1000, 100));;
        System.out.println(productDAO.findProductsByName("name"));
        System.out.println(productDAO.findProductsWithEmptyDescription());

    }
}
