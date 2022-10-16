package com.Daelgan.attendancetracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

public class AddMemberActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);

        Spinner BeltLevelSpinner = setupBeltListSpinner();

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();

        EditText JoinDateET = (EditText)this.findViewById(R.id.addMemberJoinDateET);
        JoinDateET.setText(format.format(date));

        EditText DoLTET = (EditText)this.findViewById(R.id.addMemberDateOfLastTestET);
        DoLTET.setText(format.format(date));

        EditText ClassesSinceLastTestET = (EditText) this.findViewById(R.id.addMemberClassesSinceLastTestET);
        ClassesSinceLastTestET.setText("0");

        EditText ClassesPurchasedET = (EditText) this.findViewById(R.id.addMemberClassesPurchasedET);
        ClassesPurchasedET.setText("0");

        EditText ClassesAttendedET = (EditText) this.findViewById(R.id.addMemberClassesAttendedET);
        ClassesAttendedET.setText("0");

        Button cancelButton = (Button) findViewById(R.id.addMemberCancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addSubmitButtonOnClickListener(BeltLevelSpinner);
    }

    private Spinner setupBeltListSpinner()
    {
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
        Spinner BeltLevelSpinner = (Spinner) findViewById(R.id.addMemberBeltLevelSpinner);
        BeltLevelSpinner.setAdapter(BeltLevelListAdapter);

        return BeltLevelSpinner;
    }

    private void addSubmitButtonOnClickListener(Spinner BeltLevelSpinner){
        Button submitButton = (Button) findViewById(R.id.addMemberSubmitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference membersReference = FirebaseDatabase.getInstance().getReference("Members");
                DatabaseReference membersCountReference = FirebaseDatabase.getInstance().getReference("memberCount");
                DatabaseReference familyReference = FirebaseDatabase.getInstance().getReference("Families");
                DatabaseReference familyCountReference = FirebaseDatabase.getInstance().getReference("FamilyCount");

                View parentView = (View) v.getParent();

                EditText NameET                 = (EditText)parentView.findViewById(R.id.addMemberNameEditText);
                EditText FamilyNameET           = (EditText)parentView.findViewById(R.id.addMemberFamilyNameEditText);
                EditText DoBET                  = (EditText)parentView.findViewById(R.id.addMemberDoBET);
                EditText JoinDateET             = (EditText)parentView.findViewById(R.id.addMemberJoinDateET);
                EditText DateOfLastTestET       = (EditText)parentView.findViewById(R.id.addMemberDateOfLastTestET);
                EditText ClassesSinceLastTestET = (EditText)parentView.findViewById(R.id.addMemberClassesSinceLastTestET);
                EditText ContactNumberET        = (EditText)parentView.findViewById(R.id.addMemberContactNumberET);
                EditText ContactEmailET         = (EditText)parentView.findViewById(R.id.addMemberContactEmailET);
                EditText ContactRelationET      = (EditText)parentView.findViewById(R.id.addMemberContactRelationET);
                EditText ContactNameET          = (EditText)parentView.findViewById(R.id.addMemberContactNameET);
                EditText ClassesPurchasedET     = (EditText)parentView.findViewById(R.id.addMemberClassesPurchasedET);
                EditText ClassesAttendedET      = (EditText)parentView.findViewById(R.id.addMemberClassesAttendedET);

                String Name                 = NameET.getText().toString();
                String FamilyName           = FamilyNameET.getText().toString();
                String DoB                  = DoBET.getText().toString();
                String JoinDate             = JoinDateET.getText().toString();
                String BeltLevel            = BeltLevelSpinner.getSelectedItem().toString();
                String BeltLevelRank        = Integer.toString(BeltLevelSpinner.getSelectedItemPosition());
                String DateOfLastTest       = DateOfLastTestET.getText().toString();
                String ClassesSinceLastTest = ClassesSinceLastTestET.getText().toString();
                String ContactNumber        = ContactNumberET.getText().toString();
                String ContactEmail         = ContactEmailET.getText().toString();
                String ContactRelation      = ContactRelationET.getText().toString();
                String ContactName          = ContactNameET.getText().toString();
                String ClassesPurchased     = ClassesPurchasedET .getText().toString();
                String ClassesAttended      = ClassesAttendedET.getText().toString();

                Calendar now = Calendar.getInstance();
                Calendar dob = Calendar.getInstance();

                if(DoB.length() >= 7)
                    dob.set(Integer.parseInt(DoB.substring(6)), Integer.parseInt(DoB.substring(3,4)), Integer.parseInt(DoB.substring(0, 1)));

                int year1 = now.get(Calendar.YEAR);
                int year2 = dob.get(Calendar.YEAR);
                int age = year1 - year2;
                int month1 = now.get(Calendar.MONTH);
                int month2 = dob.get(Calendar.MONTH);
                if (month2 > month1) {
                    age--;
                }
                else if (month1 == month2) {
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
                        if(memberCount == null || memberCount.equals(""))
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

                //Increment Family Count and add Family to database
                DatabaseReference finalMembersReference = membersReference;
                familyCountReference.child("Count").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String familyCount = snapshot.getValue(String.class);
                        int familyCountInt;
                        if(familyCount.equals(""))
                            familyCountInt = 0;
                        else
                            familyCountInt = Integer.parseInt(familyCount);
                        ++familyCountInt;

                        String newFamilyCount = String.valueOf(familyCountInt);

                        familyCountReference.child("Count").setValue(newFamilyCount);

                        finalMembersReference.child(Name+"-"+FamilyName).child("FamilyID").setValue(newFamilyCount);
                        familyReference.child(newFamilyCount).child("FamilyName").setValue(FamilyName);
                        familyReference.child(newFamilyCount).child("ContactNumber").setValue(ContactNumber);
                        familyReference.child(newFamilyCount).child("ContactEmail").setValue(ContactEmail);
                        familyReference.child(newFamilyCount).child("ContactRelation").setValue(ContactRelation);
                        familyReference.child(newFamilyCount).child("ContactName").setValue(ContactName);
                        familyReference.child(newFamilyCount).child("ClassesPurchased").setValue(ClassesPurchased);

                        int classesRemaining = Integer.parseInt(ClassesPurchased) - Integer.parseInt(ClassesAttended);
                        familyReference.child(newFamilyCount).child("ClassesRemaining").setValue(String.valueOf(classesRemaining));

                        familyCountReference.child("Count").removeEventListener(this);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                membersReference.child(Name+"-"+FamilyName).child("Name").setValue(Name);
                membersReference.child(Name+"-"+FamilyName).child("FamilyName").setValue(FamilyName);
                membersReference.child(Name+"-"+FamilyName).child("DoB").setValue(DoB);
                membersReference.child(Name+"-"+FamilyName).child("Age").setValue(String.valueOf(age));
                membersReference.child(Name+"-"+FamilyName).child("JoinDate").setValue(JoinDate);
                membersReference.child(Name+"-"+FamilyName).child("BeltLevel").setValue(BeltLevel);
                membersReference.child(Name+"-"+FamilyName).child("BeltRank").setValue(BeltLevelRank);
                membersReference.child(Name+"-"+FamilyName).child("DateOfLastTest").setValue(DateOfLastTest);
                membersReference.child(Name+"-"+FamilyName).child("ClassesSinceLastTest").setValue(ClassesSinceLastTest);

                membersReference = FirebaseDatabase.getInstance().getReference("Classes");

                membersReference.child(Name+"-"+FamilyName).child("Name").setValue(Name);

                if(ClassesAttended.equals(""))
                    membersReference.child(Name+"-"+FamilyName).child("ClassesAttended").setValue("0");
                else
                    membersReference.child(Name+"-"+FamilyName).child("ClassesAttended").setValue(ClassesAttended);

                Context context = getApplicationContext();
                Toast submitToast = Toast.makeText(context, "Submitting...", Toast.LENGTH_SHORT);
                submitToast.show();

                finish();
            }
        });
    }
}