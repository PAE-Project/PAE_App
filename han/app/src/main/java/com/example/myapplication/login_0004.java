package com.example.myapplication;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class login_0004 extends AppCompatActivity {
    Button go_0006, male, female;
    EditText Name, NickName, Year, Month, Day, Phonenum, adress1, adress2;
    public JSONObject userdata = new JSONObject();
    View.OnClickListener cl;
    double x,y;
    private OkHttpClient client = new OkHttpClient();
    private MediaType JSON = MediaType.parse("application/json; charset=utf-8");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_0004);

        go_0006 = (Button) findViewById(R.id.go_0006);
        male = (Button) findViewById(R.id.male);
        female = (Button) findViewById(R.id.female);
        Name = (EditText) findViewById(R.id.Name);
        NickName = (EditText) findViewById(R.id.NickName);
        Year = (EditText) findViewById(R.id.Year);
        Month = (EditText) findViewById(R.id.Month);
        Day = (EditText) findViewById(R.id.Day);
        Phonenum = (EditText) findViewById(R.id.Phonenum);
        adress1 = (EditText) findViewById(R.id.adress1);
        adress2 = (EditText) findViewById(R.id.adress2);
        final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        final LocationListener gpsLocationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                y = location.getLongitude(); // 위도
                x = location.getLatitude(); // 경도
            } public void onStatusChanged(String provider, int status, Bundle extras) {

            } public void onProviderEnabled(String provider) {

            } public void onProviderDisabled(String provider) {

            }
        };

        Intent intent = new Intent(getApplicationContext(), login_0006.class);
        Intent getIntent = getIntent();
        String pw = getIntent.getStringExtra("pw");
        String email = getIntent.getStringExtra("email");
        boolean helper = getIntent.getBooleanExtra("helper",false);

        System.out.println(pw+" "+email+" "+helper);


        cl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.go_0006:
                        LocalDate date = null;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            date = LocalDate.now();
                        }
                        try {
                            userdata.put("pw",pw);
                            userdata.put("email",email);
                            userdata.put("name",Name.getText().toString());
                            userdata.put("nickname",NickName.getText().toString());
                            userdata.put("address",adress1.getText().toString()+adress2.getText().toString());
                            userdata.put("date",date);
                            userdata.put("phone",Phonenum.getText().toString());
                            userdata.put("helper",helper);
                            userdata.put("location_x",x);
                            userdata.put("location_y",y);
                            userdata.put("created_at",date);
                            userdata.put("updated_at",date);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                        // RequestBody 생성
                        RequestBody body = RequestBody.create(userdata.toString(), JSON);

                        // Request 생성
                        Request request = new Request.Builder()
                                .url("http://10.0.2.2:8080/api/user")
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


                        startActivity(intent);
                        Toast toast = Toast.makeText(getApplicationContext(), "회원가입 완료",Toast.LENGTH_SHORT);
                        toast.show();
                        break;
                    case R.id.male:
                        male.setSelected(true);
                        female.setSelected(false);
                        try {
                            userdata.put("gender",true);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case R.id.female:
                        male.setSelected(false);
                        female.setSelected(true);
                        try {
                            userdata.put("gender",false);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                }

            }
        };
        go_0006.setOnClickListener(cl);
        male.setOnClickListener(cl);
        female.setOnClickListener(cl);


    }
}