package com.hoaitu.qlntusers.ui.ggMap;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.hoaitu.qlntusers.R;

public class MapsActivity2 extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        // Add a marker in Sydney and move the camera
        LatLng nl = new LatLng(10.867947066803744, 106.7878656893415);
        mMap.addMarker(new MarkerOptions().position(nl).title("Đại học Nông Lâm")

        );

        LatLng NoTrangLong = new LatLng(10.81982219742125, 106.70266722514265);
        mMap.addMarker(new MarkerOptions().position(NoTrangLong).title("Địa điểm đến").icon(BitmapDescriptorFactory.fromResource(R.drawable.local))

        );


        mMap.addPolyline(new PolylineOptions().add(nl,
                new LatLng(10.870682, 106.772207),

                new LatLng(10.873320111279678, 106.76462020666084),
                new LatLng(10.861687900540804, 106.75449218565021),
                new LatLng(10.827800183902244, 106.71930160417259),
                new LatLng(10.822573378607617, 106.70385208059706),
                new LatLng(10.819707027324018, 106.70247878961257)
                ,NoTrangLong ).width(10).color(Color.RED)



        );
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(nl, 18));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
    }
}