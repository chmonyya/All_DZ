package ru.sinergy.myapplication;

import android.content.Context;

public class Cat extends Entity {

    public Cat(Context context) {
        x=7;
        y=GameView.maxY - GameView.catSize - 1;
        speed = (float) 0.7;
    }


    @Override
    public void update() { // управление котиком
        if(GameView.moveLeft && x >= 0){
            x -= speed;
        }
        if(GameView.moveRight && x <= GameView.maxX - 5){
            x += speed;
        }
    }


}
