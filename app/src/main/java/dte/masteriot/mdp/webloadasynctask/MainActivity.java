package dte.masteriot.mdp.webloadasynctask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.URL;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import android.util.Log;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    //    private static final String URL_CAMERAS = "http://informo.madrid.es/informo/tmadrid/CCTV.kml";
    private static final String URL_CAMERAS = "http://informo.madrid.es/informo/tmadrid/CCTV.kml";
    private TextView text;
    private XmlPullParserFactory parserFactory;
    private ArrayList<String> camerasURLS_ArrayList= new ArrayList<>();

    private Button btLoad;
    String animalList[] = {"Lion", "Tiger", "Monkey", "Elephant", "Dog", "Cat", "Camel"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_web_load);
        text =  (TextView) findViewById(R.id.KMLTextView);
        btLoad = (Button) findViewById( R.id.readWebpage );
        text.setText( "Click button to connect to " + URL_CAMERAS );

        Spinner spin = (Spinner) findViewById(R.id.animalNamesSpinner);
        spin.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the animal name's list
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, animalList);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);
        xmlParser();
    }

    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        Toast.makeText(getApplicationContext(), animalList[position], Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }


    public void xmlParser(){
        try {
            parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserFactory.newPullParser();
            InputStream is = getAssets().open("CCTV.kml");
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(is, null);
            int eventType = parser.getEventType();


            while (eventType != XmlPullParser.END_DOCUMENT) {
                String elementName = null;
                elementName = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if ("description".equals(elementName)) {
                            String cameraURL = parser.nextText();
                            cameraURL = cameraURL.substring(cameraURL.indexOf("http:"));
                            cameraURL = cameraURL.substring(0, cameraURL.indexOf(".jpg") + 4);
                            camerasURLS_ArrayList.add( cameraURL );
                            Log.v("DESCRIPCION", cameraURL);
                        }
                        else if ("Data name=\"Numero\"".equals(elementName)) {
                            Log.v("DESCRIPCION", "yes");
                        }
                        else if ("Data name=\"Numero\"".equals(elementName)) {

                        }
                        break;
                    }
                    eventType = parser.next();

            }
        } catch (Exception e) {

        }
    }

    public void readKMLCameras(View view) {
        btLoad.setEnabled(false);
        text.setText( "Connecting to " + URL_CAMERAS );
        DownloadWebPageTask task = new DownloadWebPageTask();
        task.execute( URL_CAMERAS );
    }

    private class DownloadWebPageTask extends AsyncTask<String, Void, String> {

        private String contentType = "";

        @Override
        @SuppressWarnings( "deprecation" )
        protected String doInBackground(String... urls) {
            String response = "";

            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL( urls[0] );
                urlConnection = (HttpURLConnection) url.openConnection();
                contentType = urlConnection.getContentType();
                InputStream is = urlConnection.getInputStream();

                // Content type should be "application/vnd.google-earth.kml+xml"

                if ( contentType.toString()
                                 .contentEquals("application/vnd.google-earth.kml+xml") )
                {
                    InputStreamReader reader = new InputStreamReader( is );
                    BufferedReader in = new BufferedReader( reader );

                    String line = in.readLine();
                    while ( line != null ) {
                        response += line + "\n";
                        line = in.readLine();
                    }
                }
                else {
                    response = contentType + " not processed";
                }

                urlConnection.disconnect();

            } catch (Exception e) {
                response = e.toString();
            }

            return response;
        }

        @Override
        protected void onPostExecute(String result) {

            Toast.makeText(MainActivity.this, contentType, Toast.LENGTH_SHORT).show();
            text.setText( result );
            btLoad.setEnabled(true);
        }
    }
}
