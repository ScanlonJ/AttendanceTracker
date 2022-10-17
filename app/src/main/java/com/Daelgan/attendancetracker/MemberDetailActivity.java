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

import java.util.Calendar;

public class MemberDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_detail);

        Intent intent = getIntent();
        String Name = intent.getExtras().getString("Name");

        String Names[] = Name.split(" ");

        String firstName = Names[0];
        String lastName = Names[1];
        String fullName = firstName + "-" + lastName;

        DatabaseReference memberReference = FirebaseDatabase.getInstance().getReference("Members").child(fullName);
        DatabaseReference memberClassesReference = FirebaseDatabase.getInstance().getReference("Classes").child(fullName);

        memberReference.child("FamilyID").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String familyID = snapshot.getValue(String.class);
                DatabaseReference familyReference = FirebaseDatabase.getInstance().getReference("Families").child(familyID);

                familyReference.child("ContactNumber").addValueEventListener(new ValueEventListener() {
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

                familyReference.child("ContactEmail").addValueEventListener(new ValueEventListener() {
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

                familyReference.child("ContactRelation").addValueEventListener(new ValueEventListener() {
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

                familyReference.child("ContactName").addValueEventListener(new ValueEventListener() {
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

                familyReference.child("ClassesPurchased").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        TextView detailMemberClassesPurchasedTV = (TextView) findViewById(R.id.detailMemberClassesPurchased2);
                        detailMemberClassesPurchasedTV.setText(snapshot.getValue(String.class));

                        /*Task<DataSnapshot> t = memberClassesReference.child("ClassesAttended").get();
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
                                if(classesAttended != null && classesAttended.equals(""))
                                    classesAttendedInt = 0;
                                else
                                    classesAttendedInt = Integer.parseInt(classesAttended);

                                int classesRemainingInt = classesPurchasedInt - classesAttendedInt;
                                familyReference.child("ClassesRemaining").setValue(String.valueOf(classesRemainingInt));
                            }
                        });*/
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        System.out.println("Database read failed for Member: " + Name + ". ClassesPurchased Failed");
                    }
                });

                familyReference.child("ClassesRemaining").addValueEventListener(new ValueEventListener() {
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
                        familyReference.child("ClassesPurchased").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String classesPurchased = snapshot.getValue(String.class);
                                int classesPurchasedInt;
                                if (classesPurchased.equals(""))
                                    classesPurchasedInt = 0;
                                else
                                    classesPurchasedInt = Integer.parseInt(classesPurchased);
                                ++classesPurchasedInt;
                                familyReference.child("ClassesPurchased").setValue(String.valueOf(classesPurchasedInt));
                                familyReference.child("ClassesPurchased").removeEventListener(this);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        familyReference.child("ClassesRemaining").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String ClassesRemaining = snapshot.getValue(String.class);
                                int ClassesRemainingInt;
                                if (ClassesRemaining.equals(""))
                                    ClassesRemainingInt = 0;
                                else
                                    ClassesRemainingInt = Integer.parseInt(ClassesRemaining);
                                ++ClassesRemainingInt;
                                familyReference.child("ClassesRemaining").setValue(String.valueOf(ClassesRemainingInt));
                                familyReference.child("ClassesRemaining").removeEventListener(this);
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
                        familyReference.child("ClassesPurchased").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String classesPurchased = snapshot.getValue(String.class);
                                int classesPurchasedInt;
                                if (classesPurchased.equals(""))
                                    classesPurchasedInt = 0;
                                else
                                    classesPurchasedInt = Integer.parseInt(classesPurchased);
                                classesPurchasedInt = classesPurchasedInt + 12;
                                familyReference.child("ClassesPurchased").setValue(String.valueOf(classesPurchasedInt));
                                familyReference.child("ClassesPurchased").removeEventListener(this);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        familyReference.child("ClassesRemaining").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String ClassesRemaining = snapshot.getValue(String.class);
                                int ClassesRemainingInt;
                                if (ClassesRemaining.equals(""))
                                    ClassesRemainingInt = 0;
                                else
                                    ClassesRemainingInt = Integer.parseInt(ClassesRemaining);
                                ClassesRemainingInt = ClassesRemainingInt + 12;
                                familyReference.child("ClassesRemaining").setValue(String.valueOf(ClassesRemainingInt));
                                familyReference.child("ClassesRemaining").removeEventListener(this);
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
                        familyReference.child("ClassesPurchased").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String classesPurchased = snapshot.getValue(String.class);
                                int classesPurchasedInt;
                                if (classesPurchased.equals(""))
                                    classesPurchasedInt = 0;
                                else
                                    classesPurchasedInt = Integer.parseInt(classesPurchased);
                                classesPurchasedInt = classesPurchasedInt + 50;
                                familyReference.child("ClassesPurchased").setValue(String.valueOf(classesPurchasedInt));
                                familyReference.child("ClassesPurchased").removeEventListener(this);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        familyReference.child("ClassesRemaining").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String ClassesRemaining = snapshot.getValue(String.class);
                                int ClassesRemainingInt;
                                if (ClassesRemaining.equals(""))
                                    ClassesRemainingInt = 0;
                                else
                                    ClassesRemainingInt = Integer.parseInt(ClassesRemaining);
                                ClassesRemainingInt = ClassesRemainingInt + 50;
                                familyReference.child("ClassesRemaining").setValue(String.valueOf(ClassesRemainingInt));
                                familyReference.child("ClassesRemaining").removeEventListener(this);
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

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Database read failed for Member: " + Name + ". Name Failed");
            }
        });

        memberReference.child("Name").addValueEventListener(new ValueEventListener() {
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

        memberReference.child("FamilyName").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TextView detailMemberFamilyNameTV = (TextView) findViewById(R.id.detailMemberFamilyName);
                detailMemberFamilyNameTV.setText(snapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Database read failed for Member: " + Name + ". Name Failed");
            }
        });

        memberReference.child("DoB").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String DoB = snapshot.getValue(String.class);

                TextView detailMemberDoBTV = (TextView) findViewById(R.id.detailMemberDoB2);
                detailMemberDoBTV.setText(DoB);

                Calendar now = Calendar.getInstance();
                Calendar dob = Calendar.getInstance();

                if( (DoB != null ? DoB.length() : 0) >= 7)
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

                memberReference.child("Age").setValue(String.valueOf(age));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Database read failed for Member: " + Name + ". DoB Failed");
            }
        });

        memberReference.child("Age").addValueEventListener(new ValueEventListener() {
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

        memberReference.child("JoinDate").addValueEventListener(new ValueEventListener() {
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

        memberReference.child("BeltLevel").addValueEventListener(new ValueEventListener() {
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

        memberReference.child("DateOfLastTest").addValueEventListener(new ValueEventListener() {
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

        memberReference.child("ClassesSinceLastTest").addValueEventListener(new ValueEventListener() {
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

        memberClassesReference.child("ClassesAttended").addValueEventListener(new ValueEventListener() {
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

        Button promoteButton = (Button) findViewById(R.id.detailMemberPromotionButton);
        Button addFamilyMemberButton = (Button) findViewById(R.id.detailMemberAddFamilyMemberButton);

        promoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String beltLevels[] = {"White", "Yellow Stripe", "Yellow", "Orange", "Green Stripe", "Green", "Blue", "Red Stripe", "Red", "Black Stripe", "Black"};

                memberReference.child("BeltRank").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String BeltRank = snapshot.getValue(String.class);
                        int beltRank = Integer.parseInt(BeltRank);

                        if( beltRank < 10){
                            ++beltRank;

                            memberReference.child("BeltRank").setValue(String.valueOf(beltRank));
                            memberReference.child("BeltLevel").setValue(beltLevels[beltRank]);
                            memberReference.child("ClassesSinceLastTest").setValue("0");

                            Calendar now = Calendar.getInstance();
                            int year = now.get(Calendar.YEAR);
                            int month = now.get(Calendar.MONTH);
                            int day = now.get(Calendar.DAY_OF_MONTH);

                            memberReference.child("DateOfLastTest").setValue("" + day + "/" + month + "/" + year);
                        }

                        memberReference.child("BeltRank").removeEventListener(this);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        System.out.println("Database read failed for Member: " + Name + ". BeltRank Failed");
                    }
                });
            }
        });

        addFamilyMemberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StartAddFamilyMemberActivity(v);
            }
        });
    }

    void StartAddFamilyMemberActivity( View view ) {
        Intent intent = new Intent(this, AddFamilyMemberActivity.class);

        View parent = (View) view.getParent();
        TextView t = parent.findViewById(R.id.detailMemberName2);
        TextView t2 = parent.findViewById(R.id.detailMemberFamilyName);

        String fullName = t.getText().toString() + "-" + t2.getText().toString();
        intent.putExtra("Name", fullName);

        startActivity(intent);
    }

}