package dte.masteriot.mdp.registration;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.BulletSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    TextView t1;
    String name,reg,country, byod, rate;
    String mText;
    Button bAccept, bCancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        t1= (TextView) findViewById(R.id.textView);
        bAccept = (Button) findViewById(R.id.bAccept);
        bCancel = (Button) findViewById(R.id.bCancel);


        //Getting the Intent
        Intent i = getIntent();

        //Getting the Values from First Activity using the Intent received
        name=i.getStringExtra("name_key");
        reg=i.getStringExtra("reg_key");
        country=i.getStringExtra("country_key");
        byod=i.getStringExtra("byod_key");
        rate=i.getStringExtra("rate_key");

        mText = "Form data received:"      +
                "\n  Name: " + name     +
                "\n  Reg: "  + reg     +
                "\n  Country: "  + country +
                "\n  Byod: "  + byod    +
                "\n  Rate: "  + rate;


        // Showing the text in the TextView:
        t1.setText(mText);

        bAccept.setOnClickListener(new InteractButtons());
        bCancel.setOnClickListener(new InteractButtons());
    }

    class InteractButtons implements View.OnClickListener{
        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.bAccept:
                    setResult(RESULT_OK);
                    break;

                case R.id.bCancel:
                    setResult(RESULT_CANCELED);
                    break;

            }
            finish();


        }
    }


}
