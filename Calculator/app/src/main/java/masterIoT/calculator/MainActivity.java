package masterIoT.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
//import android.graphics.Color;
//import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.EditText;



public class MainActivity extends AppCompatActivity {

    EditText tleft;
    EditText tright;
    TextView tbottom;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tbottom = (TextView) findViewById(R.id.textBottom);
        tleft = (EditText)findViewById(R.id.tleft);
        tright = (EditText)findViewById(R.id.tright);


        Button bplus = (Button) findViewById(R.id.signPlus);
        bplus.setOnClickListener( new OperationButtonListener() );
        Button bminus = (Button) findViewById(R.id.signMinus);
        bminus.setOnClickListener( new OperationButtonListener() );
        Button bmult = (Button) findViewById(R.id.signMultiply);
        bmult.setOnClickListener( new OperationButtonListener() );
        Button bbar = (Button) findViewById(R.id.signBar);
        bbar.setOnClickListener( new OperationButtonListener() );
    }

    class OperationButtonListener implements OnClickListener{

        @Override
        public void onClick (View v)
        {
            int nleft = Integer.parseInt(tleft.getText().toString());
            int nright = Integer.parseInt(tright.getText().toString());
            int result;
            String operation;

            switch(v.getId())
            {
                case R.id.signPlus:
                    result = nleft + nright;
                    operation = tleft.getText().toString() + " + " +
                            tright.getText().toString() + " = " + result;
                    tbottom.setText(operation);
                    break;

                case R.id.signMinus:
                    result = nleft - nright;
                    operation = tleft.getText().toString()+ " - " +
                            tright.getText().toString() + " = "  + result;
                    tbottom.setText(operation);
                    break;

                case R.id.signMultiply:
                    result = (nleft * nright);
                    operation = tleft.getText().toString() + " * " +
                            tright.getText().toString() + " = " + result;
                    tbottom.setText(operation);
                    break;

                case R.id.signBar:
                    result = (nleft / nright);
                    operation = tleft.getText().toString() + " / " +
                            tright.getText().toString() + " = " +result;
                    tbottom.setText(operation);
                    break;
            }


        }

    }
}
