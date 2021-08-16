package hibernate.lesson4.entity;

import hibernate.lesson4.utils.UserType;
import hibernate.lesson4.utils.UserTypeAttributeConverter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "USERS")
public class User {
    private long id;
    private String country;
    private String password;
    private String userName;
    private UserType userType;
    private List<Order> orders;

    public User() {
    }

    public User(long id, String country, String password, String userName, List<Order> orders, UserType userType) {
        this.id = id;
        this.country = country;
        this.password = password;
        this.userName = userName;
        this.orders = orders;
        this.userType = userType;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "COUNTRY")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Column(name = "PASSWORD")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "NAME")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @OneToMany(fetch = FetchType.EAGER, targetEntity = Order.class, cascade = CascadeType.ALL, mappedBy = "userOrdered")
    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Convert(converter = UserTypeAttributeConverter.class)
    @Column(name = "USER_TYPE")
    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(country, user.country) && Objects.equals(password, user.password) && Objects.equals(userName, user.userName) && userType == user.userType && Objects.equals(orders, user.orders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, country, password, userName, userType, orders);
    }
}
