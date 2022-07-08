package com.appsnipp.schooleducation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.appsnipp.schooleducation.adapter.AdapterMapel;
import com.appsnipp.schooleducation.api.ApiClient;
import com.appsnipp.schooleducation.api.ApiInterface;
import com.appsnipp.schooleducation.model.MapelData;
import com.appsnipp.schooleducation.model.ResponseData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Mapel extends AppCompatActivity {
    ApiInterface apiInterface;
    private RecyclerView rvData;
    private RecyclerView.Adapter adData;
    private RecyclerView.LayoutManager lmData;
    private List<MapelData> listData = new ArrayList<>();
    private SwipeRefreshLayout srlData;
    private ProgressBar pbData;
    private String id_siswa = "1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapel);
        rvData = findViewById(R.id.rv_data);
        srlData = findViewById(R.id.srl_data);
        pbData = findViewById(R.id.pb_data);
        lmData = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvData.setLayoutManager(lmData);

        srlData.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srlData.setRefreshing(true);
                retrieveData();
                srlData.setRefreshing(false);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        retrieveData();
    }

    public void retrieveData() {
        pbData.setVisibility(View.VISIBLE);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseData> tampilData = apiInterface.matkulResponse(id_siswa);

        tampilData.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {

                listData = response.body().getMatkul();
                adData = new AdapterMapel(Mapel.this, listData);
                rvData.setAdapter(adData);
                adData.notifyDataSetChanged();
                pbData.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Toast.makeText(Mapel.this, "Gagal Menghubungi Server  ", Toast.LENGTH_SHORT).show();
                pbData.setVisibility(View.INVISIBLE);
            }
        });
    }

}