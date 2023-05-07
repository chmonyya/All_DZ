package ru.sinergy.myapplication;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;



public class MainActivity extends AppCompatActivity {

    private Random rnd = new Random();

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_main);
        // dz2_2 = findViewById(R.id.dz2_2);
    }

    /*
    при повороте
    onSaveInstanceState
    onPause
    onStop
    onDestroy
    onCreate
    onStart
    onRestoreInstanceState
    onResume
     */

    protected void onSaveInstanceState(Bundle b) {
        super.onSaveInstanceState(b);
        if (mode!=Mode.CANCEL) {
            b.putInt("timer_tick", tick);
            b.putString("timer_mode", mode.name());
        }
    }

    protected void onRestoreInstanceState(Bundle b) {
        super.onRestoreInstanceState(b);
        mode = Mode.valueOf(b.getString("timer_mode"));
        if (mode!=Mode.CANCEL) {
            tick = b.getInt("timer_tick");
        }
    }

    //protected void onResume() {
    //    super.onResume();
    //    Log.d(LOG_TAG, "onResume ");
   // }












    // создание полей для вывода на экран нужных значений
    private TextView coordinatesOut; // окно вывода значений координат
    private int x; // задание поля для координаты X
    private int y; // задание поля для координаты Y
    private String sDown; // строка для записи координат нажатия
    private String sMove; // строка для записи координат движения
    private String sUp; // строка для записи координат отпускания

    // задание дополнительных полей координат кота Шрёдингера
    private int xCat = 50 + rnd.nextInt(800); // задание поля для координаты X
    private int yCat = 50 + rnd.nextInt(1300); // задание поля для координаты Y
    private final int deltaCat = 50; // допустимая погрешность в нахождении кота
    private long stamp = 0;

    public void onClickDZ3_2(View view) {
        //Toast.makeText(this, "onClickDZ2_2", Toast.LENGTH_SHORT).show();
        setContentView(R.layout.dz3_2);
        // присваивание переменной активити элемента представления activity_main
        coordinatesOut = findViewById(R.id.coordinatesOut);

        // выполнение действий при касании экрана
        coordinatesOut.setOnTouchListener(listener);
    }

    // объект обработки касания экрана (слушатель)
    private View.OnTouchListener listener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) { // в motionEvent пишутся координаты
            if (System.currentTimeMillis() - stamp < 4000) return true; //защита от мультиклика
            x = (int) motionEvent.getX(); // инициализация координат X
            y = (int) motionEvent.getY(); // инициализация координат Y

            switch (motionEvent.getAction()) { // метод getAction() считывает состояние касания (нажатие/движение/отпускание)

                case MotionEvent.ACTION_DOWN: // нажатие
                    sDown = "Касание: x=" + x + ", y=" + y;
                    sMove = "";
                    sUp = "";
                    break;

                    case MotionEvent.ACTION_MOVE: // движение
                    //sMove = "Движение: x=" + x + ", y=" + y;
                    // задание условия нахождения кота Шрёдингера
                    if ( Math.abs(x - xCat) < deltaCat && Math.abs(y - yCat) < deltaCat) { // если пользователь коснулся места нахождения кота
                        stamp = System.currentTimeMillis();
                        // размещаем тост (контекст, сообщение, длительность сообщения)
                        Toast toast = Toast.makeText(getApplicationContext(), R.string.successful_search, Toast.LENGTH_SHORT); // инициализация
                        //LinearLayout toastContainer = (LinearLayout) toast.getView(); !!! return null sience android api 11, use toast.setView(ImageView)
                        // добавление в тост картинки
                        ImageView cat = new ImageView(getApplicationContext()); // создание объекта картинки (контекст)
                        cat.setImageResource(R.drawable.found_cat); // добавление картинки из ресурсов
                        toast.setView(cat);
                        toast.setGravity(Gravity.LEFT | Gravity.TOP,  55, y - 100); // задание позиции на экране (положение, смещение по оси Х, смещение по оси Y)
                        toast.show(); // демонстрация тоста на экране
                        coordinatesOut.setText("Мяу");
                        xCat = 50 + rnd.nextInt(800);
                        yCat = 50 + rnd.nextInt(1300);
                        return true;
                    } else {
                        sMove = "Движение: x=" + x + ", y=" + y;
                    }
                    break;

                    case MotionEvent.ACTION_UP: // отпускание
                case MotionEvent.ACTION_CANCEL: // внутрений сбой (аналогичен ACTION_UP)
                    sMove = "";
                    sUp = "Отпускание: x="+x+", y="+y;
                    break;
            }

            // вывод на экран в три строки считанных значений координат
            coordinatesOut.setText(sDown + "\n" + sMove + "\n" + sUp);

            return true; // подтверждение нашей обработки событий
        }
    };



    Mode mode = Mode.CANCEL;
    int tick = 0;

    public void onClickDZ2_6(View view){
        setContentView(R.layout.dz2_6);
        TextView tim = findViewById(R.id.tim);
        mode = Mode.RESET;
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {


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








}







