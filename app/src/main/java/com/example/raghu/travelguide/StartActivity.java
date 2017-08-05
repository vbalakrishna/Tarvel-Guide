package com.example.raghu.travelguide;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    String[] items = { "Places", "Restaurants", "Hotels" };
    String interestedin = "";
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        final Spinner s = (Spinner) findViewById(R.id.spinner);
        final EditText e = (EditText) findViewById(R.id.editText);
        s.setOnItemSelectedListener(this);

        ArrayAdapter<String> aa = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,items);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setPrompt("Choose Your Interest");
        s.setAdapter(aa);


        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String place = e.getText().toString();
                if (place.equals("") || interestedin.equals("")){
                    Toast.makeText(getApplicationContext(),"Make sure to fill all the fields :)" ,Toast.LENGTH_LONG).show();
                }
                else{
                    Intent intent = new Intent(StartActivity.this, ResultActivity.class);
                    String message = interestedin+" in "+place+"-";
                    message += "https://maps.googleapis.com/maps/api/place/textsearch/json?query="+interestedin+"+in+"+place+"&key=AIzaSyDiOuDDV7yRdHk9mvQMP4uenZ05B1KYSbw";
                    intent.putExtra(EXTRA_MESSAGE, message);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        interestedin = items[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
