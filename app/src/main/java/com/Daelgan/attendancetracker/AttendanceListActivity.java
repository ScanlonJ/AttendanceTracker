package com.Daelgan.attendancetracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class AttendanceListActivity extends AppCompatActivity{

    private RecyclerView recyclerView;
    AttendanceListRecyclerViewAdapter adapter;
    DatabaseReference mbase;
    ArrayList<String> checkedBoxes = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        // Create a instance of the database and get
        // its reference
        mbase = FirebaseDatabase.getInstance().getReference("Members");

        Query query = mbase.orderByChild("BeltRank");

        recyclerView = findViewById(R.id.rvAttendanceMemberList);

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
        adapter = new AttendanceListRecyclerViewAdapter(options);

        // Connecting Adapter class with the Recycler view*/
        recyclerView.setAdapter(adapter);

        Button submitButton = (Button) findViewById(R.id.attendanceSubmitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i < checkedBoxes.size(); i++) {

                    final String Name = checkedBoxes.get(i);

                    System.out.println("out - " + Name);

                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Members");

                    reference.child(Name).child("ClassesAttended").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String classesAttended = snapshot.getValue(String.class);
                            int classesAttendedInt;
                            if(classesAttended.equals(""))
                                classesAttendedInt = 0;
                            else
                                classesAttendedInt = Integer.parseInt(classesAttended);
                            ++classesAttendedInt;
                            reference.child(Name).child("ClassesAttended").setValue(String.valueOf(classesAttendedInt));
                            reference.child(Name).child("ClassesAttended").removeEventListener(this);

                            Task<DataSnapshot> t = reference.child(Name).child("ClassesPurchased").get();

                            int finalClassesAttendedInt = classesAttendedInt;
                            t.addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DataSnapshot> task) {

                                    String classesPurchased = task.getResult().getValue(String.class);
                                    int classesPurchasedInt;
                                    if (classesPurchased.equals(""))
                                        classesPurchasedInt = 0;
                                    else
                                        classesPurchasedInt = Integer.parseInt(classesPurchased);

                                    int classesRemainingInt = classesPurchasedInt - finalClassesAttendedInt;
                                    reference.child(Name).child("ClassesRemaining").setValue(String.valueOf(classesRemainingInt));
                                }
                            });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    reference.child(Name).child("ClassesSinceLastTest").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String classesAttended = snapshot.getValue(String.class);
                            int classesAttendedInt;
                            if(classesAttended.equals(""))
                                classesAttendedInt = 0;
                            else
                                classesAttendedInt = Integer.parseInt(classesAttended);
                            ++classesAttendedInt;
                            reference.child(Name).child("ClassesSinceLastTest").setValue(String.valueOf(classesAttendedInt));
                            reference.child(Name).child("ClassesSinceLastTest").removeEventListener(this);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("Classes");

                    reference2.child(Name).child("ClassesAttended").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String classesAttended = snapshot.getValue(String.class);
                            int classesAttendedInt;
                            if(classesAttended.equals(""))
                                classesAttendedInt = 0;
                            else
                                classesAttendedInt = Integer.parseInt(classesAttended);
                            ++classesAttendedInt;
                            reference2.child(Name).child("ClassesAttended").setValue(String.valueOf(classesAttendedInt));
                            reference2.child(Name).child("ClassesAttended").removeEventListener(this);

                            String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

                            reference2.child(Name).child("Class"+classesAttendedInt).setValue(date);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

                Context context = getApplicationContext();
                Toast submitToast = Toast.makeText(context, "Submitting...", Toast.LENGTH_SHORT);
                submitToast.show();

                finish();
            }
        });

    }

    public void CheckboxChecked(View view){
        View viewParent = (View) view.getParent();
        TextView Name = (TextView) viewParent.findViewById(R.id.AttendanceMemberName);

        checkedBoxes.add(Name.getText().toString());
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