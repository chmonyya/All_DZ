package ru.sinergy.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import java.util.ArrayList;

public class GameView extends SurfaceView implements Runnable {

    public static int maxX = 20; // кубиков по горизонтали
    public static int maxY = 28; // кубиков по вертикали
    public static float dotPerBoxX, dotPerBoxY = 0; // точек в кубике

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

    public GameView(Context context) {
        super(context);
        this.context = context;
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


    private void draw() {
        if (surfaceHolder.getSurface().isValid()) {  //проверяем валидный ли surface

            if(cat==null) { // костыль с тупой surfaceHolder
                dotPerBoxX = surfaceHolder.getSurfaceFrame().width()/maxX; // вычисляем число пикселей в юните
                dotPerBoxY = surfaceHolder.getSurfaceFrame().height()/maxY;

                catBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cat);
                catBitmap = Bitmap.createScaledBitmap(catBitmap, (int)(catSize * dotPerBoxX), (int)(catSize * dotPerBoxY), false);
                //catBitmap.recycle(); java.lang.RuntimeException: Canvas: trying to use a recycled bitmap android.graphics.Bitmap@e268930

                cat = new Cat(getContext()); // добавляем сущность котика
            }

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

            } else if (mouse.isFree()) {
                play = false; // останавливаем игру
                //Itog.endGame(count);
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast toast = Toast.makeText(context.getApplicationContext(),"Игра окончена \nПоймано мышей: "+count, Toast.LENGTH_LONG);
                        //ImageView cat = new ImageView(context);
                        //toast.setView(cat);
                        //toast.setGravity(Gravity.LEFT | Gravity.TOP,  55, y - 100);
                        toast.show();
                    }
                });
            }
        }
    }



}
