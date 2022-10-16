package com.Daelgan.attendancetracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddFamilyMemberActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_family_member);

        Intent intent = getIntent();
        String fullName = intent.getExtras().getString("Name");

        DatabaseReference memberReference = FirebaseDatabase.getInstance().getReference("Members").child(fullName);

        Spinner BeltLevelSpinner = setupBeltListSpinner();

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();

        EditText JoinDateET = (EditText) this.findViewById(R.id.addFamilyMemberJoinDateET);
        JoinDateET.setText(format.format(date));

        EditText DoLTET = (EditText) this.findViewById(R.id.addFamilyMemberDateOfLastTestET);
        DoLTET.setText(format.format(date));

        EditText ClassesSinceLastTestET = (EditText) this.findViewById(R.id.addFamilyMemberClassesSinceLastTestET);
        ClassesSinceLastTestET.setText("0");

        EditText ClassesAttendedET = (EditText) this.findViewById(R.id.addFamilyMemberClassesAttendedET);
        ClassesAttendedET.setText("0");

        Button cancelButton = (Button) findViewById(R.id.addFamilyMemberCancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addSubmitButtonOnClickListener(BeltLevelSpinner, fullName);
    }

    private Spinner setupBeltListSpinner() {
        List<String> BeltLevelsList = new ArrayList<String>();
        BeltLevelsList.add("White");
        BeltLevelsList.add("Yellow Stripe");
        BeltLevelsList.add("Yellow");
        BeltLevelsList.add("Orange");
        BeltLevelsList.add("Green Stripe");
        BeltLevelsList.add("Green");
        BeltLevelsList.add("Blue");
        BeltLevelsList.add("Red Stripe");
        BeltLevelsList.add("Red");
        BeltLevelsList.add("Black Stripe");
        BeltLevelsList.add("Black");

        ArrayAdapter<String> BeltLevelListAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, BeltLevelsList);

        BeltLevelListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner BeltLevelSpinner = (Spinner) findViewById(R.id.addFamilyMemberBeltLevelSpinner);
        BeltLevelSpinner.setAdapter(BeltLevelListAdapter);

        return BeltLevelSpinner;
    }

    private void addSubmitButtonOnClickListener(Spinner BeltLevelSpinner, String fullName) {
        Button submitButton = (Button) findViewById(R.id.addFamilyMemberSubmitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference memberReference = FirebaseDatabase.getInstance().getReference("Members");
                DatabaseReference membersCountReference = FirebaseDatabase.getInstance().getReference("memberCount");

                String names[] = fullName.split("-");
                String familyName = names[1];

                View parentView = (View) v.getParent();

                EditText NameET = (EditText) parentView.findViewById(R.id.addFamilyMemberNameEditText);
                EditText DoBET = (EditText) parentView.findViewById(R.id.addFamilyMemberDoBET);
                EditText JoinDateET = (EditText) parentView.findViewById(R.id.addFamilyMemberJoinDateET);
                EditText DateOfLastTestET = (EditText) parentView.findViewById(R.id.addFamilyMemberDateOfLastTestET);
                EditText ClassesSinceLastTestET = (EditText) parentView.findViewById(R.id.addFamilyMemberClassesSinceLastTestET);
                EditText ClassesAttendedET = (EditText) parentView.findViewById(R.id.addFamilyMemberClassesAttendedET);

                String Name = NameET.getText().toString();
                String DoB = DoBET.getText().toString();
                String JoinDate = JoinDateET.getText().toString();
                String BeltLevel = BeltLevelSpinner.getSelectedItem().toString();
                String BeltLevelRank = Integer.toString(BeltLevelSpinner.getSelectedItemPosition());
                String DateOfLastTest = DateOfLastTestET.getText().toString();
                String ClassesSinceLastTest = ClassesSinceLastTestET.getText().toString();
                String ClassesAttended = ClassesAttendedET.getText().toString();

                Calendar now = Calendar.getInstance();
                Calendar dob = Calendar.getInstance();

                if (DoB.length() >= 7)
                    dob.set(Integer.parseInt(DoB.substring(6)), Integer.parseInt(DoB.substring(3, 4)), Integer.parseInt(DoB.substring(0, 1)));

                int year1 = now.get(Calendar.YEAR);
                int year2 = dob.get(Calendar.YEAR);
                int age = year1 - year2;
                int month1 = now.get(Calendar.MONTH);
                int month2 = dob.get(Calendar.MONTH);
                if (month2 > month1) {
                    age--;
                } else if (month1 == month2) {
                    int day1 = now.get(Calendar.DAY_OF_MONTH);
                    int day2 = dob.get(Calendar.DAY_OF_MONTH);
                    if (day2 > day1) {
                        age--;
                    }
                }

                //Increment Member count
                membersCountReference.child("Count").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String memberCount = snapshot.getValue(String.class);
                        int memberCountInt;
                        if (memberCount == null || memberCount.equals(""))
                            memberCountInt = 0;
                        else
                            memberCountInt = Integer.parseInt(memberCount);
                        ++memberCountInt;

                        membersCountReference.child("Count").setValue(String.valueOf(memberCountInt));
                        membersCountReference.child("Count").removeEventListener(this);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                DatabaseReference finalMemberReference = memberReference;
                memberReference.child(fullName).child("FamilyID").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String familyID = snapshot.getValue(String.class);
                        int familyIDInt;
                        if( familyID == null || familyID.equals(""))
                            familyIDInt = 0;
                        else
                            familyIDInt = Integer.parseInt(familyID);

                        finalMemberReference.child(Name + "-" + familyName).child("FamilyID").setValue(String.valueOf(familyIDInt));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                memberReference.child(Name + "-" + familyName).child("Name").setValue(Name);
                memberReference.child(Name + "-" + familyName).child("FamilyName").setValue(familyName);
                memberReference.child(Name + "-" + familyName).child("FamilyID").setValue("1");
                memberReference.child(Name + "-" + familyName).child("DoB").setValue(DoB);
                memberReference.child(Name + "-" + familyName).child("Age").setValue(String.valueOf(age));
                memberReference.child(Name + "-" + familyName).child("JoinDate").setValue(JoinDate);
                memberReference.child(Name + "-" + familyName).child("BeltLevel").setValue(BeltLevel);
                memberReference.child(Name + "-" + familyName).child("BeltRank").setValue(BeltLevelRank);
                memberReference.child(Name + "-" + familyName).child("DateOfLastTest").setValue(DateOfLastTest);
                memberReference.child(Name + "-" + familyName).child("ClassesSinceLastTest").setValue(ClassesSinceLastTest);

                memberReference = FirebaseDatabase.getInstance().getReference("Classes");

                memberReference.child(Name + "-" + familyName).child("Name").setValue(Name);

                if (ClassesAttended.equals(""))
                    memberReference.child(Name + "-" + familyName).child("ClassesAttended").setValue("0");
                else
                    memberReference.child(Name + "-" + familyName).child("ClassesAttended").setValue(ClassesAttended);

                Context context = getApplicationContext();
                Toast submitToast = Toast.makeText(context, "Submitting...", Toast.LENGTH_SHORT);
                submitToast.show();

                finish();
            }
        });
    }
}

