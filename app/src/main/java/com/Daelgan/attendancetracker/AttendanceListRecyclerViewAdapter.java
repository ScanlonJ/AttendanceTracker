package com.Daelgan.attendancetracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AttendanceListRecyclerViewAdapter extends FirebaseRecyclerAdapter<Member, AttendanceListRecyclerViewAdapter.Attendance2ListMemberViewHolder> {

    AttendanceListRecyclerViewAdapter(@NonNull FirebaseRecyclerOptions<Member> options)
    {
        super(options);
    }

    // inflates the row layout from xml when needed
    @Override
    public Attendance2ListMemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.attendance_list_item, parent, false);
        return new AttendanceListRecyclerViewAdapter.Attendance2ListMemberViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(@NonNull Attendance2ListMemberViewHolder holder, int position, @NonNull Member model) {
        String name = model.getName() + " " + model.getFamilyName();
        holder.Name.setText(name);
        holder.checkBox.setChecked(false);

        DatabaseReference memberReference = FirebaseDatabase.getInstance().getReference("Members").child(model.getName() + "-" + model.getFamilyName());
        memberReference.child("FamilyID").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String FamilyID = snapshot.getValue(String.class);

                DatabaseReference familyReference = FirebaseDatabase.getInstance().getReference("Families").child(FamilyID);

                familyReference.child("ClassesRemaining").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        holder.ClassesRemaining.setText(snapshot.getValue(String.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    class Attendance2ListMemberViewHolder extends RecyclerView.ViewHolder {
        TextView Name;
        TextView ClassesRemaining;
        CheckBox checkBox;

        public Attendance2ListMemberViewHolder(@NonNull View itemView){
            super(itemView);

            Name = itemView.findViewById(R.id.AttendanceMemberName);
            ClassesRemaining = itemView.findViewById(R.id.AttendanceMemberClassesRemaining);
            checkBox = itemView.findViewById(R.id.AttendanceCheckBox);
        }
    }
}