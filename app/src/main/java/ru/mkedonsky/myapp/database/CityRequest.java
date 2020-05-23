package ru.mkedonsky.myapp.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity(tableName = "city_requests",
        indices = {@Index(value = {"city_name"}, unique = true)})
public class CityRequest {

    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "city_name")
    public String city_name;

    @ColumnInfo(name = "last_request")
    public String last_request;

    @ColumnInfo(name = "temp")
    public float temp;

    @ColumnInfo(name = "min_temp")
    public float min_temp;

    @ColumnInfo(name = "max_temp")
    public float max_temp;

    @ColumnInfo(name = "pressure")
    public float pressure;

    @ColumnInfo(name = "overcast")
    public String overcast;

    public long getId() {
        return id;
    }

    public String getCity_name() {
        return city_name;
    }

    public String getLast_request() {
        return last_request;
    }

    public float getTemp() {
        return temp;
    }

    public float getMin_temp() {
        return min_temp;
    }

    public float getMax_temp() {
        return max_temp;
    }

    public float getPressure() {
        return pressure;
    }

    public String getOvercast() {
        return overcast;
    }

    @Override
    public String toString() {
        return  city_name + "  " +
                last_request + "  " +"\n"+
                "Температура: "+temp +"C˚"+"\n" +
                "Min: " +min_temp +"C˚"+"\n" +
                "Max: " +max_temp + "C˚" +"\n" +
                "Давление:" +pressure + "hPa" +
                overcast;

    }
}
