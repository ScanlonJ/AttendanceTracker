package com.Daelgan.attendancetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void StartMembersListActivity(View view) {
        Intent intent = new Intent(this, MembersListActivity.class);
        startActivity(intent);
    }

    public void StartAttendanceListActivity(View view) {
        Intent intent = new Intent(this, AttendanceListActivity.class);
        startActivity(intent);
    }
}