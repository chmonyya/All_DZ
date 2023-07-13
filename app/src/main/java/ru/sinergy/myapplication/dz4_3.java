package ru.sinergy.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;

public class dz4_3 extends AppCompatActivity {

    private ImageButton back;
    private ImageButton dz43_b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dz4_3);
        back= findViewById(R.id.back43);back.setOnClickListener(l);
        dz43_b1= findViewById(R.id.dz43_b1);dz43_b1.setOnClickListener(l);



     }


    private View.OnClickListener l = new View.OnClickListener () {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.back43: startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    break;
                case R.id.dz43_b1: startActivity(new Intent(getApplicationContext(), dz4_3_1.class));
                    break;
            }
        }
    };





}