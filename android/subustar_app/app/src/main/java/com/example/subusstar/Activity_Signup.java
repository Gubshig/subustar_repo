package com.example.subusstar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Activity_Signup extends AppCompatActivity {

    SQLHandler sqlHandler;
    EditText edit_signup_un, edit_signup_nm, edit_signup_ag, edit_signup_ac, edit_signup_pn, edit_signup_em, edit_signup_ck;
    TextView text_signup_ck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        edit_signup_un = findViewById(R.id.edit_signup_un);
        edit_signup_nm = findViewById(R.id.edit_signup_nm);
        edit_signup_ag = findViewById(R.id.edit_signup_ag);
        edit_signup_ac = findViewById(R.id.edit_signup_ac);
        edit_signup_pn = findViewById(R.id.edit_signup_pn);
        edit_signup_em = findViewById(R.id.edit_signup_em);
        edit_signup_ck = findViewById(R.id.edit_signup_ck);
        text_signup_ck = findViewById(R.id.text_signup_ck);

        sqlHandler = new SQLHandler(
                "subustar", "cit", "citcitcit", "27.35.9.249", "60000");
        sqlHandler.connect();
    }

    public void event_signup(View view) {
        String username = edit_signup_un.getText().toString();

        String result[] = sqlHandler.read("studenttable", "WHERE username='"+username+"';");

        if (result == null || result[0] == null || result[0].equals("")) {
            sqlHandler.insert("studenttable", "'"   +
                    edit_signup_un.getText().toString() + "', '"    +
                    edit_signup_ck.getText().toString() + "', '" +
                    edit_signup_nm.getText().toString() + "', " +
                    edit_signup_ag.getText().toString() + ", 0, '" +
                    edit_signup_ac.getText().toString() + "', '" +
                    edit_signup_em.getText().toString() + "'");

            text_signup_ck.setText("성공적으로 계정을 만드셨습니다");

            // 로그인 액티비티로 이동하기
            Intent i = new Intent(getApplicationContext(), Activity_Login.class);
            startActivity(i);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }
        else {

            text_signup_ck.setText("이미 있는 ID입니다");
        }
    }
}
