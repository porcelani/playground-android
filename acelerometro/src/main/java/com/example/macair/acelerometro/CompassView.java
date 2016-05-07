package com.example.macair.acelerometro;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

/**
 * Created by Edlaine on 07/05/16.
 */
public class CompassView extends View{

    private Paint paint;
    private Path path;
    private float			azimuth = 0;
    private final double	PI		= 4.0 * Math.atan (1);

    public CompassView (Context context) {
        super (context);
        init ();
    }

    private void init () {

        setBackgroundColor (Color.rgb (0x7F, 0xFF, 0xD4));

        paint = new Paint ();
        paint.setAntiAlias (true);
        paint.setTextSize (25);

        path = new Path ();
        path.setFillType (Path.FillType.EVEN_ODD);
    }

    @Override
    protected void onDraw (Canvas canvas) {

        int xc = getMeasuredWidth () / 2;
        int yc = getMeasuredHeight() / 2;

        float radius = (float)(Math.max (xc, yc) * 0.7);

        paint.setStyle (Paint.Style.FILL);
        paint.setColor (Color.rgb (0x99, 0xCC, 0xFF));
        paint.setStrokeWidth (5);

        path.reset();
        path.addCircle (xc, yc, radius-3, Path.Direction.CCW);
        path.close ();

        canvas.drawPath (path, paint);

        paint.setStyle (Paint.Style.FILL);
        paint.setColor (Color.BLUE);
        paint.setStrokeWidth (1);

        path.reset();
        path.addCircle (xc, yc, 3, Path.Direction.CCW);
        path.close ();

        canvas.drawPath (path, paint);

        float xp = (float)(xc + (radius - 8) * Math.sin ((double)(-azimuth) / 180.0 * PI));
        float yp = (float)(yc - (radius - 8) * Math.cos ((double)(-azimuth) / 180.0 * PI));

        paint.setStyle (Paint.Style.STROKE);
        paint.setColor (Color.BLUE);

        paint.setStrokeWidth (1);
        canvas.drawCircle (xc, yc, radius, paint);
        canvas.drawCircle (xc, yc, radius - 3, paint);
        canvas.drawCircle (xc, yc, radius + 3, paint);

        paint.setStrokeWidth (2);
        canvas.drawLine (2 * xc - xp, 2 * yc - yp, xp, yp, paint);

        float xo = xp + (xc - xp) / 5,	yo = yp + (yc - yp) / 5;

        float X  = yc - yp,				Y  = xp - xc, norm = (float)Math.sqrt (X * X + Y * Y);
        float xv = 8 * X / norm,		yv = 8 * Y / norm;
        float x1 = xo + xv,				x2 = xo - xv;
        float y1 = yo + yv,				y2 = yo - yv;

        path.reset ();
        path.moveTo (xp, yp);
        path.lineTo (x1, y1);
        path.lineTo (x2, y2);
        path.lineTo (xp, yp);
        path.close();

        paint.setStyle (Paint.Style.FILL);
        paint.setStrokeWidth (1);

        canvas.drawPath (path, paint);
    }

    public void updateData (float azimuth)
    {
        this.azimuth = azimuth;
        invalidate ();
    }
}
