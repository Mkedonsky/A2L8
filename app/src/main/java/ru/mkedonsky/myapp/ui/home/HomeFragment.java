package ru.mkedonsky.myapp.ui.home;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.mkedonsky.myapp.MainActivity;
import ru.mkedonsky.myapp.R;
import ru.mkedonsky.myapp.database.CityRequest;
import ru.mkedonsky.myapp.database.DatabaseSource;
import ru.mkedonsky.myapp.rest.OpenWeatherRepo;
import ru.mkedonsky.myapp.rest.entities.WeatherRequestRestModel;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private CityRequest cityRequest;

    private TextView cityTextView;
    private TextView updatedTextView;
    private TextView detailsTextView;
    private TextView currentTemperatureTextView;
    private TextView weatherIconTextView;
    private TextView tempMinTextView;
    private TextView tempMaxTextView;
    private ImageView imageInetSet;
    private LastCity lastCity;
    private SimpleDateFormat format;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRetainInstance(true);


        cityTextView = view.findViewById(R.id.city_field);
        updatedTextView = view.findViewById(R.id.updated_field);
        detailsTextView = view.findViewById(R.id.details_field);
        currentTemperatureTextView = view.findViewById(R.id.current_temperature_field);
        weatherIconTextView = view.findViewById(R.id.weather_icon);
        tempMinTextView = view.findViewById(R.id.temp_Min);
        tempMaxTextView = view.findViewById(R.id.temp_Max);
        imageInetSet = view.findViewById(R.id.imageInet);
        initFonts();
        lastCity = new LastCity(requireActivity());
        if (lastCity.getLastCity() == null) {
            lastCity.readCity();
        }
        updateWeatherData(lastCity.getLastCity());
        format = new SimpleDateFormat("yyyy.MM.dd:HH.mm");
    }


    private void initFonts() {
        Typeface weatherFont = Typeface.createFromAsset(requireActivity().getAssets(), "fonts/weather.ttf");
        weatherIconTextView.setTypeface(weatherFont);
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.fragmen, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.change_city) {
            showInputDialog();
        }
        if (item.getItemId() == R.id.update_weathear) {
            updateWeatherData(MainActivity.getLocCity());

//            //doSomething
//            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
//            builder.setTitle("Настройки");
//            TextView text = new TextView(getContext());
//            text.setText("Оппа, заглушка!\nА настроек то нет!");
//            builder.setView(text);
//            builder.setPositiveButton("Делать нечего((", (dialog, which) -> {
//            });
//            builder.show();
        }

        return super.onOptionsItemSelected(item);
    }


    private void showInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle(R.string.change_city);

        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("OK", (dialog, which) ->
                updateWeatherData(input.getText().toString()));
        builder.show();
    }

    private void updateWeatherData(final String city) {
        Log.e("City", "City--" + city);
        OpenWeatherRepo.getSingleton().getAPI().loadWeather(city + ",ru",
                "762ee61f52313fbd10a4eb54ae4d4de2", "metric", "ru")
                .enqueue(new Callback<WeatherRequestRestModel>() {
                    @Override
                    public void onResponse(@NonNull Call<WeatherRequestRestModel> call,
                                           @NonNull Response<WeatherRequestRestModel> response) {
                        if (response.body() != null && response.isSuccessful()) {
                            renderWeather(response.body());
                            lastCity.setLastCity(city);
                        } else {
                            clickAlertDialog();
                            if (response.code() == 500) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
                                builder.setTitle(R.string.exclamation)
                                        .setMessage(R.string.server_error)
                                        .setIcon(R.mipmap.sharp_warning_black_18dp)
                                        .setCancelable(false)
                                        .setPositiveButton(R.string.button,
                                                (dialog, id) -> {
                                                });
                                AlertDialog alert = builder.create();
                                alert.show();
                            } else if (response.code() == 401) {
                                //не авторизованы, что-то с этим делаем.
                                //например, открываем страницу с логинкой
                            }// и так далее
                        }
                    }

                    //сбой при интернет подключении
                    @Override
                    public void onFailure(Call<WeatherRequestRestModel> call, Throwable t) {
                        Toast.makeText(getActivity(), getString(R.string.network_error),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void renderWeather(WeatherRequestRestModel model) {
//        DatabaseHelper.addCityR(model.name,model.dt,model.main.temp,model.main.tempMax,
//                model.main.tempMin,model.main.pressure);
        setPlaceName(model.name, model.sys.country);
        setDetails(model.weather[0].description, model.main.feelsLike, model.main.humidity, model.main.pressure);
        setCurrentTemp(model.main.temp);
        setCurrentTempMinMax(model.main.tempMin, model.main.tempMax);
        setUpdatedText(model.dt);
        setWeatherIcon(model.weather[0].id,
                model.sys.sunrise * 1000,
                model.sys.sunset * 1000);
        // Это вот я добавляю данные базу
        DatabaseSource.getInstance().addCityRequest(
                model.name,
                format.format(new Date()),
                model.main.temp,
                model.main.tempMin,
                model.main.tempMax,
                model.main.pressure,
                "Cloudy"
        );
        Toast toast = Toast.makeText(getContext(), "Database add success!", Toast.LENGTH_SHORT);
        toast.show();
    }

    private void setPlaceName(String name, String country) {
        String cityText = name.toUpperCase() + ", " + country;
        cityTextView.setText(cityText);
    }

    private void setDetails(String description, float feelsLike, float humidity, float pressure) {
        String detailsText = description.toUpperCase() + "\n"
                + "FeelsLike: " + String.format(Locale.getDefault(), "%.1f", feelsLike) + "\u2103" + "\n"
                + "Humidity: " + humidity + "%" + "\n"
                + "Pressure: " + pressure + "hPa";
        detailsTextView.setText(detailsText);
    }

    private void setCurrentTemp(float temp) {
        String currentTextText = String.format(Locale.getDefault(), "%.1f", temp) + "\u2103";
        currentTemperatureTextView.setText(currentTextText);
    }

    private void setCurrentTempMinMax(float tempMin, float tempMax) {
        String tempMinText = "Min: " + String.format(Locale.getDefault(), "%.0f", tempMin) + "\u2103   ";
        tempMinTextView.setText(tempMinText);
        String tempMaxText = "Max: " + String.format(Locale.getDefault(), "%.0f", tempMax) + "\u2103";
        tempMaxTextView.setText(tempMaxText);

    }

    private void setUpdatedText(long dt) {
        DateFormat dateFormat = DateFormat.getDateTimeInstance();
        String updateOn = dateFormat.format(new Date(dt * 1000));
        String updatedText = "Last update: " + updateOn;
        updatedTextView.setText(updatedText);
    }

    private void setWeatherIcon(int actualId, long sunrise, long sunset) {
        int id = actualId / 100;
        String icon = "";

        if (actualId == 800) {
            long currentTime = new Date().getTime();
            if (currentTime >= sunrise && currentTime < sunset) {
                icon = getString(R.string.weather_sunny);
                Picasso.get()
                        .load("https://images.unsplash.com/photo-1561344603-efeba2453da1?ixlib=rb-1.2.1&auto=format&fit=crop&w=934&q=80")
                        .into(imageInetSet);
            } else {
                icon = getString(R.string.weather_clear_night);
                Picasso.get()
                        .load("https://images.unsplash.com/photo-1516339901601-2e1b62dc0c45?ixlib=rb-1.2.1&auto=format&fit=crop&w=920&q=80")
                        .into(imageInetSet);
            }
        } else {
            switch (id) {
                case 2: {
                    icon = getString(R.string.weather_thunder);
                    Picasso.get()
                            .load("https://images.unsplash.com/photo-1429552077091-836152271555?ixlib=rb-1.2.1&auto=format&fit=crop&w=932&q=80")
                            .into(imageInetSet);
                    break;
                }
                case 3: {
                    icon = getString(R.string.weather_drizzle);
                    Picasso.get()
                            .load("https://images.unsplash.com/photo-1429552077091-836152271555?ixlib=rb-1.2.1&auto=format&fit=crop&w=932&q=80")
                            .into(imageInetSet);
                    break;
                }
                case 5: {
                    icon = getString(R.string.weather_rainy);
                    Picasso.get()
                            .load("https://images.unsplash.com/photo-1525087740718-9e0f2c58c7ef?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=934&q=80")
                            .into(imageInetSet);
                    break;
                }
                case 6: {
                    icon = getString(R.string.weather_snowy);
                    Picasso.get()
                            .load("https://images.unsplash.com/photo-1478265409131-1f65c88f965c?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1275&q=80")
                            .into(imageInetSet);
                    break;
                }
                case 7: {
                    icon = getString(R.string.weather_foggy);
                    Picasso.get()
                            .load("https://images.unsplash.com/photo-1508777630146-b3c71332139a?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=948&q=80")
                            .into(imageInetSet);
                    break;
                }
                case 8: {
                    icon = getString(R.string.weather_cloudy);
                    Picasso.get()
                            .load("https://images.unsplash.com/photo-1513002749550-c59d786b8e6c?ixlib=rb-1.2.1&auto=format&fit=crop&w=934&q=80")
                            .into(imageInetSet);
                    break;
                }
            }
        }
        weatherIconTextView.setText(icon);
    }


    private void clickAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle(R.string.exclamation)
                .setMessage(R.string.press_button)
                .setIcon(R.mipmap.sharp_warning_black_18dp)
                .setCancelable(false)
                .setPositiveButton(R.string.button,
                        (dialog, id) -> {
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }


    @Override
    public void onClick(View v) {

    }

}


