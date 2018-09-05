package com.example.lucasgaleano.buttonexample;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class MyView extends View {

    Paint paint;
    Path path;

    public MyView(Context context) {
        super(context);
        init();
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init(){
        paint = new Paint();

        paint.setStyle(Paint.Style.STROKE);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);


        path = new Path();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(3);
        path.moveTo(50, 50);
        path.cubicTo(300, 50, 100, 400, 400, 400);
        canvas.drawPath(path, paint);

        path.reset();
        paint.setColor(Color.GRAY);
        paint.setStrokeWidth(1);
        path.moveTo(50, 50);
        path.lineTo(300, 50);
        path.lineTo(100, 400);
        path.lineTo(400, 400);

        canvas.drawPath(path, paint);

    }

}
