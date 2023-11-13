package com.example.myapplication;

import static java.lang.System.out;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity_share extends AppCompatActivity {
    public JSONObject sharedata = new JSONObject();
    String category, state;
    TextView date_tv, nickname_tv, state_tv, category_tv, address_tv;
    EditText title_et, content_et, price_et;
    Button reg_button, btn_UploadPicture;
    byte[] imgByte;
    View.OnClickListener cl;
    private OkHttpClient client = new OkHttpClient();
    private MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    LocalDate date_now = null;
    ImageView imageView;
    private final int GET_GALLERY_IMAGE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_share);
        imageView = findViewById(R.id.user_image);
        reg_button = (Button) findViewById(R.id.reg_button);
        btn_UploadPicture = (Button) findViewById(R.id.btn_UploadPicture);
        title_et = (EditText) findViewById(R.id.title_et);
        content_et = (EditText) findViewById(R.id.content_et);
        price_et = (EditText) findViewById(R.id.price_et);
        date_tv = (TextView) findViewById(R.id.date_tv);
        nickname_tv = (TextView) findViewById(R.id.nickname_tv);
        state_tv = (TextView) findViewById(R.id.state_tv);
        category_tv = (TextView) findViewById(R.id.category_tv);
        address_tv = (TextView) findViewById(R.id.address_tv);

        state_tv.setText("설정");
        category_tv.setText("설정");

        Intent secondIntent = getIntent();
        String nickname = secondIntent.getStringExtra("닉네임");
        String address = secondIntent.getStringExtra("주소");

        address_tv.setText(address);



        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            date_now = LocalDate.now();
        }
        nickname_tv.setText(nickname);
        date_tv.setText(date_now.toString());
        state_tv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                PopupMenu popup = new PopupMenu(RegisterActivity_share.this, view);
                getMenuInflater().inflate(R.menu.popup, popup.getMenu());

                //팝업메뉴의 메뉴아이템을 선택하는 것을 듣는 리스너 객체 생성 및 설정
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        switch (menuItem.getItemId()) {
                            case R.id.sell:
                                state_tv.setText("판매 중");
                                break;

                            case R.id.sell_finish:
                                state_tv.setText("판매 완료");
                                break;

                            case R.id.share:
                                state_tv.setText("나눔 중");
                                break;

                            case R.id.share_finish:
                                state_tv.setText("나눔 완료");
                                break;
                        }
                        return false;
                    }
                });

                popup.show();

                return true;
            }
        });

        category_tv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                PopupMenu popup2 = new PopupMenu(RegisterActivity_share.this, view);
                getMenuInflater().inflate(R.menu.popup2, popup2.getMenu());

                //팝업메뉴의 메뉴아이템을 선택하는 것을 듣는 리스너 객체 생성 및 설정
                popup2.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        switch (menuItem.getItemId()) {
                            case R.id.clothes:
                                category_tv.setText("의류");
                                break;

                            case R.id.things:
                                category_tv.setText("용품");
                                break;
                        }

                        return false;
                    }
                });

                popup2.show();

                return true;
            }
        });



        cl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                switch (v.getId()){

                    case R.id.btn_UploadPicture:
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(intent, 101);
                        break;

                    case R.id.reg_button:

                        try {
                            sharedata.put("title", title_et.getText().toString());
                            sharedata.put("category", category_tv.getText().toString());
                            sharedata.put("content", content_et.getText().toString());
                            sharedata.put("date", date_now);
                            sharedata.put("state",state_tv.getText().toString());
                            sharedata.put("img", "test");
                            sharedata.put("img2", "test");
                            sharedata.put("img3", "test");
                            sharedata.put("address",address);
                            sharedata.put("nickname", nickname);
                            sharedata.put("price", Integer.parseInt(price_et.getText().toString()));
                            sharedata.put("created_at", date_now);
                            sharedata.put("updated_at", date_now);

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }



                        // RequestBody 생성
                        RequestBody body = RequestBody.create(sharedata.toString(), JSON);

                        // Request 생성
                        Request request = new Request.Builder()
                                .url("http://3.35.45.245:8080/api/share")
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
                        Toast toast = Toast.makeText(getApplicationContext(), "작성완료",Toast.LENGTH_SHORT);
                        toast.show();
                        intent = new Intent(getApplicationContext(),main_share.class);
                        startActivity(intent);
                        break;

                }

            }

        };
        reg_button.setOnClickListener(cl);
        btn_UploadPicture.setOnClickListener(cl);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101) {
            if (resultCode == RESULT_OK) {
                Uri fileUri = data.getData();
                ContentResolver resolver = getContentResolver();
                try {
                    InputStream instream = resolver.openInputStream(fileUri);
                    Bitmap imgBitmap = BitmapFactory.decodeStream(instream);
                    imageView.setImageBitmap(imgBitmap);    // 선택한 이미지 이미지뷰에 셋
                    instream.close();   // 스트림 닫아주기
                    Toast.makeText(getApplicationContext(), "파일 불러오기 성공", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "파일 불러오기 실패", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public String getRealpath(Uri uri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor c = getContentResolver().query(uri, proj, null, null, null);
        int index = c.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        c.moveToFirst();
        String path = c.getString(index);

        return path;
    }

}
