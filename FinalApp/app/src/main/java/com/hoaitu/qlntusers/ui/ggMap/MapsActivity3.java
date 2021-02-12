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

public class MapsActivity3 extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps3);
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

        LatLng dhktl = new LatLng(10.871473005681102, 106.77906226816377);
        mMap.addMarker(new MarkerOptions().position(dhktl).title("Địa điểm đến").icon(BitmapDescriptorFactory.fromResource(R.drawable.local))

        );

        mMap.addPolyline(new PolylineOptions().add(nl,
                new LatLng(10.86754797841271, 106.78773176514578),

                new LatLng(10.867359055004489, 106.787745008852),
                new LatLng(10.86727476356932, 106.78713346521047),
                new LatLng(10.867422273565216, 106.78390408562976),
                new LatLng(10.86765334260648, 106.78279650067026),
                new LatLng(10.868264454196826, 106.78062927583537),
                new LatLng(10.869212728324923, 106.77773249016495),
                new LatLng(10.869592037132392, 106.77783977852312),
                new LatLng(10.869549891733156, 106.7777539478366),
                new LatLng(10.869950272785594, 106.77805435523945),
                new LatLng(10.870434943867105, 106.77826893195576)
//                new LatLng(10.869459915034154, 106.77787972071361),
//                new LatLng(10.869973640771702, 106.7780200676004),
//                new LatLng(10.870145454684883, 106.7781205946484)

                ,dhktl ).width(10).color(Color.RED)



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