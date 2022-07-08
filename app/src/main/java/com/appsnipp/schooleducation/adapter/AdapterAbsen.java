package com.appsnipp.schooleducation.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appsnipp.schooleducation.Absensi;
import com.appsnipp.schooleducation.R;
import com.appsnipp.schooleducation.model.AbsenData;

import java.util.List;

public class AdapterAbsen extends RecyclerView.Adapter<AdapterAbsen.HolderData> {
    private Context ctx;
    private List<AbsenData> listData;



    public AdapterAbsen(Context ctx, List<AbsenData> listData) {
        this.ctx = ctx;
        this.listData = listData;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item1, parent, false);

        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        AbsenData dm = listData.get(position);
        holder.keterangan.setText(dm.getKeterangan());
        holder.jam_absen.setText(dm.getWaktu_absen());
        if(holder.keterangan.getText().toString().equals("Hadir"))
        {
            holder.gambar.setImageResource(R.drawable.hadir);
        }else if(holder.keterangan.getText().toString().equals("Sakit"))
        {
            holder.gambar.setImageResource(R.drawable.sick);
        }else if(holder.keterangan.getText().toString().equals("Izin"))
        {
            holder.gambar.setImageResource(R.drawable.letter);
        }else{
            holder.gambar.setImageResource(R.drawable.absent);
        }
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {
        TextView keterangan, jam_absen;
        ImageView gambar;

        public HolderData(@NonNull View itemView) {
            super(itemView);
            keterangan = itemView.findViewById(R.id.textViewSub2Title);
            jam_absen = itemView.findViewById(R.id.hiyaa);
            gambar = itemView.findViewById(R.id.imageView);
        }


    }




}

