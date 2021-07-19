package ru.mkedonsky.myapp.database;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.mkedonsky.myapp.R;

public class RecyclerDataAdapter extends RecyclerView.Adapter<RecyclerDataAdapter.ViewHolder> {
    private final String[] data;
    private final DatabaseSource databaseSource;


    public RecyclerDataAdapter(Context context, DatabaseSource databaseSource) {
        this.databaseSource = databaseSource;
        data = databaseSource.getDataForRecycler();
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_iayout,
                parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.listItemTextView.setText(data[position]);
    }

    @Override
    public int getItemCount() {
       int count = databaseSource.getItemCount();
       if (count > 100) {
           count = 100;
       }
       return count;
    }

     static class ViewHolder extends RecyclerView.ViewHolder {
        TextView listItemTextView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);

        }
        private void initView(View itemView ){
            listItemTextView =  itemView.findViewById(R.id.listItemTextView);

        }
    }
}
