package ru.sinergy.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class dz4_2_1 extends AppCompatActivity {
    private Button confirm;
    private Button back;
    Spinner spinner;
    String[] data = {"Детский", "Взрослый", "Пенсионер"};
    private Ticket t;
    EditText ed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dz4_2_1);

        confirm = findViewById(R.id.ticketConfirm);;
        confirm.setOnClickListener(confirmLst);
        back= findViewById(R.id.close421);
        back.setOnClickListener(b);

        ed = findViewById(R.id.fio421);
        ed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
               t.userId = editable.toString();
            }
        });


        spinner = (Spinner) findViewById(R.id.spiner421);
        // адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        // заголовок
        spinner.setPrompt("Тип билета");
        // выделяем элемент
        spinner.setSelection(2);
        // устанавливаем обработчик нажатия
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // показываем позиция нажатого элемента
                if (position==0) {
                    t.setCost(TicketType.KIDS);
                } else if (position==1) {
                    t.setCost(TicketType.ADULT);
                } else if (position==2) {
                    t.setCost(TicketType.OLD);
                }
                //Toast.makeText(getBaseContext(), "Position = " + position, Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });


        t = new Ticket(TicketType.ADULT);
    }





    private View.OnClickListener confirmLst = new View.OnClickListener () {

        @Override
        public void onClick(View v) {
            if (t.userId==null) {
                Toast.makeText(getBaseContext(), "Введите ФИО", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent it = new Intent(getApplicationContext(), dz4_2_2.class);
            it.putExtra("userId", t.userId);
            it.putExtra("type", t.type);
            it.putExtra("cost", t.cost);
            it.putExtra("from", t.from);
            it.putExtra("to", t.to);
            it.putExtra("out", t.out);
            it.putExtra("arrival", t.arrival);
            it.putExtra("place", t.place);
            startActivity(it);
        }

    };

    private View.OnClickListener b = new View.OnClickListener () {
        @Override
        public void onClick(View v) {
            Intent it = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(it);
        }
    };


}