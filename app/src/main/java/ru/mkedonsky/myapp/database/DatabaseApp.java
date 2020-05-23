package ru.mkedonsky.myapp.database;

import android.app.Application;

import androidx.room.Room;

public class DatabaseApp extends Application {

    private static DatabaseApp instance;
    private CityRequestsDatabase db;

    public static DatabaseApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        db = Room.databaseBuilder(
                getApplicationContext(),
                CityRequestsDatabase.class,
                "city_requests")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }

    public WeatherDao getWeatherDao() {

        return db.getWeatherDao();
    }


}
