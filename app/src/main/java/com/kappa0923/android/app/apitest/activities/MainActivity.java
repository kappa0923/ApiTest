package com.kappa0923.android.app.apitest.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kappa0923.android.app.apitest.R;
import com.kappa0923.android.app.apitest.entities.WeatherEntity;
import com.kappa0923.android.app.apitest.network.WeatherApi;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String BASE_URL = "http://weather.livedoor.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        OkHttpClient httpClient = new OkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        final WeatherApi api = retrofit.create(WeatherApi.class);

        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<WeatherEntity> task = api.getWeather("130010");
                task.enqueue(new Callback<WeatherEntity>() {
                    @Override
                    public void onResponse(Call<WeatherEntity> call, Response<WeatherEntity> response) {
                        TextView weatherText = (TextView)findViewById(R.id.weather_text);
                        weatherText.setText(response.body().getDescription().getText());
                    }

                    @Override
                    public void onFailure(Call<WeatherEntity> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "失敗しちゃった...\n" + t.getMessage(), Toast.LENGTH_SHORT).show();
                        t.printStackTrace();
                    }
                });
            }
        });
    }
}
