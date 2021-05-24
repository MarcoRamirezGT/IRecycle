package com.example.irecycle;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class BottomSheetMap {

    private final Activity activity;
    String title, address, hours;
    TextView titleText, addressText, hoursText;
    ImageView imageViewDrive;

    // CLASS CONSTRUCTOR
    public BottomSheetMap(Activity myActivity, String myTitle, String myAddress, String myHours) {
        activity = myActivity;
        title = myTitle;
        address = myAddress;
        hours = myHours;
    }

    public void startChangePriorityBottomSheet() {


        @SuppressLint("InflateParams")
        LayoutInflater inflater = activity.getLayoutInflater();

        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.bottom_sheet_map, null);
        final BottomSheetDialog dialog = new BottomSheetDialog(activity);

        // FIND IDs
        titleText=  view.findViewById(R.id.textTitle);
        addressText = view.findViewById(R.id.textAddress);
        hoursText = view.findViewById(R.id.textHours);
        imageViewDrive = view.findViewById(R.id.imageViewDrive);


        titleText.setText(title);
        addressText.setText(address);
        hoursText.setText(hours);
        imageViewDrive.setImageResource(R.drawable.ic_baseline_drive_eta);

        dialog.setContentView(view);


//        Log.d("Edman", currentUserId);


        dialog.show();
    }
}
