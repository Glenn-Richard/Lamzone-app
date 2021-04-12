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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_list);

        ApiSerivces mApiServices = new ApiMeetingServices();
        List<Meeting> mMeetings;

        mMeetings = mApiServices.getMeetings();
        final RecyclerView rv = findViewById(R.id.list_item_rv);
        final FloatingActionButton fab = findViewById(R.id.fab);

        rv.setLayoutManager(new LinearLayoutManager(this));
        MeetingAdapter adapter = new MeetingAdapter(mMeetings, meeting -> {
            mApiServices.deleteMeeting(meeting);
            rv.getAdapter().notifyDataSetChanged();

        });
        rv.setAdapter(adapter);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListActivity.this,AddMeetingActivity.class);
                startActivity(intent);
            }
        });
    }
}