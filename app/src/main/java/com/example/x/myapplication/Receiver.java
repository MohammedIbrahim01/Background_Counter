package com.example.x.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class Receiver extends BroadcastReceiver {

    public static final String KEY_COUNT = "key-count";
    public static final String NAME_SHARED_PREFERENCES = "name-shared-preferences";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    public void onReceive(Context context, Intent intent) {

        sharedPreferences = context.getSharedPreferences(NAME_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        int count = sharedPreferences.getInt(KEY_COUNT, 0);
        editor.putInt(KEY_COUNT, ++count);
        editor.apply();

    }
}
