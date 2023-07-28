package ru.sinergy.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Itog extends AppCompatActivity {

    public static TextView counter;

    public static MediaPlayer music;
    public static MediaPlayer kill;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.itog);
        counter = findViewById(R.id.itog_count);

        // определение размеров экрана
        Point point = new Point(); // с помощью данного объекта можно получить размеры экрана по осям X и Y
        getWindowManager().getDefaultDisplay().getSize(point);

        GameView gameView = new GameView(this, point.x, point.y);

        LinearLayout gameLayout = findViewById(R.id.gameLayout);
        gameLayout.addView(gameView);

        music = new MediaPlayer();
        kill = new MediaPlayer();
        try {
            AssetFileDescriptor descriptor = getAssets().openFd("mouse_hunt.mp3");
            music.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
            descriptor.close();
            //mediaPlayer.prepareAsync();
            music.prepare(); // ассинхронная подготовка плейера к проигрыванию
            music.setVolume(1f, 1f); // задание уровня громкости левого и правого динамиков
            music.setLooping(false); // назначение отстутствия повторов
            music.start();

            descriptor = getAssets().openFd("pisk.mp3");
            kill.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
            descriptor.close();
            kill.prepare(); // ассинхронная подготовка плейера к проигрыванию
            kill.setVolume(1f, 1f); // задание уровня громкости левого и правого динамиков
            kill.setLooping(false); // назначение отстутствия повторов

        } catch (Exception e) { // обработка исключения на случай отстутствия файла
            e.printStackTrace(); // вывод в консоль сообщения отсутствия файла
        }
        //start service and play music

       // Button leftButton = findViewById(R.id.leftButton);
     //   Button rightButton = findViewById(R.id.rightButton);

      //  leftButton.setOnTouchListener(m);
     //   rightButton.setOnTouchListener(m);

    }



/*
    private View.OnTouchListener  m = new View.OnTouchListener () {
        @Override
        public boolean onTouch(View v, MotionEvent motion) {
            switch (v.getId()) {
                case R.id.leftButton:
                    switch (motion.getAction()) { // определяем нажата или отпущена
                        case MotionEvent.ACTION_DOWN:
                            moveLeft = true;
                            break;
                        case MotionEvent.ACTION_UP:
                            moveLeft = false;
                            break;
                    }
                    break;
                case R.id.rightButton:
                    switch (motion.getAction()) { // определяем нажата или отпущена
                        case MotionEvent.ACTION_DOWN:
                            moveRight = true;
                            break;
                        case MotionEvent.ACTION_UP:
                            moveRight = false;
                            break;
                    }
                    break;

            }
            return true;
        }
    };
*/


}