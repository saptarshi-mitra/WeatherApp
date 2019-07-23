package com.iamsm.weatherapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

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

import static com.iamsm.weatherapp.MainActivity.day_of_week;

public class Less_day extends AppCompatActivity {
    public static String BaseUrl="https://api.openweathermap.org/";
    public static String AppId="4d99b523e2324aec5e0614b53d564e1d";
    public EditText cityName;
    public TextView currData;
    public TextView day1,day2,day3,day1Max,day2Max,day3Max,day1Min,day2Min,day3Min,day1Humidity,day2Humidity,day3Humidity,day1Pressure,day2Pressure,day3Pressure;;
    public Button three_day_search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.three_day);
        cityName=(EditText) findViewById(R.id.editText2);
        currData=(TextView) findViewById(R.id.curr_data);
        three_day_search=findViewById(R.id.three_day_search);
        day1=findViewById(R.id.day1);
        day2=findViewById(R.id.day2);
        day3=findViewById(R.id.day3);
        day1Max=findViewById(R.id.day1maxtemp);
        day1Min=findViewById(R.id.day1mintemp);
        day1Humidity=findViewById(R.id.day1humidity);
        day1Pressure=findViewById(R.id.day1pressure);
        day2Max=findViewById(R.id.day2maxtemp);
        day2Min=findViewById(R.id.day2mintemp);
        day2Humidity=findViewById(R.id.day2humidity);
        day2Pressure=findViewById(R.id.day2pressure);
        day3Max=findViewById(R.id.day3maxtemp);
        day3Min=findViewById(R.id.day3mintemp);
        day3Humidity=findViewById(R.id.day3humidity);
        day3Pressure=findViewById(R.id.day3pressure);

        three_day_search = findViewById(R.id.three_day_search);

        three_day_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city=cityName.getText().toString();
                //String string=city_name.getText().toString();
                Retrofit retrofit=new Retrofit.Builder()
                        .baseUrl(BaseUrl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                final WeatherService weatherService=retrofit.create(WeatherService.class);
                //Call<WeatherResponse> call= weatherService.getTemp( id, AppId);
                Call<WeatherResponse> call= weatherService.getTemp_entry( city, AppId);
                call.enqueue(new Callback<WeatherResponse>() {
                    @Override
                    public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                        if(response.code()==200)
                        {
                            WeatherResponse weatherResponse = response.body();
                            assert weatherResponse!=null;
                            Calendar cal = Calendar.getInstance();
                            Date now=new Date();
                            cal.setTime(now);
                            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                        //    System.out.println( sdf.format(cal.getTime()));
                            String time=sdf.format(now);
                            time=time.substring(0,2);
                            int time_int=Integer.parseInt(time);
                            if(time_int%3<=1)
                            {
                               Double currentTemp= Double.valueOf(Math.round(weatherResponse.list.get(1).getMain().getTemp()-273.0));
                               String setTemp=currentTemp.toString();
                               currData.setText(setTemp);
                            }
                            else
                                {
                                    Double currentTemp= Double.valueOf(Math.round(weatherResponse.list.get(2).getMain().getTemp()-273.0));
                                    String setTemp1=currentTemp.toString();
                                    currData.setText(setTemp1);
                                }
                            List<Double> max_temp=new ArrayList<>();
                            List<Double> min_temp=new ArrayList<>();
                            List<Integer> avg_hum=new ArrayList<>();
                            List<Double> avg_press=new ArrayList<>();
                            //day1
                            for(int i=0;i<8;i++)
                            {
                                max_temp.add(weatherResponse.list.get(i).getMain().getTempMax()-273.0);
                                min_temp.add(weatherResponse.list.get(i).getMain().getTempMin()-273.0);
                                avg_hum.add(weatherResponse.list.get(i).getMain().getHumidity());
                                avg_press.add(weatherResponse.list.get(i).getMain().getPressure());
                            }
                            Double m=getMaxTemp(max_temp);
                            Double m1=getMinTemp(min_temp);
                            Double h=getAvgHum(avg_hum);
                            Double  p=getAvgPress(avg_press);
                            String x=m.toString();
                            String x1=m1.toString();
                            String x2=h.toString();
                            String x3=p.toString();

                            int week=cal.get(cal.DAY_OF_WEEK);
                            String day=getDayOfWeek(week);
                            day1.setText(day);
                            day1Max.setText(x);
                            day1Min.setText(x1);
                            day1Pressure.setText(x2);
                            day1Humidity.setText(x3);

                            max_temp.clear();
                            min_temp.clear();
                            avg_hum.clear();
                            avg_press.clear();
                            //day2
                            for(int i=8;i<16;i++)
                            {
                                max_temp.add(weatherResponse.list.get(i).getMain().getTempMax()-273.0);
                                min_temp.add(weatherResponse.list.get(i).getMain().getTempMin()-273.0);
                                avg_hum.add(weatherResponse.list.get(i).getMain().getHumidity());
                                avg_press.add(weatherResponse.list.get(i).getMain().getPressure());
                            }
                            m = getMaxTemp(max_temp);
                            m1 = getMinTemp(min_temp);
                            h = getAvgHum(avg_hum);
                            p = getAvgPress(avg_press);
                            x = m.toString();
                            x1 = m1.toString();
                            x2 = h.toString();
                            x3 = p.toString();
                            week++;
                            day = getDayOfWeek(week);
                            day2.setText(day);
                            day2Max.setText(x);
                            day2Min.setText(x1);
                            day2Pressure.setText(x2);
                            day2Humidity.setText(x3);
                            max_temp.clear();
                            min_temp.clear();
                            avg_hum.clear();
                            avg_press.clear();
                            //day3
                            for(int i=16;i<24;i++)
                            {
                                max_temp.add(weatherResponse.list.get(i).getMain().getTempMax()-273.0);
                                min_temp.add(weatherResponse.list.get(i).getMain().getTempMin()-273.0);
                                avg_hum.add(weatherResponse.list.get(i).getMain().getHumidity());
                                avg_press.add(weatherResponse.list.get(i).getMain().getPressure());
                            }
                            m = getMaxTemp(max_temp);
                            m1 = getMinTemp(min_temp);
                            h = getAvgHum(avg_hum);
                            p = getAvgPress(avg_press);
                            x = m.toString();
                            x1 = m1.toString();
                            x2 = h.toString();
                            x3 = p.toString();
                            week++;
                            day = getDayOfWeek(week);
                            day3.setText(day);
                            day3Max.setText(x);
                            day3Min.setText(x1);
                            day3Pressure.setText(x2);
                            day3Humidity.setText(x3);
                            max_temp.clear();
                            min_temp.clear();
                            avg_hum.clear();
                            avg_press.clear();
                        }


                    }

                    @Override
                    public void onFailure(Call<WeatherResponse> call, Throwable t) {
                            currData.setText(t.getMessage());

                    }
                });
            }


        });




    }
    public static double getMaxTemp(List<Double> max_temp)
    {
        Collections.sort(max_temp);
        double t=Math.round(max_temp.get(7));
        return t;
    }
    public static double getMinTemp(List<Double> min_temp)
    {
        Collections.sort(min_temp);
        double t=Math.round(min_temp.get(7));
        return t;
    }
    public static String getDayOfWeek(int week)
    {
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
    public static double getAvgHum(List<Integer> avg_hum)
    {
        int s=avg_hum.size();
        double k=0;
        for(int i=0;i<s;i++)
        {
            k=k+avg_hum.get(i);
        }
        double avg= Math.round(k/s);
        return avg;
    }
    public static double getAvgPress(List<Double> avg_press)
    {
        int s=avg_press.size();
        double k=0;
        for(int i=0;i<s;i++)
        {
            k=k+avg_press.get(i);
        }
        double avg=Math.round(k /s);
        return avg;
    }

}
