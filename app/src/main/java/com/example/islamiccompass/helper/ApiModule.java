package com.example.islamiccompass.helper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(ActivityComponent.class)
public class ApiModule {

//  Set the hostname variable to the server's ip address and port number.
    static final String hostname = "http://XXX.XXX.XXX.X:XXXX";

    static Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    @Provides
    public static Retrofit provideRetrofit(){
        System.out.println("this provide method is called");
        return new Retrofit.Builder()
                .baseUrl(hostname)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
}
