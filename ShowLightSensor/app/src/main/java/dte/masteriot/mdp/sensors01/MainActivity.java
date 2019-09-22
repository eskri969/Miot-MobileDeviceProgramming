package dte.masteriot.mdp.sensors01;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.SharedPreferences;
import android.util.Log;


public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;

    Button bLight;
    TextView tvSensorValueLight;
    private Sensor lightSensor;
    boolean lightSensorIsActive;

    Button bAccel;
    TextView tvSensorValueAccel;
    private Sensor accelSensor;
    boolean accelSensorIsActive;

    Button bStep;
    TextView tvSensorValueStep;
    private Sensor stepSensor;
    boolean stepSensorIsActive;
    int nSteps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lightSensorIsActive = false;

        // Get the references to the UI:
        bLight = findViewById(R.id.bLight); // button to start/stop sensor's readings
        tvSensorValueLight = findViewById(R.id.lightMeasurement); // sensor's values

        // Get the reference to the sensor manager and the sensor:
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        // Obtain the reference to the default light sensor of the device:
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        // Listener for the button:
        bLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lightSensorIsActive) {
                    // unregister listener and make the appropriate changes in the UI:
                    sensorManager.unregisterListener(MainActivity.this, lightSensor);
                    bLight.setText(R.string.light_sensor_off);
                    bLight.setBackground(getResources().getDrawable(R.drawable.round_button_off, getTheme()));
                    tvSensorValueLight.setText("Light sensor is OFF");
                    lightSensorIsActive = false;
                } else {
                    // register listener and make the appropriate changes in the UI:
                    sensorManager.registerListener(MainActivity.this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
                    bLight.setText(R.string.light_sensor_on);
                    bLight.setBackground(getResources().getDrawable(R.drawable.round_button_on, getTheme()));
                    tvSensorValueLight.setText("Waiting for first light sensor value");
                    lightSensorIsActive = true;
                }
            }
        });


        // Get the references to the UI:
        bAccel = findViewById(R.id.bAccel); // button to start/stop sensor's readings
        tvSensorValueAccel = findViewById(R.id.AccelerometerMeasurement); // sensor's values

        // Get the reference to the sensor manager and the sensor:
        //sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        // Obtain the reference to the default light sensor of the device:
        accelSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // Listener for the button:
        bAccel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (accelSensorIsActive) {
                    // unregister listener and make the appropriate changes in the UI:
                    sensorManager.unregisterListener(MainActivity.this, accelSensor);
                    bAccel.setText(R.string.accel_sensor_off);
                    bAccel.setBackground(getResources().getDrawable(R.drawable.round_button_off, getTheme()));
                    tvSensorValueAccel.setText("Accelerometer sensor is OFF");
                    accelSensorIsActive = false;
                } else {
                    // register listener and make the appropriate changes in the UI:
                    sensorManager.registerListener(MainActivity.this, accelSensor, SensorManager.SENSOR_DELAY_NORMAL);
                    bAccel.setText(R.string.accel_sensor_on);
                    bAccel.setBackground(getResources().getDrawable(R.drawable.round_button_on, getTheme()));
                    tvSensorValueAccel.setText("Waiting for first accelerometer sensor value");
                    accelSensorIsActive = true;
                }
            }
        });


        nSteps=0;
        bStep = findViewById(R.id.bStep); // button to start/stop sensor's readings
        tvSensorValueStep = findViewById(R.id.StepMeasurement); // sensor's values

        // Get the reference to the sensor manager and the sensor:
        //sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        // Obtain the reference to the default light sensor of the device:
        stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);

        // Listener for the button:
        bStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (stepSensorIsActive) {
                    // unregister listener and make the appropriate changes in the UI:
                    sensorManager.unregisterListener(MainActivity.this, stepSensor);
                    bStep.setText(R.string.step_sensor_off);
                    bStep.setBackground(getResources().getDrawable(R.drawable.round_button_off, getTheme()));
                    tvSensorValueStep.setText("Step sensor is OFF");
                    stepSensorIsActive = false;
                } else {
                    // register listener and make the appropriate changes in the UI:
                    sensorManager.registerListener(MainActivity.this, stepSensor, SensorManager.SENSOR_DELAY_NORMAL);
                    bStep.setText(R.string.step_sensor_on);
                    bStep.setBackground(getResources().getDrawable(R.drawable.round_button_on, getTheme()));
                    tvSensorValueStep.setText("Waiting for first step sensor value");
                    stepSensorIsActive = true;
                }
            }
        });
    }

    // Methods related to the SensorEventListener interface:

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        // Show the sensor's value in the UI:
        switch (sensorEvent.sensor.getType()) {

            case android.hardware.Sensor.TYPE_LIGHT:
                tvSensorValueLight.setText(Float.toString(sensorEvent.values[0]));
                break;

            case android.hardware.Sensor.TYPE_ACCELEROMETER:
                tvSensorValueAccel.setText("x = " + Float.toString(sensorEvent.values[0]) +
                        "\ny = " + Float.toString(sensorEvent.values[1]) +
                        "\nz = " + Float.toString(sensorEvent.values[2]));
                break;

            case Sensor.TYPE_STEP_DETECTOR:
                Log.d("STEP", "step_detected");
                nSteps++;
                tvSensorValueStep.setText("number of steps = "+nSteps);
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putBoolean("lightSensorIsActive", lightSensorIsActive);

        editor.putBoolean("accelSensorIsActive", accelSensorIsActive);

        editor.putBoolean("stepSensorIsActive", stepSensorIsActive);

        editor.commit();




    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        lightSensorIsActive = sharedPref.getBoolean("lightSensorIsActive", false);
        accelSensorIsActive = sharedPref.getBoolean("accelSensorIsActive", false);
        stepSensorIsActive = sharedPref.getBoolean("stepSensorIsActive", false);

        if(lightSensorIsActive){
            sensorManager.registerListener(MainActivity.this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
            bLight.setText(R.string.light_sensor_on);
            bLight.setBackground(getResources().getDrawable(R.drawable.round_button_on, getTheme()));
            tvSensorValueLight.setText("Waiting for first light sensor value");
            lightSensorIsActive = true;
        }
        else{
            sensorManager.unregisterListener(MainActivity.this, lightSensor);
            bLight.setText(R.string.light_sensor_off);
            bLight.setBackground(getResources().getDrawable(R.drawable.round_button_off, getTheme()));
            tvSensorValueLight.setText("Light sensor is OFF");
            lightSensorIsActive = false;
        }
        if(accelSensorIsActive){
            sensorManager.registerListener(MainActivity.this, accelSensor, SensorManager.SENSOR_DELAY_NORMAL);
            bAccel.setText(R.string.accel_sensor_on);
            bAccel.setBackground(getResources().getDrawable(R.drawable.round_button_on, getTheme()));
            tvSensorValueAccel.setText("Waiting for first accelerometer sensor value");
            accelSensorIsActive = true;
        }
        else{
            sensorManager.unregisterListener(MainActivity.this, accelSensor);
            bAccel.setText(R.string.accel_sensor_off);
            bAccel.setBackground(getResources().getDrawable(R.drawable.round_button_off, getTheme()));
            tvSensorValueAccel.setText("Accelerometer sensor is OFF");
            accelSensorIsActive = false;
        }
        if(stepSensorIsActive){
            sensorManager.registerListener(MainActivity.this, stepSensor, SensorManager.SENSOR_DELAY_NORMAL);
            bStep.setText(R.string.step_sensor_on);
            bStep.setBackground(getResources().getDrawable(R.drawable.round_button_on, getTheme()));
            tvSensorValueStep.setText("Waiting for first step sensor value");
            stepSensorIsActive = true;
        }
        else{
            sensorManager.unregisterListener(MainActivity.this, stepSensor);
            bStep.setText(R.string.step_sensor_off);
            bStep.setBackground(getResources().getDrawable(R.drawable.round_button_off, getTheme()));
            tvSensorValueStep.setText("Step sensor is OFF");
            stepSensorIsActive = false;
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        // In this app we do nothing if sensor's accuracy changes
    }


}
