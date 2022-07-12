package com.appsnipp.schooleducation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.appsnipp.schooleducation.api.ApiClient;
import com.appsnipp.schooleducation.api.ApiInterface;
import com.appsnipp.schooleducation.model.LoginData;
import com.appsnipp.schooleducation.model.ResponseData;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {
    TextInputEditText editTextNIS,editTextPassword;
    String nis,password,device_id;
    Button btn_register;
    TextView login;
    ApiInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextNIS = findViewById(R.id.editTextNisRegis);
        editTextPassword = findViewById(R.id.editTextPasswordRegis);
        btn_register = findViewById(R.id.cirRegisterButton);
        login = findViewById(R.id.txt_login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this, Login.class));
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nis = editTextNIS.getText().toString();
                password = editTextPassword.getText().toString();
                device_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<ResponseData> loginCall = apiInterface.regisResponse(nis,password,device_id);
                loginCall.enqueue(new Callback<ResponseData>() {
                    @Override
                    public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                        if(response.body() != null && response.isSuccessful() && response.body().isStatus()){

                            //Ini untuk pindah
                            Toast.makeText(Register.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Register.this, Login.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(Register.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseData> call, Throwable t) {
//                        Toast.makeText(Register.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}