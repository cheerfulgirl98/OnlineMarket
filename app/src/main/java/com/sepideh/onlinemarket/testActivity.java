package com.sepideh.onlinemarket;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.jakewharton.rxbinding3.view.RxView;

import java.util.concurrent.TimeUnit;

import kotlin.Unit;

public class testActivity extends AppCompatActivity {

    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        button=findViewById(R.id.chek_rx);
        RxView.clicks(button).debounce(3000, TimeUnit.MILLISECONDS).subscribe(this::testm);
    }

    private void testm(Unit unit) {
    }
}
