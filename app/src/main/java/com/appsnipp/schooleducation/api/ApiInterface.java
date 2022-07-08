package com.appsnipp.schooleducation.api;


import com.appsnipp.schooleducation.model.ResponseData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ApiInterface {

    @FormUrlEncoded
    @PUT("Mahasiswa")
    Call<ResponseData> profileResponse(
            @Field("id_siswa") String id_siswa,
            @Field("nama") String nama,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("Matkul")
    Call<ResponseData> matkulResponse(
            @Field("id_siswa") String id_siswa
    );

    @FormUrlEncoded
    @POST("Absen")
    Call<ResponseData> absenResponse(
            @Field("id_siswa") String id_siswa,
            @Field("id_mapel") String id_mapel
            );

    @FormUrlEncoded
    @POST("Meet.php")
    Call<ResponseData> absenData(
            @Field("npp") String npp,
            @Field("nama") String nama,
            @Field("batch") String batch,
            @Field("page") String page,
            @Field("password") String password,
            @Field("pilih") String pilih
            );


}
