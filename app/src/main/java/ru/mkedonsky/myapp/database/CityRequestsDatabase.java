package ru.mkedonsky.myapp.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {CityRequest.class}, version = 2)
abstract class CityRequestsDatabase extends RoomDatabase {

    abstract WeatherDao getWeatherDao();
}
