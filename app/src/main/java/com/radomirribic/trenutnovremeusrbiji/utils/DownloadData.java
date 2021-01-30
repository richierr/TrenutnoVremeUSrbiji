package com.radomirribic.trenutnovremeusrbiji.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;


import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.radomirribic.trenutnovremeusrbiji.MainActivity;
import com.radomirribic.trenutnovremeusrbiji.R;
import com.radomirribic.trenutnovremeusrbiji.utils.adapters.StationAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class DownloadData extends AsyncTask<Void, Void, String> {
    private Context context;
    private RecyclerView recyclerView;
    private static final String TAG = "LogDownloadData";
    
    
    public DownloadData(Context context) {
        this.context=context;
    }

    
    //Do in background returns the rss as a String
    @Override
    protected String doInBackground(Void... voids) {
        String rssFeed=downloadXML(context.getString(R.string.rss_url));
        return rssFeed;
    }

    
    
    
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(s==null){


        }else{

            ParseWeather parseWeather=new ParseWeather();
            parseWeather.parse(s);
            Log.d(TAG, "onPostExecute: HERE I AM");
//            TextView testTextView =
//                    (TextView) ((MainActivity)context).findViewById(R.id.txtViewTest);
//            testTextView.setText(s);
            for(FeedEntry entry:parseWeather.getWeatherEntries()){
                Log.d(TAG, "onPostExecute: name "+entry.getStationName()+" pic num "+entry.getIcon());
            }

            setRecyclerView(((MainActivity) context).findViewById(R.id.recycler_view),parseWeather.getWeatherEntries());

        }
        



    }




    private void setRecyclerView(View view, ArrayList<FeedEntry> feedEntries){

        recyclerView=view.findViewById(R.id.recycler_view);
        StationAdapter adapter = new StationAdapter(feedEntries);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager((MainActivity)context, 1);
        recyclerView.addItemDecoration(new DividerItemDecoration((MainActivity)context, LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


    }

    private String downloadXML(String path) {

        StringBuilder xmlResult=new StringBuilder();
        try{
            URL url=new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            int response = connection.getResponseCode();

            Log.d(TAG, "downloadXML: LOG RESPONSE WAS "+response);

            InputStream inputStream=connection.getInputStream();
            BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
            int charRead;
            char[] inputBuffer=new char[500];
            while(true){
                charRead=reader.read(inputBuffer);
                if(charRead<0){
                    break;
                }
                if(charRead>0){
                    xmlResult.append(String.copyValueOf(inputBuffer,0,charRead));
                }
            }
            reader.close();
            
            return xmlResult.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
