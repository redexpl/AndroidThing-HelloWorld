package com.example.alessandro.androidthing_helloworld;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManager;

import java.io.IOException;


public class MainActivity extends Activity implements View.OnClickListener {

    private Button mButton;
    private Gpio mLedGpio;
    private boolean ledState = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //take button instance
        mButton = (Button) findViewById(R.id.button);
        try {
            //take gpio led pin instance
            mLedGpio = PeripheralManager.getInstance().openGpio("BCM6");
            //set gpio pin for output
            mLedGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
        } catch (IOException e) {
            mLedGpio = null;
        }
        //add on click behaviour
        mButton.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            mLedGpio.close();
        } catch (IOException e) {
        } finally {
            mLedGpio=null;
        }
    }

    @Override
    public void onClick(View v) {
        if(mButton.equals(v)) {
            //if button was clicked
            if(mLedGpio != null) {
                //if instance of gpio for controlling the led is valid
                try {
                    //switch led current state
                    ledState = !ledState;
                    mLedGpio.setValue(ledState);
                } catch (IOException e) {
                }
            }
        }
    }
}
