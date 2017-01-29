package kishore.mad.com.weatherapp;
/*
* Homework 05
* Nanda Kishore Kolluru
* Ravi Teja Yarlagadda
* SavedCitiesRecyclerAdapter.java
* */
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;
import static kishore.mad.com.weatherapp.R.string.F;
import static kishore.mad.com.weatherapp.WeatherDataTaskUtil.DEGREE;

/**
 * Created by kishorekolluru on 10/9/16.
 */

public class SavedCitiesRecyclerAdapter extends RecyclerView.Adapter<SavedCitiesRecyclerAdapter.ViewHolder> implements View.OnClickListener{
    ArrayList<Favorite> items;
    private static final DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
    private ArrayList<Favorite> mDataset;
    private RecyclerView recycler;

    @Override
    public void onClick(View v) {
        int position = recycler.getChildAdapterPosition(v);
        Log.d("demo", "Clicked "+position+" item in list " +mDataset.get(position).getCity());
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder{
        // each data item is just a string in this case
        public View linLayItem;
        public ViewHolder(View v) {
            super(v);
            linLayItem = v;
        }


    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public SavedCitiesRecyclerAdapter(ArrayList<Favorite> myDataset, RecyclerView favListView) {
        this.recycler = favListView;
        this.mDataset = myDataset;
    }
    /*
        TextView tvUpd = holder.Upd;
        tvCity.setText(items.get(position).getCity() + ", " + items.get(position).getState());
        tvTemp.setText(new StringBuilder().append(new DecimalFormat("#.#").format(items.get(position).getTemperature())).append(DEGREE).append("F").toString());
        tvUpd.setText(new StringBuilder().append("Updated on :").append(df.format(items.get(position).getUpdatedOn())).toString());

        return convertView;
    }*/


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_list_item, parent, false);
        TextView v1 = (TextView) v.findViewById(R.id.tvFavCity);
        TextView v2 = (TextView) v.findViewById(R.id.tvFavTemp);
        v1.setText("v1");
        v2.setText("v2");
        v.setOnClickListener(this);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        View v  = holder.linLayItem;
        TextView v1 = (TextView) v.findViewById(R.id.tvFavCity);
        TextView v2 = (TextView) v.findViewById(R.id.tvFavTemp);
        v1.setText(mDataset.get(position).getCity());
        v2.setText("v2");

    }


    @Override
    public int getItemCount() {
        return 0;
    }
}
