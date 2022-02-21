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
        holder.Name.setText((model.getName()));
        holder.ClassesRemaining.setText(model.getClassesRemaining());
        holder.checkBox.setChecked(false);
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