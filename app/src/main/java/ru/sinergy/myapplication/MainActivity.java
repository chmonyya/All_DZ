package ru.sinergy.myapplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;



public class MainActivity extends AppCompatActivity {

    private Random rnd = new Random();
    private static Timer timer;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_main);
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
        if (timerMode!=TimerMode.CANCEL) {
            b.putInt("timer_tick", tick);
            b.putString("timer_mode", timerMode.name());
        }
    }

    protected void onRestoreInstanceState(Bundle b) {
        super.onRestoreInstanceState(b);
        timerMode = TimerMode.valueOf(b.getString("timer_mode"));
        if (timerMode!=TimerMode.CANCEL) {
            tick = b.getInt("timer_tick");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onClickBack(null);
    }
    //protected void onResume() {
    //    super.onResume();
    //    Log.d(LOG_TAG, "onResume ");
   // }




    public void onClickDZ4_1(View view) {
        setContentView(R.layout.dz4_1);
        /*
            TextView dz41_info = findViewById(R.id.dz34_info);
    Toast toast = Toast.makeText(getApplicationContext(), R.string.successful_search, Toast.LENGTH_SHORT); // инициализация
                        //LinearLayout toastContainer = (LinearLayout) toast.getView(); !!! return null sience android api 11, use toast.setView(ImageView)
                        // добавление в тост картинки
                        ImageView cat = new ImageView(getApplicationContext()); // создание объекта картинки (контекст)
                        cat.setImageResource(R.drawable.found_cat); // добавление картинки из ресурсов
                        toast.setView(cat);
                        toast.setGravity(Gravity.LEFT | Gravity.TOP,  55, y - 100); // задание позиции на экране (положение, смещение по оси Х, смещение по оси Y)
                        toast.show(); // демонстрация тоста на экране
         */
    }
































    private static MediaPlayer mediaPlayer;
    private static SeekBar seekBar; // создание поля SeekBar
    private static SeekBar volume;
    //private static TextView seekBarHint; // поле информации у SeekBar
    private static TextView dz34_info;
    private static PlayerMode playerMode = PlayerMode.STOP;
    private static List<String> mp3 = new ArrayList<>();
    private static int currIdx = 0;
    private Button playPauseButton;
    private static String songName="";
    private static String time="";

    public void onClickDZ3_4(View view) {
        //mediaPlayer = new MediaPlayer();
        setContentView(R.layout.dz3_4);
        dz34_info = findViewById(R.id.dz34_info);
        //seekBarHint = findViewById(R.id.seekBarHint);
        seekBar = findViewById(R.id.seekBar);
        volume = findViewById(R.id.volume);
        volume.setMin(0);
        volume.setMax(10);
        volume.setProgress(10, true);
        playPauseButton = (Button) findViewById(R.id.play_pause);
        findSong();
        if (mp3.isEmpty()) {
            dz34_info.setText("музыки не найдено!");
            return;
        }
        playSong(false);
        dz34_info.setText("Трек : "+currIdx+", "+songName+", Play для старта");
        playerMode = PlayerMode.PAUSE;
        // создание слушателя изменения SeekBar
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            // метод при перетаскивании ползунка по шкале, где progress позволяет получить нове значение ползунка (позже progress назрачается длина трека в миллисекундах)
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
                if (playerMode!=PlayerMode.PLAY) return;
                //seekBarHint.setVisibility(View.INVISIBLE); // установление не видимости seekBarHint
                int timeTrack = (int) Math.ceil(progress/1000f); // перевод времени из миллисекунд в секунды

                // вывод на экран времени отсчёта трека
                if (timeTrack < 10) {
                    time = ("00:0" + timeTrack);
                } else if (timeTrack < 60){
                    time = ("00:" + timeTrack);
                } else if (timeTrack >= 60) {
                    time = ("01:" + (timeTrack - 60));
                }

                // передвижение времени отсчёта трека
                double percentTrack = progress / (double) seekBar.getMax(); // получение процента проигранного трека (проигранное время делится на длину трека)
                // seekBar.getX() - начало seekBar по оси Х
                // seekBar.getWidth() - ширина контейнера seekBar
                // 0.92 - поправочный коэффициент (так как seekBar занимает не всю ширину своего контейнера)
                //seekBarHint.setX(seekBar.getX() + Math.round(seekBar.getWidth()*percentTrack*0.92));
            }
            @Override // метод при начале перетаскивания ползунка по шкале
            public void onStartTrackingTouch(SeekBar seekBar) {
                //seekBarHint.setVisibility(View.INVISIBLE); // установление видимости seekBarHint
            }
            @Override // метод при завершении перетаскивания ползунка по шкале
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (mediaPlayer != null && mediaPlayer.isPlaying()) { // если mediaPlayer не пустой и mediaPlayer воспроизводится
                    mediaPlayer.seekTo(seekBar.getProgress()); // обновление позиции трека при изменении seekBar
                }
            }
        });

        volume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            // метод при перетаскивании ползунка по шкале, где progress позволяет получить нове значение ползунка (позже progress назрачается длина трека в миллисекундах)
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
                if (mediaPlayer==null) return;
                float vol = (float) progress / 10;
                mediaPlayer.setVolume(vol, vol);
            }
            @Override // метод при начале перетаскивания ползунка по шкале
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override // метод при завершении перетаскивания ползунка по шкале
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            int currentPosition, total;
            boolean flash;
            String info = "";
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (mediaPlayer==null) {
                            return;
                        }

                        switch (playerMode) {

                            case PAUSE:
                                if (flash) {
                                    playPauseButton.setText("PLAY");//dz34_info.setText("Пауза");
                                    flash = false;
                                } else {
                                    playPauseButton.setText("");//dz34_info.setText("Пауза");
                                    flash = true;
                                }
                                break;

                            case STOP:
                                if (mediaPlayer.isPlaying()) {
                                    mediaPlayer.stop();
                                }
                                //seekBarHint.setVisibility(View.INVISIBLE);
                                playPauseButton.setText("REPEAT");
                                currIdx = 0;
                                break;

                            case PLAY:
                                currentPosition = mediaPlayer.getCurrentPosition();
                                total = mediaPlayer.getDuration();
                                if (currentPosition>0 && currentPosition < total) {
                                    dz34_info.setText ("Трек : "+currIdx+"\n" + songName+" \n"+time);
                                    seekBar.setProgress(currentPosition); // обновление seekBar текущей позицией трека
                                } else {
                                    if (currIdx < mp3.size()-1) {
                                        dz34_next(null);
                                    } else {
                                        dz34_info.setText("Всё проиграно");
                                        playerMode = PlayerMode.STOP;
                                    }
                                }
                                break;
                        }
                    }
                });
            }
        }, 0, 500);
    }

    public void dz34_next(View view) {
        if (currIdx>=mp3.size()-1) {
            dz34_info.setText("Конец списка");
            return;
        }
        currIdx++;
        playSong(true);
        dz34_info.setText("Трек : "+currIdx+", "+songName);
    }
    public void dz34_previos(View view) {
        if (currIdx<=0) {
            dz34_info.setText("Начало списка");
            return;
        }
        currIdx--;
        playSong(true);
        dz34_info.setText("Трек : "+currIdx+", "+songName);
    }


    public void dz34_play_pause(View view) {
        if (playerMode==PlayerMode.STOP) {
            playerMode = PlayerMode.PLAY;
            playSong(true);
            mediaPlayer.start();
        } else if (playerMode==PlayerMode.PLAY) {
            playerMode = PlayerMode.PAUSE;
            mediaPlayer.pause();
        } else {
            playerMode = PlayerMode.PLAY;
            playPauseButton.setText("||");
            mediaPlayer.start();
        }

    }


    enum PlayerMode{
        PLAY, PAUSE, STOP
    }

    public void findSong() {
        try {
            String [] list = getAssets().list("");
            for(String filename : list) {
                if (filename.endsWith(".mp3")) {
                    mp3.add(filename);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playSong(final boolean start) {
        String filename = mp3.get(currIdx);
        if (mediaPlayer!=null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        mediaPlayer = new MediaPlayer();
      /*  mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                //mediaPlayer.setLooping(false); // назначение отстутствия повторов
                //seekBar.setMax(mediaPlayer.getDuration()); // ограниечение seekBar длинной трека
                //seekBar.setProgress(0);
                //songName = filename.replaceFirst(".mp3", "");
                if (start) {
                    playerMode = PlayerMode.PAUSE;
                    dz34_play_pause(null);//mp.start();
                }
            }
        });*/

        try {
            AssetFileDescriptor descriptor = getAssets().openFd(filename);
            mediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
            descriptor.close();
            //mediaPlayer.prepareAsync();
            mediaPlayer.prepare(); // ассинхронная подготовка плейера к проигрыванию
            //mediaPlayer.setVolume(0.7f, 0.7f); // задание уровня громкости левого и правого динамиков
            mediaPlayer.setLooping(false); // назначение отстутствия повторов
            seekBar.setMax(mediaPlayer.getDuration()); // ограниечение seekBar длинной трека
            seekBar.setProgress(0);
            songName = filename.replaceFirst(".mp3", "");
            if (start) {
                playerMode = PlayerMode.PAUSE;
                dz34_play_pause(null);//mp.start();
            }
        } catch (Exception e) { // обработка исключения на случай отстутствия файла
            e.printStackTrace(); // вывод в консоль сообщения отсутствия файла
            playerMode = PlayerMode.STOP;
        }
    }




































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






































    TimerMode timerMode = TimerMode.CANCEL;
    int tick = 0;

    public void onClickDZ2_6(View view){
        setContentView(R.layout.dz2_6);
        TextView tim = findViewById(R.id.dz26_info);
        timerMode = TimerMode.RESET;
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {


            @Override
            public void run() {
                switch (timerMode){
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

    enum TimerMode{
        BACK, FORWARD, PAUSE, RESET, CANCEL
    }

    public void back(View view){
        timerMode = TimerMode.BACK;
    }

    public void forward(View view){
        timerMode = TimerMode.FORWARD;
    }

    public void pause(View view){
        timerMode = TimerMode.PAUSE;
    }

    public void reset(View view){
        timerMode = TimerMode.RESET;
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
        timerMode = TimerMode.CANCEL;
        playerMode = PlayerMode.STOP;
        if (timer!=null) {
            timer.cancel();
            timer = null;
        }
        if (mediaPlayer!=null) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer = null;
        }
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







