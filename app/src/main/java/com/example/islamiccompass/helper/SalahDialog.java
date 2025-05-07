package com.example.islamiccompass.helper;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.widget.TableRow;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.islamiccompass.R;
import com.example.islamiccompass.model.Salah;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SalahDialog extends Dialog {

    public Context c;
    public Dialog d;
    public Salah salah;
    public TextView row1Time;
    public TextView row2Time;
    public TextView row3Time;
    public TextView row4Time;
    public TextView row5Time;
    public TextView row6Time;
    public TextView row7Time;

    public SalahDialog(Context context, Salah salah){
        super(context);
        this.c = context;
        this.salah = salah;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.salah_dialog);

        row1Time = findViewById(R.id.row1Time);
        row2Time = findViewById(R.id.row2Time);
        row3Time = findViewById(R.id.row3Time);
        row4Time = findViewById(R.id.row4Time);
        row5Time = findViewById(R.id.row5Time);
        row6Time = findViewById(R.id.row6Time);
        row7Time = findViewById(R.id.row7Time);

        row2Time.setText(salah.getFajr());
        row3Time.setText(salah.getSunrise());
        row4Time.setText(salah.getDhuhr());
        row5Time.setText(salah.getAsr());
        row6Time.setText(salah.getMaghrib());
        row7Time.setText(salah.getIsha());

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String time = sdf.format(new Date());
        Date t = null;

        try {
            System.out.println("the try and catch worked");
            t = sdf.parse(time);

            assert t != null;
            if(t.after(sdf.parse(salah.getFajr())) && t.before(sdf.parse(salah.getSunrise()))){
                TableRow row = findViewById(R.id.tableRow2);
                row.setBackgroundColor(Color.parseColor("#80FF5733"));
                TextView textView = findViewById(R.id.row2Salat);
                textView.setTextColor(Color.parseColor("#FFD700"));

                YoYo.with(Techniques.Tada)
                        .duration(700)
                        .repeat(5)
                        .playOn(row);

            }else if(t.after(sdf.parse(salah.getSunrise())) && t.before(sdf.parse(salah.getDhuhr()))){
                TableRow row = findViewById(R.id.tableRow3);
                row.setBackgroundColor(Color.parseColor("#80FF5733"));
                TextView textView = findViewById(R.id.row3Salat);
                textView.setTextColor(Color.parseColor("#FFD700"));

                YoYo.with(Techniques.Tada)
                        .duration(700)
                        .repeat(5)
                        .playOn(row);

            }else if(t.after(sdf.parse(salah.getDhuhr())) && t.before(sdf.parse(salah.getAsr()))){
                TableRow row = findViewById(R.id.tableRow4);
                row.setBackgroundColor(Color.parseColor("#80FF5733"));
                TextView textView = findViewById(R.id.row4Salat);
                textView.setTextColor(Color.parseColor("#FFD700"));

                YoYo.with(Techniques.Tada)
                        .duration(700)
                        .repeat(5)
                        .playOn(row);


            }else if(t.after(sdf.parse(salah.getAsr())) && t.before(sdf.parse(salah.getMaghrib()))){
                TableRow row = findViewById(R.id.tableRow5);
                row.setBackgroundColor(Color.parseColor("#80FF5733"));
                TextView textView = findViewById(R.id.row5Salat);
                textView.setTextColor(Color.parseColor("#FFD700"));

                YoYo.with(Techniques.Tada)
                        .duration(700)
                        .repeat(5)
                        .playOn(row);

            }else if(t.after(sdf.parse(salah.getMaghrib())) && t.before(sdf.parse(salah.getIsha()))){
                TableRow row = findViewById(R.id.tableRow6);
                row.setBackgroundColor(Color.parseColor("#80FF5733"));
                TextView textView = findViewById(R.id.row6Salat);
                textView.setTextColor(Color.parseColor("#FFD700"));

                YoYo.with(Techniques.Tada)
                        .duration(700)
                        .repeat(5)
                        .playOn(row);

            }else if(t.after(sdf.parse(salah.getIsha())) && t.before(sdf.parse("23:59"))){
                TableRow row = findViewById(R.id.tableRow7);
                row.setBackgroundColor(Color.parseColor("#80FF5733"));
                TextView textView = findViewById(R.id.row7Salat);
                textView.setTextColor(Color.parseColor("#FFD700"));

                YoYo.with(Techniques.Tada)
                        .duration(700)
                        .repeat(5)
                        .playOn(row);

            }

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }
}