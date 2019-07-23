package com.iamsm.weatherapp;

import android.content.Intent;
import android.os.Bundle;



import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.iamsm.weatherapp.R;
import com.iamsm.weatherapp.WeatherResponse;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static String BaseUrl="https://api.openweathermap.org/";
    public static String AppId="4d99b523e2324aec5e0614b53d564e1d";
    public static int id=1275004;


    private TextView mytest_text;
    private TextView day1,day2,day3,day4,day5;
    private EditText city_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        //Starting code
        mytest_text=findViewById(R.id.curr_weather);
        day1=findViewById(R.id.weather_day1);
        day2=findViewById(R.id.weather_day2);
        day3=findViewById(R.id.weather_day3);
        day4=findViewById(R.id.weather_day4);
        day5=findViewById(R.id.weather_day5);
        city_name=findViewById(R.id.editText);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.search) {
            // Handle the camera action
        } else if (id == R.id.forecast_more) {

        } else if (id == R.id.forecast_less) {
            Intent intent=new Intent(this,Less_day.class);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void getcurrentdata(View view){
        String string=city_name.getText().toString();
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

       WeatherService weatherService=retrofit.create(WeatherService.class);
        //Call<WeatherResponse> call= weatherService.getTemp( id, AppId);
        Call<WeatherResponse> call= weatherService.getTemp_entry( string, AppId);
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if(response.code()==200)
                {
                    WeatherResponse weatherResponse=response.body();
                    assert weatherResponse != null;
                    Date now = new Date();
                    Calendar calendar=Calendar.getInstance();
                    calendar.setTime(now);
                    SimpleDateFormat simpleDate = new SimpleDateFormat("hh:mm:ss");
                    String time=simpleDate.format(now);
                    time=time.substring(0,2);
                    int ac_time=Integer.parseInt(time);
                    Double temp;
                    if((ac_time%3)<=1) {
                        temp=weatherResponse.list.get(1).getMain().getTemp();
                        temp-=273.0;
                        int res=(int) Math.round(temp);
                        mytest_text.setText( res + "");
                    }
                    else {
                        temp=weatherResponse.list.get(2).getMain().getTemp();
                        temp-=273.0;
                        int res=(int) Math.round(temp);
                        mytest_text.setText(res + "");
                    }

                    //for day1 forecast

                    List<Double> list_max=new ArrayList<>();
                    List<Double> list_min=new ArrayList<>();
                    for(int i=0;i<8;i++){
                        list_max.add(weatherResponse.list.get(i).getMain().getTempMax());
                        list_min.add(weatherResponse.list.get(i).getMain().getTempMin());
                    }
                    int maxtemp=max_temp(list_max);
                    int mintemp=min_temp(list_min);

                    int week=calendar.get(calendar.DAY_OF_WEEK);
                    String day=day_of_week(week);
                    //for day1 forecast
                    day1.setText(day + " : " + maxtemp+ "/" + mintemp);

                    //for day2 forecast

                    list_max.clear();
                    list_min.clear();
                    for(int i=8;i<16;i++){
                        list_max.add(weatherResponse.list.get(i).getMain().getTempMax());
                        list_min.add(weatherResponse.list.get(i).getMain().getTempMin());
                    }
                    maxtemp=max_temp(list_max);
                    mintemp=min_temp(list_min);

                    week++;
                    day=day_of_week(week);
                    day2.setText(day + " : " + maxtemp+ "/" + mintemp);

                    //for day3 forecast

                    list_max.clear();
                    list_min.clear();
                    for(int i=16;i<24;i++){
                        list_max.add(weatherResponse.list.get(i).getMain().getTempMax());
                        list_min.add(weatherResponse.list.get(i).getMain().getTempMin());
                    }
                    maxtemp=max_temp(list_max);
                    mintemp=min_temp(list_min);

                    week++;
                    day=day_of_week(week);
                    day3.setText(day + " : " + maxtemp+ "/" + mintemp);

                    //for day4 forecast

                    list_max.clear();
                    list_min.clear();
                    for(int i=24;i<32;i++){
                        list_max.add(weatherResponse.list.get(i).getMain().getTempMax());
                        list_min.add(weatherResponse.list.get(i).getMain().getTempMin());
                    }
                    maxtemp=max_temp(list_max);
                    mintemp=min_temp(list_min);

                    week++;
                    day=day_of_week(week);
                    day4.setText(day + " : " + maxtemp+ "/" + mintemp);

                    //for day5 forecast

                    list_max.clear();
                    list_min.clear();
                    for(int i=32;i<40;i++){
                        list_max.add(weatherResponse.list.get(i).getMain().getTempMax());
                        list_min.add(weatherResponse.list.get(i).getMain().getTempMin());
                    }
                    maxtemp=max_temp(list_max);
                    mintemp=min_temp(list_min);

                    week++;
                    day=day_of_week(week);
                    day5.setText(day + " : " + maxtemp+ "/" + mintemp);

                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                mytest_text.setText(t.getMessage());
            }
        });
    }
    public static String day_of_week(int week){
        String day="";
        if(week>7)
            week=week-7;

        if(week==1)
            day="Sunday";
        if(week==2)
            day="Monday";
        if(week==3)
            day="Tuesday";
        if(week==4)
            day="Wednesday";
        if(week==5)
            day="Thursday";
        if(week==6)
            day="Friday";
        if(week==7)
            day="Saturday";


        return day;
    }

    public static int max_temp(List<Double> list) {
        Collections.sort(list);
        int max=(int) Math.round(list.get(7));
        max-=273;
        return max;
    }

    public static int min_temp(List<Double> list) {
        Collections.sort(list);
        int min=(int) Math.round(list.get(0));
        min-=273;
        return min;
    }

}
