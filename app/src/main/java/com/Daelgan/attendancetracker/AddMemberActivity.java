package com.Daelgan.attendancetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddMemberActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);

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

        Button submitButton = (Button) findViewById(R.id.addMemberSubmitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Members");

                View parentView = (View) v.getParent();

                EditText NameET                 = (EditText)parentView.findViewById(R.id.addMemberNameEditText);
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

                reference.child(Name).child("Name").setValue(Name);
                reference.child(Name).child("DoB").setValue(DoB);
                reference.child(Name).child("Age").setValue(String.valueOf(age));
                reference.child(Name).child("JoinDate").setValue(JoinDate);
                reference.child(Name).child("BeltLevel").setValue(BeltLevel);
                reference.child(Name).child("BeltRank").setValue(BeltLevelRank);
                reference.child(Name).child("DateOfLastTest").setValue(DateOfLastTest);
                reference.child(Name).child("ClassesSinceLastTest").setValue(ClassesSinceLastTest);
                reference.child(Name).child("ContactNumber").setValue(ContactNumber);
                reference.child(Name).child("ContactEmail").setValue(ContactEmail);
                reference.child(Name).child("ContactRelation").setValue(ContactRelation);
                reference.child(Name).child("ContactName").setValue(ContactName);
                reference.child(Name).child("ClassesPurchased").setValue(ClassesPurchased);
                reference.child(Name).child("ClassesAttended").setValue(ClassesAttended);

                reference = FirebaseDatabase.getInstance().getReference("Classes");

                reference.child(Name).child("Name").setValue(Name);

                if(ClassesAttended.equals(""))
                    reference.child(Name).child("ClassesAttended").setValue("0");
                else
                    reference.child(Name).child("ClassesAttended").setValue(ClassesAttended);

                Context context = getApplicationContext();
                Toast submitToast = Toast.makeText(context, "Submitting...", Toast.LENGTH_SHORT);
                submitToast.show();

                finish();
            }
        });
    }
}