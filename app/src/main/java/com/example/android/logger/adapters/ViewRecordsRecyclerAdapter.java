package com.example.android.logger.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.logger.R;
import com.example.android.logger.models.Employee;

import java.util.ArrayList;

public class ViewRecordsRecyclerAdapter extends RecyclerView.Adapter<ViewRecordsRecyclerAdapter.RecordsViewHolder> {

    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    ArrayList<Employee> mRecordsList;

    public ViewRecordsRecyclerAdapter(Context context, ArrayList<Employee> recordsList) {
        mContext = context;
        mRecordsList = recordsList;
        mLayoutInflater = LayoutInflater.from(mContext);
    }


    @NonNull
    @Override
    public RecordsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.rv_list_item_view, parent, false);

        RecordsViewHolder recordsViewHolder = new RecordsViewHolder(itemView);
        return recordsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecordsViewHolder holder, int position) {
        Employee employee = mRecordsList.get(position);

        holder.nameTextView.setText(employee.getEmployeeName());
        holder.dateTextView.setText(employee.getAttendanceDate());
        holder.timeTextView.setText(employee.getAttendanceTime());

    }

    @Override
    public int getItemCount() {
        return mRecordsList.size();
    }

    public class RecordsViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameTextView;
        private final TextView dateTextView;
        private final TextView timeTextView;

        public RecordsViewHolder(View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.tv_attendance_name);
            dateTextView = itemView.findViewById(R.id.tv_attendance_date);
            timeTextView = itemView.findViewById(R.id.tv_attendance_time);


        }
    }



}
