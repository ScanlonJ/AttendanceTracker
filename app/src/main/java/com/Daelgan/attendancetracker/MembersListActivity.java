package com.Daelgan.attendancetracker;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;
import java.util.ListIterator;

public class MembersListActivity extends AppCompatActivity{

    private RecyclerView recyclerView;
    MemberListRecyclerViewAdapter adapter;
    DatabaseReference mbase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members_list);

        // Create a instance of the database and get
        // its reference
        mbase = FirebaseDatabase.getInstance().getReference("Members");

        Query query = mbase.orderByChild("BeltRank");

        recyclerView = findViewById(R.id.rvMemberList);

        // To display the Recycler view linearly
        recyclerView.setLayoutManager(
                new LinearLayoutManager(this));

        // It is a class provide by the FirebaseUI to make a
        // query in the database to fetch appropriate data
        FirebaseRecyclerOptions<Member> options
                = new FirebaseRecyclerOptions.Builder<Member>()
                .setQuery(query, Member.class)
                .build();

        // Connecting object of required Adapter class to
        // the Adapter class itself
        adapter = new MemberListRecyclerViewAdapter(options);

        // Connecting Adapter class with the Recycler view*/
        recyclerView.setAdapter(adapter);
    }

    public void StartAddMemberActivity(View view){
        Intent intent = new Intent(this, AddMemberActivity.class);
        startActivity(intent);
    }

    public void StartMemberDetailActivity(View view){
        Intent intent = new Intent(this, MemberDetailActivity.class);

        View parent = (View) view.getParent();
        TextView t = parent.findViewById(R.id.MemberName);
        intent.putExtra("Name", t.getText().toString());

        startActivity(intent);
    }

    // Function to tell the app to start getting
    // data from database on starting of the activity
    @Override protected void onStart()
    {
        super.onStart();
        adapter.startListening();
    }

    // Function to tell the app to stop getting
    // data from database on stopping of the activity
    @Override protected void onStop()
    {
        super.onStop();
        adapter.stopListening();
    }
}