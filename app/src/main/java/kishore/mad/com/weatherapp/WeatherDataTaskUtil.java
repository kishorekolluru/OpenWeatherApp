package kishore.mad.com.weatherapp;
/*
* Homework 05
* Nanda Kishore Kolluru
* Ravi Teja Yarlagadda
* WeatherDataTaskUtil.java
* */
import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import static android.R.attr.max;
import static android.content.Context.MODE_PRIVATE;
import static kishore.mad.com.weatherapp.QueryActivity.SHARED_PREF_NAME;

/**
 * Created by kishorekolluru on 10/8/16.
 */
public class WeatherDataTaskUtil {
    public static final String DEGREE = "\u00B0";

    public static ArrayList<CityWeather> parseWeatherJson(String s) throws JSONException {
        CityWeather weather;
        JSONObject main = new JSONObject(s);
        ArrayList<CityWeather> weatherList = new ArrayList<>();
        JSONArray hourlyForecasts = main.getJSONArray("hourly_forecast");
        for (int i = 0; i < hourlyForecasts.length(); i++) {
            JSONObject obj = hourlyForecasts.getJSONObject(i);
            weather = new CityWeather();
            weather.setClimateType(getClimateType(obj));
            weather.setClouds(getClouds(obj));
            weather.setDewpoint(getDewpoint(obj));
            weather.setFeelsLike(getFeelsLike(obj));
            weather.setHumidity(getHumidity(obj));
            weather.setIconUrl(getIconUrl(obj));
            weather.setMaximumTemp(getMaxTemp(obj));
            weather.setMinimumTemp(getMinTemp(obj));
            weather.setPressure(getPressure(obj));
            weather.setWindDirection(getWindDir(obj));
            weather.setTime(getTime(obj));
            weather.setWindSpeed(getWindSped(obj));
            weather.setTemperature(getTemp(obj));
            weatherList.add(weather);
        }

        double min = Integer.MAX_VALUE, max = -1;
        for (CityWeather w : weatherList) {
            GregorianCalendar gc1 = new GregorianCalendar();
            GregorianCalendar gc2 = new GregorianCalendar();
            gc2.setTime(w.getTime());
            if (gc1.get(Calendar.MONTH) == gc2.get(Calendar.MONTH) &&
                    gc1.get(Calendar.DATE) == gc2.get(Calendar.DATE) &&
                    gc1.get(Calendar.YEAR) == gc2.get(Calendar.YEAR)) {

                if (min > w.getTemperature()) {
                    min = w.getTemperature();
                }
                if (max < w.getTemperature()) {
                    max = w.getTemperature();
                }

            }

        }
        for (CityWeather w : weatherList) {
            w.setMaximumTemp(max);
            w.setMinimumTemp(min);
        }
        return weatherList;
    }

    private static double getTemp(JSONObject obj) throws JSONException {
        return obj.getJSONObject("temp").getDouble("english");
    }

    private static int getWindSped(JSONObject obj) throws JSONException {
        return obj.getJSONObject("wspd").getInt("english");
    }

    private static Date getTime(JSONObject obj) throws JSONException {
        int hours = obj.getJSONObject("FCTTIME").getInt("hour");
        int mins = obj.getJSONObject("FCTTIME").getInt("min_unpadded");
        int secs = obj.getJSONObject("FCTTIME").getInt("sec");
        int year = obj.getJSONObject("FCTTIME").getInt("year");
        int month = obj.getJSONObject("FCTTIME").getInt("mon");//gregorian month is 0 based but api is 1 based
        int date = obj.getJSONObject("FCTTIME").getInt("mday");
        GregorianCalendar gc = new GregorianCalendar(year, month - 1, date, hours, mins, secs);
        return gc.getTime();
    }

    private static String getWindDir(JSONObject obj) throws JSONException {
        return obj.getJSONObject("wdir").getString("degrees") + DEGREE + " " + obj.getJSONObject("wdir").getString("dir");
    }

    private static double getPressure(JSONObject obj) throws JSONException {
        return obj.getJSONObject("mslp").getDouble("english");
    }

    private static double getMinTemp(JSONObject obj) {
        return 0;
    }

    private static double getMaxTemp(JSONObject obj) {
        return 0;
    }

    private static String getIconUrl(JSONObject obj) throws JSONException {
        return obj.getString("icon_url");
    }

    private static double getHumidity(JSONObject obj) throws JSONException {
        return obj.getDouble("humidity");
    }

    private static Double getFeelsLike(JSONObject obj) throws JSONException {
        return obj.getJSONObject("feelslike").getDouble("english");
    }

    private static double getDewpoint(JSONObject obj) throws JSONException {
        return obj.getJSONObject("dewpoint").getDouble("english");
    }

    private static String getClouds(JSONObject obj) throws JSONException {
        return obj.getString("condition");
    }

    private static String getClimateType(JSONObject obj) throws JSONException {
        return obj.getString("wx");
    }

    private static SharedPreferences prefs;

    public static boolean saveFavorite(Activity act, MenuItem item, String city, String state, CityWeather weather) {

        Favorite fav = new Favorite();
        fav.setCity(city);
        fav.setState(state);
        fav.setTemperature(weather.getTemperature());
        fav.setUpdatedOn(new Date());

        prefs = act.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Map<String, String> sharedPrefs = (Map<String, String>) prefs.getAll();
        Gson gson = new Gson();
        String favorite;
        if (!sharedPrefs.containsKey(city)) {
            favorite = gson.toJson(fav);
            editor.putString(city, favorite);
            Toast.makeText(act, "Added to Favorites", Toast.LENGTH_SHORT).show();
        } else {
            Favorite favStored = gson.fromJson(sharedPrefs.get(city), Favorite.class);
            favStored.setUpdatedOn(new Date());
            favStored.setTemperature(weather.getTemperature());
            favorite = gson.toJson(favStored);
            editor.putString(city, favorite);
            Toast.makeText(act, "Updated Favorites Record", Toast.LENGTH_SHORT).show();
        }
        editor.apply();
        return false;
    }

    public static ArrayList<Favorite> getFavorites(Activity act) {
        if (prefs == null)
            prefs = act.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        Gson gson = new Gson();
        ArrayList<Favorite> favs = new ArrayList<>();
        Map<String, String> map = (Map<String, String>) prefs.getAll();
        for (String key : map.keySet()) {
            String f = map.get(key);
            favs.add(gson.fromJson(f, Favorite.class));
        }
        return favs;
    }

    public static void removeFavorite(Favorite object) {
        if (prefs != null) {//getFavorites() is called atleast once from the QueryActivity onCreate()
            SharedPreferences.Editor editor = prefs.edit();
            editor.remove(object.getCity());
            editor.commit();
        }

    }

    public static String checkError(String s) throws JSONException {
        JSONObject main = new JSONObject(s);
        String err = null;
        JSONObject resp = main.getJSONObject("response");
        if (!resp.isNull("error")) {
            err = resp.getJSONObject("error").getString("description");
        }
        if (err == null && !resp.isNull("results")) {//if city is correct the api suggests cities of same name in various states
            err = "Enter correct State Initials";
        }
        return err;
    }
}
