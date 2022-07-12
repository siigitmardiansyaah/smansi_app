package com.appsnipp.schooleducation.model;

import com.appsnipp.schooleducation.Mapel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseData {
    private List<MapelData> matkul;
    private List<AbsenData> absen;
    @SerializedName("message")
    private String message;
    @SerializedName("status")
    private boolean status;
    @SerializedName("login")
    private LoginData login;

    public List<MapelData> getMatkul() {
        return matkul;
    }

    public void setMatkul(List<MapelData> matkul) {
        this.matkul = matkul;
    }

    public List<AbsenData> getAbsen() {
        return absen;
    }

    public void setAbsen(List<AbsenData> absen) {
        this.absen = absen;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public LoginData getLogin() {
        return login;
    }

    public void setLogin(LoginData login) {
        this.login = login;
    }
}