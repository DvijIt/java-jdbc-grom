import java.util.Date;

public class Order {
    private long id;
    private String productName;
    private int price;
    private Date dateOrdered;
    private Date dateConfirmed;

    public Order(long id, String productName, int price, Date dateOrdered, Date dateConfirmed) {
        this.id = id;
        this.productName = productName;
        this.price = price;
        this.dateOrdered = dateOrdered;
        this.dateConfirmed = dateConfirmed;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getDateOrdered() {
        return dateOrdered;
    }

    public void setDateOrdered(Date dateOrdered) {
        this.dateOrdered = dateOrdered;
    }

    public Date getDateConfirmed() {
        return dateConfirmed;
    }

    public void setDateConfirmed(Date dateConfirmed) {
        this.dateConfirmed = dateConfirmed;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", dateOrdered=" + dateOrdered +
                ", dateConfirmed=" + dateConfirmed +
                '}';
    }
}
