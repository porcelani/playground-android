package com.example.macair.acelerometro;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

    private SensorManager sensorManager;
    private ListView listview;
    private SensorInfo[]			sensorList;
    private SensorAdapter			adapter;
    private SparseArray<SensorInfo> map;
    private Sensor sensorOrientation;


    private CompassView compassView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService (SENSOR_SERVICE);

        List<Sensor> list = sensorManager.getSensorList (Sensor.TYPE_ALL);

        sensorList = new SensorInfo[list.size()];
        map	= new SparseArray<> ();

        for (int i = 0; i < list.size(); i++) {
            Sensor sensor = list.get(i);
            SensorInfo sensorInfo = new SensorInfo (sensor);
            sensorList[i] = sensorInfo;
            map.put (sensor.getType(), sensorInfo);
        }

        adapter	= new SensorAdapter (this, sensorList);
        listview = (ListView)findViewById (R.id.listView);

        listview.setAdapter (adapter);

//        Utilização de um sensor para criar bússola
//        sensorOrientation = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
//        compassView = new CompassView (this);
//        setContentView (compassView);
//        sensorManager.registerListener (mySensorEventListener, sensorOrientation, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private SensorEventListener mySensorEventListener = new SensorEventListener ()
    {
        @Override
        public void onAccuracyChanged (Sensor sensor, int accuracy) {
        }

        @Override
        public void onSensorChanged (SensorEvent event) {
            compassView.updateData (event.values[0]);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        for (SensorInfo si : sensorList)
            sensorManager.registerListener (this, si.getSensor(), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        SensorInfo si = map.get (event.sensor.getType());

        if (si != null && si.setValues (event.values))
            adapter.notifyDataSetChanged();

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }


}



//class MyCompassView extends View {

//    private Paint paint;
//    private Path path;
//    private float			azimuth = 0;
//    private final double	PI		= 4.0 * Math.atan (1);
//
//    public MyCompassView (Context context) {
//        super (context);
//        init ();
//    }
//
//    private void init () {
//
//        setBackgroundColor (Color.rgb (0x7F, 0xFF, 0xD4));
//
//        paint = new Paint ();
//        paint.setAntiAlias (true);
//        paint.setTextSize (25);
//
//        path = new Path ();
//        path.setFillType (Path.FillType.EVEN_ODD);
//    }
//
//    @Override
//    protected void onDraw (Canvas canvas) {
//
//        int xc = getMeasuredWidth () / 2;
//        int yc = getMeasuredHeight() / 2;
//
//        float radius = (float)(Math.max (xc, yc) * 0.7);
//
//        paint.setStyle (Paint.Style.FILL);
//        paint.setColor (Color.rgb (0x99, 0xCC, 0xFF));
//        paint.setStrokeWidth (5);
//
//        path.reset();
//        path.addCircle (xc, yc, radius-3, Path.Direction.CCW);
//        path.close ();
//
//        canvas.drawPath (path, paint);
//
//        paint.setStyle (Paint.Style.FILL);
//        paint.setColor (Color.BLUE);
//        paint.setStrokeWidth (1);
//
//        path.reset();
//        path.addCircle (xc, yc, 3, Path.Direction.CCW);
//        path.close ();
//
//        canvas.drawPath (path, paint);
//
//        float xp = (float)(xc + (radius - 8) * Math.sin ((double)(-azimuth) / 180.0 * PI));
//        float yp = (float)(yc - (radius - 8) * Math.cos ((double)(-azimuth) / 180.0 * PI));
//
//        paint.setStyle (Paint.Style.STROKE);
//        paint.setColor (Color.BLUE);
//
//        paint.setStrokeWidth (1);
//        canvas.drawCircle (xc, yc, radius, paint);
//        canvas.drawCircle (xc, yc, radius - 3, paint);
//        canvas.drawCircle (xc, yc, radius + 3, paint);
//
//        paint.setStrokeWidth (2);
//        canvas.drawLine (2 * xc - xp, 2 * yc - yp, xp, yp, paint);
//
//        float xo = xp + (xc - xp) / 5,	yo = yp + (yc - yp) / 5;
//
//        float X  = yc - yp,				Y  = xp - xc, norm = (float)Math.sqrt (X * X + Y * Y);
//        float xv = 8 * X / norm,		yv = 8 * Y / norm;
//        float x1 = xo + xv,				x2 = xo - xv;
//        float y1 = yo + yv,				y2 = yo - yv;
//
//        path.reset ();
//        path.moveTo (xp, yp);
//        path.lineTo (x1, y1);
//        path.lineTo (x2, y2);
//        path.lineTo (xp, yp);
//        path.close();
//
//        paint.setStyle (Paint.Style.FILL);
//        paint.setStrokeWidth (1);
//
//        canvas.drawPath (path, paint);
//    }
//
//    public void updateData (float azimuth)
//    {
//        this.azimuth = azimuth;
//        invalidate ();
//    }
//}

