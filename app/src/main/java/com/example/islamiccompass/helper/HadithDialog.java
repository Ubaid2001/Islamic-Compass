package com.example.islamiccompass.helper;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.Window;
import android.widget.TextView;

import com.example.islamiccompass.R;

public class HadithDialog extends Dialog {

    public Context c;
    public Dialog d;
    private TextView narrator;
    private TextView hadith;
    private TextView reference;
    private String header;
    private String hadithBody;
    private String refno;

    public HadithDialog(Context context, String header, String hadithBody, String refno) {
        super(context);
        this.c = context;
        this.header = header;
        this.hadithBody = hadithBody;
        this.refno = refno;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.hadith_dialog);

        narrator = (TextView) findViewById(R.id.narrator);
        narrator.setText("Narrated By ");
        header = header.replace("Narrated", "");
        narrator.append(header);

        reference = (TextView) findViewById(R.id.reference);
        reference.setText("Reference: ");
        reference.append(refno);

        hadith = (TextView) findViewById(R.id.hadith);
        hadith.setText(hadithBody);

        if(hadith.getText().toString().contains("(ﷺ)")){

            String ss = "(ﷺ)";
            String s1 = hadith.getText().toString();

            String htmlText = s1.replace(ss, "<font color='#FFD700'>"+ ss +"</font>");
            hadith.setText(Html.fromHtml(htmlText));

        }

    }
}