package fhu.machinelearninglearning;

import android.graphics.Paint;

public class point
{
    private int x, y;
    private Paint paint;

    point(int x, int y, Paint paint)
    {
        this.x = x;
        this.y = y;
        this.paint = paint;
    }

    point(int x, int y)
    {
        this.x = x;
        this.y = y;
        Paint defaultPaint = new Paint();
        defaultPaint.setColor(-16777216);
        this.paint = defaultPaint;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public Paint getPaint()
    {
        return paint;
    }

    public void setPaint(Paint paint)
    {
        this.paint = paint;
    }
}