package com.appsnipp.schooleducation.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appsnipp.schooleducation.Absensi;
import com.appsnipp.schooleducation.R;
import com.appsnipp.schooleducation.model.MapelData;

import java.util.List;

public class AdapterMapel extends RecyclerView.Adapter<AdapterMapel.HolderData> {
    private Context ctx;
    private List<MapelData> listData;
    String id_mapel1,hari,jam,hari_indo;

    public AdapterMapel(Context ctx, List<MapelData> listData) {
        this.ctx = ctx;
        this.listData = listData;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);

        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        MapelData dm = listData.get(position);
        holder.mapel.setText(dm.getNama_mapel());
        String[] kata = dm.getWaktu().split(",");
        hari = kata[0];
        jam = kata[1];
        if(hari.equals("Monday"))
        {
            hari_indo = "Senin";
        }else if(hari.equals("Tuesday"))
        {
            hari_indo = "Selasa";
        }else if(hari.equals("Wednesday"))
        {
            hari_indo = "Rabu";
        }else if(hari.equals("Thursday"))
        {
            hari_indo = "Kamis";
        }else if(hari.equals("Friday"))
        {
            hari_indo = "Jumat";
        }else if(hari.equals("Saturday")){
            hari_indo = "Sabtu";
        }else{
            hari_indo = "Minggu";
        }
        holder.kelas.setText(dm.getNama_guru() + " - " + hari_indo + " , " + jam + " - "+dm.getNama_kelas());
        holder.id_mapel.setText(dm.getId_mapel());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {
        TextView mapel, kelas,id_mapel;

        public HolderData(@NonNull View itemView) {
            super(itemView);
            mapel = itemView.findViewById(R.id.textViewSub1Title);
            kelas = itemView.findViewById(R.id.list_mapel2);
            id_mapel = itemView.findViewById(R.id.id_mapel);


            mapel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    id_mapel1 = id_mapel.getText().toString();
                    Intent intent = new Intent(arg0.getContext(), Absensi.class);
                    intent.putExtra("id_mapel",id_mapel1);
                    ctx.startActivity(intent);
                }
            });



        }


    }




}

