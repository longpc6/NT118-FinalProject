package com.example.indoorairqualitymonitoringapp;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Overlay;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapFragment extends Fragment {

    private String accessToken;
    private DashBoardactivity activity;
    private MapView mapView;
    private TextView coordinatesTextView,newTextView,newTextView1;

    public static MapFragment newInstance(String accessToken, DashBoardactivity activity) {
        MapFragment fragment = new MapFragment();
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Configuration.getInstance().load(activity, activity.getSharedPreferences("osmdroid", 0));
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        mapView = rootView.findViewById(R.id.mapView);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        coordinatesTextView = rootView.findViewById(R.id.coordinatesX);


        callMapApi();
        return rootView;
    }

    private void callMapApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://uiot.ixxc.dev")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<JsonObject> lightCall = apiService.getDeviceData("Bearer " + accessToken, "6iWtSbgqMQsVq8RPkJJ9vo");
        lightCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject lightData = response.body();
                    JsonObject attributes = lightData.getAsJsonObject("attributes");
                    JsonObject location = attributes.getAsJsonObject("location");
                    JsonArray coordinates = location.getAsJsonObject("value").getAsJsonArray("coordinates");



                    double lightLongitude = coordinates.get(0).getAsDouble();
                    double lightLatitude = coordinates.get(1).getAsDouble();

                    GeoPoint lightGeoPoint = new GeoPoint(lightLatitude, lightLongitude);
                    Marker lightMarker = new Marker(mapView);
                    lightMarker.setPosition(lightGeoPoint);
                    lightMarker.setIcon(getResources().getDrawable(R.drawable.iclight));
                    lightMarker.setOnMarkerClickListener((marker, mapView) -> {
                        // Update the coordinatesX TextView with the marker's position
                        coordinatesTextView.setVisibility(coordinatesTextView.VISIBLE);
                        updateCoordinatesTextView1(lightData);

                        return true;
                    });
                    mapView.getOverlays().add(lightMarker);

                    mapView.getController().setCenter(lightGeoPoint);

                    int zoomLevel = 18;
                    mapView.getController().setZoom(zoomLevel);

                    // Refresh the mapView
                    mapView.invalidate();

                    callDefaultWeatherApi(apiService);
                } else {
                    //showErrorToast("API error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void updateCoordinatesTextView(JsonObject weatherData) {
        JsonObject attributes = weatherData.getAsJsonObject("attributes");
        JsonObject location = attributes.getAsJsonObject("location");
        JsonArray coordinates = location.getAsJsonObject("value").getAsJsonArray("coordinates");
        String humidityValue = attributes.getAsJsonObject("humidity").get("value").isJsonNull() ? "-" : attributes.getAsJsonObject("humidity").get("value").getAsString();
        String manuValue = attributes.getAsJsonObject("manufacturer").get("value").isJsonNull()? "-" : attributes.getAsJsonObject("manufacturer").get("value").getAsString();
        String placeValue = attributes.getAsJsonObject("place").get("value").isJsonNull()? "-" : attributes.getAsJsonObject("place").get("value").getAsString();
        String rainfallValue = attributes.getAsJsonObject("rainfall").get("value").isJsonNull()? "-" : attributes.getAsJsonObject("rainfall").get("value").getAsString();
//        String sunAltitudeValue = attributes.getAsJsonObject("sunAltitude").get("value").isJsonNull()? "-" : attributes.getAsJsonObject("sunAltitude").get("value").getAsString();
//        String sunAzimuthValue = attributes.getAsJsonObject("sunAzimuth").get("value").isJsonNull()? "-" : attributes.getAsJsonObject("sunAzimuth").get("value").getAsString();
//        String sunIrradianceValue = attributes.getAsJsonObject("sunIrradiance").get("value").isJsonNull()? "-" : attributes.getAsJsonObject("sunIrradiance").get("value").getAsString();
//        String sunZenithValue = attributes.getAsJsonObject("sunZenith").get("value").isJsonNull()? "-" :attributes.getAsJsonObject("sunZenith").get("value").getAsString();
//        String tagsValue = attributes.getAsJsonObject("tags").get("value").isJsonNull()? "-" : attributes.getAsJsonObject("tags").get("value").getAsString();
        String temperatureValue = attributes.getAsJsonObject("temperature").get("value").isJsonNull()? "-" : attributes.getAsJsonObject("temperature").get("value").getAsString();
//        String uVIndexValue = attributes.getAsJsonObject("uVIndex").get("value").isJsonNull()? "-" : attributes.getAsJsonObject("uVIndex").get("value").getAsString();
//        String windDirectionValue = attributes.getAsJsonObject("windDirection").get("value").isJsonNull()? "-" : attributes.getAsJsonObject("windDirection").get("value").getAsString();
        String windSpeedValue = attributes.getAsJsonObject("windSpeed").get("value").isJsonNull()? "-" : attributes.getAsJsonObject("windSpeed").get("value").getAsString();
        String details = coordinates+ "\n"
                +"\n**WEATHER**\n\n"
                +"humidity: "+humidityValue+"\n"
                +"manufacturer: "+manuValue+"\n"
                +"place: "+placeValue+"\n"
                +"rainfall: "+rainfallValue+"\n"
//                +"sunAltitude: "+sunAltitudeValue+"\n"
//                +"sunAzimuth: "+sunAzimuthValue+"\n"
//                +"sunIrradiance: "+sunIrradianceValue+"\n"
//                +"sunZenithValue: "+sunZenithValue+"\n"
//                +"tags: "+tagsValue+"\n"
                +"temperature: "+temperatureValue+"\n"
//                +"UV: "+uVIndexValue+"\n"
//                +"windDirection: "+windDirectionValue+"\n"
                +"windSpeed: "+windSpeedValue+"\n";
        coordinatesTextView.setText(details);
    }
    private void updateCoordinatesTextView1(JsonObject lightData){
        JsonObject attributes = lightData.getAsJsonObject("attributes");
        JsonObject location = attributes.getAsJsonObject("location");
        JsonArray coordinates = location.getAsJsonObject("value").getAsJsonArray("coordinates");
        String brightness = attributes.getAsJsonObject("brightness").get("value").getAsString();
        String colourRGB = attributes.getAsJsonObject("colourRGB").get("value")== null?"-":"-";
        String colourTemperature = attributes.getAsJsonObject("colourTemperature").get("value").getAsString();
        String email = attributes.getAsJsonObject("email").get("value").getAsString();
//        String onOff = attributes.getAsJsonObject("onOff").get("value").getAsBoolean()==true? "On":"Off";

        String details = coordinates+"\n"
                +"\n**LIGHT**\n\n"
                + "Brightness %: " + brightness + "\n"
                + "ColourRGB: " + colourRGB + "\n"
                + "Colour Temperature (K): " + colourTemperature + "\n"
                + "Email: " + email + "\n";
//                + "On/Off: " + onOff +"\n"
//                + "Tags: " + " " + "\n";

        coordinatesTextView.setText(details);

    }


    private void callDefaultWeatherApi(ApiService apiService) {
        Call<JsonObject> weatherCall = apiService.getDeviceData("Bearer " + accessToken, "5zI6XqkQVSfdgOrZ1MyWEf");
        weatherCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject weatherData = response.body();
                    JsonObject attributes = weatherData.getAsJsonObject("attributes");
                    JsonObject location = attributes.getAsJsonObject("location");
                    JsonArray coordinates = location.getAsJsonObject("value").getAsJsonArray("coordinates");

                    double weatherLongitude = coordinates.get(0).getAsDouble();
                    double weatherLatitude = coordinates.get(1).getAsDouble();

                    GeoPoint weatherGeoPoint = new GeoPoint(weatherLatitude, weatherLongitude);
                    Marker weatherMarker = new Marker(mapView);
                    weatherMarker.setPosition(weatherGeoPoint);
                    weatherMarker.setIcon(getResources().getDrawable(R.drawable.icweather));
                    weatherMarker.setOnMarkerClickListener((marker, mapView) -> {
                        // Update the coordinatesX TextView with the marker's position
                        coordinatesTextView.setVisibility(coordinatesTextView.VISIBLE);
                        updateCoordinatesTextView(weatherData);
                        return true;
                    });
                    mapView.getOverlays().add(weatherMarker);

                    mapView.getController().setCenter(weatherGeoPoint);

                    int zoomLevel = 18;
                    mapView.getController().setZoom(zoomLevel);
                    mapView.invalidate();
                } else {
                    showErrorToast("API error: " + response.code());
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
}
