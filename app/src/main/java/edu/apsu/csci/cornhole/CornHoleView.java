package edu.apsu.csci.cornhole;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Frank on 4/18/2016.
 */
public class CornHoleView extends View implements View.OnTouchListener {
// jl
    private Handler handler;
    private AnimationRunnable animationRunnable;

    private int player1Score = 0;
    private int player2Score = 0;
    int id =1;

    private int temp = 0;
    private float wind=0;
    float rand;

    private int power;
    private float direction;

    private float dpiHeightPixels;
    private float dpiWidthPixels;
    private float dpiHolePixels;
    private float dpiBagPixels;

    BeanBag bag;
    private Paint boardPaint;
    private Paint boardHolePaint;
    ArrayList<BeanBag> bags = new ArrayList<>();



    public CornHoleView(Context context) {
        super(context);

    }

    public CornHoleView(Context context, AttributeSet attr) {
        super(context, attr);
        setup(attr);

    }

    public CornHoleView(Context context, AttributeSet attr, int defStyleAttr) {
        super(context, attr, defStyleAttr);
        setup(attr);

    }

    private void setup(AttributeSet attr){
        boardPaint = new Paint();
        boardPaint.setColor(Color.BLUE);
        boardPaint.setStyle(Paint.Style.FILL);

        boardHolePaint= new Paint();
        boardHolePaint.setColor(Color.RED);
        boardHolePaint.setStyle(Paint.Style.FILL);

        setBag();
        wind = randomNumber();
        rand = randomNumber();



        float dpiHeight = 200;
        float dpiWidth = 125;
        float dpiHole = 40;
        float dpiBag = 34;

        DisplayMetrics dm = getResources().getDisplayMetrics();
        dpiHeightPixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dpiHeight,dm);
        dpiWidthPixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dpiWidth,dm);
        dpiHolePixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dpiHole,dm);
        dpiBagPixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dpiBag,dm);

        this.setOnTouchListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        canvas.drawRect(getLeft() + (getRight() - getLeft()) / 4, getTop() + (getBottom() - getTop()) / 200
                , getLeft() + (getRight() - getLeft()) / 4 + dpiWidthPixels, getTop() + (getBottom() - getTop()) / 200 + dpiHeightPixels, boardPaint);

        canvas.drawOval((float) (getLeft() + (getRight() - getLeft()) / 2.5), getTop() + (getBottom() - getTop()) / 40
                , (float) (getLeft() + (getRight() - getLeft()) / 2.5 + dpiHolePixels),
                getTop() + (getBottom() - getTop()) / 40 + dpiHolePixels, boardHolePaint);

        canvas.drawRect(bag.getBagX1(), bag.getBagY1(), bag.getBagX1() + dpiBagPixels, bag.getBagY1()+dpiBagPixels, bag.bagPaint);

        for (BeanBag b : bags) {//gets the values out of the arrayList to be drawn
            canvas.drawRect(b.getBagX1(), b.getBagY1(), b.getBagX1() + dpiBagPixels, b.getBagY1()+dpiBagPixels, b.bagPaint);
        }

        Log.i("onDraw ", "getLeft() " + getLeft());
        Log.i("Setup","getRight()"+ getRight());

        Log.i("onDraw ", "X1 " + String.valueOf((float) (getLeft() + (getRight() - getLeft()) / 2.5)));
        Log.i("onDraw ","X2 " + (getHeight() - 1 - dpiBagPixels));
        Log.i("onDraw ", "Y1 " + (float) (getLeft() + (getRight() - getLeft()) / 2.5 + dpiBagPixels));
        Log.i("onDraw ", "Y2 " + (getHeight() - 1));

    }

    private void setBag(){
        float bagX1 = (float)(getLeft() + (getRight() - getLeft()) / 2.5);
        float bagY1 = getHeight() - 1 - dpiBagPixels;



        bag = new BeanBag(bagX1, bagY1);

        switch (id){
            case 1:


                bag.setColor("BLACK");
                if(bags.size() >= 8){
                    bags.clear();
                }
                id++;

                break;
            case 2:
                bag.setColor("CYAN");
                if(bags.size() >= 8){
                    bags.clear();
                }


                id--;

                break;
        }

        power =0;
        direction=0;
        temp=0;

    }





    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);


        float bagX1 = (float)(getLeft() + (getRight() - getLeft()) / 2.5);
        float bagY1 = getHeight() - 1 - dpiBagPixels;

        bag = new BeanBag(bagX1, bagY1);






        Log.i("Setup","bagX1 "+ bagX1);


        Log.i("Setup ", "getLeft() "+getLeft());
        Log.i("Setup", "getRight()" + getRight());
    }

    @Override //Does magical stuff as described by Dr. Nicholson and stack over flow
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int desiredWidth = 100;
        int desiredHeight = 100;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        //Measure Width
        if (widthMode == MeasureSpec.EXACTLY) {
            //Must be this size
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            width = Math.min(desiredWidth, widthSize);
        } else {
            //Be whatever you want
            width = desiredWidth;
        }

        //Measure Height
        if (heightMode == MeasureSpec.EXACTLY) {
            //Must be this size
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            height = Math.min(desiredHeight, heightSize);
        } else {
            //Be whatever you want
            height = desiredHeight;
        }

        //MUST CALL THIS
        setMeasuredDimension(width, height);
    }

    public void start() {
        if (handler == null) {
            handler = new Handler();
            animationRunnable = new AnimationRunnable();
            handler.post(animationRunnable);
        }
    }

    public void stop() {
        if (handler != null) {
            handler = null;
            animationRunnable = null;
        }

        bag = new BeanBag(bag.getBagX1(), bag.getBagY1(),bag.bagPaint);
        if (bag.bagX1 >=(float) (getLeft() + (getRight() - getLeft())) / 2.5 &&
                bag.bagX1 <=(float) (getLeft() + (getRight() - getLeft()) / 2.5) + dpiHolePixels ){
            if (bag.bagY1 >=  getTop() + (getBottom() - getTop()) / 40 && bag.bagY1 <=  getTop() + (getBottom() - getTop()) / 40 + dpiHolePixels){
               // bag.setColor("TRANSPARENT");
                if (id == 1){
                   player2Score += 3;

                    GameActivity gameActivity = new GameActivity();

                            gameActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    GameActivity game2Activity = new GameActivity();
                                    game2Activity.setText(1,player2Score);
                                }
                            });






                }else if (id == 2){
                   player1Score +=3;
                     GameActivity gameActivity = new GameActivity();

                    gameActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            GameActivity game2Activity = new GameActivity();
                          game2Activity.setText(2,player1Score);
                        }
                    });


                }
            }

        }
        bags.add(bag);

        /*canvas.drawRect(getLeft() + (getRight() - getLeft()) / 4, getTop() + (getBottom() - getTop()) / 200
                , getLeft() + (getRight() - getLeft()) / 4 + dpiWidthPixels, getTop() + (getBottom() - getTop()) / 200 + dpiHeightPixels, boardPaint);

       */

        setBag();
        wind = randomNumber();
        rand = randomNumber();




        invalidate();

    }




    float downX,downY,upX,upY;
    @Override
    public boolean onTouch(View v, MotionEvent event) {


        Log.i("onTouch", "In onTouch");
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                Log.i("onTouch", "downX" + downX + ", downY" + downY);
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                upX = event.getX();
                upY = event.getY();
                Log.i("onTouch", "upX" + upX + ", upY" + upY);


                setPowerAndDirection(downX, downY, upX, upY);
                break;
            case MotionEvent.ACTION_CANCEL:
                break;

        }



        return true;
    }

    public void setPowerAndDirection(float dX,float dY, float uX,float uY){
        if (dX >= getLeft() + (getRight() - getLeft()) / 2.5 && dX <= getLeft() + (getRight() - getLeft()) / 2.5 + dpiBagPixels
                && dY >= getHeight() - 1 - dpiBagPixels && dY <= getHeight() - 1 ) {

            power = (int) (dY - uY);

            Log.i("Power", "Power is " + power);

            if (dX > uX) {
                direction = (int) (dX - uX)/20;
                direction = 0-direction;
            } else if (dX < uX) {
                direction = (int) (uX - dX)/20;
            }

           /* if(direction > 0) {
                direction = direction + wind;
            }else if(direction < 0){
                direction = direction - wind;
            }else if (direction ==0){*/

                if (rand > 1.26){
                    direction = direction + wind;
                }else if(rand < 0.63){
                    direction = direction - wind;
                }
         //   }

            Log.i("Direction", "Direction is " + direction);

            start();

        }


    }

    public float randomNumber() {
        float minX = 0.0f;
        float maxX = 1.9f;

        Random random = new Random();
        float randomNum = random.nextFloat() * (maxX -minX) + minX;


        return randomNum;
    }



    public class AnimationRunnable implements Runnable{
        float bagx1,bagy1;




        @Override
        public void run() {
            bagx1=bag.getBagX1();


            Log.i("run()","direction " + direction);
            Log.i("run()","wind " + wind);

            bagx1+=direction;
            bag.setBagX1(bagx1);

            bagy1=bag.getBagY1();
            bagy1=bagy1-5;
            bag.setBagY1(bagy1);


            if (power <= temp){
                stop();
            }
            temp=temp+2;




            postInvalidate();
            if (handler != null) {
                handler.postDelayed(animationRunnable, 1);
            }


        }
    }

    private class BeanBag{


        private float bagX1,bagY1;
        private String color;
        Paint bagPaint = new Paint();

        public float getBagX1() {
            return bagX1;
        }

        public void setBagX1(float bagX1) {
            this.bagX1 = bagX1;
        }

        public float getBagY1() {
            return bagY1;
        }

        public void setBagY1(float bagY1) {
            this.bagY1 = bagY1;
        }



        public void setColor(String color) {
            bagPaint.setColor(Color.parseColor(color));

            this.color = color;
        }



        public BeanBag(float bagX1, float bagY1) {
            this.bagX1 = bagX1;
            this.bagY1 = bagY1;
        }

        public BeanBag(float bagX1, float bagY1, Paint bagPaint) {
            this.bagX1 = bagX1;
            this.bagY1 = bagY1;
            this.bagPaint = bagPaint;
        }
    }



}