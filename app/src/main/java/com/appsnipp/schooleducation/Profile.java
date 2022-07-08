package com.appsnipp.schooleducation;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.appsnipp.schooleducation.api.ApiClient;
import com.appsnipp.schooleducation.api.ApiInterface;
import com.appsnipp.schooleducation.model.ResponseData;

import cn.pedant.SweetAlert.SweetAlertDialog ;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile extends AppCompatActivity {
    private Context context;
    ApiInterface apiInterface;
    EditText txt_nama, txt_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        TextView back_button = findViewById(R.id.back_button);
        Button btn_update = findViewById(R.id.update_btn);
        txt_nama = findViewById(R.id.txt_nama);
        txt_password = findViewById(R.id.txt_password);
        context = this;
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, MainActivity.class));
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id_siswa = "1";
                String nama = txt_nama.getText().toString();
                String password = txt_password.getText().toString();
                new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE).setTitleText("Apakah anda yakin ingin update ?").setContentText("Data terbaru akan muncul setelah anda logout").setConfirmText("Iya").showCancelButton(true).setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {@Override
                public void onClick(SweetAlertDialog sDialog) {
                    apiInterface = ApiClient.getClient().create(ApiInterface.class);
                    Call<ResponseData> sendbio = apiInterface.profileResponse(id_siswa,nama,password);
                    sendbio.enqueue(new Callback<ResponseData>() {
                        @Override
                        public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                            if(response.isSuccessful())
                            {
                                sDialog.setTitleText("Profile Diupdate").setContentText("Profile anda berhasil diupdate").setConfirmText("OK").setConfirmClickListener(null).changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                            }else
                            {
                                sDialog.setTitleText("Gagal Diupdate").setContentText("Profile anda gagal berhasil diupdate").setConfirmText("OK").setConfirmClickListener(null).changeAlertType(SweetAlertDialog.WARNING_TYPE);
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
        });


    }
}