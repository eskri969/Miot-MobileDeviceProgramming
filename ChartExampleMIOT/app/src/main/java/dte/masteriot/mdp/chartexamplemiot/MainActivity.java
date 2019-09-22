package dte.masteriot.mdp.chartexamplemiot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;
import com.bendaschel.sevensegmentview.SevenSegmentView;


import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.utils.FileUtils;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.content.Context;
import android.widget.TextView;
import android.util.Log;



public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private List<Entry> mSinusData;
    private SevenSegmentView seg1, seg2, seg3;
    private SensorManager sensorManager;

    private Sensor lightSensor;
    //private boolean lightSensorIsActive;
    private float maxLight;
    private float minLight;
    //private float avgLight;
    private TextView tvMaxValueLight;
    private TextView tvAvgValueLight;
    private TextView tvMinValueLight;
    private float lastLightValue;
    private float totalLighValue;
    private float nLightSamples;
    //private boolean firtstLightSample;
    private LineChart chartLight;
    private List<Entry> lightSamples;
    private Long initialTime;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LineChart lChart = (LineChart) findViewById(R.id.chart);

        // Get the reference to the sensor manager and the sensor:
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        // Obtain the reference to the default light sensor of the device:
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorManager.registerListener(MainActivity.this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        tvMaxValueLight = (TextView) findViewById(R.id.maxLight);
        tvMaxValueLight.setText("---");
        tvMinValueLight = (TextView) findViewById(R.id.minLight);
        tvMinValueLight.setText("---");
        tvAvgValueLight = (TextView) findViewById(R.id.avgLight);
        tvAvgValueLight.setText("---");
        seg1=(SevenSegmentView) findViewById(R.id.display1);
        seg2=(SevenSegmentView) findViewById(R.id.display2);
        seg3=(SevenSegmentView) findViewById(R.id.display3);
        seg1.setCurrentValue(0);
        seg2.setCurrentValue(0);
        seg3.setCurrentValue(0);

        initialTime = System.currentTimeMillis();
        chartLight = (LineChart) findViewById(R.id.chart);
        chartLight.getDescription().setEnabled(false);
        chartLight.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

        lightSamples = new ArrayList<Entry>();
        LineDataSet lightDataSet = new LineDataSet(lightSamples, "Light Measures");
        LineData lineData = new LineData(lightDataSet);
        chartLight.setData(lineData);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        // In this app we do nothing if sensor's accuracy changes
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        lastLightValue = event.values[0];
        nLightSamples++;
        totalLighValue += lastLightValue;
        if (nLightSamples==1) {
            maxLight=lastLightValue;
            tvMaxValueLight.setText(Float.toString(lastLightValue));
            minLight=lastLightValue;
            tvMinValueLight.setText(Float.toString(lastLightValue));
        }
        else {
            if (lastLightValue > maxLight) {
                maxLight = lastLightValue;
                tvMaxValueLight.setText(Float.toString(lastLightValue));
            }
            if (lastLightValue < minLight) {
                minLight = lastLightValue;
                tvMinValueLight.setText(Float.toString(lastLightValue));
            }
        }
        tvAvgValueLight.setText(Float.toString(totalLighValue/nLightSamples));

        seg1.setCurrentValue((int)((lastLightValue-((lastLightValue%100-lastLightValue%10)/10)-(lastLightValue%10))/100));
        seg2.setCurrentValue((int)((lastLightValue%100-lastLightValue%10)/10));
        seg3.setCurrentValue((int)(lastLightValue%10));

        Log.d("TIEMPO", Float.toString(lastLightValue)+ " " +Long.toString((System.currentTimeMillis()-initialTime)/1000));
        lightSamples.add(new Entry((((float)(System.currentTimeMillis()-initialTime))/1000),lastLightValue));
        LineDataSet lightDataSet = new LineDataSet(lightSamples, "Light Measures");
        LineData lineData = new LineData(lightDataSet);
        chartLight.setData(lineData);
        chartLight.invalidate(); //refresh

    }
}
