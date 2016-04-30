package com.porcelani.calculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import static java.lang.Float.valueOf;

public class Calculator extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        //set action on create
        findViewById(R.id.btSub).setOnClickListener(this);

    }
    public void subtrac(View view) {
        EditText result = getResult();
        result.setText(String.valueOf(getOperator1() - getOperator2()));
    }

    @Override
    public void onClick(View v) {
        subtrac(v);
    }

     //or define action on button >>>>>  android:onClick="somar"/>
    public void somar(View view) {
        EditText result = getResult();
        result.setText(String.valueOf(getOperator1() + getOperator2()));
    }

    private EditText getResult() {
        return (EditText) findViewById(R.id.etResultado);
    }

    private Float getOperator2() {
        EditText op2 = (EditText) findViewById(R.id.etOperador2);
        return valueOf(op2.getText().toString());
    }

    private Float getOperator1() {
        EditText op1 = (EditText) findViewById(R.id.etOperador1);
        return valueOf(op1.getText().toString());
    }


}
