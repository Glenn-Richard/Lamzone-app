package com.example.lamzone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.invoke.MethodType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import services.ApiSerivces;
import services.ApiMeetingServices;


public class ListActivity extends AppCompatActivity {

    int LAUNCH_SECOND_ACTIVITY = 1;

    ArrayList<Meeting> mMeetings = new ArrayList<Meeting>();
    ApiSerivces mApiServices = new ApiMeetingServices();
     RecyclerView rv;
    MeetingAdapter adapter;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_list);
        rv = findViewById(R.id.list_item_rv);
        rv.setLayoutManager(new LinearLayoutManager(this));

        mMeetings.addAll(mApiServices.getMeetings());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Ma réu");


        adapter = new MeetingAdapter(new ListItemListener() {
            @Override
            public void onDelete(Meeting meeting) {
                mApiServices.deleteMeeting(meeting);
                adapter.updateList(new ArrayList(mApiServices.getMeetings()));
            }
        });
        adapter.updateList(mMeetings);
        rv.setAdapter(adapter);

        setFabOnClick();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.dateFilter){
            Collections.sort(mMeetings);
            adapter.updateList(mMeetings);
        }
        if (id == R.id.reunionA || id == R.id.reunionB || id == R.id.reunionC ){
            adapter.updateList(sortMeetingByRoom(mMeetings,id));
        }

        return super.onOptionsItemSelected(item);
    }

    public void setFabOnClick(){
        final FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(v -> {
            Intent intent = new Intent(ListActivity.this,AddMeetingActivity.class);
            startActivityForResult(intent, LAUNCH_SECOND_ACTIVITY);
        });
    }
    public ArrayList<Meeting> sortMeetingByRoom(List<Meeting> meetings,int id){
        ArrayList<Meeting> sortedMeetings = new ArrayList<>();
        if (id == R.id.reunionA){
            for (int i=0;i<meetings.size();i++){
                if (meetings.get(i).getLocation().getName().equals("Reunion A")){
                    sortedMeetings.add(meetings.get(i));
                }
            }
            return sortedMeetings;
        }
        if (id == R.id.reunionB){
            for (int i=0;i<meetings.size();i++){
                if (meetings.get(i).getLocation().getName().equals("Reunion B")){
                    sortedMeetings.add(meetings.get(i));
                }
            }
            return sortedMeetings;
        }
        if (id == R.id.reunionC){
            for (int i=0;i<meetings.size();i++){
                if (meetings.get(i).getLocation().getName().equals("Reunion C")){
                    sortedMeetings.add(meetings.get(i));
                }
            }
            return sortedMeetings;
        }
        return sortedMeetings;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LAUNCH_SECOND_ACTIVITY) {
            if(resultCode == Activity.RESULT_OK){
                Meeting meeting = data.getParcelableExtra("result");
                Log.d("MEETING",meeting.toString());
                mApiServices.getMeetings().add(meeting);
                adapter.updateList(new ArrayList(mApiServices.getMeetings()));
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//onActivityResult
}