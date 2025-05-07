package com.example.islamiccompass.fragments;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.islamiccompass.R;
import com.example.islamiccompass.api.RandomApi;
import com.example.islamiccompass.helper.AlarmReciever;
import com.example.islamiccompass.helper.SalahDialog;
import com.example.islamiccompass.helper.SalahResponseObject;
import com.example.islamiccompass.model.Salah;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SalahFragment#} factory method to
 * create an instance of this fragment.
 */
public class SalahFragment extends Fragment {

    View view;
    private RelativeLayout salahRel;
    private Salah salah = new Salah();
    private double latitude;
    private double longitude;

    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;



    public SalahFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_salah, container, false);
        salahRel = (RelativeLayout) view.findViewById(R.id.salahRel);

        getParentFragmentManager().setFragmentResultListener("requestKey", this, (requestKey, bundle) -> {
            latitude = bundle.getDouble("lat");
            longitude = bundle.getDouble("long");
            System.out.println("The current result: "+latitude + " & " +longitude);
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onStart() {
        super.onStart();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                getSalah();

            }
        }, 2500);

    }

    private void getSalah(){

        if(latitude != 0 && longitude != 0) {
            System.out.println("Salah LAT & LONG: " + latitude + " & " + longitude);

            SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yy");
            String c = ft.format(new Date());

            System.out.println(c);

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.aladhan.com/v1/timings/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    RandomApi randomApi = retrofit.create(RandomApi.class);

                    Call<SalahResponseObject> call = randomApi.getSalah(c, latitude, longitude);

                    call.enqueue(new Callback<SalahResponseObject>() {
                        @Override
                        public void onResponse(Call<SalahResponseObject> call, Response<SalahResponseObject> response) {

                            if (!response.isSuccessful()) {
                                System.out.println("Code: " + response.code());
                                return;
                            }

                            System.out.println("The Android is connected to the Server For Salah!!!");

                            SalahResponseObject salahResponseObject = response.body();
                            SalahResponseObject.Datum datum = salahResponseObject.data;
                            SalahResponseObject.Datum.Timings timings = datum.timings;

                            salah.setFajr(timings.fajr);
                            salah.setSunrise(timings.sunrise);
                            salah.setDhuhr(timings.dhuhr);
                            salah.setAsr(timings.asr);
                            salah.setMaghrib(timings.maghrib);
                            salah.setIsha(timings.isha);

                            System.out.println(salah);

                            salahRel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    System.out.println("This is onclick "+salah);
                                    SalahDialog salahDialog = new SalahDialog(getContext(), salah);
                                    salahDialog.show();
                                }
                            });

                            settingAlarms();
                        }

                        @Override
                        public void onFailure(Call<SalahResponseObject> call, Throwable t) {
                            System.out.println(t.getMessage());
                        }
                    });

                }
            });

        }

    }

    private void settingAlarms() {

        alarmManager = (AlarmManager) requireContext().getSystemService(Context.ALARM_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if(alarmManager.canScheduleExactAlarms()){
                Intent intent = new Intent(getContext(), AlarmReciever.class);
                pendingIntent = PendingIntent.getBroadcast(getContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);

                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
                String time = sdf.format(new Date());

                List<String> salats = new ArrayList<>();
                salats.add(salah.getFajr());
                salats.add(salah.getDhuhr());
                salats.add(salah.getAsr());
                salats.add(salah.getMaghrib());
                salats.add(salah.getIsha());

                boolean isSalah = false;

                for(String s: salats){
                    if(time.equals(s)){
                        String[] strings = s.split(":");

                        int hour = Integer.parseInt(strings[0]);
                        int min = Integer.parseInt(strings[1]);
                        calendar.set(Calendar.HOUR_OF_DAY, hour);
                        calendar.set(Calendar.MINUTE, min);
                        alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                        isSalah = true;

                        break;

                    } else System.out.println("Not salah time");
                }

                Bundle bundle = new Bundle();
                bundle.putBoolean("isSalah", isSalah);
                getParentFragmentManager().setFragmentResult("isSalah", bundle);

            }
        }
    }
}