package com.example.islamiccompass.helper;

import com.google.gson.annotations.SerializedName;

public class GeoResponseObject {

    @SerializedName("address")
    public Address address;

    public class Address {

        @SerializedName("city")
        public String city;
        @SerializedName("county")
        public String county;
        @SerializedName("state")
        public String state;
        @SerializedName("ISO3166-2-lvl4")
        public String iso3166_2_lv14;
        @SerializedName("country")
        public String country;
        @SerializedName("country_code")
        public String country_code;
    }
}
