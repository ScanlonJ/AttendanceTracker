package com.Daelgan.attendancetracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class MemberListRecyclerViewAdapter extends FirebaseRecyclerAdapter<Member, MemberListRecyclerViewAdapter.memberViewHolder> {

    MemberListRecyclerViewAdapter(@NonNull FirebaseRecyclerOptions<Member> options)
    {
        super(options);
    }

    // inflates the row layout from xml when needed
    @Override
    public memberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.members_list_item, parent, false);
        return new MemberListRecyclerViewAdapter.memberViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(@NonNull memberViewHolder holder, int position, @NonNull Member model) {
        holder.Name.setText((model.getName()));
    }


    class memberViewHolder extends RecyclerView.ViewHolder {
        TextView Name;

        public memberViewHolder(@NonNull View itemView){
            super(itemView);

            Name = itemView.findViewById(R.id.MemberName);
        }
    }
}