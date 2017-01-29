package kishore.mad.com.weatherapp;
/*
* Homework 05
* Nanda Kishore Kolluru
* Ravi Teja Yarlagadda
* Favorite.java
* */
import java.util.Date;

/**
 * Created by kishorekolluru on 10/9/16.
 */

public class Favorite {
    private Date updatedOn;
    private String city;
    private String state;
    private double temperature;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }
}
