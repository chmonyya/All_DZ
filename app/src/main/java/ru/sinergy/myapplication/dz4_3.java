package ru.sinergy.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class dz4_3 extends AppCompatActivity {

    private ImageButton back;
    private ImageButton dz43_b1;
    private ImageButton dz43_b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dz4_3);
        back= findViewById(R.id.back43);back.setOnClickListener(l);
        dz43_b1= findViewById(R.id.dz43_b1);dz43_b1.setOnClickListener(l);
        dz43_b2= findViewById(R.id.dz43_b2);dz43_b2.setOnClickListener(l);



     }


    private View.OnClickListener l = new View.OnClickListener () {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.back43: startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    break;
                case R.id.dz43_b1: startActivity(new Intent(getApplicationContext(), dz4_3_1.class));
                    break;
                case R.id.dz43_b2: startActivity(new Intent(getApplicationContext(), dz432.class));
                    break;
            }
        }
    };














    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView name;
        public TextView desc;
        public PersonViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.dz431CardImg);
            name = itemView.findViewById(R.id.dz431CardName);
            desc = itemView.findViewById(R.id.dz431CardDesc);
        }
    }

    static class MyAdapter extends RecyclerView.Adapter<PersonViewHolder> {
        private List<Person> list;
        public MyAdapter(List<Person> list, Context context) {
            this.list = list;
        }
        @Override
        public int getItemCount() {
            return list.size();
        }
        @Override
        public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.dz431card, viewGroup, false);
            PersonViewHolder holder = new PersonViewHolder(v);
            return holder;
        }
        @Override
        public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {
            personViewHolder.image.setImageResource(list.get(i).avatarId);
            personViewHolder.name.setText(list.get(i).name);
            personViewHolder.desc.setText(list.get(i).desc);
        }
    }

    static class Person {
        public final String name;
        public final String desc;
        public final int avatarId;
        public Person(String name, int avatarId, String desc) {
            this.name = name;
            this.avatarId = avatarId;
            this.desc = desc;
        }
    }









}

