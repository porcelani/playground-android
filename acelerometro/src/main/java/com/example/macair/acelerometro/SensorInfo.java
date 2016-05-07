package com.example.macair.acelerometro;

import android.hardware.Sensor;

/**
 * Created by Edlaine on 07/05/16.
 */
public class SensorInfo {

    private Sensor		sensor;
    private int			delta		= 1000;
    private long		lastTime	= 0;
    private float[]		values		= {0, 0, 0};

    SensorInfo (Sensor sensor) {
        this.sensor = sensor;
    }

    Sensor getSensor () {
        return sensor;
    }

    void setDelta (int delta) {
        this.delta = delta;
    }

    boolean setValues (float[] values) {
        long tempo = System.currentTimeMillis();

        if (tempo - lastTime <= delta) {
            return false;
        }

        this.values[0]	= values[0];
        this.values[1]	= values[1];
        this.values[2]	= values[2];

        lastTime = tempo;

        return true;
    }

    float getValue (int i) {
        return (i >= 0 && i < values.length) ? values[i] : Float.NaN;
    }

    String getName () {
        return sensor.getName();
    }
}
