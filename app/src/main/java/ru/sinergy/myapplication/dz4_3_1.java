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

        hero.add(new dz4_3.Person("Храм Фусими-инари", R.drawable.dz4311, "Киото"));
        hero.add(new dz4_3.Person("Небесное дерево", R.drawable.dz4312, "Токио"));
        hero.add(new dz4_3.Person("Мусорный остров", R.drawable.dz4313, "Токио"));
        hero.add(new dz4_3.Person("Район Асакуса", R.drawable.dz4314, "Токио"));
        hero.add(new dz4_3.Person("Рыбный рынок Цукидзи", R.drawable.dz4315, "Токио"));
        hero.add(new dz4_3.Person("Бамбуковый лес Сагано", R.drawable.dz4316, "Киото"));
        hero.add(new dz4_3.Person("Золотой павильон", R.drawable.dz4317, "Киото"));
        hero.add(new dz4_3.Person("Гора Фудзи", R.drawable.dz4318, "остров Хонсю"));
        hero.add(new dz4_3.Person("Химедзи", R.drawable.dz4319, "остров Хонсю"));
        hero.add(new dz4_3.Person("Город Нара", R.drawable.dz43110, "остров Хонсю"));
        hero.add(new dz4_3.Person("Храм Тосёгу", R.drawable.dz43111, "Никко"));
        hero.add(new dz4_3.Person("Императорский дворец", R.drawable.dz43112, "Токио"));
        hero.add(new dz4_3.Person("Храм Тодай-дзи", R.drawable.dz43113, "Никко"));

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

