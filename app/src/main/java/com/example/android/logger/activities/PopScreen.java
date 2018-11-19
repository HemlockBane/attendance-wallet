package com.example.android.logger.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.logger.R;

public class PopScreen extends AppCompatDialogFragment {
    TextView dateText;
    TextView monthText;
    TextView yearText;
    private  PopScreenListener popScreenListener;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.pop_up_sort, null);

        dateText = view.findViewById(R.id.et_popup_date);
        monthText = view.findViewById(R.id.et_popup_month);
        yearText = view.findViewById(R.id.et_popup_year);

        builder.setView(view).
                setTitle("Sort")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String date = dateText.getText().toString();
                        String month = monthText.getText().toString();
                        String year = yearText.getText().toString();

                        popScreenListener.applyText(date, month, year);
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            popScreenListener = (PopScreenListener)context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "implement the PopScreenListener");
        }
    }

    public interface PopScreenListener{
        void applyText(String date, String month, String year);
    }
}
