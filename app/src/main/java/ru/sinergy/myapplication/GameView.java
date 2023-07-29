package ru.sinergy.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class GameView extends SurfaceView implements Runnable {

    public static int maxX = 20; // кубиков по горизонтали
    public static int maxY = 28; // кубиков по вертикали
    public static float dotPerBoxX, dotPerBoxY = 0; // точек в кубике
    private int screenX, screenY; // поля размеров экрана по осям X и Y
    private boolean play = true;
    private Thread gameThread = null;
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    private ArrayList<Mouse> mouses = new ArrayList<>(); // мышехранилище
    private final int MOUSE_DELAY = 50; // интервал появления мышей
    private int currentTime = 0;
    protected Bitmap cheeseBitmap, catBitmap; //неменяемые картинки - сырный фон, котик
    protected static float catSize = 5;
    private Cat cat;
    private int count;
    private int delay;
    private Context context;
    public static boolean moveLeft,moveRight = false;

    public GameView(Context context, int screenX, int screenY) {
        super(context);
        this.context = context;
        this.screenX = screenX;
        this.screenY = screenY;
        dotPerBoxX = screenX/maxX; // вычисляем число пикселей в юните
        dotPerBoxY = screenY/maxY;

        catBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cat);
        catBitmap = Bitmap.createScaledBitmap(catBitmap, (int)(catSize * dotPerBoxX), (int)(catSize * dotPerBoxY), false);
        //catBitmap.recycle(); java.lang.RuntimeException: Canvas: trying to use a recycled bitmap android.graphics.Bitmap@e268930

        cat = new Cat(getContext()); // добавляем сущность котика

        surfaceHolder = getHolder();


        paint = new Paint();
        gameThread = new Thread(this);
        gameThread.start();

        cheeseBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cheese);
        //unitW = surfaceHolder.getSurfaceFrame().width()/maxX; // тут нихрена не работает даёт 0
        //unitH = surfaceHolder.getSurfaceFrame().height()/maxY;
        //catBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.telescope);
        //catBitmap = Bitmap.createScaledBitmap(catBitmap, (int)(catSize * unitW), (int)(catSize * unitH), false);
        //catBitmap.recycle();
       // cat = new Cat(getContext()); // добавляем сущность котика

    }


    @Override
    public void run() {
        while (true) {
            if (play) {
                if (cat != null) {
                    cat.update();
                    for (Mouse m : mouses) {
                        if (!m.isDead()) {
                            m.update();
                        }
                    }
                }
                draw();
                checkCollision();

                if (currentTime >= MOUSE_DELAY) {
                    Mouse mouse = new Mouse(getContext());
                    mouses.add(mouse);
                    currentTime = 0;
                } else {
                    currentTime++;
                }

            } else {

                delay++;
                if (delay==150) {
                    Itog.music.stop();
                    Intent it = new Intent(context.getApplicationContext(), MainActivity.class);
                    context.startActivity(it);
                    break;
                }
            }

            try {
                gameThread.sleep(17); // пауза на 17 миллисекунд
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (event.getX() < (screenX / 2)) {
                    moveLeft = true;
                    moveRight = false;
                } else {
                    moveRight = true;
                    moveLeft = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;

            case MotionEvent.ACTION_UP:
                moveLeft = false;
                moveRight = false;
                break;
        }

        return true;
    }



    private void draw() {
        if (surfaceHolder.getSurface().isValid()) {  //проверяем валидный ли surface
           /* if(cat==null) { // костыль с тупой surfaceHolder
                dotPerBoxX = surfaceHolder.getSurfaceFrame().width()/maxX; // вычисляем число пикселей в юните
                dotPerBoxY = surfaceHolder.getSurfaceFrame().height()/maxY;

                catBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cat);
                catBitmap = Bitmap.createScaledBitmap(catBitmap, (int)(catSize * dotPerBoxX), (int)(catSize * dotPerBoxY), false);
                //catBitmap.recycle(); java.lang.RuntimeException: Canvas: trying to use a recycled bitmap android.graphics.Bitmap@e268930

                cat = new Cat(getContext()); // добавляем сущность котика
            }*/
            canvas = surfaceHolder.lockCanvas(); // закрываем canvas
            canvas.drawBitmap(cheeseBitmap, 0, 0, null); //рисуем сыр на фоне

            canvas.drawBitmap(catBitmap, cat.x*dotPerBoxX, cat.y*dotPerBoxY, paint);// рисуем котика

            for(Mouse m: mouses){ // рисуем мышек
                if (m.isDead()) continue;
                canvas.drawBitmap(m.mouse, m.x*dotPerBoxX, m.y*dotPerBoxY, paint);
            }

            surfaceHolder.unlockCanvasAndPost(canvas); // открываем canvas
        }
    }



    private void checkCollision(){ // перебираем мышей на коллизию с котиком
        for (Mouse mouse : mouses) {
            if (mouse.isDead()) continue;
            if(mouse.isCollision(cat.x, cat.y, catSize)) { //мышку сьел

                mouse.dead();
                count++;
                Itog.counter.setText(""+count);
                Itog.kill.start();

            } else if (mouse.isFree()) {
                play = false; // останавливаем игру

                canvas = surfaceHolder.lockCanvas();
                Bitmap end;
                if (count>0) {
                    end = BitmapFactory.decodeResource(getResources(), R.drawable.win);
                    end = Bitmap.createScaledBitmap(end, (int)(15 * dotPerBoxX), (int)(12 * dotPerBoxY), false);
                    canvas.drawBitmap(end, (int)(2 * dotPerBoxX), (int)(10 * dotPerBoxY), null);
                } else {
                    end = BitmapFactory.decodeResource(getResources(), R.drawable.looz);
                    end = Bitmap.createScaledBitmap(end, (int)(15 * dotPerBoxX), (int)(12 * dotPerBoxY), false);
                    canvas.drawBitmap(end, (int)(2 * dotPerBoxX), (int)(10 * dotPerBoxY), null);
                }
                surfaceHolder.unlockCanvasAndPost(canvas);

                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast toast = Toast.makeText(context.getApplicationContext(),"Игра окончена \nПоймано мышей: "+count, Toast.LENGTH_LONG);
                        //ImageView cat = new ImageView(context);
                        //toast.setView(cat);
                        //toast.setGravity(Gravity.CENTER , 0,0);
                        toast.show();
                    }
                });
            }
        }
    }



}
