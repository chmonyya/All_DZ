package ru.sinergy.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

public class Mouse extends Entity {

    private int xx = 2; // коэф. по Х
    private float minSpeed = (float) 0.1; // минимальная скорость
    private float maxSpeed = (float) 0.5; // максимальная скорость
    private Random random = new Random(); //рандомайзер
    protected Bitmap mouse;
    protected float mouseSize;
    private boolean dead;

    public Mouse(Context context) {
        y=0;
        x = random.nextInt(GameView.maxX) - xx;
        mouseSize = xx*2;
        speed = minSpeed + (maxSpeed - minSpeed) * random.nextFloat();

        Bitmap cBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.mouse);
        mouse = Bitmap.createScaledBitmap(cBitmap, (int)(mouseSize * GameView.dotPerBoxX/2), (int)(mouseSize * GameView.dotPerBoxY), false);
        //mouse.recycle(); java.lang.RuntimeException: Canvas: trying to use a recycled bitmap android.graphics.Bitmap@e268930

    }

    @Override
    public void update() {
        y += speed;
    }

    public boolean isCollision(float catX, float catY, float catSize) {
        return !(((x+mouseSize) < catX)||(x > (catX+catSize))||((y+mouseSize) < catY)||(y > (catY+catSize)));
    }
    public boolean isFree() {
        return y >= GameView.maxY;
    }

    public void dead() {
        dead = true;
    }
    public boolean isDead() {
        return dead;
    }
}
