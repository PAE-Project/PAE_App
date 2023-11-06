package com.example.myapplication;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class login_sign_up3 extends AppCompatActivity {
    Button go_0006, yyyymmdd, address1;
    RadioButton male, female;
    RadioGroup radioGroup;
    EditText Name, NickName, Phonenum, address2;
    LocalDate date = null;
    public JSONObject userdata = new JSONObject();
    View.OnClickListener cl;
    boolean gender = true;
    double x, y;
    private OkHttpClient client = new OkHttpClient();
    private MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_sign_up3);

        go_0006 = (Button) findViewById(R.id.go_0006);
        yyyymmdd = (Button) findViewById(R.id.yyyymmdd);

        male = (RadioButton) findViewById(R.id.male);
        female = (RadioButton) findViewById(R.id.female);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(radioGroupButtonChangeListener);
        Name = (EditText) findViewById(R.id.Name);
        NickName = (EditText) findViewById(R.id.NickName);

        Phonenum = (EditText) findViewById(R.id.Phonenum);
        Phonenum.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        address1 = (Button) findViewById(R.id.address1);
        address2 = (EditText) findViewById(R.id.address2);


        RadioGroup.OnCheckedChangeListener radioGroupButtonChangeListener = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if (i == R.id.male) {
                    gender = true;
                } else if (i == R.id.female) {
                    gender = false;
                }
            }
        };
        // 위치 관리자 객체 참조하기
        final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(login_sign_up3.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        } else {
            // 가장최근 위치정보 가져오기
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                x = location.getLongitude();
                y = location.getLatitude();

            }
        }
        final LocationListener gpsLocationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // 위치 리스너는 위치정보를 전달할 때 호출되므로 onLocationChanged()메소드 안에 위지청보를 처리를 작업을 구현 해야합니다.
                x = location.getLongitude(); // 위도
                y = location.getLatitude(); // 경도
            } public void onStatusChanged(String provider, int status, Bundle extras) {}
            public void onProviderEnabled(String provider) {}
            public void onProviderDisabled(String provider) {}
        };

        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                yyyymmdd.setText(year + "년 " + (month + 1) + "월 " + dayOfMonth + "일");
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    LocalDate date_data = null;
                    date_data = LocalDate.of(year, month, dayOfMonth);
                    date = date_data;
                }

            }
        }, mYear, mMonth, mDay);


        Intent getIntent = getIntent();
        String pw = getIntent.getStringExtra("pw");
        String email = getIntent.getStringExtra("email");
        boolean helper = getIntent.getBooleanExtra("helper", true);

        cl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.address1:
                        Intent i = new Intent(login_sign_up3.this, WebViewActivity.class);
                        startActivityForResult(i, SEARCH_ADDRESS_ACTIVITY);
                        break;
                    case R.id.go_0006:
                        if (NickName.length() == 0) {
                            Toast.makeText(getApplicationContext(), "닉네임을 입력해주세요", Toast.LENGTH_LONG).show();
                        } else if (date.equals("")) {
                            Toast.makeText(getApplicationContext(), "생년월일 입력해주세요", Toast.LENGTH_LONG).show();
                        } else if (Phonenum.length() == 0) {
                            Toast.makeText(getApplicationContext(), "전화번호를 입력해주세요", Toast.LENGTH_LONG).show();
                        } else{
                            LocalDate date_now = null;
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                date_now = LocalDate.now();
                            }
                            try {
                                userdata.put("pw", pw);
                                userdata.put("email", email);
                                userdata.put("name", Name.getText().toString());
                                userdata.put("nickname", NickName.getText().toString());
                                userdata.put("address", address1.getText().toString() + address2.getText().toString());
                                userdata.put("date", date);
                                userdata.put("phone", Phonenum.getText().toString());
                                userdata.put("helper", helper);
                                userdata.put("gender", gender);
                                userdata.put("location_x", x);
                                userdata.put("location_y", y);
                                userdata.put("created_at", date_now);
                                userdata.put("updated_at", date_now);

                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }

                            // RequestBody 생성
                            RequestBody body = RequestBody.create(userdata.toString(), JSON);

                            // Request 생성
                            Request request = new Request.Builder()
                                    .url("http://3.35.45.245:8080/api/user")
                                    .post(body)
                                    .build();

                            // 비동기 방식으로 요청 전송
                            client.newCall(request).enqueue(new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                    e.printStackTrace();
                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    if (!response.isSuccessful()) {
                                        throw new IOException("Unexpected code " + response);
                                    }

                                    // 수신된 JSON 데이터 디버그 로그로 출력
                                    try {
                                        String responseData = response.body().string();
                                        JSONObject receivedJson = new JSONObject(responseData);
                                        Log.d("Received JSON", receivedJson.toString());
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            Intent intent = new Intent(getApplicationContext(), login_sign_in.class);
                            startActivity(intent);
                            Toast toast = Toast.makeText(getApplicationContext(), "회원가입 완료", Toast.LENGTH_SHORT);
                            toast.show();
                        }

                        break;
                    case R.id.yyyymmdd:
                        datePickerDialog.show();
                        break;
                }
            }
        };
        address1.setOnClickListener(cl);
        go_0006.setOnClickListener(cl);
        male.setOnClickListener(cl);
        female.setOnClickListener(cl);
        yyyymmdd.setOnClickListener(cl);


    }

    RadioGroup.OnCheckedChangeListener radioGroupButtonChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
            if (i == R.id.male) {
                gender = true;
            } else if (i == R.id.female) {
                gender = false;
            }
        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case SEARCH_ADDRESS_ACTIVITY:
                if (resultCode == RESULT_OK) {
                    String data = intent.getExtras().getString("data");
                    if (data != null) {
                        address1.setText(data);
                    }
                }
                break;
        }
    }

}
