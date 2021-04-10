package com.example.lamzone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_list);

        final RecyclerView rv = findViewById(R.id.list_item_rv);

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new MeetingAdapter());  ,
    }
}