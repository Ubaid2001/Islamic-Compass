package com.example.islamiccompass.helper;

import com.google.gson.annotations.SerializedName;

public class SalahResponseObject {
    @SerializedName("data")
    public Datum data;

    public class Datum {
        @SerializedName("timings")
        public Timings timings;

        public class Timings {

            @SerializedName("Fajr")
            public String fajr;

            @SerializedName("Sunrise")
            public String sunrise;

            @SerializedName("Dhuhr")
            public String dhuhr;

            @SerializedName("Asr")
            public String asr;

            @SerializedName("Maghrib")
            public String maghrib;

            @SerializedName("Isha")
            public String isha;

            @SerializedName("Imsak")
            public String imsak;

            @SerializedName("Midnight")
            public String midnight;

            @SerializedName("Firstthird")
            public String firstthired;

            @SerializedName("Lastthird")
            public String lastthird;

        }
    }
}
