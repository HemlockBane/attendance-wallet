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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ViewRecordsRecyclerAdapter extends RecyclerView.Adapter<ViewRecordsRecyclerAdapter.RecordsViewHolder> {

    public String TIME_TEMPLATE = "HH:mm aa";
    public String DAY_TEMPLATE = "EEE dd MMM";

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


        long time = employee.getAttendanceTimeInMilliseconds();
        Date dateObject = new Date(time);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateObject);

        holder.nameTextView.setText(employee.getEmployeeName());
        holder.dateTextView.setText(formatDate(dateObject));
        holder.timeTextView.setText(formatTime(dateObject));

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

    public String formatDate(Date date) {
        SimpleDateFormat timeFormat = new SimpleDateFormat(TIME_TEMPLATE);
        return timeFormat.format(date);
    }

    public String formatTime(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DAY_TEMPLATE);
        return dateFormat.format(date);
    }


}
