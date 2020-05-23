package ru.mkedonsky.myapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCityRequest(CityRequest cityRequest);

    @Update
    void updateCityRequest(CityRequest cityRequest);

    @Delete
    void deleteCityRequest(CityRequest cityRequest);

    @Query("SELECT * FROM city_requests ORDER BY last_request DESC LIMIT 100")
    List<CityRequest> getAllRequests();

    @Query("SELECT COUNT() FROM city_requests")
    int getCountCitys();

    @Query("DELETE FROM city_requests WHERE city_name = :cityName")
    void deleteCityRequestByCity(String cityName);

    @Query("SELECT * FROM city_requests WHERE city_name = :cityName")
    List<CityRequest> getRequestByCity(String cityName);
}
