package com.example.indoorairqualitymonitoringapp.dashboardelements;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.indoorairqualitymonitoringapp.ApiService;
import com.example.indoorairqualitymonitoringapp.DashBoardactivity;
import com.example.indoorairqualitymonitoringapp.R;
import com.example.indoorairqualitymonitoringapp.WeatherFragment;
import com.example.indoorairqualitymonitoringapp.models.DatapointRequest;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;
import org.osmdroid.config.Configuration;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GraphFragment extends Fragment {
    private AppCompatButton showGraphBtn, showStartTimeAcBtn, showEndTimeAcBtn;
    private DashBoardactivity activity;
    private ValueLineChart mCubicValueLineChart;

    private static String accessToken;
    private ApiService apiService;

    private String typeAttribute,typetime;
    private String assetAttribute;
    private Spinner selectSpinner, spinner;

    public static GraphFragment newInstance(String accessToken, DashBoardactivity activity) {
        GraphFragment fragment = new GraphFragment();
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate (R.layout.fragment_graph, container, false);
        showStartTimeAcBtn =view.findViewById(R.id.Ac_btn_ShowDayStart);
        showEndTimeAcBtn = view.findViewById(R.id.Ac_btn_ShowDayEnd);
        showGraphBtn = view.findViewById(R.id.btn_Show);
        showStartTimeAcBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePicker(showStartTimeAcBtn);
            }
        });
        showEndTimeAcBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePicker(showEndTimeAcBtn);
            }
        });
        showGraphBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateGraph();
            }
        });

        mCubicValueLineChart = view.findViewById(R.id.cubiclinechart);
        spinnerAttribute(view);
        return view;
    }
    private void updateGraph() {
        if (!"Select Option".equals(typeAttribute) && !showStartTimeAcBtn.getText().toString().isEmpty() && !showEndTimeAcBtn.getText().toString().isEmpty()) {
            callgraphapi();
        } else {
            Toast.makeText(getActivity(), "Please select attribute, start date, and end date", Toast.LENGTH_SHORT).show();
        }
    }
    private void openDatePicker(AppCompatButton AcButton) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                (view, year, month, dayOfMonth) -> {
                    // Month is 0-based, so add 1 to get the correct month
                    LocalDate selectedDate = LocalDate.of(year, month+1, dayOfMonth);
                    DateTimeFormatter utcFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                    String formattedDate = selectedDate.atStartOfDay().format(utcFormatter);
                    AcButton.setText(formattedDate);
                },
                2023, 0, 1);

        datePickerDialog.show();
    }

    private void selectAttribute(){
        if(typeAttribute == "humidity") {assetAttribute = "5zI6XqkQVSfdgOrZ1MyWEf";}
        else if(typeAttribute=="temperature") {assetAttribute = "5zI6XqkQVSfdgOrZ1MyWEf";}
        else if(typeAttribute == "CO2") {assetAttribute = "4lt7fyHy3SZMgUsECxiOgQ";}
        else if(typeAttribute == "PM10") {assetAttribute = "4lt7fyHy3SZMgUsECxiOgQ";}
        else if(typeAttribute == "PM25") {assetAttribute = "4lt7fyHy3SZMgUsECxiOgQ";}
        else if(typeAttribute == "AQI") {assetAttribute = "4lt7fyHy3SZMgUsECxiOgQ";}
        else if(typeAttribute=="rainfall") {assetAttribute= "5zI6XqkQVSfdgOrZ1MyWEf";}
        else if(typeAttribute=="windspeed") {assetAttribute ="5zI6XqkQVSfdgOrZ1MyWEf";}
    }
    private void spinnerAttribute(View view){
        selectSpinner = view.findViewById(R.id.frameAttribute);

        List<String> optionsAttributes = new ArrayList<>();
        optionsAttributes.add("Select Option");
        optionsAttributes.add("temperature");
        optionsAttributes.add("CO2");
        optionsAttributes.add("PM10");
        optionsAttributes.add("PM25");
        optionsAttributes.add("AQI");
        optionsAttributes.add("humidity");
        optionsAttributes.add("rainfall");
        optionsAttributes.add("windspeed");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, optionsAttributes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectSpinner.setAdapter(adapter);
        selectSpinner.setSelection(0);
        selectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedAttribute = (String) adapterView.getItemAtPosition(i);
                if(!"Select Option".equals(selectedAttribute)) typeAttribute = selectedAttribute;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
    }
    public void callgraphapi(){
        mCubicValueLineChart.clearChart();
        ValueLineSeries series = new ValueLineSeries();
        series.setColor(0xFF63cbb0);
        selectAttribute();
        OkHttpClient client = getUnsafeOkHttpClient();
        apiService = new Retrofit.Builder()
                .baseUrl("https://uiot.ixxc.dev/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(ApiService.class);

        DatapointRequest request = new DatapointRequest(showStartTimeAcBtn.getText().toString(), showEndTimeAcBtn.getText().toString());
        Call<JsonArray> call = apiService.getDataPoints(assetAttribute, typeAttribute, "Bearer " + accessToken, request);

        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if (response.isSuccessful()) {
                    JsonArray tempDataPoints = response.body();
                    for (int i = tempDataPoints.size() - 1; i >= 0; i--) {
                        JsonElement tempDataPoint = tempDataPoints.get(i);
                        JsonObject coor = tempDataPoint.getAsJsonObject();

                        long timestamp =  coor.get("x").getAsLong();
                        Timestamp tmpX = new Timestamp(timestamp);

                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM");
                        String x = sdf.format(timestamp);

                        float y = coor.get("y").getAsFloat();

                        series.addPoint(new ValueLinePoint(x, y));
                    }
                    mCubicValueLineChart.addSeries(series);
                    mCubicValueLineChart.startAnimation();
                }
                else {
                    Log.e("API Response", "Unsuccessful response: " + response.code());
                    Toast.makeText(getActivity(), "Unsuccessful response: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.e("Request", "Unsuccessful request: " + t.getMessage());
                Toast.makeText(getActivity(), "ERROR " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }

    public static OkHttpClient getUnsafeOkHttpClient() {
        try {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();

            //Log
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor);

            //Bear token
            builder.addInterceptor(chain -> {
                Request newRequest = chain.request()
                        .newBuilder()
                        .addHeader("Authorization", "Bearer " +accessToken)
                        .build();

                return chain.proceed(newRequest);
            });

            return builder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}