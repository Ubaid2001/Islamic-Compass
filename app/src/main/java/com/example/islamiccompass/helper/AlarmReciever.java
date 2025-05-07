package com.example.islamiccompass.helper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;

import androidx.annotation.RequiresApi;

import com.example.islamiccompass.R;

public class AlarmReciever extends BroadcastReceiver {
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onReceive(Context context, Intent intent) {

        // we will use vibrator first
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(4000);

        Uri path = Uri.parse("android.resource://" + "com.example.islamiccompass/" + R.raw.adhan);
        RingtoneManager.setActualDefaultRingtoneUri(context.getApplicationContext(), RingtoneManager.TYPE_RINGTONE, path);

        // setting default ringtone
        Ringtone ringtone = RingtoneManager.getRingtone(context.getApplicationContext(), path);

        // play ringtone
        ringtone.play();

    }

}
