package com.example.lamzone;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import services.ApiMeetingServices;
import services.ApiSerivces;
import services.ApiServiceGenerator;

public class AddMeetingActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_meeting);

        ApiSerivces mApiServices = new ApiMeetingServices();

        RecyclerView rv = findViewById(R.id.list_item_rv);

        Button buttonA = findViewById(R.id.reunionA);
        Button buttonB = findViewById(R.id.reunionB);
        Button buttonC = findViewById(R.id.reunionC);
        Button submit = findViewById(R.id.submit);

        TextView text = findViewById(R.id.roomSelected);

        EditText editText = findViewById(R.id.edittext);

        Spinner spinnerday = findViewById(R.id.spinnerday);
        Spinner spinnermonth = findViewById(R.id.spinnermonth);
        Spinner spinneryear = findViewById(R.id.spinneryear);
        Spinner spinnerhours = findViewById(R.id.spinnerhours);
        Spinner spinnerminutes = findViewById(R.id.spinnerminutes);

        spinnerday.setOnItemSelectedListener(this);
        spinnermonth.setOnItemSelectedListener(this);
        spinneryear.setOnItemSelectedListener(this);
        spinnerhours.setOnItemSelectedListener(this);
        spinnerminutes.setOnItemSelectedListener(this);

        buttonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setText("Vous avez sélectionner la salle: A");
            }
        });
        buttonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setText("Vous avez sélectionner la salle: B");
            }
        });
        buttonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setText("Vous avez sélectionner la salle: C");
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Meeting meeting = null;
                Room room = null;
                meeting.setId(mApiServices.getMeetings().size()-1);
                switch (text.getText().toString()) {
                    case "Vous avez sélectionner la salle: A":
                        room.setName("Reunion A");
                        meeting.setLocation(room);
                        break;
                    case "Vous avez sélectionner la salle: B":
                        room.setName("Reunion B");
                        meeting.setLocation(room);
                        break;
                    case "Vous avez sélectionner la salle: C":
                        room.setName("Reunion C");
                        meeting.setLocation(room);
                        break;
                }
                meeting.setSubject(editText.getText().toString());
                meeting.setEmails(ApiServiceGenerator.EMAILS);
                mApiServices.addMeeting(meeting);
                rv.getAdapter().notifyDataSetChanged();
            }
        });

    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String jour = "Jour";
        String mois = "Mois";
        String annee = "Année";
        String heure = "Heure";
        String minutes = "Minutes";
        if(parent.getItemAtPosition(position).toString().equals(jour) || parent.getItemAtPosition(position).toString().equals(mois) ||
                parent.getItemAtPosition(position).toString().equals(annee) || parent.getItemAtPosition(position).toString().equals(heure) ||
                parent.getItemAtPosition(position).toString().equals(minutes)){
            return ;
        }else{
            Toast.makeText(parent.getContext(),parent.getItemAtPosition(position).toString(),Toast.LENGTH_SHORT).show();
            String value = parent.getItemAtPosition(position).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
