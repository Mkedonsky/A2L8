package ru.mkedonsky.myapp.ui.history;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ru.mkedonsky.myapp.R;
import ru.mkedonsky.myapp.database.DatabaseApp;
import ru.mkedonsky.myapp.database.DatabaseSource;
import ru.mkedonsky.myapp.database.RecyclerDataAdapter;
import ru.mkedonsky.myapp.database.WeatherDao;

public class HistoryFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initRecyclerView(view);
        Log.e("Adapter","Адаптер создан");
    }
    private void initRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        RecyclerDataAdapter adapter = new RecyclerDataAdapter(getContext(), DatabaseSource.getInstance());
        Log.e("adapter","adapter--"+ adapter);
        recyclerView.setAdapter(adapter);

    }
}
