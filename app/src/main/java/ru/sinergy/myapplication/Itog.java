package ru.sinergy.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Itog extends AppCompatActivity {

    public static boolean moveLeft,moveRight = false;
    public static TextView counter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.itog);
        counter = findViewById(R.id.itog_count);

        GameView gameView = new GameView(this);

        LinearLayout gameLayout = findViewById(R.id.gameLayout);
        gameLayout.addView(gameView);

        Button leftButton = findViewById(R.id.leftButton);
        Button rightButton = findViewById(R.id.rightButton);

        leftButton.setOnTouchListener(m);
        rightButton.setOnTouchListener(m);

    }




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



}