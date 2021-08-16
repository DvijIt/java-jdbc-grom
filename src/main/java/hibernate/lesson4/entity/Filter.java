package hibernate.lesson4.entity;

import hibernate.lesson4.utils.UserType;

import java.util.Date;

public class Filter {
    private String name;
    private String country;
    private String city;
    private String street;
    private Date dateFrom;
    private Date dateTo;
    private double moneyPaid;
    private Integer numberOfGuests;
    private double price;
    private char breakfastIncluded;
    private char petsAllowed;
    private Date dateAvailableFrom;
    private String userName;
    private UserType userType;

    public Filter(String name, String country, String city, String street, Date dateFrom, Date dateTo, double moneyPaid, Integer numberOfGuests, double price, char breakfastIncluded, char petsAllowed, Date dateAvailableFrom, String userName, UserType userType) {
        this.name = name;
        this.country = country;
        this.city = city;
        this.street = street;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.moneyPaid = moneyPaid;
        this.numberOfGuests = numberOfGuests;
        this.price = price;
        this.breakfastIncluded = breakfastIncluded;
        this.petsAllowed = petsAllowed;
        this.dateAvailableFrom = dateAvailableFrom;
        this.userName = userName;
        this.userType = userType;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public double getMoneyPaid() {
        return moneyPaid;
    }

    public Integer getNumberOfGuests() {
        return numberOfGuests;
    }

    public double getPrice() {
        return price;
    }

    public char getBreakfastIncluded() {
        return breakfastIncluded;
    }

    public char getPetsAllowed() {
        return petsAllowed;
    }

    public Date getDateAvailableFrom() {
        return dateAvailableFrom;
    }

    public String getUserName() {
        return userName;
    }

    public UserType getUserType() {
        return userType;
    }
}
