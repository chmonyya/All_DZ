package ru.sinergy.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    // private Button dz2_2;
    //  private Button dz2_3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // dz2_2 = findViewById(R.id.dz2_2);
        //  dz2_3 = findViewById(R.id.dz2_3);

    }

    public void onClickDZ2_2(View view) {
        //Toast.makeText(this, "onClickDZ2_2", Toast.LENGTH_SHORT).show();
        setContentView(R.layout.dz2_2);
        TextView info = findViewById(R.id.dz2_2_info);
        TextView bank = findViewById(R.id.bank);
        ProgressBar pb = findViewById(R.id.dz2_2_pb);
        pb.setMin(0);
        pb.setMax(14000);

        Thread t = new Thread(new Runnable() {
            int cash = 1000;
            int month = 0;
            float bankAdd = 0;
            float bankAddTotal = 0;
            float bankMonthPercent = 5f / 12f;

            @Override
            public void run() {

                try {
                    while (cash < 14000) {
                        month++;
                        bankAdd = cash / 100 * bankMonthPercent;
                        bankAddTotal += bankAdd;

                        cash = (int) (cash + 2500 + bankAdd);

                        pb.setProgress(cash, true);
                        info.setText("Месяц: " + month + " Накоплено: " + cash);
                        bank.setText("Доход от депозита мес/всего: " + (int) bankAdd + "/" + (int) bankAddTotal);
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException ex) {
                }

                info.setText("Для накопления суммы требуется " + month + " мес.");

            }
        });
        t.start();
    }


    public void onClickBack(View view) {
        setContentView(R.layout.activity_main);
        mode = Mode.CANCEL;
    }


    public void onClickDZ2_3 (View view) {
        //Toast.makeText(this, "В разработке", Toast.LENGTH_SHORT).show();
        setContentView(R.layout.dz2_3);
        ProgressBar pb = findViewById(R.id.dz2_3_pb);
        pb.setMin(0);
        pb.setMax(100);
        TextView itogo = findViewById(R.id.itogo);

        ArrayList<Ticket> tickets = new ArrayList<>();


        //заказ

        int adult = 9;
        int old = 5;
        int kids = 11;


        for (int i = 0; i < adult; i++) {
            Ticket tik = new Ticket(TicketType.ADULT);
            tickets.add(tik);
        }
        for (int i = 0; i < old; i++) {
            Ticket tik = new Ticket(TicketType.OLD);
            tickets.add(tik);
        }

        for (int i = 0; i < kids; i++) {
            Ticket tik = new Ticket(TicketType.KIDS);
            tickets.add(tik);
        }

        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            int tick = 1;

            @Override
            public void run() {

                pb.setProgress(tick, true);
                tick++;
                if (tick==100) {
                    this.cancel();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            double itog = 0;
                            for (Ticket t : tickets) {
                                itog = itog + t.cost;
                            }
                            itogo.setText("Билетов:"+tickets.size()+", сумма: "+itog+ "монеток");
                        }
                    });
                    //itogo.setText("Билетов:"+", сумма: "+itog+ "монеток");
                }


            }
        }, 0, 25);
    }


    public void onNumInp(View view){


    }


    public void onClickDZ2_4(View view) {
        setContentView(R.layout.dz2_4);

        EditText ed = findViewById(R.id.biletNum);
        ed.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                TextView itog = findViewById(R.id.itog);

                if (s.length() < 6){
                    itog.setText("!!! Номер должен быть шестизначным !!!");
                    return;
                }
                String str = s.toString();
                int orig = Integer.valueOf(str);

                if (isHappy(orig)){
                    itog.setText("Ваш билет счастливый");
                }
                else{
                    while (!isHappy(orig)){
                        orig++;
                    }
                    itog.setText("Ваш билет несчастливый:(  \nБлижайший счастливый билет: " + orig);


                }


            }

            public boolean isHappy (int num) {
                int chet = 0;
                int nechet = 0;
                String s = String.valueOf(num);


                for (int i = 0; i < s.length(); i++) {
                    char c = s.charAt(i);
                    int n = Character.getNumericValue(c);

                    if (n % 2 == 0){
                        chet += n;
                    }
                    else {
                        nechet += n;
                    }

                }
                return  chet==nechet;
            }



            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }
        });
    }


    int weight;
    public void onClickDZ2_5(View view){
        setContentView(R.layout.dz2_5);
        EditText ed = findViewById(R.id.gruz);


        ed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    weight = Integer.valueOf(editable.toString());


                }
                catch (NumberFormatException e){
                    weight = 0;
                }
            }
        });
    }

    public void chet(View view){
        TextView count = findViewById(R.id.count);
        double delit = 9.80665 / 3.721;
        count.setText("Понадобится \n" + ((int)((weight*100) / delit)) + "\n кг топлива");
    }

    public void exit(View view){
        finish();
    }


    Mode mode = Mode.CANCEL;

    public void onClickDZ2_6(View view){
        setContentView(R.layout.dz2_6);
        TextView tim = findViewById(R.id.tim);
        mode = Mode.RESET;
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            int tick = 0;

            @Override
            public void run() {
                switch (mode){
                    case FORWARD:
                        tick++;
                        break;

                    case BACK:
                        tick--;
                        break;

                    case PAUSE:
                        return;

                    case RESET:
                        tick = 0;
                        break;

                    case CANCEL:
                        this.cancel();
                        break;
                }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tim.setText("" + tick);
                        }
                    });

            }



        }, 0, 1000);
    }

    enum Mode{
        BACK, FORWARD, PAUSE, RESET, CANCEL
    }

    public void back(View view){
        mode = Mode.BACK;
    }

    public void forward(View view){
        mode = Mode.FORWARD;
    }

    public void pause(View view){
        mode = Mode.PAUSE;
    }

    public void reset(View view){
        mode = Mode.RESET;
    }



















}







