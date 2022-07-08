package com.appsnipp.schooleducation.model;

import com.appsnipp.schooleducation.Mapel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseData {
    private List<MapelData> matkul;
    private List<AbsenData> absen;

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
}