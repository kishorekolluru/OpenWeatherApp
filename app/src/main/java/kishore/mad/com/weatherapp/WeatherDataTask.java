package kishore.mad.com.weatherapp;
/*
* Homework 05
* Nanda Kishore Kolluru
* Ravi Teja Yarlagadda
* WeatherDataTask.java
* */
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static kishore.mad.com.weatherapp.WeatherDataTaskUtil.parseWeatherJson;

/**
 * Created by kishorekolluru on 10/8/16.
 */

public class WeatherDataTask extends AsyncTask<String, Void, List<CityWeather>> {
    IData activity;
    boolean errored;
    String errMsg;

    public WeatherDataTask(IData activity) {
        this.activity = activity;
    }

    @Override
    protected List<CityWeather> doInBackground(String... params) {
        ArrayList<CityWeather> weatherList = new ArrayList<>();
        String url = params[0];
        try {
            HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
            con.connect();
            int statuscode = con.getResponseCode();
            if (statuscode == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder b = new StringBuilder();
                String line = br.readLine();
                while (line != null) {
                    b.append(line);
                    line = br.readLine();
                }
                errMsg = WeatherDataTaskUtil.checkError(b.toString());
                if (errMsg != null) {
                    errored = true;
                    return null;
                } else {
                    weatherList = WeatherDataTaskUtil.parseWeatherJson(b.toString());
                }

            }
        } catch (Exception e) {
            Log.d("demo", e.toString());
        }
        return weatherList;
    }

    @Override
    protected void onPostExecute(List<CityWeather> cityWeathers) {
        if (!errored) {
            activity.stopProgress(cityWeathers);
        } else {
            activity.toastError(errMsg);
        }

    }

    static public interface IData {
        public void stopProgress(List<CityWeather> cityWeathers);


        public void toastError(String error);
    }
}
