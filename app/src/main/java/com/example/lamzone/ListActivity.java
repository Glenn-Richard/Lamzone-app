package com.example.lamzone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import services.ApiSerivces;
import services.ApiMeetingServices;

public class ListActivity extends AppCompatActivity {

    public static final String MEETING_EXTRA = "MEETING_EXTRA";
    private Meeting meetingAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_list);



        ApiSerivces mApiServices = new ApiMeetingServices();
        List<Meeting> mMeetings;

        mMeetings = mApiServices.getMeetings();
        final RecyclerView rv = findViewById(R.id.list_item_rv);
        final FloatingActionButton fab = findViewById(R.id.fab);
        meetingAdd = getIntent().getParcelableExtra(MEETING_EXTRA);

        rv.setLayoutManager(new LinearLayoutManager(this));

        MeetingAdapter adapter = new MeetingAdapter(mMeetings, new ListItemListener() {
            @Override
            public void onDelete(Meeting meeting) {
                mApiServices.deleteMeeting(meeting);
                rv.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void OnAddMeeting(Meeting meeting) {
                meeting = meetingAdd;
                mApiServices.addMeeting(meeting);
                rv.getAdapter().notifyDataSetChanged();
            }
        });
        rv.setAdapter(adapter);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(ListActivity.this,AddMeetingActivity.class);
            startActivity(intent);
        });
    }
}