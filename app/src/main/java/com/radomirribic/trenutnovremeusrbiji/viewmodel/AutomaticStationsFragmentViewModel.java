package com.radomirribic.trenutnovremeusrbiji.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.radomirribic.trenutnovremeusrbiji.utils.OnResultCallback;
import com.radomirribic.trenutnovremeusrbiji.repository.WeatherDataRepository;
import com.radomirribic.trenutnovremeusrbiji.fragments.AutomaticStationsFragment;

import java.util.List;

public class AutomaticStationsFragmentViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<List<AutomaticStationsFragment.FeedEntry>> rssFeed=new MutableLiveData<>();
    WeatherDataRepository weatherDataRepository;

    public void getData(){
        if(weatherDataRepository==null){
            weatherDataRepository=new WeatherDataRepository();
        }
        weatherDataRepository.fetchData( new OnResultCallback() {
            @Override
            public void onResult(Object object) {
                rssFeed.postValue((List<AutomaticStationsFragment.FeedEntry>)object);
            }

            @Override
            public void onError(Object object) {

            }
        });
    }

    public MutableLiveData<List<AutomaticStationsFragment.FeedEntry>> getRssFeed() {
        return rssFeed;
    }
}