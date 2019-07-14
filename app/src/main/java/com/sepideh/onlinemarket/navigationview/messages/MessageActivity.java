package com.sepideh.onlinemarket.navigationview.messages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.sepideh.onlinemarket.R;

public class MessageActivity extends AppCompatActivity {

    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        intent=getIntent();
        Log.d("mynotif", "onCreate: "+ intent.getStringExtra("notifMsg"));
    }
}
