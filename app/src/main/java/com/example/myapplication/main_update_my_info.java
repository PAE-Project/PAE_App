package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class main_update_my_info extends AppCompatActivity {
    public JSONObject userdata = new JSONObject();
    private OkHttpClient client = new OkHttpClient();
    private MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    Button home, ch_address1;
    EditText ch_name, ch_nick, ch_phone, ch_address2;
    View.OnClickListener cl;
    boolean helper = true, gender = true;
    double x, y;
    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;
    List<Address> list = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_update_my_info);
        Intent secondIntent = getIntent();
        String email = secondIntent.getStringExtra("이메일");
        ch_name = (EditText) findViewById(R.id.ch_name);
        ch_nick = (EditText) findViewById(R.id.ch_nick);
        ch_phone = (EditText) findViewById(R.id.ch_phone);
        ch_address1 = (Button) findViewById(R.id.ch_address1);
        ch_address2 = (EditText) findViewById(R.id.ch_address2);
        home = (Button) findViewById(R.id.home);

        cl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()){
                    case R.id.ch_address1:
                        Intent i = new Intent(main_update_my_info.this, WebViewActivity.class);
                        startActivityForResult(i, SEARCH_ADDRESS_ACTIVITY);
                        break;
                    case R.id.home:
                        LocalDate date_now = null;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            date_now = LocalDate.now();
                        }
                        try {
                            userdata.put("pw", "1234");
                            userdata.put("date", date_now);
                            userdata.put("name", ch_name.getText().toString());
                            userdata.put("nickname", ch_nick.getText().toString());
                            userdata.put("phone", ch_phone.getText().toString());
                            userdata.put("helper", helper);
                            userdata.put("gender", gender);
                            userdata.put("address", ch_address1.getText().toString()+" "+ch_address2.getText().toString());
                            userdata.put("location_x",x);
                            userdata.put("location_y",y);
                            userdata.put("updated_at", date_now);

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                        // RequestBody 생성
                        RequestBody body = RequestBody.create(userdata.toString(), JSON);

                        // Request 생성
                        Request request = new Request.Builder()
                                .url("http://3.35.45.245:8080/api/user/"+email)
                                .put(body)
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
                        Toast toast = Toast.makeText(getApplicationContext(), "변경완료", Toast.LENGTH_LONG);
                        toast.show();
                        Intent intent = new Intent(getApplicationContext(), main_screen1.class);
                        intent.putExtra("이메일", email);
                        startActivity(intent);
                        finish();
                        break;

                }

            }
        };
        ch_address1.setOnClickListener(cl);
        home.setOnClickListener(cl);

    }
    RadioGroup.OnCheckedChangeListener radioGroupButtonChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
            if (i == R.id.agree) {
                helper = true;
            } else if (i == R.id.disagree) {
                helper = false;
            } else if (i == R.id.male){
                gender = true;
            } else if (i == R.id.female){
                gender = false;
            }
        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case SEARCH_ADDRESS_ACTIVITY:
                if (resultCode == RESULT_OK) {
                    String data = intent.getExtras().getString("data");
                    if (data != null) {
                        ch_address1.setText(data);
                        getCurrentXY(ch_address1.getText().toString());
                    }
                }
                break;
        }

    }

    private void getCurrentXY(String str) {
        // 지오코더 gps를 주소로 변환

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addresses;

        try {
            addresses = geocoder.getFromLocationName(str,10);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Address address = addresses.get(0);
        x = address.getLongitude();
        y = address.getLatitude();
    }
}