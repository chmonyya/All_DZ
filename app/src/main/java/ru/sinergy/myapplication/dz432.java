package ru.sinergy.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

public class dz432 extends AppCompatActivity {

    private List<dz4_3.Person> hero = new ArrayList<>();//.asList("Наруто","узимаки","какикаки");
    private ImageButton back432;
    private RecyclerView rv432;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dz432);
        back432= findViewById(R.id.back432);back432.setOnClickListener(l);
        rv432= findViewById(R.id.rv432);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv432.setLayoutManager(layoutManager);

        hero.add(new dz4_3.Person("Вася", R.drawable.baterfly, "ddd"));


        ArrayAdapter ad = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, hero);
        rv432.setAdapter(new dz4_3.MyAdapter(hero, this));//rv431.setAdapter(ad);

    }

    private View.OnClickListener l = new View.OnClickListener () {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.back432: startActivity(new Intent(getApplicationContext(), dz4_3.class));
                    break;

            }
        }
    };


}