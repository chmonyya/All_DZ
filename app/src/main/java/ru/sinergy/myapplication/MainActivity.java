package ru.sinergy.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
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
    }


    public void onClickDZ2_3 (View view) {
        //Toast.makeText(this, "В разработке", Toast.LENGTH_SHORT).show();
        setContentView(R.layout.dz2_3);
        ProgressBar pb = findViewById(R.id.dz2_3_pb);
        pb.setMin(0);
        pb.setMax(100);
        TextView itogo = findViewById(R.id.itogo);

        ArrayList<Ticket> tickets = new ArrayList<>();

        /*
        заказ
         */
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



}







