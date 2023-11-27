package com.unal.jardiapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unal.jardiapp.R;
import com.unal.jardiapp.ViewPlantActivity;
import com.unal.jardiapp.model.Plant;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PlantAdapterList extends RecyclerView.Adapter<PlantAdapterList.CompanyViewHolder> {

    ArrayList<Plant> plantList;
    ArrayList<Plant> originalPlantList;

    public PlantAdapterList(ArrayList<Plant> plantList){
        this.plantList = plantList;
        originalPlantList = new ArrayList<>();
        originalPlantList.addAll(plantList);
    }

    @NonNull
    @Override
    public CompanyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plant_list_design, null, false);
        return new CompanyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantAdapterList.CompanyViewHolder holder, int position) {
        holder.nameText.setText(plantList.get(position).getName());
        holder.speciesText.setText(plantList.get(position).getSpecies());
    }

    @Override
    public int getItemCount() {
        return plantList.size();
    }

    public class CompanyViewHolder extends RecyclerView.ViewHolder {

        TextView nameText, speciesText;
        public CompanyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.nameText);
            speciesText = itemView.findViewById(R.id.speciesText);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, ViewPlantActivity.class);
                    intent.putExtra("ID", plantList.get(getAdapterPosition()).getId());
                    context.startActivity(intent);
                }
            });
        }
    }

    public void filter(String searchingText){
        if (searchingText.length() == 0){
            plantList.clear();
            plantList.addAll(originalPlantList);
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<Plant> collection = plantList.stream().filter(i -> i.getName().toLowerCase().contains(searchingText.toLowerCase())).collect(Collectors.toList());
                plantList.clear();
                plantList.addAll(collection);
            } else {
                for (Plant plant : originalPlantList) {
                    if (plant.getName().toLowerCase().contains(searchingText.toLowerCase())) {
                        plantList.add(plant);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }
}
