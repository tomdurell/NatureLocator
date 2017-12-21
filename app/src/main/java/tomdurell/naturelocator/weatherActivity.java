package tomdurell.naturelocator;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.AsyncTask;

import java.util.ArrayList;

import android.widget.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static java.lang.System.in;


public class weatherActivity extends AppCompatActivity {

    //key for weather API = c66e240e36d34119bf4dd94d98f61cc7
    //google maps API AIzaSyDTx-dISksxqZ0428adxhWA8wajQe0pAok

    // array list to store tweet items from web service
    ArrayList<String> items = new ArrayList<String>();
    // json test string
    String jsonTest;
    // doubles to hold coordinates for location
    double lat;
    double longi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        //setup location manager
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        String locationProvider = LocationManager.GPS_PROVIDER;
        //check permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        //grab last location
        Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
        //if null such as on a new device set to lincoln to avoid crashes
        if(lastKnownLocation == null) {
            lat = 53.228029;
            longi =-0.546055;
        }
        else {
            //grab location
            lat = lastKnownLocation.getLatitude();
            longi = lastKnownLocation.getLongitude();
        }
        //format to 3.d.p for preparation to be passed to yourServiceUrl
        lat = (double)Math.round(lat * 1000d) / 1000d;
        longi = (double)Math.round(longi * 1000d) / 1000d;
        //initiate http call
        new AsyncTaskParseJson().execute();

    }

    //Json parsing
    public class AsyncTaskParseJson extends AsyncTask<String, String, String> {



        //Set URL
        String yourServiceUrl = "https://api.weatherbit.io/v2.0/current?lat="+lat+"&lon="+longi+"&key=c66e240e36d34119bf4dd94d98f61cc7";


        @Override
        // this method is used for connecting to the service and extracting all the json data to a string
        protected String doInBackground(String... arg0)  {

            try {
                // create new instance of the httpConnect class
                httpConnect jParser = new httpConnect();

                // get json string from service url
                String json = jParser.getJSONFromUrl(yourServiceUrl);

                // save returned json to your test string
                jsonTest = json.toString();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        // below method will run when service HTTP request is complete, will then bind json text in arrayList to ListView
        protected void onPostExecute(String strFromDoInBg) {
            try {
                //parsing through weather api to extract data and display in text views within the app
                JSONObject jObject = new JSONObject(jsonTest);
                JSONArray data = jObject.getJSONArray("data");
                JSONObject weatherInformation = data.getJSONObject(0);
                //string setup
                String cloudCov = weatherInformation.getString("clouds");
                String temperature = weatherInformation.getString("temp");
                String windSpeed = weatherInformation.getString("wind_spd");
                String appTemperature = weatherInformation.getString("app_temp");

                JSONObject detailedWeather = weatherInformation.getJSONObject("weather");
                String weatherDescription = detailedWeather.getString("description");
                //finding textviews by id
                TextView tv1 = (TextView)findViewById(R.id.jsonClouds);
                TextView tv2 = (TextView)findViewById(R.id.jsonTemperature);
                TextView tv3 = (TextView)findViewById(R.id.jsonWindspeed);
                TextView tv4 = (TextView)findViewById(R.id.jsonAppTemp);
                TextView tv5 = (TextView)findViewById(R.id.jsonDescription);
                //applying new text value
                tv1.setText("Cloud coverage is at " + cloudCov + " %");
                tv2.setText("The current temperature is : "+temperature+" °C");
                tv3.setText("The windspeed is currently " + windSpeed + "mph");//mph
                tv4.setText("The apparent" + " temperature is : "+appTemperature+" °C");
                tv5.setText("The weather state of the weather is: "+ weatherDescription);


            } catch (JSONException e) {
                e.printStackTrace();

            }
        }
    }

}


