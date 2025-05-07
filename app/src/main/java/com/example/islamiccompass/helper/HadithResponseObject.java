package com.example.islamiccompass.helper;

import com.google.gson.annotations.SerializedName;

public class HadithResponseObject {
    @SerializedName("data")
    public Datum data;


    public class Datum {

        @SerializedName("book")
        public String book;

        @SerializedName("bookName")
        public String bookName;

        @SerializedName("chapterName")
        public String chapterName;

        @SerializedName("hadith_english")
        public String hadith;

        @SerializedName("header")
        public String header;

        @SerializedName("id")
        public int id;

        @SerializedName("refno")
        public String refno;
    }
}
