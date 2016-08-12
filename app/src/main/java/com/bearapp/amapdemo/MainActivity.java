package com.bearapp.amapdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;

public class MainActivity extends AppCompatActivity implements LocationSource, AMapLocationListener {

    private MapView mapView;
    private AMap map;

    private OnLocationChangedListener onLocationChangedListener;
    private AMapLocationClient mapLocationClient;
    private AMapLocationClientOption mapLocationClientOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        map = mapView.getMap();
        setUpMap();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    private void setUpMap() {
        map.setLocationSource(this);
        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.setMyLocationEnabled(true);
        map.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
    }

    private void requestLocation() {

    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        this.onLocationChangedListener = onLocationChangedListener;
        if (mapLocationClient == null) {
            mapLocationClient = new AMapLocationClient(this);
            mapLocationClientOption = new AMapLocationClientOption();
            mapLocationClient.setLocationListener(this);
            mapLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            mapLocationClientOption.setOnceLocation(true);
            mapLocationClientOption.setNeedAddress(true);
            mapLocationClient.setLocationOption(mapLocationClientOption);
            mapLocationClient.startLocation();
        }
    }

    @Override
    public void deactivate() {
        onLocationChangedListener = null;
        if (mapLocationClient != null) {
            mapLocationClient.stopLocation();
            mapLocationClient.onDestroy();
        }
        mapLocationClient = null;
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (onLocationChangedListener != null && aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                onLocationChangedListener.onLocationChanged(aMapLocation);
            }
        }
    }
}
