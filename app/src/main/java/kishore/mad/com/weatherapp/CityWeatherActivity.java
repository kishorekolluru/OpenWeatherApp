package kishore.mad.com.weatherapp;
/*
* Homework 05
* Nanda Kishore Kolluru
* Ravi Teja Yarlagadda
* CityWeatherActivity.java
* */
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;

import static android.R.attr.start;

public class CityWeatherActivity extends AppCompatActivity implements WeatherDataTask.IData {
    public static final String base_url = "http://api.wunderground.com/api/abcb0d02d1d38886/hourly/q/";
    public static final String SELECTED_ITEM = "sel_item";
    ProgressDialog pdialog;
    private List<CityWeather> cityWeathers;
    private String city;
    private String state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStatusBar();
        setContentView(R.layout.activity_city_weather);
        pdialog = new ProgressDialog(this);
        pdialog.setCancelable(false);
        pdialog.setIndeterminate(true);
        pdialog.setMessage(getString(R.string.downloading_hourly_data));
        pdialog.show();
        city = (String) getIntent().getExtras().get(QueryActivity.CITY);
        state = (String) getIntent().getExtras().get(QueryActivity.STATE);
        new WeatherDataTask(this).execute(base_url + state + "/" + city.replaceAll(" ", "_") + ".json");
    }

    @Override
    public void stopProgress(List<CityWeather> cityWeathers) {
        pdialog.dismiss();
        this.cityWeathers = cityWeathers;
        displayHourlyData();
    }


    @Override
    public void toastError(String error) {
        pdialog.dismiss();
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private void displayHourlyData() {
        LinearLayout linLayMain = (LinearLayout) findViewById(R.id.activity_city_weather);
        TextView v1 = new TextView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        v1.setLayoutParams(params);
        v1.setText(new StringBuilder().append(getString(R.string.current_loc)).append(city).toString());
        v1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        linLayMain.addView(v1);

        ListView lview = new ListView(this);
        lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("demo", "List VIew item clicked");
                Intent intent = new Intent(CityWeatherActivity.this, DetailedWeatherActivity.class);
                intent.putExtra(QueryActivity.CITY, city);
                intent.putExtra(QueryActivity.STATE, state);
                intent.putExtra(QueryActivity.POSITION, position);
                intent.putExtra(SELECTED_ITEM, new Gson().toJson(cityWeathers));
                startActivity(intent);
            }
        });
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lview.setLayoutParams(params1);
        WeatherListItemAdapter adapter = new WeatherListItemAdapter(this, R.layout.list_view_layout, cityWeathers);
        adapter.setNotifyOnChange(true);
        lview.setAdapter(adapter);
        linLayMain.addView(lview);
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideStatusBar();
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
            boolean result = WeatherDataTaskUtil.saveFavorite(this, item, city, state, cityWeathers.get(0));
            return result;
        } else
            return super.onOptionsItemSelected(item);
    }

    private void hideStatusBar() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }
}
