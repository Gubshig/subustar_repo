package com.example.subusstar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Activity_Init extends AppCompatActivity {
    // 서버 연결 상태를 보여주는 텍스트 뷰
    TextView text_status;
    // 주소를 다시 입력할 수 있도록 하는 텍스트 에디터
    EditText edit_address;
    // 서버 관련 다루는 핸들러
    SQLHandler sqlHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);

        // UI 초기화 (레이아웃에서 텍스트 뷰와 텍스트 에디터 가져오기)
        text_status = findViewById(R.id.text_init_status);
        edit_address = findViewById(R.id.edit_init_address);

        // SQL 핸들러 생성
        sqlHandler = new SQLHandler(
                "jundb", "cit", "citcitcit", "27.35.9.249", "60000");
        // 서버에 연결 시도
        connect();
    }

    // 호출하면 서버에 연결하는 함수, 연결 성공 시 로그인 창으로 이동
    public void connect() {
        // 서버에 연결하기
        if (sqlHandler.connect()) {
            // 로그인 액티비티로 이동하기
            text_status.setText("서버와 연결을 성공했습니다");
            Intent i = new Intent(getApplicationContext(), Activity_Login.class);
            startActivity(i);
            // 애니메이션
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            // 엑티비티 종료
            finish();
        }
        else {
            text_status.setText("서버와 연결을 실패했습니다");
        }
    }

    // 다시 연결을 시도하는 onClick 함수
    public void event_reconnect(View view) {
        String address[] = edit_address.getText().toString().split(":");
        try {
            sqlHandler = new SQLHandler(
                    "jundb", "cit", "citcitcit", address[0], address[1]);
            connect();
        } catch (Exception e) {
            text_status.setText("연결 실패, 주소가 잘못되었거나 인터넷이 접속되지 않았습니다.");
        }
    }
}