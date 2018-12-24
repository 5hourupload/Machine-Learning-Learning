package fhu.machinelearninglearning;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

public class MyDrawable extends Drawable {
    private final Paint mRedPaint;
    public static int width, height;

    public MyDrawable() {
        // Set up color and text size
        mRedPaint = new Paint();
        mRedPaint.setARGB(255,255,255,255);
    }

    @Override
    public void draw(Canvas canvas) {
        width = getBounds().width();
        height = getBounds().height();
        // Draw a red circle in the center
        canvas.drawPoint(100,100,mRedPaint);
    }

    @Override
    public void setAlpha(int alpha) {
        // This method is required
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        // This method is required
    }

    @Override
    public int getOpacity() {
        // Must be PixelFormat.UNKNOWN, TRANSLUCENT, TRANSPARENT, or OPAQUE
        return PixelFormat.OPAQUE;
    }
}
