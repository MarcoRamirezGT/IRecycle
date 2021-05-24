package com.example.irecycle;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class BottomSheetMap {

    private final Activity activity;
    String title, address, hours;
    TextView titleText, addressText, hoursText;
    ImageView imageViewDrive;
    LinearLayout layoutDrive;

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
        layoutDrive = view.findViewById(R.id.layoutDrive);


        titleText.setText(title);
        addressText.setText(address);
        hoursText.setText(hours);
        imageViewDrive.setImageResource(R.drawable.ic_baseline_drive_eta);

        imageViewDrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (title.equalsIgnoreCase("Universidad del Valle de Guatemala")) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("geo: 14.604015374142557, -90.48923844481173"));
                    i.setClassName("com.google.android.apps.maps",
                            "com.google.android.maps.MapsActivity");
                    activity.startActivity(i);
                }

                if (title.equalsIgnoreCase("Colonia San Lázaro")) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("geo: 14.588214335972838, -90.48316246145642"));
                    i.setClassName("com.google.android.apps.maps",
                            "com.google.android.maps.MapsActivity");
                    activity.startActivity(i);
                }

                if (title.equalsIgnoreCase("Iglesia San Martín De Porres")) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("geo: 14.587347466705575, -90.4899022758298"));
                    i.setClassName("com.google.android.apps.maps",
                            "com.google.android.maps.MapsActivity");
                    activity.startActivity(i);
                }

                if (title.equalsIgnoreCase("Mapa en Relieve de Guatemala")) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("geo: 14.660754482609716, -90.50822173164946"));
                    i.setClassName("com.google.android.apps.maps",
                            "com.google.android.maps.MapsActivity");
                    activity.startActivity(i);
                }

                if (title.equalsIgnoreCase("Centro de Educación Ambiental (C.E.A)")) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("geo: 14.649089471391399, -90.5168651586343"));
                    i.setClassName("com.google.android.apps.maps",
                            "com.google.android.maps.MapsActivity");
                    activity.startActivity(i);
                }

                if (title.equalsIgnoreCase("Parque Ecológico La Asunción")) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("geo: 14.637174780017755, -90.49454941815742"));
                    i.setClassName("com.google.android.apps.maps",
                            "com.google.android.maps.MapsActivity");
                    activity.startActivity(i);
                }
            }
        });

        dialog.setContentView(view);




//        Log.d("Edman", currentUserId);


        dialog.show();
    }
}
