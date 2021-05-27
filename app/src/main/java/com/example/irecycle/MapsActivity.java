package com.example.irecycle;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        LatLng UVG = new LatLng(14.604015374142557, -90.48923844481173);
        LatLng coloniaSanLazaro = new LatLng(14.588214335972838, -90.48316246145642);
        LatLng iglesiaSanMartin = new LatLng(14.587347466705575, -90.4899022758298);
        LatLng mapaEnRelieve = new LatLng(14.660754482609716, -90.50822173164946);
        LatLng centroEducacionAmbiental = new LatLng(14.649089471391399, -90.5168651586343);
        LatLng parqueEcologicoLaAsuncion = new LatLng(14.637174780017755, -90.49454941815742);
        mMap.addMarker(new MarkerOptions().position(UVG).title("Universidad del Valle de Guatemala"));
        mMap.addMarker(new MarkerOptions().position(coloniaSanLazaro).title("Colonia San Lázaro"));
        mMap.addMarker(new MarkerOptions().position(iglesiaSanMartin).title("Iglesia San Martín De Porres"));
        mMap.addMarker(new MarkerOptions().position(mapaEnRelieve).title("Mapa en Relieve de Guatemala"));
        mMap.addMarker(new MarkerOptions().position(centroEducacionAmbiental).title("Centro de Educación Ambiental (C.E.A)"));
        mMap.addMarker(new MarkerOptions().position(parqueEcologicoLaAsuncion).title("Parque Ecológico La Asunción"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(UVG, 13f));

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {

                openMarker(marker.getTitle());

                return true;
            }
        });
    }

    public void openMarker(String title) {

        Log.d("Edman", title);



        if (title.equalsIgnoreCase("Universidad del Valle de Guatemala")) {
            final BottomSheetMap bottomSheetMap = new BottomSheetMap(this, title, "18 Avenida 11-95 Guatemala, 01015", "7am - 9pm");
            bottomSheetMap.startChangePriorityBottomSheet();
        }

        if (title.equalsIgnoreCase("Colonia San Lázaro")) {
            final BottomSheetMap bottomSheetMap = new BottomSheetMap(this, title, "29 Avenida 5-31, Cdad. de Guatemala", "7am - 6pm");
            bottomSheetMap.startChangePriorityBottomSheet();
        }

        if (title.equalsIgnoreCase("Iglesia San Martín De Porres")) {
            final BottomSheetMap bottomSheetMap = new BottomSheetMap(this, title, "Blvd. Vista Hermosa, Cdad. de Guatemala", "7am - 7pm");
            bottomSheetMap.startChangePriorityBottomSheet();
        }

        if (title.equalsIgnoreCase("Mapa en Relieve de Guatemala")) {
            final BottomSheetMap bottomSheetMap = new BottomSheetMap(this, title, "Av. Simeón Cañas, Cdad. de Guatemala 01002", "9am - 5pm");
            bottomSheetMap.startChangePriorityBottomSheet();
        }

        if (title.equalsIgnoreCase("Centro de Educación Ambiental (C.E.A)")) {
            final BottomSheetMap bottomSheetMap = new BottomSheetMap(this, title, "2-13 1 Calle, Cdad. de Guatemala", "7am - 3pm");
            bottomSheetMap.startChangePriorityBottomSheet();
        }

        if (title.equalsIgnoreCase("Parque Ecológico La Asunción")) {
            final BottomSheetMap bottomSheetMap = new BottomSheetMap(this, title, "7 Calle, Col 10 de Mayo, Cdad. de Guatemala", "6am - 6pm");
            bottomSheetMap.startChangePriorityBottomSheet();
        }
    }
}













