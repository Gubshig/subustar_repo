package com.example.subusstar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Activity_Login extends AppCompatActivity {
    // 사용자 이름과 비밀번호가 입력되는 텍스트 에디터
    EditText edit_un, edit_pw;
    // 로그인 상태를 알려주는 텍스트 뷰
    TextView text_status;
    // 서버 관련 다루는 핸들러
    SQLHandler sqlHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // UI 초기화 (레이아웃에서 텍스트 뷰와 텍스트 에디터 가져오기)
        edit_un = findViewById(R.id.edit_login_username);
        edit_pw = findViewById(R.id.edit_login_password);
        text_status = findViewById(R.id.text_login_status);

        // 서버 핸들러 만들기
        sqlHandler = new SQLHandler(
                "subustar", "cit", "citcitcit", "27.35.9.249", "60000");
        sqlHandler.connect();
    }

    // 호출하면 서버에 계정 정보가 있는 지 확인하는 onClick 함수
    public void event_signin(View view) {
        // 입력된 사용자 이름과 비밀번호를 가져오기
        String username = edit_un.getText().toString();
        String password = edit_pw.getText().toString();

        // 데이터베이스에 계정 정보가 있는 지 확인하기, 따옴표 주의!
        String result[] = sqlHandler.read(
                "studenttable",
                "WHERE username='"+username+"' AND PW='"+password+"';");
        if (result == null || result[0].equals("")) {
            text_status.setText("계정 정보가 존재하지 않습니다. 다시 로그인해주세요.");
        }
        else {
            text_status.setText(result[0]+"님, 환영합니다!");
            // 회원가입 액티비티로 이동하기
            Intent i = new Intent(getApplicationContext(), Activity_Home.class);
            i.putExtra("ur", username);
            startActivity(i);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    }

    // 호출하면 서버에 계정을 추가하는 onClick 함수
    public void event_signup(View view) {
        // 회원가입 액티비티로 이동하기
        Intent i = new Intent(getApplicationContext(), Activity_Signup.class);
        startActivity(i);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}