package com.appsnipp.schooleducation;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.appsnipp.schooleducation.api.ApiClient;
import com.appsnipp.schooleducation.api.ApiInterface;
import com.appsnipp.schooleducation.model.ResponseData;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.navigation.NavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    double long_gps,lang_gps;
    String id_siswa,id_jadwal,hasil,id_qr;
    String[] kata;
    GpsTracker gpsTracker;
    ApiInterface apiInterface;
    private Context context;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CardView cardViewScan = findViewById(R.id.scanqr);
        CardView cardViewMapel = findViewById(R.id.mapel);
        CardView cardViewProfile = findViewById(R.id.profile);
        CardView cardViewLogout = findViewById(R.id.logout);
        context = this;


        cardViewScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
                integrator.setPrompt("Scan QRcode");
                integrator.setOrientationLocked(false);
                integrator.initiateScan();
            }
        });

        cardViewMapel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Mapel.class));
            }
        });

        cardViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Profile.class));
            }
        });


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        IntentResult result =
                IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Keluar",
                        Toast.LENGTH_LONG).show();
            } else {
               id_siswa = "1";
               long_gps = gpsTracker.getLongitude();
               lang_gps = gpsTracker.getLatitude();
               hasil = result.getContents();
               String[] kata = hasil.split("-");
               id_jadwal = kata[0];
               id_qr = kata[1];
                new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE).setTitleText("Apakah Anda Yakin Ingin Absen ?").setContentText("Pastikan Mata Pelajarannya sudah sesuai").setConfirmText("Iya").showCancelButton(true).setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {@Override
                public void onClick(SweetAlertDialog sDialog) {
                    apiInterface = ApiClient.getClient().create(ApiInterface.class);
                    Call<ResponseData> sendbio = apiInterface.absenData(id_jadwal,id_qr,id_siswa,long_gps,lang_gps);
                    sendbio.enqueue(new Callback<ResponseData>() {
                        @Override
                        public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                            if(response.isSuccessful())
                            {
                                sDialog.setTitleText("Berhasil Absen").setContentText(response.body().getMessage()).setConfirmText("OK").setConfirmClickListener(null).changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                                startActivity(intent);
                            }else
                            {
                                sDialog.setTitleText("Gagal Absen").setContentText(response.body().getMessage()).setConfirmText("OK").setConfirmClickListener(null).changeAlertType(SweetAlertDialog.WARNING_TYPE);
                                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseData> call, Throwable t) {
                            Log.d("RETRO", "Falure : " + "Gagal Mengirim Request");
                        }
                    });
                }
                }).show();

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
