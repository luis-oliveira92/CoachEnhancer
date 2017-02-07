package com.example.hadi.coachenhancer;
/**
 * Created by Hadi on 12/19/2016.
 */
import android.graphics.Canvas;
import android.graphics.Paint;

public class ConnectPoint extends Point {

    private final Point neighbour;

    public ConnectPoint(final float x, final float y, final int col, final Point neighbour, final int width) {
        super(x, y, col, width);
        this.neighbour = neighbour;
    }

    @Override
    public void draw(final Canvas canvas, final Paint paint) {
        paint.setColor(col);
        paint.setStrokeWidth(width);
        canvas.drawLine(x, y, neighbour.x, neighbour.y, paint);
        // experimenting with smoother drawing --
        // the circle should hopefully act as a smoothing hinge between line segments
        // o----o----o------o
        canvas.drawCircle(x, y, width/2, paint);
    }

    @Override
    public String toString() {
        return x + ", " + y + ", " + col + "; N[" + neighbour.toString() + "]";
    }
}

