package com.example.indoorairqualitymonitoringapp;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherFragment extends Fragment {
    private String accessToken;
    private DashBoardactivity activity;
    private TextView sundayNove;
    private TextView coimbatore;
    private TextView someId;
    private TextView txtHumidity;
    private TextView txtRainfall;
    private TextView txtWingspeed;
    private TextView[] dayTextViews = new TextView[4];
    private TextView[] tempTextViews = new TextView[4];
    private TextView[] humdTextViews = new TextView[4];

    public static WeatherFragment newInstance(String accessToken, DashBoardactivity activity) {
        WeatherFragment fragment = new WeatherFragment();
        fragment.accessToken = accessToken;
        fragment.activity = activity;
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof DashBoardactivity) {
            activity = (DashBoardactivity) context;
        } else {
            Log.e("GraphFragment", "Activity must be of type DashBoardactivity");
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);

        // Khai báo TextView
        sundayNove = view.findViewById(R.id.sunday_nove);
        coimbatore = view.findViewById(R.id.coimbatore);
        someId = view.findViewById(R.id.some_id);
        txtHumidity = view.findViewById(R.id.txtHumidity);
        txtRainfall = view.findViewById(R.id.txtRaifall);
        txtWingspeed = view.findViewById(R.id.txtWingspeed);
        dayTextViews[0] = view.findViewById(R.id.day1);
        tempTextViews[0] = view.findViewById(R.id.temp1);
        humdTextViews[0] = view.findViewById(R.id.hum1);
        dayTextViews[1] = view.findViewById(R.id.day2);
        tempTextViews[1] = view.findViewById(R.id.temp2);
        humdTextViews[1] = view.findViewById(R.id.hum2);
        dayTextViews[2] = view.findViewById(R.id.day3);
        tempTextViews[2] = view.findViewById(R.id.temp3);
        humdTextViews[2] = view.findViewById(R.id.hum3);
        dayTextViews[3] = view.findViewById(R.id.day4);
        tempTextViews[3] = view.findViewById(R.id.temp4);
        humdTextViews[3] = view.findViewById(R.id.hum4);

        makeApiCall();
        // Gọi hàm để cập nhật ngày tháng hiện tại
        nextday();
        mapkeApicall4days();
        updateCurrentDate();
        return view;
    }
    private void makeApiCall() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://uiot.ixxc.dev")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService weatherApi = retrofit.create(ApiService.class);

        Call<JsonObject> call = weatherApi.getDeviceData("Bearer " + accessToken, "5zI6XqkQVSfdgOrZ1MyWEf");;
       call.enqueue(new Callback<JsonObject>() {
           @Override
           public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
               if (response.isSuccessful()) {
                   JsonObject weatherData = response.body();
                   updateCoordinatesTextView(weatherData);
               }else{
                   //showErrorToast("API error: " + response.code());
               }
           }


           @Override
           public void onFailure(Call<JsonObject> call, Throwable t) {
               t.printStackTrace();
           }
       });
    }
    private void showErrorToast(String errorMessage) {
        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show();
    }
    private void mapkeApicall4days(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService weatherApi = retrofit.create(ApiService.class);
        Call<WeatherForecast> call = weatherApi.getWeatherForecast("Bien hoa", "3c0feae54cd55cc306738f55324901ff", "metric");
        call.enqueue(new Callback<WeatherForecast>() {
            @Override
            public void onResponse(Call<WeatherForecast> call, Response<WeatherForecast> response) {
                if (response.isSuccessful()) {
                    WeatherForecast weatherForecast = response.body();
                    updateForecastTextView(weatherForecast);
                } else {
                    showErrorToast("API error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<WeatherForecast> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }
    private void updateForecastTextView(WeatherForecast weatherForecast) {
        List<WeatherData> forecastItems = weatherForecast.getWeatherDataList();

        // Kiểm tra xem có đủ dữ liệu để hiển thị hay không
        if (forecastItems.size() >= 4) {
            for (int i = 0; i < 4; i++) {
                WeatherData forecastItem = forecastItems.get(i);

                // Lấy ngày dự báo và hiển thị lên TextView tương ứng

                // Lấy nhiệt độ và độ ẩm dự báo và hiển thị lên TextView tương ứng
                String temperature = String.valueOf(Math.round(forecastItem.getTemperature()));
                String humidity = String.valueOf(Math.round(forecastItem.getHumidity()));
                tempTextViews[i].setText(temperature + "°C");
                humdTextViews[i].setText(humidity + "%");
            }
        }
        }
        private void nextday(){
            Calendar calendar = Calendar.getInstance();
            Date currentDate = calendar.getTime();
            for (int i = 0; i < 4; i++) {
                String formattedDate = formatDate(currentDate, i+1);
                

                dayTextViews[i].setText(formattedDate);
            }
        }
    private String formatDate(Date date, int offset) {

        // Dùng SimpleDateFormat để định dạng ngày
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM", Locale.getDefault());

        // Tính toán ngày tiếp theo
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, offset);

        // Trả về chuỗi đã định dạng
        return sdf.format(calendar.getTime());
    }

    private void updateCurrentDate() {
        // Định dạng ngày tháng
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE,\ndd MMMM YYYY", Locale.getDefault());

        // Lấy ngày hiện tại
        String currentDate = dateFormat.format(new Date());

        // Hiển thị ngày hiện tại lên TextView
        sundayNove.setText(currentDate);
    }
    private void updateCoordinatesTextView(JsonObject weatherData) {
        JsonObject attributes = weatherData.getAsJsonObject("attributes");
        String humidityValue = attributes.getAsJsonObject("humidity").get("value").isJsonNull() ? "-" : attributes.getAsJsonObject("humidity").get("value").getAsString()+"%";
        String placeValue = attributes.getAsJsonObject("place").get("value").isJsonNull()? "-" : attributes.getAsJsonObject("place").get("value").getAsString();
        String rainfallValue = attributes.getAsJsonObject("rainfall").get("value").isJsonNull()? "-" : attributes.getAsJsonObject("rainfall").get("value").getAsString()+"mm";
        String temperatureValue = attributes.getAsJsonObject("temperature").get("value").isJsonNull()? "-" : String.valueOf(Math.round(Double.parseDouble(attributes.getAsJsonObject("temperature").get("value").getAsString())));
        String windSpeedValue = attributes.getAsJsonObject("windSpeed").get("value").isJsonNull()? "-" : attributes.getAsJsonObject("windSpeed").get("value").getAsString()+"km/h";
        coimbatore.setText(placeValue);
        someId.setText(String.valueOf(temperatureValue)+"°C");
        txtHumidity.setText(String.valueOf(humidityValue));
        txtRainfall.setText(String.valueOf(rainfallValue));
        txtWingspeed.setText(String.valueOf(windSpeedValue));
    }
    public void convertTemperatureToFahrenheit() {
        // Lấy giá trị nhiệt độ hiện tại từ TextView
        String temperatureText = someId.getText().toString();

        // Kiểm tra xem nhiệt độ có giá trị hay không
        if (!temperatureText.equals("-")) {
            // Chuyển đổi từ độ C sang độ F
            if (temperatureText.contains("°C")) {
                double celsius = Double.parseDouble(temperatureText.replace("°C", ""));
                double fahrenheit = (celsius * 9 / 5) + 32;

                // Hiển thị nhiệt độ mới lên TextView
                someId.setText(String.format(Locale.getDefault(), "%.0f°F", fahrenheit));
            }
            else if (temperatureText.contains("°F")){
                double fahrenheit = Double.parseDouble(temperatureText.replace("°F", ""));
                double celsius = (fahrenheit - 32) * 5/9;

                // Hiển thị nhiệt độ mới lên TextView
                someId.setText(String.format(Locale.getDefault(), "%.0f°C", celsius));
            }
        }
    }


}
