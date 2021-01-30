package com.radomirribic.trenutnovremeusrbiji.utils.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.radomirribic.trenutnovremeusrbiji.R;
import com.radomirribic.trenutnovremeusrbiji.utils.FeedEntry;

import java.util.List;

public class StationAdapter extends RecyclerView.Adapter {

    private List<FeedEntry> feedEntries;
    private int feedId;
    private Context mContext;


    //construct
    public StationAdapter(List<FeedEntry> feedEntries) {
        this.feedEntries=feedEntries;
    }


    //onCreateViewHolder
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.station_card, parent, false);
        mContext=parent.getContext();

        return new MyListViewHolder(view);
    }

    //onBindViewHolder
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyListViewHolder myListViewHolder=(MyListViewHolder)holder;
        myListViewHolder.bindView(position);

    }

    @Override
    public int getItemCount() {
        return feedEntries.size();
    }


    class MyListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView stationName, temperature,pressure,windDirection,windSpeed,humidity,weatherDescription;
        ImageView weatherIcon;

        public MyListViewHolder(@NonNull View itemView) {
            super(itemView);
            stationName=itemView.findViewById(R.id.txtStationName);
            temperature=itemView.findViewById(R.id.txtTemperature);
            pressure=itemView.findViewById(R.id.txtPressure);
            windDirection=itemView.findViewById(R.id.txtWindDirection);
            windSpeed=itemView.findViewById(R.id.txtWindSpeed);
            humidity=itemView.findViewById(R.id.txtHumidity);
            weatherDescription=itemView.findViewById(R.id.txtWeatherDescription);

        }


        public void bindView(int position){
            stationName.setText(feedEntries.get(position).getStationName());
            temperature.setText(feedEntries.get(position).getTemperature());
            pressure.setText(feedEntries.get(position).getPressure());
            windDirection.setText(feedEntries.get(position).getWindDirection());
            windSpeed.setText(feedEntries.get(position).getWindSpeed());
            humidity.setText(feedEntries.get(position).getHumidity());
            weatherDescription.setText(feedEntries.get(position).getWeatherDescription());
            //Glide.with(mContext).load(getIconResource(feedEntries.get(position).getIcon())).into(weatherIcon);
            //Glide.with(mContext).load(feedEntries.get(position).getIcon()).into(weatherIcon);
            Glide.with(mContext).load(feedEntries.get(position).getIcon()).into(weatherIcon);

        }

//        private byte[] getIconResource(int icon) {
//
//        }

        private Integer getPicId(int id){
            StringBuilder st=new StringBuilder();
            st.append("a");
            st.append(id);
            st.append(".png");
            return Integer.valueOf(st.toString());

        }


        @Override
        public void onClick(View v) {

        }
    }
}