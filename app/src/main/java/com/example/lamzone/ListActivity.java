package com.example.lamzone;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;

import services.ApiMeetingServices;
import services.ApiSerivces;
import services.ApiServiceGenerator;


public class ListActivity extends AppCompatActivity {

    static ApiSerivces mApiServices = new ApiMeetingServices();
    @Nullable
    public AlertDialog dialog = null;
    int LAUNCH_SECOND_ACTIVITY = 1;
    ArrayList<Meeting> mMeetings = new ArrayList<>();
    RecyclerView rv;
    MeetingAdapter adapter;
    Calendar cal1 = Calendar.getInstance();
    @Nullable
    private TextView dialogTextView = null;
    @Nullable
    private Spinner dialogSpinner = null;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        rv = findViewById(R.id.list_item_rv);

        mMeetings.addAll(mApiServices.getMeetings());

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.ma_reu);


        adapter = new MeetingAdapter(meeting -> {
            mApiServices.deleteMeeting(meeting);
            adapter.updateList(new ArrayList<>(mApiServices.getMeetings()));
        });
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter.updateList(mMeetings);
        rv.setAdapter(adapter);

        setFabOnClick();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.SHOW_ALL) {
            adapter.updateList((ArrayList<Meeting>) mApiServices.getMeetings());
        }
        if (id == R.id.dateFilter) {
            getDatePicker();
        }
        if (id == R.id.roomFilter) {
            showRoomDialog();
        }

        return super.onOptionsItemSelected(item);
    }

    private void showRoomDialog() {
        final AlertDialog dialog = getRoomDialog();

        dialog.show();

        dialogTextView = dialog.findViewById(R.id.roomChosen);
        dialogSpinner = dialog.findViewById(R.id.room_spinner);

        populateDialogSpinner();
    }

    public void setFabOnClick() {
        final FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(v -> {
            Intent intent = new Intent(ListActivity.this, AddMeetingActivity.class);
            startActivityForResult(intent, LAUNCH_SECOND_ACTIVITY);
        });
    }

    private void getDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {
            cal1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            cal1.set(Calendar.MONTH, month);
            cal1.set(Calendar.YEAR, year);
            adapter.updateList((ArrayList<Meeting>) mApiServices.filterByDate(mApiServices.getMeetings(), cal1));
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(ListActivity.this,
                dateSetListener, 2021, 1, 1);

        datePickerDialog.show();
    }

    @NonNull
    private AlertDialog getRoomDialog() {
        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);

        alertBuilder.setTitle(R.string.filtre_reunion);
        alertBuilder.setView(R.layout.filter_dialog_alert);
        alertBuilder.setPositiveButton(R.string.filter, null);
        alertBuilder.setOnDismissListener(dialogInterface -> {
            dialogTextView = null;
            dialogSpinner = null;
            dialog = null;
        });

        dialog = alertBuilder.create();

        // This instead of listener to positive button in order to avoid automatic dismiss
        dialog.setOnShowListener(dialogInterface -> {
            dialogSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    dialogTextView.setText((dialogSpinner.getSelectedItem()).toString());

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            button.setOnClickListener(view -> onPositiveRoomButtonClick(dialog));
        });

        return dialog;
    }

    private void onPositiveRoomButtonClick(DialogInterface dialogInterface) {
        // If dialog is open
        if (dialogTextView != null && dialogSpinner != null) {

            String room = dialogSpinner.getSelectedItem().toString();

            if (room != null) {

                adapter.updateList(mApiServices.filterMeetingByRoom(room, mApiServices.getMeetings()));

                dialogInterface.dismiss();
            }
            // If name has been set, but project has not been set (this should never occur)
            else {
                dialogInterface.dismiss();
            }
        }
        // If dialog is already closed
        else {
            dialogInterface.dismiss();
        }
    }

    private void populateDialogSpinner() {
        ArrayList<String> roomsName = new ArrayList<>();
        for (Room room : ApiServiceGenerator.ROOMS) {
            roomsName.add(room.getName());
        }
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roomsName);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (dialogSpinner != null) {
            dialogSpinner.setAdapter(adapter);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LAUNCH_SECOND_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                Meeting meeting = data.getParcelableExtra("result");
                Log.d("MEETING", meeting.toString());
                mApiServices.getMeetings().add(meeting);
                adapter.updateList(new ArrayList<>(mApiServices.getMeetings()));
            }
        }
    }//onActivityResult
}