package com.porcelani.rastreador;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.TextView;

import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView tvStatus;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvStatus = (TextView) findViewById(R.id.tvStatus);
        inicia();
    }

    private void inicia() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000,
                10.0f, new LocationListener() {
                    public void onStatusChanged(String provider, int status, Bundle extras) {
                    }

                    public void onProviderEnabled(String provider) {
                    }

                    public void onProviderDisabled(String provider) {
                    }

                    public void onLocationChanged(Location location) {
                        double latitute = location.getLatitude();
                        double longitude = location.getLongitude();

                        TextView et = (TextView) findViewById(R.id.tvCoordenadas);
                        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                        String deviceId = telephonyManager.getDeviceId();
                        et.setText("" + latitute + " " + longitude);
                        Enviador enviador = new Enviador();
                        enviador.execute(deviceId, "" + latitute, "" + longitude);

                    }

                });

    }
    class Enviador extends AsyncTask<String, String, String> {

        @Override
        protected void onPostExecute(String s) {
            tvStatus.setText(tvStatus.getText() + "\n" + s);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            tvStatus.setText(tvStatus.getText() + "\n" + values[0]);
        }

        @Override
        protected String doInBackground(String... params) {
            publishProgress("Inicio");
            try {
                URL url = new URL("http://www.munif.com.br/rastreador/registra?nom=" + params[0] + "&lat=" + params[1] + "&lon=" + params[2]);

                // http://www.munif.com.br/rastreador/registra
                publishProgress("Conectando");
                HttpURLConnection urc = (HttpURLConnection) url.openConnection();
                urc.connect();

                publishProgress("Conectado " + urc.getResponseCode());
                urc.disconnect();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                return "Problema " + e.toString();
            }
            return "Sucesso";
        }
    }
}
