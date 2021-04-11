package com.example.lamzone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

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

        rv.setLayoutManager(new LinearLayoutManager(this));
        MeetingAdapter adapter=new MeetingAdapter(mMeetings, meeting -> {
            mApiServices.deleteMeeting(meeting);
            rv.getAdapter().notifyDataSetChanged();

        });

        rv.setAdapter(adapter);
    }
}