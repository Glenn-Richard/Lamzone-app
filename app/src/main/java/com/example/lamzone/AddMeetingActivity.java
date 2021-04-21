package com.example.lamzone;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import services.ApiMeetingServices;
import services.ApiSerivces;
import services.ApiServiceGenerator;

public class AddMeetingActivity extends ListActivity{

    ApiSerivces mApiServices = new ApiMeetingServices();
    int LAUNCH_SECOND_ACTIVITY = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_meeting);

        Toolbar toolbar = findViewById(R.id.toolbarAddMeeting);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("New Meeting");

        Button buttonA = findViewById(R.id.reunionA);
        Button buttonB = findViewById(R.id.reunionB);
        Button buttonC = findViewById(R.id.reunionC);
        Button submit = findViewById(R.id.submit);

        TextView text = findViewById(R.id.roomSelected);
        TextView timeTv = findViewById(R.id.timePicker);
        TextView dateTv = findViewById(R.id.datePicker);


        EditText editText = findViewById(R.id.edittext);


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

        timeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean is24HView = true;

// Time Set Listener.
                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {



                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        timeTv.setText(mApiServices.getPickerTime(String.valueOf(hourOfDay),String.valueOf(minute)));
                    }
                };

                TimePickerDialog timePickerDialog = new TimePickerDialog(AddMeetingActivity.this,
                        timeSetListener, 12, 00, is24HView);


                timePickerDialog.show();
            }
        });

        dateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month++;
                        dateTv.setText(mApiServices.getPickerDate(String.valueOf(dayOfMonth),String.valueOf(month),String.valueOf(year)));
                    }
                };
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddMeetingActivity.this,
                        dateSetListener,2021,01,01);

                datePickerDialog.show();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Meeting meeting = new Meeting();
                Room room = new Room();
                meeting.setId(mApiServices.getMeetings().size());
                switch (text.getText().toString()) {
                    case "Vous avez sélectionner la salle: A":
                        room.setName("Reunion A");
                        room.setId(00);
                        meeting.setLocation(room);
                        break;
                    case "Vous avez sélectionner la salle: B":
                        room.setName("Reunion B");
                        room.setId(01);
                        meeting.setLocation(room);
                        break;
                    case "Vous avez sélectionner la salle: C":
                        room.setName("Reunion C");
                        room.setId(02);
                        meeting.setLocation(room);
                        break;
                }
                meeting.setSubject(editText.getText().toString());
                meeting.setEmails(ApiServiceGenerator.EMAILS);
                String date = dateTv.getText()+" "+timeTv.getText();
                SimpleDateFormat datetimeFormatter1 = new SimpleDateFormat(
                        "MM-dd-yyyy HH:mm:ss");
                Date lFromDate1 = new Date();
                try {
                    lFromDate1 = datetimeFormatter1.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                long data = lFromDate1.getTime()/1000;
                meeting.setTimestamp(data);
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result",meeting);
                Toast.makeText(AddMeetingActivity.this,String.valueOf(meeting.getId()),Toast.LENGTH_LONG).show();
                setResult(RESULT_OK,returnIntent);
                Toast.makeText(getApplicationContext(),"Réunion enregistrée",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}
