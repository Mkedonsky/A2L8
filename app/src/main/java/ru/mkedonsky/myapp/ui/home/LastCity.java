package ru.mkedonsky.myapp.ui.home;

import androidx.fragment.app.FragmentActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


class LastCity {
    private String lastCity;
    private final File file;

    LastCity(FragmentActivity activity) {
        file = new File(activity.getFilesDir(), "lastCity.txt");
        System.out.println("Last city created");
    }

    String getLastCity() {

        return lastCity;
    }

    void setLastCity(String lastCity) {
        this.lastCity = lastCity;
        writeCity(lastCity);
    }

    void readCity() {
        if (file.exists()) {
            try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String city = reader.readLine();
                if (city != null) {
                    this.lastCity = city;
                    System.out.println("Last city read");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void writeCity(String city) {
        if (!file.exists()) {
            try {
                file.createNewFile();
                System.out.println("Created file");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(city);
            System.out.println("Last city wrote");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
