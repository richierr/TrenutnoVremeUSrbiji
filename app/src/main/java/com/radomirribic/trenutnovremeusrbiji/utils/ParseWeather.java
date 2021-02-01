package com.radomirribic.trenutnovremeusrbiji.utils;

import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.ActionBar;

import com.radomirribic.trenutnovremeusrbiji.MainActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ParseWeather {
    private static final String TAG ="Parse weather" ;
    private ArrayList<FeedEntry> weatherEntries;
    private Context mContext;


    public ParseWeather(Context context) {
        mContext=context;
        this.weatherEntries = new ArrayList<>();
    }

    public ArrayList<FeedEntry> getWeatherEntries() {
        return weatherEntries;
    }

    public void setWeatherEntries(ArrayList<FeedEntry> weatherEntries) {
        this.weatherEntries = weatherEntries;
    }

    //Parse stores the entries into the arraylist
    public void parse(String xmlData) {
        String time=null;
        boolean status = true;
        FeedEntry currenRecord = null;
        boolean inEntry = false;
        String textValue = "";
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(xmlData));
            boolean timestampSet=false;
            int eventType = xpp.getEventType();
            while ((eventType != XmlPullParser.END_DOCUMENT)) {
                String tagName = xpp.getName();

                switch (eventType) {
                    case XmlPullParser.START_TAG:

                        if ("item".equalsIgnoreCase(tagName)) {
                            inEntry = true;
                            currenRecord = new FeedEntry();
                        }
                        break;
                    case XmlPullParser.TEXT:
                        textValue = xpp.getText();
                        String timestampText=textValue.toLowerCase();
                        if(timestampSet==false && timestampText.contains("rhmz srbije")){
                            time=textValue;
                            timestampSet=true;
                            time=time.replaceAll("RHMZ Srbije - Podaci sa meteoroloških stanica ","");
                            MainActivity mainActivity=(MainActivity)mContext;
                            ActionBar actionBar=((MainActivity) mContext).getSupportActionBar();

                            actionBar.setSubtitle("Podaci ažurirani: "+time);


                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if(inEntry){
                            if ("item".equalsIgnoreCase(tagName)) {
                                weatherEntries.add(currenRecord);
                                inEntry = false;
                            } else if ("title".equalsIgnoreCase(tagName)) {
                                currenRecord.setStationName(textValue);

                            } else if ("description".equalsIgnoreCase(tagName)) {
                                setFeedEntryFields(textValue, currenRecord);

                            }
                        }
                        break;
                    default:
                }
                eventType=xpp.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        Collections.sort(weatherEntries, (FeedEntry s1, FeedEntry s2) ->{
            return s1.getStationName().compareToIgnoreCase(s2.getStationName());
        });



    }

//    private String parseTime(){
//
//    }

    private void setFeedEntryFields(String data, FeedEntry currenRecord) {

        ArrayList<String> dataArrayList = new ArrayList<>( Arrays.asList(data.split(";")));

        for (String entry : dataArrayList) {
            String test =entry.toLowerCase();

            if (test.contains("temperatura")) {
                currenRecord.setTemperature(entry);
                continue;
            } else if (test.contains("pritisak")) {
                currenRecord.setPressure(entry);
                continue;
            } else if (test.contains("pravac")) {
                currenRecord.setWindDirection(entry);
                continue;
            } else if (test.contains("brzina")) {
                currenRecord.setWindSpeed(entry);
                continue;
            } else if (test.contains("nost")) {
                currenRecord.setHumidity(entry);
                continue;
            } else if (test.contains("opis vremena")) {
                currenRecord.setWeatherDescription(entry);
                continue;
            } else if (test.contains("opisa vremena")) {
                int iconNumber = Integer.parseInt(entry.replaceAll("[\\D]", ""));
                currenRecord.setIcon(iconNumber);
            }

        }
    }
}

//
//    private int temperature;
//    private double pressure;
//    private String windDirection;
//    private int windSpeed;
//    private int humidity;
//    private String weatherDescription;
//    private int icon;

//    ID stanice: 13067; Temperatura: 6 °C;
//Pritisak: 988.3 hPa; Pravac vetra: SW;
//Brzina vetra: 3 m/s;
//Vlažnost: 87 %;
//Opis vremena: Pretežno oblačno;
//Šifra opisa vremena: 22;