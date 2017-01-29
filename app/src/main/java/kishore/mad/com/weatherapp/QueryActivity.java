package kishore.mad.com.weatherapp;
/*
* Homework 05
* Nanda Kishore Kolluru
* Ravi Teja Yarlagadda
* QueryActivity.java
* */
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.lang3.text.WordUtils;

import java.util.ArrayList;

import static android.view.View.GONE;

public class QueryActivity extends AppCompatActivity {
    String baseUrl = "aef27d34f4a084a86868bb0c7ae931ee";
    public static final String STATE = "state";
    public static final String CITY = "city";
    public static final String SHARED_PREF_NAME = "weatherAppSharedPrefs1001";
    public static final String POSITION = "pos";
    private ArrayList<Favorite> adapterList;
    public static String msg = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStatusBar();
        setContentView(R.layout.activity_query);
        hookInterestingComponents();
        init();

    }

    private void init() {
        initFavList();
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager cm =
                        (ConnectivityManager) QueryActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null &&
                        activeNetwork.isConnected();
                if (isConnected) {

                    String city = etCity.getText().toString().trim().replaceAll("_", " ");
                    String state = etState.getText().toString().trim();
                    if (city != null && !"".equals(city) && state != null && !"".equals(state)) {
                        Intent intnt = new Intent(QueryActivity.this, CityWeatherActivity.class);
                        city = WordUtils.capitalizeFully(city);
                        state = state.toUpperCase();
                        intnt.putExtra(CITY, city);
                        intnt.putExtra(STATE, state);
                        startActivity(intnt);
                    } else {
                        Toast.makeText(QueryActivity.this, "Please enter valid city and state", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(QueryActivity.this, "Internet not available!", Toast.LENGTH_LONG).show();
                }

            }
        });
        checkMsg();

    }

    private void checkMsg() {
        if (msg != null) {
            toast(msg);
            msg = null;
        }
    }

    private void toast(String msg) {
        Toast.makeText(QueryActivity.this, msg, Toast.LENGTH_LONG).show();
    }

    ArrayList<Favorite> favorites = new ArrayList<>();
    TextView tvFavoritesTitle;
    ListView favListView;

    private void initFavList() {
        //Creating a shared preference


        Log.d("demo", "initFavList called");
        favorites = WeatherDataTaskUtil.getFavorites(this);
        if (favorites.size() > 0) {
            if (tvFavoritesTitle == null && favListView == null) {

                RelativeLayout layMain = (RelativeLayout) findViewById(R.id.activity_query);
                tvNoCityLocations.setVisibility(GONE);


                tvFavoritesTitle = new TextView(this);
                tvFavoritesTitle.setText("Favorites");
                tvFavoritesTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
                tvFavoritesTitle.setTextColor(Color.BLACK);
                tvFavoritesTitle.setTypeface(Typeface.DEFAULT_BOLD);
                int id = View.generateViewId();
                tvFavoritesTitle.setId(id);
                tvFavoritesTitle.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                params1.addRule(RelativeLayout.BELOW, R.id.linLay1);
                tvFavoritesTitle.setLayoutParams(params1);
                layMain.addView(tvFavoritesTitle);

                RecyclerView favListView = new RecyclerView(this);
                LinearLayoutManager linMgr = new LinearLayoutManager(this);
                linMgr.setOrientation(LinearLayoutManager.VERTICAL);
//                favListView = new ListView(this);
                adapterList = new ArrayList<Favorite>(favorites);
                SavedCitiesRecyclerAdapter adapter = new SavedCitiesRecyclerAdapter(adapterList,favListView);
                favListView.setHasFixedSize(true);
                favListView.setAdapter(adapter);
                favListView.setLayoutManager(linMgr);

                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                params.addRule(RelativeLayout.BELOW, id);
                favListView.setLayoutParams(params);
                favListView.setLongClickable(true);
                /*favListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                        SavedCitiesRecyclerAdapter adapter = (SavedCitiesRecyclerAdapter) parent.getAdapter();
                        adapter.remove(adapter.items.get(position));
                        Log.d("demo", "The position long clicked is " + position);
                        if (WeatherDataTaskUtil.getFavorites(QueryActivity.this).size() < 1) {
                            tvNoCityLocations.setVisibility(View.VISIBLE);
                            if (tvFavoritesTitle != null)
                                tvFavoritesTitle.setVisibility(GONE);
                            if (favListView != null)
                                favListView.setVisibility(GONE);
                        } else {
                            tvNoCityLocations.setVisibility(View.GONE);
                            if (tvFavoritesTitle != null)
                                tvFavoritesTitle.setVisibility(View.VISIBLE);
                            if (favListView != null)
                                favListView.setVisibility(View.VISIBLE);
                        }
                        Toast.makeText(QueryActivity.this, "Deleted Favorite", Toast.LENGTH_SHORT).show();
                        return false;

                    }
                });*/
                /*favListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.d("demo", "List Item CLICKED!!! " + position);
                        Intent intnt = new Intent(QueryActivity.this, CityWeatherActivity.class);
                        String city = favorites.get(position).getCity();
                        String state = favorites.get(position).getState();
                        intnt.putExtra(CITY, city);
                        intnt.putExtra(STATE, state);
                        startActivity(intnt);
                    }
                });*/
                layMain.addView(favListView);

            } else {
                /*SavedCitiesRecyclerAdapter adapter = new SavedCitiesRecyclerAdapter(this, R.layout.favorite_list_item, new ArrayList<Favorite>(favorites));
                favListView.setAdapter(adapter);
                tvNoCityLocations.setVisibility(View.GONE);
                tvFavoritesTitle.setVisibility(View.VISIBLE);
                favListView.setVisibility(View.VISIBLE);*/
            }

        } else {
            tvNoCityLocations.setVisibility(View.VISIBLE);
            if (tvFavoritesTitle != null)
                tvFavoritesTitle.setVisibility(GONE);
            if (favListView != null)
                favListView.setVisibility(GONE);


        }
    }

    Button btnSubmit;
    EditText etCity, etState;
    TextView tvNoCityLocations;

    private void hookInterestingComponents() {
        btnSubmit = (Button) findViewById(R.id.buttonSubmit);
        etCity = (EditText) findViewById(R.id.etCity);
        etState = (EditText) findViewById(R.id.etState);
        tvNoCityLocations = (TextView) findViewById(R.id.tvNoCityLocations);
    }

    @Override
    protected void onResume() {
        Log.d("demo", "On RESUME called");
        super.onResume();
        initFavList();
        hideStatusBar();
        checkMsg();
    }

    private void hideStatusBar() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
