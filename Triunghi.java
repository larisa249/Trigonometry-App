package com.example.trigo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class Triunghi extends View {
    private Paint paint, textPaint;

    public Triunghi(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Triunghi(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public Triunghi(Context context) {
        super(context);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(0xFF000000);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(8);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(0xFFFFFFFF);
        textPaint.setTextSize(80);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        Path path = new Path();
        path.moveTo(10, 10);
        path.lineTo(10, height - 10);
        path.lineTo(width - 10, height - 10);
        path.close();


        canvas.drawPath(path, paint);

        canvas.drawText("A", 20, 80, textPaint);
        canvas.drawText("B", 20, height - 20, textPaint);
        canvas.drawText("C", width - 40, height - 20, textPaint);
    }
}
