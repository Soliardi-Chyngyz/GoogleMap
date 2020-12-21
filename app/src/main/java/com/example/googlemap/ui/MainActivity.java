package com.example.googlemap.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.ClosedSubscriberGroupInfo;
import android.util.Log;
import android.widget.Button;

import com.example.googlemap.App;
import com.example.googlemap.R;
import com.example.googlemap.data.model.LatLngRepresente;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        OnMapReadyCallback, GoogleMap.OnMapClickListener,
        GoogleMap.OnMapLongClickListener, GoogleMap.OnMarkerClickListener {
    private static final int PERMISSION_REQUEST_CODE = 1;
    private GoogleMap gMap;
    private Button normal, hybrid, polyline, polygon;

    private List<LatLng> cords = new ArrayList<>();
    private List<LatLngRepresente> listGetLng = App.getInstance().getAppDataBase().latLngDao().getLatLng();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initControlView();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null)
            // getMapAsync получаем map
            mapFragment.getMapAsync(this);
    }

    private void initControlView() {
        normal = findViewById(R.id.btn1);
        hybrid = findViewById(R.id.btn2);
        polyline = findViewById(R.id.btn3);
        polygon = findViewById(R.id.btn4);
        normal.setOnClickListener(view -> setNormal());
        hybrid.setOnClickListener(view -> setHybrid());
        polyline.setOnClickListener(view -> setPolyline());
        polygon.setOnClickListener(view -> setPolygon());
    }

    private void setPolygon() {
        PolygonOptions polygonOptions = new PolygonOptions();
        polygonOptions.addAll(cords);
        gMap.addPolygon(polygonOptions);
    }

    private void setPolyline() {
        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.addAll(cords);
        gMap.addPolyline(polylineOptions);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        gMap.setOnMapClickListener(this);
        gMap.setOnMapLongClickListener(this);
        gMap.setOnMarkerClickListener(this);

        checkPermissionMyLocPerm();

        if (App.instance.getAppDataBase().latLngDao().getLatLng() != null) {
            MarkerOptions markerOptions = new MarkerOptions();
            for (int i = 0; i < listGetLng.size(); i++) {
                LatLng latLng1 = listGetLng.get(i).getLatLng();
                markerOptions.visible(true);
                markerOptions.position(latLng1);
                gMap.addMarker(markerOptions);
                cords.add(latLng1);
            }
        }

        // Activity это класс позволяюший нам достать какие то ресурсы
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
        ) {
            return;
        }

        CameraPosition cameraPosition = CameraPosition.builder()
                .target(new LatLng(42, 74))
                .zoom(13f)
                .build();
        gMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        gMap.setMyLocationEnabled(true);
    }

    private void checkPermissionMyLocPerm() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            // условие для того чтобы разрешение было дано юзером
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED)
                // проверка на то что у приложения есть разрешение
                if (ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    gMap.setMyLocationEnabled(true);
                } else {
                    gMap.setMyLocationEnabled(true);
                }
        }
    }

    private void setHybrid() {
        gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    private void setNormal() {
        gMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.visible(true);
        markerOptions.position(latLng);
        gMap.addMarker(markerOptions);
        cords.add(latLng);
        App.getInstance().getAppDataBase().latLngDao().insert(new LatLngRepresente(latLng));
    }

    @Override
    public void onMapLongClick(LatLng latLng) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.remove();
        int pos = 0;
        for (int i = 0; i < listGetLng.size(); i++) {
            if(listGetLng.get(i).getLatLng() == marker.getPosition()){
                pos = listGetLng.get(i).getId();
                Log.i("TAG", "onMarkerClick: " + pos);
            }
        }
        App.getInstance().getAppDataBase().latLngDao().deleteById(pos);
        return false;
    }
}