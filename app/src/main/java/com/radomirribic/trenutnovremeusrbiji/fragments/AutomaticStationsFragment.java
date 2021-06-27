package com.radomirribic.trenutnovremeusrbiji.fragments;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.radomirribic.trenutnovremeusrbiji.viewmodel.AutomaticStationsFragmentViewModel;
import com.radomirribic.trenutnovremeusrbiji.R;
import com.radomirribic.trenutnovremeusrbiji.adapters.StationAdapter;
import com.radomirribic.trenutnovremeusrbiji.databinding.AutomaticStationsFragmentListBinding;


import java.util.ArrayList;

public class AutomaticStationsFragment extends Fragment {

    public static final String TAG = "DownloadDataFragment";
    private AutomaticStationsFragmentViewModel mViewModel;
    private AutomaticStationsFragmentListBinding binding;
    private StationAdapter stationAdapter;

    public static AutomaticStationsFragment newInstance() {
        return new AutomaticStationsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater, R.layout.automatic_stations_fragment_list,container,false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AutomaticStationsFragmentViewModel.class);
        mViewModel.getData();
        binding.setViewmodel(mViewModel);
        binding.setFragment(this);
        binding.downloadDataRecy.setItemAnimator(new DefaultItemAnimator());
        stationAdapter=new StationAdapter(new ArrayList<>());
        // stationAdapter.setUpData(mViewModel.getRssFeed().getValue());
        binding.downloadDataRecy.setLayoutManager((new LinearLayoutManager(getContext())));
        binding.downloadDataRecy.setAdapter(stationAdapter);
        observeRssData();
    }
    public void observeRssData(){
        mViewModel.getRssFeed().observe(this,list->{
                    list.forEach(feedEntry -> System.out.println(feedEntry.getHumidity()));
                    stationAdapter.setUpData(list);
                });

        stationAdapter.notifyDataSetChanged();
    }

    public static class FeedEntry {
        private String stationName, temperature,pressure,windDirection,windSpeed,humidity,weatherDescription;

        private int icon;

        public String getStationName() {
            return stationName;
        }

        public void setStationName(String stationName) {
            this.stationName = stationName;
        }

        public String getTemperature() {
            return temperature;
        }

        public void setTemperature(String temperature) {
            this.temperature = temperature;
        }

        public String getPressure() {
            return pressure;
        }

        public void setPressure(String pressure) {
            this.pressure = pressure;
        }

        public String getWindDirection() {
            return windDirection;
        }

        public void setWindDirection(String windDirection) {
            this.windDirection = windDirection;
        }

        public String getWindSpeed() {
            return windSpeed;
        }

        public void setWindSpeed(String windSpeed) {
            this.windSpeed = windSpeed;
        }

        public String getHumidity() {
            return humidity;
        }

        public void setHumidity(String humidity) {
            this.humidity = humidity;
        }

        public String getWeatherDescription() {
            return weatherDescription;
        }

        public void setWeatherDescription(String weatherDescription) {
            this.weatherDescription = weatherDescription;
        }

        public int getIcon() {
            return icon;
        }

        public void setIcon(int icon) {
            this.icon = icon;
        }

        @Override
        public String toString() {
            return "FeedEntry{" +
                    "stationName='" + stationName + '\'' +
                    ", temperature=" + temperature +
                    ", pressure=" + pressure +
                    ", windDirection='" + windDirection + '\'' +
                    ", windSpeed=" + windSpeed +
                    ", humidity=" + humidity +
                    ", weatherDescription='" + weatherDescription + '\'' +
                    '}';
        }
    }
}