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
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import services.ApiMeetingServices;
import services.ApiSerivces;

public class AddMeetingActivity extends ListActivity implements AdapterView.OnItemSelectedListener {

    ApiSerivces mApiServices;

    Calendar cal1 = Calendar.getInstance();
    Calendar cal2 = Calendar.getInstance();
    Calendar calGlobal = Calendar.getInstance();

    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

    ArrayAdapter<CharSequence> adapter;
    ArrayAdapter<CharSequence> adapter2;

    Boolean check = false;

    Spinner spinner;
    Spinner spinner2;

    TextView text;
    TextView alert_spinner;
    TextView alert_subject;
    TextView alert_date;
    TextView alert_period;
    TextView alert_emails;
    TextView timeTv;
    TextView dateTv;

    EditText editText;
    EditText email;

    Button submit;

    int period;
    long data;

    String timeString;
    String dateString;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_meeting);

        mApiServices = new ApiMeetingServices();

        Toolbar toolbar = findViewById(R.id.toolbarAddMeeting);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.new_meeting);

        spinner = findViewById(R.id.spinner);
        spinner2 = findViewById(R.id.period);

        adapter = ArrayAdapter.createFromResource(
                this,
                R.array.rooms,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        adapter2 = ArrayAdapter.createFromResource(
                this,
                R.array.period,
                android.R.layout.simple_spinner_item
        );
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(this);

        submit = findViewById(R.id.submit);

        text = findViewById(R.id.room);
        alert_spinner = findViewById(R.id.alert_spinner);
        alert_subject = findViewById(R.id.alert_subject);
        alert_date = findViewById(R.id.alert_date);
        alert_period = findViewById(R.id.alert_period);
        alert_emails = findViewById(R.id.alert_email);
        timeTv = findViewById(R.id.timePicker);
        dateTv = findViewById(R.id.datePicker);

        editText = findViewById(R.id.edittext);
        email = findViewById(R.id.email_selector);

        getTimePickerDialog();
        getDatePickerDialog();

        submit.setOnClickListener(v -> {
            submitChecking();
            if (check) {
                Meeting meeting = setMeetingSubmitted();
                sendDataToLastActivity(meeting);
            }
        });
    }

    private void sendDataToLastActivity(Meeting meeting) {
        //SEND DATA TO LIST_ACTIVITY
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", meeting);
        setResult(RESULT_OK, returnIntent);

        Toast.makeText(getApplicationContext(), "Réunion enregistrée", Toast.LENGTH_SHORT).show();

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
                room.setId(0);
                room.setColor(R.mipmap.blue);
                meeting.setLocation(room);
                break;
            case "B":
                room.setName("Reunion B");
                room.setId(1);
                room.setColor(R.mipmap.orange);
                meeting.setLocation(room);
                break;
            case "C":
                room.setName("Reunion C");
                room.setId(2);
                room.setColor(R.mipmap.magenta);
                meeting.setLocation(room);
                break;
        }

        //SETTING SUBJECT
        meeting.setSubject(editText.getText().toString());

        //SETTING EMAILS
        meeting.setEmails(emails);

        //SETTING DATE, PERIOD & TIME
        meeting.setTimestamp(data);
        meeting.setPeriod(period);
        return meeting;
    }

    private void submitChecking() {
        List<String> emails;
        emails = Arrays.asList(email.getText().toString().split(","));

        if (text.getText().toString().equals("")) {
            text.setError(getText(R.string.required));
            check = false;
            Toast.makeText(getApplicationContext(), "Veuillez choisir une salle", Toast.LENGTH_LONG).show();
        } else if (editText.getText().toString().equals("")) {
            editText.setError(getText(R.string.required));
            check = false;
            Toast.makeText(getApplicationContext(), "Veuillez définir le sujet de réunion", Toast.LENGTH_LONG).show();
        } else if (dateTv.getText().toString().equals("")) {
            dateTv.setError(getText(R.string.required));
            check = false;
            Toast.makeText(getApplicationContext(), "Veuillez sélectionner une date", Toast.LENGTH_LONG).show();
        } else if (timeTv.getText().toString().equals("")) {
            timeTv.setError(getText(R.string.required));
            check = false;
            Toast.makeText(getApplicationContext(), "Veuillez sélectionner une heure", Toast.LENGTH_LONG).show();
        }

        calGlobal.set(cal2.get(Calendar.YEAR), cal2.get(Calendar.MONTH), cal2.get(Calendar.DAY_OF_MONTH),
                cal1.get(Calendar.HOUR_OF_DAY), cal1.get(Calendar.MINUTE));
        data = calGlobal.getTime().getTime();

        if (!meetingLimiter(data, text.getText().toString())){
            alert_period.setError(getText(R.string.room_already_takes));
            check = false;
            Toast.makeText(getApplicationContext(), "Veuillez sélectionner un autre horaire", Toast.LENGTH_LONG).show();
        }
        else if (email.getText().toString().equals("")) {
        email.setError(getText(R.string.required));
        check = false;
        Toast.makeText(getApplicationContext(), "Veuillez sélectionner au moins un email", Toast.LENGTH_LONG).show();
        } else if (emails.size() > 5) {
            email.setError(getText(R.string.email_assert));
            check = false;
            Toast.makeText(getApplicationContext(), "Veuillez sélectionner 5 emails maximum", Toast.LENGTH_LONG).show();
        } else {
            check = true;
        }

    }

    private void getDatePickerDialog() {
        //DATE_PICKER_DIALOG
        dateTv.setOnClickListener(v -> {
            DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {
                cal2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                cal2.set(Calendar.MONTH, month);
                cal2.set(Calendar.YEAR, year);
                dateTv.setText(dateString = dateFormat.format(cal2.getTime()));
            };
            DatePickerDialog datePickerDialog = new DatePickerDialog(AddMeetingActivity.this,
                    dateSetListener, 2021, 1, 1);

            datePickerDialog.show();
        });
    }

    private void getTimePickerDialog() {
        //TIME_PICKER_DIALOG
        timeTv.setOnClickListener(v -> {
            // Time Set Listener.
            TimePickerDialog.OnTimeSetListener timeSetListener = (view, hourOfDay, minute) -> {

                cal1.set(Calendar.HOUR_OF_DAY, hourOfDay);
                cal1.set(Calendar.MINUTE, minute);
                timeTv.setText(timeString = timeFormat.format(cal1.getTime()));
            };

            TimePickerDialog timePickerDialog = new TimePickerDialog(AddMeetingActivity.this,
                    timeSetListener, 12, 0, true);

            timePickerDialog.show();
        });
    }

    public Boolean meetingLimiter(long time, String reu){
        long test;
        Room room = new Room();
        switch (reu){
            case "A":
                room.setName("Reunion A");
                break;
            case "B":
                room.setName("Reunion B");
                break;
            case "C":
                room.setName("Reunion C");
                break;
        }
        for (Meeting meeting : mApiServices.getMeetings()){
            test = meeting.getTimestamp()+meeting.getPeriod();
            if (time>=meeting.getTimestamp()&&time<=test&&room.getName().equals(meeting.getLocation().getName())){
                return false;
            }
        }
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getItemAtPosition(position).toString().equals("Réunion A")) {
            text.setText("A");
            Toast.makeText(this, parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
        }
        if (parent.getItemAtPosition(position).toString().equals("Réunion B")) {
            text.setText("B");
            Toast.makeText(this, parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
        }
        if (parent.getItemAtPosition(position).toString().equals("Réunion C")) {
            text.setText("C");
            Toast.makeText(this, parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
        }
        if (parent.getItemAtPosition(position).toString().equals("1h")){
            period = 3600000;
            Toast.makeText(this, parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
        }
        if (parent.getItemAtPosition(position).toString().equals("2h")){
            period = 7200000;
            Toast.makeText(this, parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
