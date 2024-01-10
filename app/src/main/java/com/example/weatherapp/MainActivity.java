package com.example.weatherapp;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    EditText editText;
    Button buttonZipcode;

    String zipcode;
    String latitude;
    String longitude;
    ArrayList<String> quotes;

    TextView quote;
    TextView location;
    TextView tempToday;
    TextView descriptionToday;
    TextView dateToday;
    ImageView imageToday;

    TextView timeNext1;
    TextView tempHighNext1;
    TextView tempLowNext1;
    ImageView imageNext1;

    TextView timeNext2;
    TextView tempHighNext2;
    TextView tempLowNext2;
    ImageView imageNext2;

    TextView timeNext3;
    TextView tempHighNext3;
    TextView tempLowNext3;
    ImageView imageNext3;

    TextView timeNext4;
    TextView tempHighNext4;
    TextView tempLowNext4;
    ImageView imageNext4;

    TextView timeNext5;
    TextView tempHighNext5;
    TextView tempLowNext5;
    ImageView imageNext5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText_zipcode);
        buttonZipcode = findViewById(R.id.button_zipcode);
        buttonZipcode.setBackgroundColor(Color.rgb(82, 121, 111));

        zipcode = "";
        latitude = "";
        longitude = "";
        quotes = new ArrayList<String>();
        quotes.add("You could cover the whole earth with asphalt, but sooner or later green grass would break through.");
        quotes.add("Green represents the dead image of life.");
        quotes.add("Green is the fresh emblem of well founded hopes. In blue the spirit can wander, but in green it can rest.");
        quotes.add("An optimist is a person who sees a green light everywhere, while a pessimist sees only the red stoplight... the truly wise person is colorblind.");
        quotes.add("Everything has yet to be invented. I never say 'green' - I say 'greener.' It's greener simply because this is a continuum of change, improvement and discovery.");
        quotes.add("If your knees aren't green by the end of the day, you ought to seriously re-examine your life.");
        quotes.add("Green is the prime color of the world, and that from which its loveliness arises.");
        quotes.add("When you're green, your growing. When you're ripe, you rot.");
        quotes.add("The future will either be green or not at all.");
        quotes.add("Green is a process, not a status. We need to think of 'green' as a verb, not an adjective. ");
        quotes.add("With the gun you can make the earth red but if you have a plough you can make the earth green.");


        quote = findViewById(R.id.textView_quote);
        location = findViewById(R.id.textView_location);
        tempToday = findViewById(R.id.textView_temp_today);
        descriptionToday = findViewById(R.id.textView_description_today);
        dateToday = findViewById(R.id.textView_date_today);
        imageToday = findViewById(R.id.imageView_today);

        timeNext1 = findViewById(R.id.textView_time1);
        tempHighNext1 = findViewById(R.id.textView_high1);
        tempLowNext1 = findViewById(R.id.textView_low1);
        imageNext1 = findViewById(R.id.imageView1);

        timeNext2 = findViewById(R.id.textView_time2);
        tempHighNext2 = findViewById(R.id.textView_high2);
        tempLowNext2 = findViewById(R.id.textView_low2);
        imageNext2 = findViewById(R.id.imageView2);

        timeNext3 = findViewById(R.id.textView_time3);
        tempHighNext3 = findViewById(R.id.textView_high3);
        tempLowNext3 = findViewById(R.id.textView_low3);
        imageNext3 = findViewById(R.id.imageView3);

        timeNext4 = findViewById(R.id.textView_time4);
        tempHighNext4 = findViewById(R.id.textView_high4);
        tempLowNext4 = findViewById(R.id.textView_low4);
        imageNext4 = findViewById(R.id.imageView4);

        timeNext5 = findViewById(R.id.textView_time5);
        tempHighNext5 = findViewById(R.id.textView_high5);
        tempLowNext5 = findViewById(R.id.textView_low5);
        imageNext5 = findViewById(R.id.imageView5);

        imageToday.setImageResource(R.drawable.loading);
        imageNext1.setImageResource(R.drawable.loading);
        imageNext2.setImageResource(R.drawable.loading);
        imageNext3.setImageResource(R.drawable.loading);
        imageNext4.setImageResource(R.drawable.loading);
        imageNext5.setImageResource(R.drawable.loading);



        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                zipcode = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        buttonZipcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(zipcode.length() == 5){

                    FindLatLong findLatLong = new FindLatLong();
                    findLatLong.execute();

                    FindWeather findWeather = new FindWeather();
                    findWeather.execute();

                }
                else{
                    Toast invalidZipcode = Toast.makeText(MainActivity.this, "Error: Please enter a valid zipcode", Toast.LENGTH_SHORT);
                    invalidZipcode.show();
                }
            }
        });


    }

    public class FindLatLong extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {

            try {
                //Find Latitude and Longitude

                URL url = new URL("https://api.openweathermap.org/geo/1.0/zip?zip="+zipcode+"&appid=796412ddb486e6f563e02f8a1fe82be7");
                Log.d("HELLO", "URL1");

                URLConnection urlConnection = url.openConnection();
                Log.d("HELLO", "URLCONNECTION1");

                InputStream inputStream = urlConnection.getInputStream();
                Log.d("HELLO", "INPUTSTREAM1");

                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String string = reader.readLine();
                Log.d("HELLO", "BUFFEREDREADER1");

                JSONObject object = new JSONObject(string);
                Log.d("HELLO", "JSONOBJECT1");
                Log.d("HELLO", object.toString());


                latitude = object.getString("lat");
                longitude = object.getString("lon");

                Log.d("HELLO", latitude);
                Log.d("HELLO", longitude);

                String str = object.getString("name") + ", " + object.getString("country");
                return str;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            location.setText(s);
        }
    }

    public class FindWeather extends AsyncTask<Void, Void, String>{

        @Override
        protected String doInBackground(Void... voids) {

            try {
                //Find weather

                URL url = new URL("https://api.openweathermap.org/data/2.5/forecast?lat="+latitude+"&lon="+longitude+"&appid=796412ddb486e6f563e02f8a1fe82be7");
                Log.d("HELLO", "URL");

                URLConnection urlConnection = url.openConnection();
                Log.d("HELLO", "URLCONNECTION");

                InputStream inputStream = urlConnection.getInputStream();
                Log.d("HELLO", "INPUTSTREAM");

                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String string = reader.readLine();
                Log.d("HELLO", "BUFFEREDREADER");

                return string;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.d("HELLO", s);

            try {
                JSONObject object = new JSONObject(s);
                JSONArray a = object.getJSONArray("list");

                Double t = new Double(a.getJSONObject(0).getJSONObject("main").getString("temp"));
                String temp = String.valueOf(Math.round(((1.8)*(t - 273.15)) + 32)+"°F");
                tempToday.setText(temp);

                String date = a.getJSONObject(0).getString("dt_txt");
                int month = new Integer(date.substring(5,7));
                int day = new Integer(date.substring(8,10));
                int year = new Integer(date.substring(0,4));
                int hour = new Integer(date.substring(11,13));
                if(hour >= 0 || hour < 5){
                    dateToday.setText(month + "/" + (day - 1) + "/" + year);
                }
                else{
                    dateToday.setText(month + "/" + (day) + "/" + year);
                }

                date = a.getJSONObject(0).getString("dt_txt");
                timeNext1.setText(calculateTime(date));

                date = a.getJSONObject(1).getString("dt_txt");
                timeNext2.setText(calculateTime(date));

                date = a.getJSONObject(2).getString("dt_txt");
                timeNext3.setText(calculateTime(date));

                date = a.getJSONObject(3).getString("dt_txt");
                timeNext4.setText(calculateTime(date));

                date = a.getJSONObject(4).getString("dt_txt");
                timeNext5.setText(calculateTime(date));


                tempHighNext1.setText(String.valueOf(Math.round(((1.8)*(new Double(a.getJSONObject(0).getJSONObject("main").getString("temp_max")) - 273.15)) + 32)+"°F"));
                tempLowNext1.setText(String.valueOf(Math.round(((1.8)*(new Double(a.getJSONObject(0).getJSONObject("main").getString("temp_min")) - 273.15)) + 32)+"°F"));

                tempHighNext2.setText(String.valueOf(Math.round(((1.8)*(new Double(a.getJSONObject(1).getJSONObject("main").getString("temp_max")) - 273.15)) + 32)+"°F"));
                tempLowNext2.setText(String.valueOf(Math.round(((1.8)*(new Double(a.getJSONObject(1).getJSONObject("main").getString("temp_min")) - 273.15)) + 32)+"°F"));

                tempHighNext3.setText(String.valueOf(Math.round(((1.8)*(new Double(a.getJSONObject(2).getJSONObject("main").getString("temp_max")) - 273.15)) + 32)+"°F"));
                tempLowNext3.setText(String.valueOf(Math.round(((1.8)*(new Double(a.getJSONObject(2).getJSONObject("main").getString("temp_min")) - 273.15)) + 32)+"°F"));

                tempHighNext4.setText(String.valueOf(Math.round(((1.8)*(new Double(a.getJSONObject(3).getJSONObject("main").getString("temp_max")) - 273.15)) + 32)+"°F"));
                tempLowNext4.setText(String.valueOf(Math.round(((1.8)*(new Double(a.getJSONObject(3).getJSONObject("main").getString("temp_min")) - 273.15)) + 32)+"°F"));

                tempHighNext5.setText(String.valueOf(Math.round(((1.8)*(new Double(a.getJSONObject(4).getJSONObject("main").getString("temp_max")) - 273.15)) + 32)+"°F"));
                tempLowNext5.setText(String.valueOf(Math.round(((1.8)*(new Double(a.getJSONObject(4).getJSONObject("main").getString("temp_min")) - 273.15)) + 32)+"°F"));


                JSONArray a1 = a.getJSONObject(0).getJSONArray("weather");
                descriptionToday.setText(a1.getJSONObject(0).getString("description"));
                imageToday.setImageResource(updateImage(a1.getJSONObject(0).getString("description")));
                imageNext1.setImageResource(updateImage(a1.getJSONObject(0).getString("description")));

                JSONArray a2 = a.getJSONObject(1).getJSONArray("weather");
                imageNext2.setImageResource(updateImage(a2.getJSONObject(0).getString("description")));

                JSONArray a3 = a.getJSONObject(2).getJSONArray("weather");
                imageNext3.setImageResource(updateImage(a3.getJSONObject(0).getString("description")));

                JSONArray a4 = a.getJSONObject(3).getJSONArray("weather");
                imageNext4.setImageResource(updateImage(a4.getJSONObject(0).getString("description")));

                JSONArray a5 = a.getJSONObject(4).getJSONArray("weather");
                imageNext5.setImageResource(updateImage(a5.getJSONObject(0).getString("description")));

                quote.setText(quotes.get((int) (Math.random()*10)));


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public String calculateTime(String str){

        int hour = new Integer(str.substring(11,13)) - 5;
        String end = "";

        if(hour < 0 && hour > -12){
            hour += 12;
            end = "pm";
        }
        else if(hour == 0){
            hour = 12;
            end = "am";
        }
        else if(hour > 12){
            hour -= 12;
            end = "pm";
        }
        else{
            end = "am";
        }

        return hour + ":00 " + end;
    }

    public int updateImage(String str){
        if(str.contains("thunderstorm")){
            return R.drawable.thunderstorm;
        }
        else if(str.contains("drizzle")){
            return R.drawable.drizzle;
        }
        else if(str.contains("snow")){
            return R.drawable.snow;
        }
        else if(str.contains("clouds")){
            return R.drawable.clouds;
        }
        else if(str.contains("clear")){
            return R.drawable.clear;
        }
        else if(str.contains("rain")){
            return R.drawable.rain;
        }
        else if(str.contains("sun")){
            return R.drawable.sunny;
        }
        else if(str.equalsIgnoreCase("mist") || str.equalsIgnoreCase("smoke") || str.equalsIgnoreCase("haze") || str.equalsIgnoreCase("sand") || str.equalsIgnoreCase("fog") || str.equalsIgnoreCase("dust") || str.equalsIgnoreCase("tornado") || str.equalsIgnoreCase("ash") || str.equalsIgnoreCase("squalls")){
            return R.drawable.atmosphere;
        }
        return R.drawable.unknown;
    }


}



