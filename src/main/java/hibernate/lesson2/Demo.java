package hibernate.lesson2;

public class Demo {
    public static void main(String[] args) {
        ProductNativeSQLDAO productNativeSQLDAO = new ProductNativeSQLDAO();

//        System.out.println(productNativeSQLDAO.findById(12));
        System.out.println(productNativeSQLDAO.findByName("single"));
//        System.out.println(productNativeSQLDAO.findByContainedName("tes"));
//        System.out.println(productNativeSQLDAO.findByPrice(30, 10));
//        System.out.println(productNativeSQLDAO.findByNameSortedAsc("prod"));
//        System.out.println(productNativeSQLDAO.findByNameSortedDesc("prod"));
//        System.out.println(productNativeSQLDAO.findByPriceSortedDesc(30, 10));
    }
}
