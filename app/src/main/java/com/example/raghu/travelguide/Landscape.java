package com.example.raghu.travelguide;

import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Display;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by RAGHU on 7/30/2017.
 */

public class Landscape {
    private String title;
    private String description;
    private String imageUrl;

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public static ArrayList<Landscape> getData(String url)  {
        ArrayList<Landscape> dataList = new ArrayList<>();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            Log.e("URL1", url);
            String json_string = getJSON(url);
            JSONObject json = new JSONObject(json_string);
            JSONArray a = json.getJSONArray("results");
            for (int i = 0; i < a.length(); i++) {
                String temp = a.getString(i);
                JSONObject jo = new JSONObject(temp);
                String name, address, icon;
                name = jo.get("name").toString();
                address = jo.get("formatted_address").toString();
                try {
                    JSONArray jarr = jo.getJSONArray("photos");
                    String pic_temp = jarr.getString(0);
                    JSONObject picjo = new JSONObject(pic_temp);
                    String pref = picjo.get("photo_reference").toString();
                    icon = "https://maps.googleapis.com/maps/api/place/photo?maxheight=400&maxwidth=700&photoreference="+pref+"&key=AIzaSyDiOuDDV7yRdHk9mvQMP4uenZ05B1KYSbw";
                }
                catch (Exception e){
                    icon = jo.get("icon").toString();
                }
                //icon = jo.get("icon").toString();
                Landscape l = new Landscape();
                l.setTitle(name);
                l.setDescription(address);
                l.setImageUrl(icon);
                dataList.add(i, l);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    @Nullable
    public static String getJSON(String url) {
        HttpsURLConnection con = null;
        try {
            URL u = new URL(url);
            con = (HttpsURLConnection) u.openConnection();

            con.connect();

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            br.close();
            return sb.toString();


        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.disconnect();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        return null;
    }
}
