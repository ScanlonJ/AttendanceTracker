package com.Daelgan.attendancetracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
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
    int count= 0;

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

        DatabaseReference familyCountReference = FirebaseDatabase.getInstance().getReference("FamilyCount").child("Count");

        final int[] numFamilies = new int[1];
        familyCountReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                numFamilies[0] = Integer.parseInt(snapshot.getValue(String.class));
                System.out.println("out -1 " + numFamilies[0]);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Button submitButton = (Button) findViewById(R.id.attendanceSubmitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println(" out 0 " + numFamilies[0]);
                int familyCounts[] = new int[numFamilies[0]+1];
                for( int i = 0; i <= numFamilies[0]; i++){
                    familyCounts[i] = 0;
                }

                for (int i = 0; i < checkedBoxes.size(); i++) {

                    final String Name = checkedBoxes.get(i);
                    System.out.println("out - " + Name);

                    String names[] = Name.split(" ");

                    String firstName = names[0];
                    String lastName = names[1];
                    String fullName = firstName + "-" + lastName;

                    onMemberCountStart();
                    DatabaseReference memberReference = FirebaseDatabase.getInstance().getReference("Members").child(fullName);
                    DatabaseReference memberClassesReference = FirebaseDatabase.getInstance().getReference("Classes").child(fullName);

                    memberReference.child("ClassesSinceLastTest").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String ClassesSinceLastTest = snapshot.getValue(String.class);
                            int ClassesSinceLastTestInt;
                            if (ClassesSinceLastTest.equals(""))
                                ClassesSinceLastTestInt = 0;
                            else
                                ClassesSinceLastTestInt = Integer.parseInt(ClassesSinceLastTest);
                            ++ClassesSinceLastTestInt;
                            memberReference.child("ClassesSinceLastTest").setValue(String.valueOf(ClassesSinceLastTestInt));
                            memberReference.child("ClassesSinceLastTest").removeEventListener(this);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    memberClassesReference.child("ClassesAttended").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String classesAttended = snapshot.getValue(String.class);
                            int classesAttendedInt;
                            if (classesAttended.equals(""))
                                classesAttendedInt = 0;
                            else
                                classesAttendedInt = Integer.parseInt(classesAttended);
                            ++classesAttendedInt;
                            memberClassesReference.child("ClassesAttended").setValue(String.valueOf(classesAttendedInt));
                            memberClassesReference.child("ClassesAttended").removeEventListener(this);

                            String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

                            memberClassesReference.child("Class" + classesAttendedInt).setValue(date);

                            memberClassesReference.child("ClassesAttended").removeEventListener(this);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    String familyID[] = new String[1];
                    memberReference.child("FamilyID").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            familyID[0] = snapshot.getValue(String.class);

                            int familyIDInt;
                            if (familyID[0] == null || familyID[0].equals(""))
                                familyIDInt = 0;
                            else
                                familyIDInt = Integer.parseInt(familyID[0]);

                            familyCounts[familyIDInt]++;

                            onSuccess(familyCounts);
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

    public void onMemberCountStart(){
        count++;
    }

    public void onSuccess(int[] familyCounts){
        count--;

        if(count == 0)
            doClassesRemainingAdjustment(familyCounts);
    }

    public void doClassesRemainingAdjustment(int[] familyCounts){
        DatabaseReference familyReference = FirebaseDatabase.getInstance().getReference("Families");

        for(int i = 1; i < familyCounts.length; i++) {
            System.out.println("out3 - "+ familyCounts[i]);
            if(familyCounts[i] > 0){
                int temp = familyCounts[i];
                int tempI = i;
                familyReference.child(String.valueOf(i)).child("ClassesRemaining").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String ClassesRemaining = snapshot.getValue(String.class);
                        System.out.println("out2 - " + ClassesRemaining);
                        int ClassesRemainingInt;
                        if (ClassesRemaining == null || ClassesRemaining.equals(""))
                            ClassesRemainingInt = 0;
                        else
                            ClassesRemainingInt = Integer.parseInt(ClassesRemaining);

                        System.out.println("out4 - " + ClassesRemainingInt);
                        System.out.println("out5 - " + temp);
                        ClassesRemainingInt = ClassesRemainingInt - temp;
                        System.out.println("out6 - " + ClassesRemainingInt);
                        familyReference.child(String.valueOf(tempI)).child("ClassesRemaining").setValue(String.valueOf(ClassesRemainingInt));

                        familyReference.child(String.valueOf(tempI)).child("ClassesRemaining").removeEventListener(this);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }

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