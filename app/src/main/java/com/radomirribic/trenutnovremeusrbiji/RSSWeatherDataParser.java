package com.radomirribic.trenutnovremeusrbiji;

import com.radomirribic.trenutnovremeusrbiji.fragments.AutomaticStationsFragment;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RSSWeatherDataParser {


    // private static ArrayList<FeedEntry> weatherEntries;
    //    private Context mContext;
    //
    //    public RSSWeatherDataParser(Context context) {
    //        mContext=context;
    //        this.weatherEntries = new ArrayList<>();
    //    }

    //    public ArrayList<FeedEntry> getWeatherEntries() {
    //        return weatherEntries;
    //    }

    //    public void setWeatherEntries(ArrayList<FeedEntry> weatherEntries) {
    //        this.weatherEntries = weatherEntries;
    //    }


    public String getTimestamp(String xmlData){
        String textValue = "";
        String time="";
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

                        }
                        break;
                    case XmlPullParser.TEXT:
                        textValue = xpp.getText();
                        String timestampText=textValue.toLowerCase();
                        if(timestampSet==false && timestampText.contains("rhmz srbije")){
                            time=textValue;
                            timestampSet=true;
                            time=time.replaceAll("RHMZ Srbije - Podaci sa meteoroloških stanica ","");
                            //                            actionBar.setSubtitle("Podaci ažurirani: "+time);
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        //                        if(inEntry){
                        //                            if ("item".equalsIgnoreCase(tagName)) {
                        //                                weatherEntries.add(currenRecord);
                        //                                inEntry = false;
                        //                            } else if ("title".equalsIgnoreCase(tagName)) {
                        //                                currenRecord.setStationName(textValue);
                        //
                        //                            } else if ("description".equalsIgnoreCase(tagName)) {
                        //                                setFeedEntryFields(textValue, currenRecord);
                        //                            }
                        //                        }
                        break;
                    default:
                }
                eventType=xpp.next();
            }
        }  catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return time;
    }

    //Parse stores the entries into the arraylist
    public static List<AutomaticStationsFragment.FeedEntry> parse(String xmlData) {
        List<AutomaticStationsFragment.FeedEntry> weatherEntries=new ArrayList<>();
        String time;
        AutomaticStationsFragment.FeedEntry currenRecord = null;
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
                            currenRecord = new AutomaticStationsFragment.FeedEntry();
                        }
                        break;
                    case XmlPullParser.TEXT:
                        textValue = xpp.getText();
                        String timestampText=textValue.toLowerCase();
                        if(timestampSet==false && timestampText.contains("rhmz srbije")){
                            time=textValue;
                            timestampSet=true;
                            time=time.replaceAll("RHMZ Srbije - Podaci sa meteoroloških stanica ","");
                            //                            actionBar.setSubtitle("Podaci ažurirani: "+time);
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

        Collections.sort(weatherEntries, (AutomaticStationsFragment.FeedEntry s1, AutomaticStationsFragment.FeedEntry s2) ->{
            return s1.getStationName().compareToIgnoreCase(s2.getStationName());
        });
        return weatherEntries;
    }

    private static void setFeedEntryFields(String data, AutomaticStationsFragment.FeedEntry currenRecord) {
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

