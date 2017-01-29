package kishore.mad.com.weatherapp;

/*
* Homework 05
* Nanda Kishore Kolluru
* Ravi Teja Yarlagadda
* CityWeather.java
* */
import java.io.Serializable;
import java.util.Date;

/**
 * Created by kishorekolluru on 10/8/16.
 */
public class CityWeather implements Serializable {
    Date time;
    double temperature;
    double dewpoint;
    String clouds;
    String iconUrl;
    int windSpeed;
    String windDirection;
    String climateType;
    double humidity;
    double feelsLike;
    double maximumTemp;
    double minimumTemp;
    double pressure;

    @Override
    public String toString() {

        return "Temp " + temperature + ";time :" + time + ";dewpoint :" + dewpoint + ";clouds :" + clouds
                + ";icon :" + iconUrl + "; windSpeed " + windSpeed + "; windDirection :" + windDirection
                + "; climateType :" + climateType + "; humidity :" + humidity + "; feelsLike :" + feelsLike
                + "; pressure :" + pressure;
    }

    public String getClimateType() {
        return climateType;
    }

    public void setClimateType(String climateType) {
        this.climateType = climateType;
    }

    public String getClouds() {
        return clouds;
    }

    public void setClouds(String clouds) {
        this.clouds = clouds;
    }

    public double getDewpoint() {
        return dewpoint;
    }

    public void setDewpoint(double dewpoint) {
        this.dewpoint = dewpoint;
    }

    public double getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(double feelsLike) {
        this.feelsLike = feelsLike;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public double getMaximumTemp() {
        return maximumTemp;
    }

    public void setMaximumTemp(double maximumTemp) {
        this.maximumTemp = maximumTemp;
    }

    public double getMinimumTemp() {
        return minimumTemp;
    }

    public void setMinimumTemp(double minimumTemp) {
        this.minimumTemp = minimumTemp;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public int getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(int windSpeed) {
        this.windSpeed = windSpeed;
    }
}
