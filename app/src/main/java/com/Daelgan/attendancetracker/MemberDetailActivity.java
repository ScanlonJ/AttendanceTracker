package com.Daelgan.attendancetracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MemberDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_detail);

        Intent intent = getIntent();
        String Name = intent.getExtras().getString("Name");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Members");

        reference.child(Name).child("Name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TextView detailMemberNameTV = (TextView) findViewById(R.id.detailMemberName2);
                detailMemberNameTV.setText(snapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Database read failed for Member: " + Name + ". Name Failed");
            }
        });

        reference.child(Name).child("DoB").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String DoB = snapshot.getValue(String.class);

                TextView detailMemberDoBTV = (TextView) findViewById(R.id.detailMemberDoB2);
                detailMemberDoBTV.setText(DoB);

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

                reference.child(Name).child("Age").setValue(String.valueOf(age));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Database read failed for Member: " + Name + ". DoB Failed");
            }
        });

        reference.child(Name).child("Age").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TextView detailMemberAgeTV = (TextView) findViewById(R.id.detailMemberAge2);
                detailMemberAgeTV.setText(snapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Database read failed for Member: " + Name + ". Age Failed");
            }
        });

        reference.child(Name).child("JoinDate").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TextView detailMemberJoinDateTV = (TextView) findViewById(R.id.detailMemberJoinDate2);
                detailMemberJoinDateTV.setText(snapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Database read failed for Member: " + Name + ". JoinDate Failed");
            }
        });

        reference.child(Name).child("BeltLevel").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TextView detailMemberBeltLevelTV = (TextView) findViewById(R.id.detailMemberBeltLevel2);
                detailMemberBeltLevelTV.setText(snapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Database read failed for Member: " + Name + ". BeltLevel Failed");
            }
        });

        reference.child(Name).child("DateOfLastTest").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TextView detailMemberDateOfLastTestTV = (TextView) findViewById(R.id.detailMemberDateOfLastTest2);
                detailMemberDateOfLastTestTV.setText(snapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Database read failed for Member: " + Name + ". DateOfLastTest Failed");
            }
        });

        reference.child(Name).child("ClassesSinceLastTest").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TextView detailMemberClassesSinceLastTestTV = (TextView) findViewById(R.id.detailMemberClassesAttendedSinceLastTest2);
                detailMemberClassesSinceLastTestTV.setText(snapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Database read failed for Member: " + Name + ". ClassesSinceLastTest Failed");
            }
        });

        reference.child(Name).child("ContactNumber").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TextView detailMemberContactNumberTV = (TextView) findViewById(R.id.detailMemberContactNumber2);
                detailMemberContactNumberTV.setText(snapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Database read failed for Member: " + Name + ". ContactNumber Failed");
            }
        });

        reference.child(Name).child("ContactEmail").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TextView detailMemberContactEmailTV = (TextView) findViewById(R.id.detailMemberContactEmail2);
                detailMemberContactEmailTV.setText(snapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Database read failed for Member: " + Name + ". ContactEmail Failed");
            }
        });

        reference.child(Name).child("ContactRelation").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TextView detailMemberContactRelationTV = (TextView) findViewById(R.id.detailMemberContactRelation2);
                detailMemberContactRelationTV.setText(snapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Database read failed for Member: " + Name + ". ContactRelation Failed");
            }
        });

        reference.child(Name).child("ContactName").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TextView detailMemberContactNameTV = (TextView) findViewById(R.id.detailMemberContactName2);
                detailMemberContactNameTV.setText(snapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Database read failed for Member: " + Name + ". ContactName Failed");
            }
        });

        reference.child(Name).child("ClassesPurchased").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TextView detailMemberClassesPurchasedTV = (TextView) findViewById(R.id.detailMemberClassesPurchased2);
                detailMemberClassesPurchasedTV.setText(snapshot.getValue(String.class));

                Task<DataSnapshot> t = reference.child(Name).child("ClassesAttended").get();
                t.addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {

                        String classesPurchased = snapshot.getValue(String.class);
                        int classesPurchasedInt;
                        if (classesPurchased.equals(""))
                            classesPurchasedInt = 0;
                        else
                            classesPurchasedInt = Integer.parseInt(classesPurchased);

                        String classesAttended = task.getResult().getValue(String.class);
                        int classesAttendedInt;
                        if(classesAttended.equals(""))
                            classesAttendedInt = 0;
                        else
                            classesAttendedInt = Integer.parseInt(classesAttended);

                        int classesRemainingInt = classesPurchasedInt - classesAttendedInt;
                        reference.child(Name).child("ClassesRemaining").setValue(String.valueOf(classesRemainingInt));
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Database read failed for Member: " + Name + ". ClassesPurchased Failed");
            }
        });

        reference.child(Name).child("ClassesAttended").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TextView detailMemberClassesAttendedTV = (TextView) findViewById(R.id.detailMemberClassesAttended2);
                detailMemberClassesAttendedTV.setText(snapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Database read failed for Member: " + Name + ". ClassesAttended Failed");
            }
        });

        reference.child(Name).child("ClassesRemaining").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TextView detailMemberClassesRemainingTV = (TextView) findViewById(R.id.detailMemberClassesRemaining2);
                detailMemberClassesRemainingTV.setText(snapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Database read failed for Member: " + Name + ". ClassesRemaining Failed");
            }
        });

        Button add1Button = (Button) findViewById(R.id.detailMemberAdd1ClassButton);
        Button add12Button = (Button) findViewById(R.id.detailMemberAdd12ClassButton);
        Button add50Button = (Button) findViewById(R.id.detailMemberAdd50ClassButton);

        add1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(Name).child("ClassesPurchased").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String classesPurchased = snapshot.getValue(String.class);
                        int classesPurchasedInt;
                        if (classesPurchased.equals(""))
                            classesPurchasedInt = 0;
                        else
                            classesPurchasedInt = Integer.parseInt(classesPurchased);
                        ++classesPurchasedInt;
                        reference.child(Name).child("ClassesPurchased").setValue(String.valueOf(classesPurchasedInt));
                        reference.child(Name).child("ClassesPurchased").removeEventListener(this);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                Context context = getApplicationContext();
                Toast submitToast = Toast.makeText(context, "Submitting...", Toast.LENGTH_SHORT);
                submitToast.show();
            }
        });

        add12Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(Name).child("ClassesPurchased").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String classesPurchased = snapshot.getValue(String.class);
                        int classesPurchasedInt;
                        if (classesPurchased.equals(""))
                            classesPurchasedInt = 0;
                        else
                            classesPurchasedInt = Integer.parseInt(classesPurchased);
                        classesPurchasedInt = classesPurchasedInt + 12;
                        reference.child(Name).child("ClassesPurchased").setValue(String.valueOf(classesPurchasedInt));
                        reference.child(Name).child("ClassesPurchased").removeEventListener(this);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                Context context = getApplicationContext();
                Toast submitToast = Toast.makeText(context, "Submitting...", Toast.LENGTH_SHORT);
                submitToast.show();
            }
        });

        add50Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(Name).child("ClassesPurchased").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String classesPurchased = snapshot.getValue(String.class);
                        int classesPurchasedInt;
                        if (classesPurchased.equals(""))
                            classesPurchasedInt = 0;
                        else
                            classesPurchasedInt = Integer.parseInt(classesPurchased);
                        classesPurchasedInt = classesPurchasedInt + 50;
                        reference.child(Name).child("ClassesPurchased").setValue(String.valueOf(classesPurchasedInt));
                        reference.child(Name).child("ClassesPurchased").removeEventListener(this);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                Context context = getApplicationContext();
                Toast submitToast = Toast.makeText(context, "Submitting...", Toast.LENGTH_SHORT);
                submitToast.show();
            }
        });
    }
}