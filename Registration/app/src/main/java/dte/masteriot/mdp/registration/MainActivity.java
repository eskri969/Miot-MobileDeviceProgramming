package dte.masteriot.mdp.registration;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

import android.content.Context;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //Defining the Views
    EditText name_view, regnum_view;
    Button bt;
    Spinner country_view;
    Switch byod_view;
    RatingBar rate_view;

    //Data for populating in Spinner
    String [] country_array={"China","Colombia","Cuba","Ecuador","France","Germany","Italy","Morocco","Spain","UK"};

    String name, reg, country, byod, rate;

    static final int CHECK_INFO= 1;  // The request code

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Referring the Views
        name_view = (EditText) findViewById(R.id.editText);
        regnum_view = (EditText) findViewById(R.id.editText2);
        bt= (Button) findViewById(R.id.submit);
        country_view = (Spinner) findViewById(R.id.spinner);
        byod_view = (Switch) findViewById(R.id.switch_byod);
        rate_view = (RatingBar) findViewById(R.id.ratingBar);

        //Setting Listener for Submit Button
        bt.setOnClickListener(this);
        CountryData countryData = new CountryData();


        //Creating Adapter for Spinner for adapting the data from array to Spinner
        CountryArrayAdapter countryArrayAdapter = new CountryArrayAdapter( this, countryData.getCountryDataList() );
        country_view.setAdapter(countryArrayAdapter);

        //country_view.setChoiceMode( ListView.CHOICE_MODE_SINGLE );
    }

    @Override
    public void onClick(View v) {

        //Getting the Values from Views(Edittext & Spinner)
        name = name_view.getText().toString();
        reg = regnum_view.getText().toString();
        country = country_view.getSelectedItem().toString();
        byod = byod_view.isChecked() ? "true" : "false";
        rate = String.valueOf( rate_view.getRating() );

        //Intent For Navigating to Second Activity (Explicit Intent)
        Intent i = new Intent(MainActivity.this,SecondActivity.class);

        // Passing the Values to Second Activity
        i.putExtra("name_key", name);
        i.putExtra("reg_key",reg);
        i.putExtra("country_key", country);
        i.putExtra("byod_key", byod);
        i.putExtra("rate_key", rate);

        //startActivity(i);
        startActivityForResult(i,CHECK_INFO);
    }

    public void onWEBClick(View view) {
        String myUriString = "http://masteriot.etsist.upm.es/?lang=en";
        // Implicit Intent:
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(myUriString));
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == CHECK_INFO) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {

                Toast.makeText(getApplicationContext(), "Accepted", Toast.LENGTH_LONG).show();


                /*
                Toast toast = new Toast(getApplicationContext());
                toast.makeText(getApplicationContext(),"Accepted",Toast.LENGTH_LONG);
                toast.show();
                 */
                // The user picked a contact.
                // The Intent's data Uri identifies which contact was selected.

                // Do something with the contact here (bigger example below)
            }
            if (resultCode == RESULT_CANCELED) {

                Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_LONG).show();
            }
        }
    }

    class Country {
        private int imageResource;
        private String name;

        public Country(int imgRes, String n){
            imageResource = imgRes;
            name = n;
        }


        public int getImageResource() {
            return imageResource;
        }

        public String getName() {
            return name;
        }

        public String toString() {
            return name;
        }
    }

    public class CountryData {
        //Data for populating in Listview
        private String[] countries_names = {"Spain", "Germany", "UK", "France", "Italy" };
        private Integer[] countries_flags = {R.drawable.spain,
                R.drawable.germany,
                R.drawable.united_kingdom,
                R.drawable.france,
                R.drawable.italy };

        private ArrayList<Country> mList = new ArrayList<Country>();

        public CountryData() {
            // Build data array list
            for (int i = 0; i < countries_names.length; ++i) {
                Country country = new Country( countries_flags[i], countries_names[i] );
                mList.add(country);
            }
        }

        public ArrayList<Country> getCountryDataList() {
            return mList;
        }
    }

    class CountryArrayAdapter extends ArrayAdapter<Country>  {
        private ArrayList<Country> items;
        private Context mContext;

        CountryArrayAdapter(Context context, ArrayList<Country> countries ) {
            super( context, 0, countries );
            items = countries;
            mContext = context;
        }

        @Override
        public View getView( int position, View convertView, ViewGroup parent ) {

            View newView = convertView;

            // This approach can be improved for performance
            if ( newView == null ) {
                LayoutInflater inflater = (LayoutInflater) mContext
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                newView = inflater.inflate(R.layout.country_list_item, parent, false);
            }
            //-----

            TextView textView = (TextView) newView.findViewById(R.id.tvCountry);
            ImageView imageView = (ImageView) newView.findViewById(R.id.imgCountry);

            Country country = items.get(position);

            textView.setText(country.getName());
            imageView.setImageResource(country.getImageResource());

            return newView;
        }
    }
}