package com.radomirribic.trenutnovremeusrbiji;

import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import com.radomirribic.trenutnovremeusrbiji.utils.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WeatherDataRepository {
    public static final String TAG="WeatherdataRepository";
    private Handler handler;

    public void fetchData(OnResultCallback onResultCallback) {
        getHandler().post(()->{
             onResultCallback.onResult(RSSWeatherDataParser.parse(downloadXML(Constants.PATH)));
        });

    }

    private String downloadXML(String path) {
        StringBuilder xmlResult = new StringBuilder();
        try {
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            int response = connection.getResponseCode();
            Log.d(TAG, "downloadXML: LOG RESPONSE WAS " + response);
            InputStream inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            int charRead;
            char[] inputBuffer = new char[500];
            while (true) {
                charRead = reader.read(inputBuffer);
                if (charRead < 0) {
                    break;
                }
                if (charRead > 0) {
                    xmlResult.append(String.copyValueOf(inputBuffer, 0, charRead));
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
    private synchronized Handler getHandler() {
        if (handler == null) {
            HandlerThread mThread = new HandlerThread("eld-sdk-thread");
            mThread.start();
            handler = new Handler(mThread.getLooper());
        }
        return handler;
    }
}
