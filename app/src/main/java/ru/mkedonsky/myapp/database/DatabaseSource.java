package ru.mkedonsky.myapp.database;

import java.util.List;

public class DatabaseSource {

    private final WeatherDao weatherDao;
    private List<CityRequest> requests;
    private static DatabaseSource instance;

    private DatabaseSource() {
        this.weatherDao = DatabaseApp.getInstance().getWeatherDao();
    }

    // Получить все
    public List<CityRequest> getAllRequests() {
        if (requests == null) {
            loadRequests();
        }
        return requests;
    }

    private void loadRequests() {
        requests = weatherDao.getAllRequests();
    }


    public void addCityRequest(CityRequest cityRequest) {
        weatherDao.insertCityRequest(cityRequest);
        loadRequests();
    }

    public void updateCityRequest(CityRequest cityRequest) {
        weatherDao.updateCityRequest(cityRequest);
        loadRequests();
    }

    public void deleteCityRequestByCity(String cityName) {
        weatherDao.deleteCityRequestByCity(cityName);
        loadRequests();
    }

    public void addCityRequest(String city_name, String last_request, float temp, float min_temp, float max_temp, float pressure, String overcast) {
        CityRequest cityRequest = new CityRequest();
        cityRequest.city_name = city_name;
        cityRequest.last_request = last_request;
        cityRequest.temp = temp;
        cityRequest.min_temp = min_temp;
        cityRequest.max_temp = max_temp;
        cityRequest.pressure = pressure;
        cityRequest.overcast = overcast;
        weatherDao.insertCityRequest(cityRequest);
        loadRequests();
    }

    public static DatabaseSource getInstance() {
        if (instance == null) {
            instance = new DatabaseSource();
        }
        return instance;
    }

    int getItemCount() {
        return weatherDao.getCountCitys();
    }

    String[] getDataForRecycler() {
        loadRequests();
        String[] data = new String[requests.size()];
        for (int i = 0; i < data.length ; i++) {
            data[i] = requests.get(i).toString();
        }
        return data;
    }
}
