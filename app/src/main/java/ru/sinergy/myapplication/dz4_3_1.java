package ru.sinergy.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class dz4_3_1 extends AppCompatActivity {


    private List <dz4_3.Person> hero = new ArrayList<>();//.asList("Наруто","узимаки","какикаки");
    private ImageButton back431;
    //private ListView lw431;
    private RecyclerView rv431;
    //private TextView dz431tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dz431);
        back431= findViewById(R.id.back431);back431.setOnClickListener(l);
        //lw431= findViewById(R.id.lw431);
        rv431= findViewById(R.id.rv431);
        //dz431tv = findViewById(R.id.dz431tv);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv431.setLayoutManager(layoutManager);

        hero.add(new dz4_3.Person("Вася", R.drawable.baterfly, "ddd"));
        hero.add(new dz4_3.Person("Петя", R.drawable.baterfly, "fff"));
        hero.add(new dz4_3.Person("ddd", R.drawable.baterfly, "fff"));
        hero.add(new dz4_3.Person("fg", R.drawable.baterfly, "fff"));
        hero.add(new dz4_3.Person("cgh", R.drawable.baterfly, "fff"));
        hero.add(new dz4_3.Person("ert", R.drawable.baterfly, "fff"));
        hero.add(new dz4_3.Person("g", R.drawable.baterfly, "fff"));
        hero.add(new dz4_3.Person("fgh", R.drawable.baterfly, "fff"));

        ArrayAdapter ad = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, hero);
        //lw431.setAdapter(ad);
        rv431.setAdapter(new dz4_3.MyAdapter(hero, this));//rv431.setAdapter(ad);
        /*lw431.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView av, View v,  int pos, long l) {
                String sel = hero[pos];
                dz431tv.setText("Выбран "+sel);
            }
        });*/

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

