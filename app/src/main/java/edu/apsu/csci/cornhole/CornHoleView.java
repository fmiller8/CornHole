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

/**
 * Created by Frank on 4/18/2016.
 */
public class CornHoleView extends View implements View.OnTouchListener {

    private Handler handler;
    private AnimationRunnable animationRunnable;



    private int power;
    private float direction;

    private float dpiHeightPixels;
    private float dpiWidthPixels;
    private float dpiHolePixels;
    private float dpiBagPixels;


    private Paint boardPaint;
    private Paint boardHolePaint;
    private Paint bagPaint;
    public float bagX1,bagY1;


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

        bagPaint = new Paint();
        bagPaint.setColor(Color.BLACK);
        bagPaint.setStyle(Paint.Style.FILL);



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

        canvas.drawRect(bagX1, bagY1, bagX1 + dpiBagPixels, bagY1+dpiBagPixels, bagPaint);

        Log.i("onDraw ", "getLeft() " + getLeft());
        Log.i("Setup","getRight()"+ getRight());

        Log.i("onDraw ", "X1 " + String.valueOf((float) (getLeft() + (getRight() - getLeft()) / 2.5)));
        Log.i("onDraw ","X2 " + (getHeight() - 1 - dpiBagPixels));
        Log.i("onDraw ","Y1 " + (float) (getLeft() + (getRight() - getLeft()) / 2.5 + dpiBagPixels));
        Log.i("onDraw ","Y2 " + (getHeight() - 1));

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);


        bagX1 = (float)(getLeft() + (getRight() - getLeft()) / 2.5);
        bagY1 = getHeight() - 1 - dpiBagPixels;




        Log.i("Setup","bagX1 "+ bagX1);


        Log.i("Setup ", "getLeft() "+getLeft());
        Log.i("Setup","getRight()"+ getRight());
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
//
    public void stop() {
        if (handler != null) {
            handler = null;
            animationRunnable = null;
        }
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

            Log.i("Direction", "Direction is " + direction);

            start();

        }


    }

    private int temp = 0;
    public class AnimationRunnable implements Runnable{



        @Override
        public void run() {

            bagX1+=direction;
            bagY1=bagY1-5;
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



}