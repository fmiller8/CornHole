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
    private int currentWidth;
    private int currentHeight;



    private Handler handler;
    private AnimationRunnable animationRunnable;


    private int round;
    private int power;
    private int direction;

    private float dpiHeightPixels;
    private float dpiWidthPixels;
    private float dpiHolePixels;
    private float dpiBagPixels;


    private Paint boardPaint;
    private Paint boardHolePaint;
    private Paint bagPaint;
    public float bagX1,bagY1,bagX2,bagY2;
    //private Paint backgroundPaint;

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

       /* backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.WHITE);
        backgroundPaint.setStyle(Paint.Style.FILL);*/

        bagX1 = (float)(getLeft() + (getRight() - getLeft()) / 2.5);
        bagY1 = getHeight() - 1 - dpiBagPixels;
        bagX2 = (float) (getLeft() + (getRight() - getLeft()) / 2.5 + dpiBagPixels);
        bagY2 = getHeight() - 1;



    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        //canvas.drawPaint(backgroundPaint);
        canvas.drawRect(getLeft()+(getRight()-getLeft())/4,  getTop()+(getBottom()-getTop())/200
                ,getLeft()+(getRight()-getLeft())/4+dpiWidthPixels,getTop()+(getBottom()-getTop())/200+dpiHeightPixels,boardPaint);

        canvas.drawOval((float) (getLeft() + (getRight() - getLeft()) / 2.5), getTop() + (getBottom() - getTop()) / 40
                , (float) (getLeft() + (getRight() - getLeft()) / 2.5 + dpiHolePixels),
                getTop() + (getBottom() - getTop()) / 40 + dpiHolePixels, boardHolePaint);

        canvas.drawRect(bagX1, bagY1, bagX2, bagY2, bagPaint);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        currentWidth = w;
        currentHeight = h;
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
    }

    public float downX,downY,upX,upY;

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



                break;
            case MotionEvent.ACTION_CANCEL:
                break;

        }

        if (downX >= getLeft() + (getRight() - getLeft()) / 2.5 && downX <= getLeft() + (getRight() - getLeft()) / 2.5 + dpiBagPixels
                && downY >= getHeight() - 1 - dpiBagPixels && downY <= getHeight() - 1 ) {

            power = (int) (downY - upY);

            Log.i("Power", "Power is " + power);

            if (downX > upX) {
                direction = (int) (downX - upX);
            } else if (downX < upX) {
                direction = (int) (upX - downX);
            }

            Log.i("Direction", "Direction is" + direction);

            start();

        }

        return true;
    }


    public class AnimationRunnable implements Runnable{

        @Override
        public void run() {

        }
    }



}