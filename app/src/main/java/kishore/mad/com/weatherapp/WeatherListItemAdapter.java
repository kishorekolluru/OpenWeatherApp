package kishore.mad.com.weatherapp;
/*
* Homework 05
* Nanda Kishore Kolluru
* Ravi Teja Yarlagadda
* WeatherListItemAdapter.java
* */
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kishorekolluru on 10/8/16.
 */

public class WeatherListItemAdapter extends ArrayAdapter<CityWeather> {
    List<CityWeather> items;
    private static final DateFormat df = new SimpleDateFormat("h:mm a");

    public WeatherListItemAdapter(Context context, int resource, List<CityWeather> objects) {
        super(context, resource, objects);
        items = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_view_layout, parent, false);
        }
        TextView clouds = (TextView) convertView.findViewById(R.id.tvClouds);
        TextView temp = (TextView) convertView.findViewById(R.id.tvTemperature);
        TextView time = (TextView) convertView.findViewById(R.id.tvTime);
        ImageView iv = (ImageView) convertView.findViewById(R.id.listViewItemImage);
        Picasso.with(getContext()).load(items.get(position).getIconUrl()).into(iv);
        clouds.setText(items.get(position).getClouds());
        time.setText(df.format(items.get(position).getTime()));
        temp.setText(new StringBuilder().append(String.valueOf((int) items.get(position).getTemperature())).append(WeatherDataTaskUtil.DEGREE).append("F").toString());
        return convertView;
    }
}
