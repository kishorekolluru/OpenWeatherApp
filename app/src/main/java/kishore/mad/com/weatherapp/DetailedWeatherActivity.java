package kishore.mad.com.weatherapp;
/*
* Homework 05
* Nanda Kishore Kolluru
* Ravi Teja Yarlagadda
* DetailedWeatherActivity.java
* */
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static kishore.mad.com.weatherapp.WeatherDataTaskUtil.saveFavorite;

public class DetailedWeatherActivity extends AppCompatActivity {

    private ArrayList<CityWeather> weatherList;
    int position;
    private String state;
    private String city;
    private static final DecimalFormat df = new DecimalFormat("#.#");
    private CityWeather weather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_weather);
        hideStatusBar();
        hookInterestingComponents();
        Type listType = new TypeToken<ArrayList<CityWeather>>() {
        }.getType();
        weatherList = new Gson().fromJson((String) (getIntent().getExtras().get(CityWeatherActivity.SELECTED_ITEM)), listType);
        city = (String) getIntent().getExtras().get(QueryActivity.CITY);
        position = (int) getIntent().getExtras().get(QueryActivity.POSITION);
        state = (String) getIntent().getExtras().get(QueryActivity.STATE);
        tvDetailCity.setText(getDetailCity());
        weather = weatherList.get(position);
        tvDetailTemperature.setText(new StringBuilder().append(df.format(weather.getTemperature())).append(WeatherDataTaskUtil.DEGREE).append(getString(R.string.F)).toString());
        tvDetailCloudy.setText(weather.getClouds());
        tvDetailMaxTemp.setText(new StringBuilder().append(df.format(weather.getMaximumTemp())).append(WeatherDataTaskUtil.DEGREE).append(getString(R.string.F)).toString());
        tvDetailMinTemp.setText(new StringBuilder().append(df.format(weather.getMinimumTemp())).append(WeatherDataTaskUtil.DEGREE).append(getString(R.string.F)).toString());
        tvDetailFeelsLike.setText(new StringBuilder().append(df.format(weather.getFeelsLike())).append(getString(R.string.fahrenheit)).toString());
        tvDetailHumidity.setText(new StringBuilder().append(df.format(weather.getHumidity())).append("%").toString());
        tvDetailDewpoint.setText(new StringBuilder().append(df.format(weather.getDewpoint())).append(getString(R.string.fahrenheit)).toString());
        tvDetailPressure.setText(new StringBuilder().append(df.format(weather.getPressure())).append(getString(R.string.hpa)).toString());
        tvDetailClouds.setText(weather.getClimateType());
        tvDetailWinds.setText(getWinds());
        Picasso.with(this).load(weather.getIconUrl()).into(ivDetailImage);

    }

    private CharSequence getWinds() {
        return df.format(weather.getWindSpeed()) + " mph, " + weather.getWindDirection();
    }

    private String getDetailCity() {
        return new StringBuilder().append(" ").append(city).append(", ").append(state).append(" (")
                .append(new SimpleDateFormat("h:mm a", Locale.US).format(new Date())).append(")").toString();
    }

    TextView tvDetailCity, tvDetailTemperature, tvDetailCloudy, tvDetailMaxTemp, tvDetailMinTemp,
            tvDetailFeelsLike, tvDetailHumidity, tvDetailDewpoint, tvDetailPressure, tvDetailClouds, tvDetailWinds;
    ImageView ivDetailImage;

    private void hookInterestingComponents() {
        tvDetailCity = (TextView) findViewById(R.id.tvDetailCity);
        tvDetailTemperature = (TextView) findViewById(R.id.tvDetailTemperature);
        tvDetailCloudy = (TextView) findViewById(R.id.tvDetailCloudy);
        tvDetailMaxTemp = (TextView) findViewById(R.id.tvDetailMaxTemp);
        tvDetailMinTemp = (TextView) findViewById(R.id.tvDetailMinTemp);
        tvDetailFeelsLike = (TextView) findViewById(R.id.tvDetailFeelsLike);
        tvDetailHumidity = (TextView) findViewById(R.id.tvDetailHumidity);
        tvDetailDewpoint = (TextView) findViewById(R.id.tvDetailDewpoint);
        tvDetailPressure = (TextView) findViewById(R.id.tvDetailPressure);
        tvDetailClouds = (TextView) findViewById(R.id.tvDetailClouds);
        tvDetailWinds = (TextView) findViewById(R.id.tvDetailWinds);
        ivDetailImage = (ImageView) findViewById(R.id.ivDetailImage);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflator = getMenuInflater();
        inflator.inflate(R.menu.activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("demo", "HERE IN THE OPIONS ITEM ");
        if (item.getItemId() == R.id.act_menu) {
            boolean result = WeatherDataTaskUtil.saveFavorite(this, item, city, state, weatherList.get(0));
            return result;
        } else
            return super.onOptionsItemSelected(item);
    }

    private void hideStatusBar() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
