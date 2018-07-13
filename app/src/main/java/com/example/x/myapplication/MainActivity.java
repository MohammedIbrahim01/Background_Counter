package com.example.x.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String KEY_COUNT = "key-count";
    public static final String NAME_SHARED_PREFERENCES = "name-shared-preferences";
    private TextView tvCounter;
    private Button btnSetAlarm;
    private Button btnCancelAlarm;
    private Button btnReset;

    private AlarmManager alarmManager;
    private SharedPreferences sharedPreferences;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSetAlarm = (Button) findViewById(R.id.btn_set_alarm);
        btnCancelAlarm = (Button) findViewById(R.id.btn_cancel_alarm);
        btnReset = (Button) findViewById(R.id.btn_reset);
        tvCounter = (TextView) findViewById(R.id.tv_counter);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        sharedPreferences = getSharedPreferences(NAME_SHARED_PREFERENCES, MODE_PRIVATE);


        int count = sharedPreferences.getInt(KEY_COUNT, 0);
        tvCounter.setText(String.valueOf(count));


        btnSetAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlarm();
            }
        });

        btnCancelAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("www", "cancel");
                cancelAlarm();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelAlarm();
                sharedPreferences.edit().putInt(KEY_COUNT, 0).apply();

            }
        });
    }

    private void cancelAlarm() {
        Log.i("www", "cancel1");

        alarmManager.cancel(pendingIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    private void setAlarm() {
        pendingIntent = PendingIntent.getBroadcast(this, 0, new Intent(getApplicationContext(), Receiver.class), 0);

        alarmManager.setRepeating(AlarmManager.RTC, System.currentTimeMillis() + 2 * 1000, 2 * 1000, pendingIntent);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        int count = sharedPreferences.getInt(s, 0);
        tvCounter.setText(String.valueOf(count));
    }
}
