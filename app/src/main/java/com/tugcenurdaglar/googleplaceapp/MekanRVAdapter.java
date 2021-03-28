package com.tugcenurdaglar.googleplaceapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MekanRVAdapter extends RecyclerView.Adapter<MekanRVAdapter.CardTasarimTutucu>{
    private Context mContex;
    private List<Mekanlar> mekanlarListe;

    public MekanRVAdapter(Context mContex, List<Mekanlar> mekanlarListe) {
        this.mContex = mContex;
        this.mekanlarListe = mekanlarListe;
    }



    public class CardTasarimTutucu extends RecyclerView.ViewHolder{
        private TextView textViewMekanAdi, textViewLokasyon,textViewAdres;

        public CardTasarimTutucu(@NonNull View itemView) {
            super(itemView);

            textViewMekanAdi = itemView.findViewById(R.id.textViewMekanAdi);
            textViewLokasyon = itemView.findViewById(R.id.textViewLokasyon);
            textViewAdres = itemView.findViewById(R.id.textViewAdres);
        }
    }

    @NonNull
    @Override
    public CardTasarimTutucu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.yerler_card_tasarim,parent,false);
        return new CardTasarimTutucu(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardTasarimTutucu holder, int position) {

        Mekanlar mekan = mekanlarListe.get(position);

        holder.textViewMekanAdi.setText(mekan.getMekan_adi());
        holder.textViewLokasyon.setText(mekan.getEnlem()+" - "+mekan.getBoylam());
        holder.textViewAdres.setText(mekan.getAdres());

    }

    @Override
    public int getItemCount() {
        return mekanlarListe.size();
    }
}
