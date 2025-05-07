package com.example.islamiccompass.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.islamiccompass.R;
import com.example.islamiccompass.api.RandomApi;
import com.example.islamiccompass.helper.HadithDialog;
import com.example.islamiccompass.helper.HadithResponseObject;
import com.example.islamiccompass.model.Hadith;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HadithFragment extends Fragment {

    View view;
    private RelativeLayout hadithRel;
    private TextView hadithText;
    private String header;
    private String hadithBody;
    private Hadith hadith = new Hadith();
    private String reference;


    @Override
    public void onStart() {
        super.onStart();


    }

    public HadithFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_hadith, container, false);

        hadithRel = (RelativeLayout) view.findViewById(R.id.hadithRel);
        hadithText = (TextView) view.findViewById(R.id.hadithText);
        getHadith();

        if(hadithText.getText().toString().contains("(ﷺ)")){

            String ss = "(ﷺ)";
            String s1 = hadithText.getText().toString();

            String htmlText = s1.replace(ss, "<font color='#FFD700'>"+ ss +"</font>");
            hadithText.setText(Html.fromHtml(htmlText));

        }

        hadithRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HadithDialog hadithDialog = new HadithDialog(getContext(), header, hadithBody, reference);
                hadithDialog.show();
            }
        });

        return view;
    }

    public void getHadith(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://random-hadith-generator.vercel.app")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                RandomApi randomApi = retrofit.create(RandomApi.class);

                Call<HadithResponseObject> call = randomApi.getHadith();

                call.enqueue(new Callback<HadithResponseObject>() {
                    @Override
                    public void onResponse(Call<HadithResponseObject> call, Response<HadithResponseObject> response) {

                        if (!response.isSuccessful()) {
                            System.out.println("Code: " + response.code());
                            return;
                        }

                        System.out.println("The Android is connected to the Server For HADITH!!!");

                        HadithResponseObject hadithResponseObject = response.body();
                        HadithResponseObject.Datum datum = hadithResponseObject.data;

                        hadith.setBookName(datum.bookName);
                        hadith.setBook(datum.book);
                        hadith.setChapterName(datum.chapterName);
                        hadith.setHadith_english(datum.hadith);
                        hadith.setHeader(datum.header);
                        hadith.setId(datum.id);
                        hadith.setRefno(datum.refno);

                        System.out.println("This is the hadith: "+hadith.getHadith_english());

                        header = hadith.getHeader();
                        hadithBody = hadith.getHadith_english();
                        reference = hadith.getRefno();
                    }


                    @Override
                    public void onFailure(Call<HadithResponseObject> call, Throwable t) {
                        System.out.println(t.getMessage());
                    }
                });
            }
        });
    }
}