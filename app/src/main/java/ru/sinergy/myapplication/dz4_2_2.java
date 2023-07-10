package ru.sinergy.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class dz4_2_2 extends AppCompatActivity {

    private Button pay;
    //private Button edit;
    private Button exit;

    private TextView info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dz4_2_2);

        pay = findViewById(R.id.ticketPay);
        pay.setOnClickListener(p);
        exit = findViewById(R.id.back422);
        exit.setOnClickListener(ex);

        info = findViewById(R.id.info422);

        Bundle b = getIntent().getExtras();
        if (b!=null) {
            //Ticket t = (Ticket) b.getSerializable(Ticket.class.getSimpleName());
            b.get("");
            info.setText(
                    "ФИО:"+b.get("userId")+"\n"
                    + "Тип билета: "+b.get("type")+"\n"
                    + "цена: "+b.get("cost")+"\n"
                    + "Из: "+b.get("from")+"\n"
                    + "В: "+b.get("to")+"\n"
                            + "Отправление: "+b.get("out")+"\n"
                            + "Прибытие: "+b.get("arrival")+"\n"
                            + "Место: "+b.get("place")+"\n"

            );
        } else {
            info.setText("данные не получены,/n попробуйте снова.");
        }


       // edit = findViewById(R.id.ticketEdit);
       // edit.setOnClickListener(ed);


    }


    private View.OnClickListener p = new View.OnClickListener () {
        @Override
        public void onClick(View v) {
            //тост
            Toast.makeText(getBaseContext(), "Билет оплачен", Toast.LENGTH_SHORT).show();
            //новый билет
            Intent it = new Intent(getApplicationContext(), dz4_2_1.class);
            startActivity(it);
        }

    };


    /*private View.OnClickListener ed = new View.OnClickListener () {
        @Override
        public void onClick(View v) {
            Intent it = new Intent(getApplicationContext(), dz4_2_1.class);
            startActivity(it);
        }
    };*/


    private View.OnClickListener ex = new View.OnClickListener () {
        @Override
        public void onClick(View v) {
            Intent it = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(it);
        }
    };


}