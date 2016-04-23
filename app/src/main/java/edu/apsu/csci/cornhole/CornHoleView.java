package edu.apsu.csci.cornhole;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by Frank on 4/18/2016.
 */
public class CornHoleView extends View {
    private int currentWidth;
    private int currentHeight;

    private int round;

    private float dpiHeightPixels;
    private float dpiWidthPixels;
    private float dpiHolePixels;
    private float dpiBagPixels;


    private Paint boardPaint;
    private Paint boardHolePaint;
    private Paint bagPaint;
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

        canvas.drawRect((float)(getLeft() + (getRight() - getLeft()) / 2.5), getHeight() - 1 - dpiBagPixels
                , (float) (getLeft() + (getRight() - getLeft()) / 2.5 + dpiBagPixels),
                getHeight() - 1, bagPaint);

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



}