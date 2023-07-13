package ru.sinergy.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class dz4_3_1 extends AppCompatActivity {


    private String [] hero = {"Наруто","узимаки","какикаки"};
    private ImageButton back431;
    private ListView lw431;
    private TextView dz431tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dz431);
        back431= findViewById(R.id.back431);back431.setOnClickListener(l);
        lw431= findViewById(R.id.lw431);
        dz431tv = findViewById(R.id.dz431tv);

        ArrayAdapter<String> ad = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, hero);
        lw431.setAdapter(ad);

        lw431.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView av, View v,  int pos, long l) {
                String sel = hero[pos];
                dz431tv.setText("Выбран "+sel);
            }
        });

    }


    private View.OnClickListener l = new View.OnClickListener () {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.back431: startActivity(new Intent(getApplicationContext(), dz4_3.class));
                    break;

            }
        }
    };



}