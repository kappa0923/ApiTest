package com.kappa0923.android.app.apitest.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kappa0923.android.app.apitest.R;
import com.kappa0923.android.app.apitest.entities.WeatherEntity;
import com.kappa0923.android.app.apitest.network.WeatherApi;
import com.kappa0923.android.app.apitest.storage.Weather;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String BASE_URL = "http://weather.livedoor.com";
    private Response<WeatherEntity> weatherEntityResponse;
    private RealmConfiguration realmConfiguration;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OkHttpClient httpClient = new OkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final WeatherApi api = retrofit.create(WeatherApi.class);
        realmConfiguration = new RealmConfiguration.Builder(this).build();
        realm = Realm.getInstance(realmConfiguration);

        Button getButton = (Button)findViewById(R.id.get_button);
        getButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText)findViewById(R.id.editText);
                String locationNum = editText.getText().toString();
                Call<WeatherEntity> task = api.getWeather(locationNum);
                task.enqueue(new Callback<WeatherEntity>() {
                    @Override
                    public void onResponse(Call<WeatherEntity> call, Response<WeatherEntity> response) {
                        weatherEntityResponse = response;
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

        Button saveButton = (Button)findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Weather weather = new Weather();
                weather.setId(0);
                weather.setDescription(weatherEntityResponse.body().getDescription().getText());
                realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.copyToRealmOrUpdate(weather);
                    }
                });
            }
        });

        Button loadButton = (Button)findViewById(R.id.load_button);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RealmResults<Weather> results = realm.where(Weather.class)
                                                    .equalTo("id", 0)
                                                    .findAll();
                TextView weatherText = (TextView)findViewById(R.id.weather_text);
                weatherText.setText(results.get(0).getDescription());
            }
        });
    }
}
