package com.example.lamzone;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import services.ApiMeetingServices;
import services.ApiSerivces;

public class AddMeetingActivity extends ListActivity implements AdapterView.OnItemSelectedListener{

    ApiSerivces mApiServices = new ApiMeetingServices();

    Calendar cal1 = Calendar.getInstance();
    Calendar cal2 = Calendar.getInstance();
    Calendar calGlobal = Calendar.getInstance();

    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

    ArrayAdapter<CharSequence> adapter;

    Boolean check = false;

    Spinner spinner;

    TextView text;
    TextView alert_spinner;
    TextView alert_subject;
    TextView alert_date;
    TextView alert_hour;
    TextView alert_emails;
    TextView timeTv;
    TextView dateTv;

    EditText editText;
    EditText email;

    Button submit;

    String timeString;
    String dateString;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_meeting);

        Toolbar toolbar = findViewById(R.id.toolbarAddMeeting);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("New Meeting");

        spinner = findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(
                this,
                R.array.rooms,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        submit = findViewById(R.id.submit);

        text = findViewById(R.id.room);
        alert_spinner = findViewById(R.id.alert_spinner);
        alert_subject = findViewById(R.id.alert_subject);
        alert_date = findViewById(R.id.alert_date);
        alert_hour = findViewById(R.id.alert_hour);
        alert_emails = findViewById(R.id.alert_email);
        timeTv = findViewById(R.id.timePicker);
        dateTv = findViewById(R.id.datePicker);

        editText = findViewById(R.id.edittext);
        email = findViewById(R.id.email_selector);

        getTimePickerDialog();
        getDatePickerDialog();

        submit.setOnClickListener(v -> {
            submitChecking();
            if (check){
                Meeting meeting = setMeetingSubmitted();
                sendDataToLastActivity(meeting);
            }
        });
    }

    private void sendDataToLastActivity(Meeting meeting) {
        //SEND DATA TO LIST_ACTIVITY
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result",meeting);
        setResult(RESULT_OK,returnIntent);

        Toast.makeText(getApplicationContext(),"Réunion enregistrée",Toast.LENGTH_SHORT).show();

        finish();
    }

    private Meeting setMeetingSubmitted() {

        List<String> emails;
        emails = Arrays.asList(email.getText().toString().split(","));

        Meeting meeting = new Meeting();
        Room room = new Room();
        // SETTING ID
        meeting.setId(mApiServices.getMeetings().size());

        //SETTING LOCATION
        switch (text.getText().toString()) {
            case "A":
                room.setName("Reunion A");
                room.setId(00);
                room.setColor(R.mipmap.blue);
                meeting.setLocation(room);
                break;
            case "B":
                room.setName("Reunion B");
                room.setId(01);
                room.setColor(R.mipmap.orange);
                meeting.setLocation(room);
                break;
            case "C":
                room.setName("Reunion C");
                room.setId(02);
                room.setColor(R.mipmap.magenta);
                meeting.setLocation(room);
                break;
        }

        //SETTING SUBJECT
        meeting.setSubject(editText.getText().toString());

        //SETTING EMAILS
        meeting.setEmails(emails);

        //SETTING DATE & TIME
        calGlobal.set(cal2.get(Calendar.YEAR),cal2.get(Calendar.MONTH),cal2.get(Calendar.DAY_OF_MONTH),
                cal1.get(Calendar.HOUR_OF_DAY),cal1.get(Calendar.MINUTE));
        long data = calGlobal.getTime().getTime();
        meeting.setTimestamp(data);
        return meeting;
    }

    private void submitChecking() {
        List<String> emails;
        emails = Arrays.asList(email.getText().toString().split(","));

        if (text.getText().toString().equals("")){
            alert_spinner.setText("champs requis");
            check = false;
            Toast.makeText(getApplicationContext(),"Veuillez choisir une salle",Toast.LENGTH_LONG).show();
        }
        else if (editText.getText().toString().equals(""))
        {
            alert_subject.setText("champs requis");
            check = false;
            Toast.makeText(getApplicationContext(),"Veuillez définir le sujet de réunion",Toast.LENGTH_LONG).show();
        }
        else if (dateTv.getText().toString().equals(""))
        {
            alert_date.setText("champs requis");
            check = false;
            Toast.makeText(getApplicationContext(),"Veuillez sélectionner une date",Toast.LENGTH_LONG).show();
        }
        else if(timeTv.getText().toString().equals(""))
        {
            alert_hour.setText("champs requis");
            check = false;
            Toast.makeText(getApplicationContext(),"Veuillez sélectionner une heure",Toast.LENGTH_LONG);
        }
        else if (email.getText().toString().equals(""))
        {
            alert_emails.setText("champs requis");
            check = false;
            Toast.makeText(getApplicationContext(),"Veuillez sélectionner au moins un email",Toast.LENGTH_LONG).show();
        }
        else if (emails.size()>5)
        {
            alert_emails.setText("5 emails maximum");
            check = false;
            Toast.makeText(getApplicationContext(),"Veuillez sélectionner 5 emails maximum",Toast.LENGTH_LONG).show();
        }
        else
        {
            check = true;
        }

    }

    private void getDatePickerDialog() {
        //DATE_PICKER_DIALOG
        dateTv.setOnClickListener(v -> {
            DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {
                cal2.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                cal2.set(Calendar.MONTH,month);
                cal2.set(Calendar.YEAR,year);
                dateTv.setText(dateString = dateFormat.format(cal2.getTime()));
            };
            DatePickerDialog datePickerDialog = new DatePickerDialog(AddMeetingActivity.this,
                    dateSetListener,2021,01,01);

            datePickerDialog.show();
        });
    }

    private void getTimePickerDialog() {
        //TIME_PICKER_DIALOG
        timeTv.setOnClickListener(v -> {
            // Time Set Listener.
            TimePickerDialog.OnTimeSetListener timeSetListener = (view, hourOfDay, minute) -> {

                cal1.set(Calendar.HOUR_OF_DAY,hourOfDay);
                cal1.set(Calendar.MINUTE,minute);
                timeTv.setText(timeString= timeFormat.format(cal1.getTime()));
            };

            TimePickerDialog timePickerDialog = new TimePickerDialog(AddMeetingActivity.this,
                    timeSetListener, 12, 00, true);

            timePickerDialog.show();
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getItemAtPosition(position).toString().equals("Réunion A")){
            text.setText("A");
            Toast.makeText(this, parent.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();
        }
        if (parent.getItemAtPosition(position).toString().equals("Réunion B")){
            text.setText("B");
            Toast.makeText(this, parent.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();
        }
        if (parent.getItemAtPosition(position).toString().equals("Réunion C")){
            text.setText("C");
            Toast.makeText(this, parent.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
