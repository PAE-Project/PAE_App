package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class map extends AppCompatActivity implements OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback{
    ArrayList<String> nameList = new ArrayList<>();
    ArrayList<String> addressList = new ArrayList<>();
    ArrayList<String> dateList = new ArrayList<>();
    ArrayList<String> phoneList = new ArrayList<>();
    ArrayList<Boolean> genderList = new ArrayList<>();
    ArrayList<Double> location_xList = new ArrayList<>();
    ArrayList<Double> location_yList = new ArrayList<>();
    private GoogleMap mMap;
    private Marker currentMarker = null;

    private static final String TAG = "googlemap";
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int UPDATE_INTERVAL_MS = 1000; // 1초
    private static final int FASTEST_UPDATE_INTERVAL_MS = 500; // 0.5초

    // OnRequestPermissionsResultCallback에서 수신된 결과에서 ActivityCompat.OnRequestPermissionsResultCallback를 사용한 퍼미션 요청을 구별하기 위함
    private static final int PERMISSION_REQUEST_CODE = 100;
    boolean needRequest = false;

    // 앱을 실행하기 위해 필요한 퍼미션 정의
    String[] REQUIRED_PERMISSION = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    Location mCurrentLocation;
    LatLng currentPosition;

    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest locationRequest; // 주의
    private Location location;
    private View mLayout; // snackbar 사용하기 위함.
    String gender = null;
    LocalDate now = null;
    int age = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        connect con = new connect();
        con.start();
        locationRequest = new LocationRequest()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL_MS)
                .setFastestInterval(FASTEST_UPDATE_INTERVAL_MS);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();

        builder.addLocationRequest(locationRequest);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    class connect extends Thread {
        @Override
        public void run() {
            try {
                URL url = new URL("http://3.35.45.245:8080/api/users");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET"); //전송방식
                connection.setDoOutput(false);       //데이터를 쓸 지 설정
                connection.setDoInput(true);        //데이터를 읽어올지 설정

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));

                // 출력물의 라인과 그 합에 대한 변수.
                StringBuilder result = new StringBuilder();
                String line;


                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }


                final String data = result.toString();
                JSONArray jsonArray = new JSONArray(data);
                final ArrayList<ListData> listViewData = new ArrayList<>();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    if(jsonObject.getBoolean("helper")){
                        nameList.add(jsonObject.getString("name"));
                        addressList.add(jsonObject.getString("address"));
                        dateList.add(jsonObject.getString("date"));
                        phoneList.add(jsonObject.getString("phone"));
                        genderList.add(jsonObject.getBoolean("gender"));
                        location_xList.add(jsonObject.getDouble("location_x"));
                        location_yList.add(jsonObject.getDouble("location_y"));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        Log.d(TAG, "onMapReady: 들어옴 ");

        mMap = googleMap;

        // 지도의 초기위치 이동
        setDefaultLocation();

        // 런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 확인합니다.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);

        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED && hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {
            // 2. 이미 퍼미션을 가지고 있다면
            startLocationUpdates(); // 3. 위치 업데이트 실행
        } else {
            // 2. 퍼미션 요청을 허용한 적 없다면 퍼미션 요청하기
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSION[0])) {
                // 요청 진행하기 전에 퍼미션이 왜필요한지 설명
                Snackbar.make(mLayout, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Snackbar.LENGTH_INDEFINITE).setAction("확인", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        // 사용자에게 퍼미션 요청, 요청 결과는 onRequestPermisionResult에서 수신
                        ActivityCompat.requestPermissions(map.this, REQUIRED_PERMISSION, PERMISSION_REQUEST_CODE);
                    }
                }).show();
            } else {
                // 사용자가 퍼미션 거부를 한적이 없는 경우 퍼미션 요청을 바로 함.
                // 요청 결과는 onRequestPermissionResult에서 수신된다.
                ActivityCompat.requestPermissions(this, REQUIRED_PERMISSION, PERMISSION_REQUEST_CODE);
            }
        }

        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                Log.d(TAG, "onMapClick: ");
            }
        });
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            now = LocalDate.now();
        }

        for (int idx = 0; idx < location_xList.size(); idx++) {
            LatLng latLng = new LatLng(location_xList.get(idx), location_yList.get(idx));
            if(genderList.get(idx)) {
                gender = "남자";
            } else{
                gender = "여자";
            }
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                age = now.getYear() - Integer.parseInt(dateList.get(idx).substring(0,4));
            }
            // 1. 마커 옵션 설정 (만드는 과정)
            MarkerOptions makerOptions = new MarkerOptions();
            makerOptions.position(latLng);
            makerOptions.title("이름 : "+nameList.get(idx));// 타이틀.
            makerOptions.snippet("성별 : " + gender + ", 나이 : " + age + ", 번호 : " + phoneList.get(idx));

            // 2. 마커 생성 (마커를 나타냄)
            mMap.addMarker(makerOptions);
        }
        googleMap.setOnInfoWindowClickListener(infoWindowClickListener);

    }

    //정보창 클릭 리스너
    GoogleMap.OnInfoWindowClickListener infoWindowClickListener = new GoogleMap.OnInfoWindowClickListener() {
        @Override
        public void onInfoWindowClick(Marker marker) {

            int i = marker.getId().charAt(1) ;
            i = i - 49;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                age = now.getYear() - Integer.parseInt(dateList.get(i).substring(0,4));
            }
            if(genderList.get(i)) {
                gender = "남자";
            } else{
                gender = "여자";
            }
            Intent intent = new Intent(getApplicationContext(), main_help_info.class);
            intent.putExtra("이름", nameList.get(i));
            intent.putExtra("나이", Integer.toString(age));
            intent.putExtra("성별", gender);
            intent.putExtra("주소", addressList.get(i));
            intent.putExtra("전화번호", phoneList.get(i));
            startActivity(intent);

        }
    };



    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {
            super.onLocationResult(locationResult);

            List<Location> locationList = locationResult.getLocations();

            if (locationList.size() > 0) {
                location = locationList.get(locationList.size() - 1);

                currentPosition = new LatLng(location.getLatitude(), location.getLongitude());

                String markerTitle = getCurrentAddress(currentPosition);
                String markerSnippet = "현재 위치";

                // 현재 위치에 마커 생성하고 이동
                setCurrentLocation(location, markerTitle, markerSnippet);

                mCurrentLocation = location;
            }
        }
    };


    private String getCurrentAddress(LatLng currentPosition) {
        // 지오코더 gps를 주소로 변환

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addresses;

        try {
            addresses = geocoder.getFromLocation(
                    currentPosition.latitude,
                    currentPosition.longitude,
                    1
            );
        } catch (IOException ioException) {
            // 네트워크 문제
            Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "지오코더 서비스 사용 불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";
        }

        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(this, "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";
        } else {
            Address address = addresses.get(0);
            return address.getAddressLine(0).toString();
        }
    }

    private void setCurrentLocation(Location location, String markerTitle, String markerSnippet) {

        if (currentMarker != null) {
            currentMarker.remove();
        }

        LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(currentLatLng);
        markerOptions.title(markerTitle);
        markerOptions.snippet(markerSnippet);
        markerOptions.draggable(true);

        currentMarker = mMap.addMarker(markerOptions);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(currentLatLng);
        mMap.moveCamera(cameraUpdate);
    }




    private void startLocationUpdates() {
        if (!checkLocationServicesStatus()) {
            showDiologForLocationServiceSetting();

        } else {
            int hasFineLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
            int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);

            if (hasFineLocationPermission != PackageManager.PERMISSION_GRANTED || hasCoarseLocationPermission != PackageManager.PERMISSION_GRANTED) {

                Log.d(TAG, "startLocationUpdates: 퍼미션 없음");
                return;
            }

            mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
            if (checkPermission()) {
                mMap.setMyLocationEnabled(true);
            }
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d(TAG, "onStart: ");

        if (checkPermission()) {
            mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);

            if (mMap != null) {
                mMap.setMyLocationEnabled(true);
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mFusedLocationClient != null) {
            mFusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }

    private boolean checkPermission() {

        int hasFineLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);

        if (hasFineLocationPermission != PackageManager.PERMISSION_GRANTED || hasCoarseLocationPermission != PackageManager.PERMISSION_GRANTED) {

            Log.d(TAG, "startLocationUpdates: 퍼미션 없음");
            return false;

        } else {
            return true;
        }

    }

    private void showDiologForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(map.this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다. 위치설정을 수정하시겠습니까?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent callGPSSettingIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.create().show();
    }

    private boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private void setDefaultLocation() {

        // 기본 위치
        LatLng DEFAULT_LOCATION = new LatLng(37.56, 126.97);
        String markerTitle = "위치 정보 가져올 수 없음";
        String markerSnippet = "위치 퍼미션과 GPS 활성 여부를 확인하세요";

        if (currentMarker != null) {
            currentMarker.remove();
        }

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(DEFAULT_LOCATION);
        markerOptions.title(markerTitle);
        markerOptions.snippet(markerSnippet);
        markerOptions.draggable(true);
        currentMarker = mMap.addMarker(markerOptions);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, 15);
        mMap.moveCamera(cameraUpdate);
    }
}
