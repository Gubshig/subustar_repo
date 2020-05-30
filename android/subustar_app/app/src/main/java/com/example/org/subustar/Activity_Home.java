package com.example.org.subustar;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.org.subustar.SQLSchema.*;

public class Activity_Home extends AppCompatActivity {
    SQLHandler sqlHandler;

    TextView text_home_studentname;
    TextView text_home_teachername;
    TextView text_home_busnumber, text_home_busspeed, text_home_buslocation, text_home_bustemperature, text_home_bushumidity;

    ImageView img_home_student, img_home_teacher, img_home_bus, img_home_weather;
    boolean student_isAboard, teacher_isAboard, bus_isStart = false;

    LinearLayout layo_home_student, layo_home_teacher, layo_home_bus;

    String ur;

    // 계속 몇 초마다 함수를 실행하기 위해서 필요한 타이머
    Handler handler;
    TimerTask timerTask;
    Timer timer;

    public void printList(String[] list) {
        if (list==null) {
            System.out.print("데이터 정보가 없습니다.");
        }
        else {
            for (int i = 0; i < list.length; i++) {
                System.out.print(list[i]+", ");
            }
        }
        System.out.println("\n-----------------");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sqlHandler = new SQLHandler(
                "subustar", "cit", "citcitcit", "27.35.9.249", "60000");
        sqlHandler.connect();

        // 텍스트
        text_home_studentname = (TextView)findViewById(R.id.text_home_studentname);
        text_home_teachername = (TextView)findViewById(R.id.text_home_teachername);
        text_home_busnumber = (TextView)findViewById(R.id.text_home_busnumber);
        text_home_busspeed = (TextView)findViewById(R.id.text_home_busspeed);
        text_home_bustemperature = (TextView)findViewById(R.id.text_home_bustemperature);
        text_home_bushumidity = (TextView)findViewById(R.id.text_home_bushumidity);
        text_home_buslocation = (TextView)findViewById(R.id.text_home_buslocation);

        // 레이아웃
        layo_home_student = (LinearLayout)findViewById(R.id.layo_home_student);
        layo_home_teacher = (LinearLayout)findViewById(R.id.layo_home_teacher);
        layo_home_bus     = (LinearLayout)findViewById(R.id.layo_home_bus);

        // 이미지
        img_home_student  = (ImageView)findViewById(R.id.img_home_student);
        img_home_teacher  = (ImageView)findViewById(R.id.img_home_teacher);
        img_home_bus  = (ImageView)findViewById(R.id.img_home_bus);

        // 학생의 사용자 이름 받아오기
        ur = getIntent().getStringExtra("ur");

        // 2초마다 UI 업데이트 하기
        timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        update(ur);
                    }
                });
            }
        };
        // 2초마다 update 함수 실행
        timer = new Timer();
        timer.schedule(timerTask, 0, 2000);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 액티비티가 멈추면 UI 업데이트도 종료한다.
        timer.cancel();
    }

    // 학생의 사용자 이름으로 (아이디로) UI 업데이트하기
    public void update(String student_ur) {
        // 학생 아이디로 학생 정보 가져오기
        // 'gubshig', 'qwerty', '김준호', 15, 1, '새싹어린이집', '01058882666', '1229', '584191365850'
        ArrayList<String[]> student = sqlHandler.read(student_table, "ur='"+ur+"'");

        // 필요한 정보만 가져오기 : 이름, 소속, 차량 번호
        String student_name = student.get(0)[2];
        String academy_name = student.get(0)[5];
        String busnum = student.get(0)[7];

        // 차량 번호로 선생님 정보와 버스 정보, 학원 정보
        // 'park', 'root', '박희선', 33, 0, '새싹어린이집', 01083116083, '1229', '584191037985'
        // 1229, 'stop', 'home', 28.5, 0, 20.0
        // '새싹어린이집', '021234567', '서울시 서초구 새싹어린이집', '어린이집'
        ArrayList<String[]> teacher = sqlHandler.read(teacher_table, "busnum="+busnum);
        ArrayList<String[]> bus = sqlHandler.read(bus_table, "busnum="+busnum);
        ArrayList<String[]> academy = sqlHandler.read(academy_table, "name='"+academy_name+"'");

        // 필요한 정보만 가져오기 : 선생님 계정, 이름, 버스 속력, 버스 위치, 버스 온도, 버스 습도
        String teacher_ur = teacher.get(0)[0];
        String teacher_name = teacher.get(0)[2];
        String bus_speed = bus.get(0)[4];
        String bus_location = bus.get(0)[2];
        String bus_temperature = bus.get(0)[3];
        String bus_humidity = bus.get(0)[5];

        // 시간으로 날씨 정보 가져오기 : 날짜를 2020년 5월 25일로 가정
        // '2020-05-25', '서울시 서초구', '맑음', 23, '보통', '좋음'
        ArrayList<String[]> weather = sqlHandler.read(weather_table, "date='2020-05-25'");

        // 필요한 정보만 가져오기 : 장소, 날씨, 기온, 미세먼지, 초미세먼지
        String weather_location = weather.get(0)[1];
        String weather_weather = weather.get(0)[2];
        String weather_temperature = weather.get(0)[3];
        String weather_finedust = weather.get(0)[4];
        String weather_ultrafinedust = weather.get(0)[5];

        // 로그 정보 20개 가져오기
        // 'park', 1229, '202005160820, 'geton'
        ArrayList<String[]> log = sqlHandler.read(log_table,
                "busnum="+busnum+" ORDER BY timestamp DESC", "20");

        // UI 수정하기
        // 학생 이름, 학생 로그 정보 설정
        text_home_studentname.setText(student_name+" 어린이");
        set_log(log, layo_home_student, student_ur, "student");
        set_image(img_home_student, student_isAboard, student_ur);

        // 선생님 이름, 선생님 로그 정보 설정
        text_home_teachername.setText(teacher_name+" 선생님");
        set_log(log, layo_home_teacher, teacher_ur, "teacher");
        set_image(img_home_teacher, teacher_isAboard, teacher_ur);

        // 버스 정보, 버스 로그 정보 설정
        text_home_busnumber.setText(String.format("버스 번호 : %s", busnum));
        text_home_busspeed.setText(String.format("버스 속력 : %s km/h", bus_speed));
        text_home_buslocation.setText(String.format("버스 위치 : %s", bus_location));
        text_home_bustemperature.setText(String.format("버스 온도 : %s 도", bus_temperature));
        text_home_bushumidity.setText(String.format("버스 습도 : %s 퍼센트", bus_humidity));
        set_log(log, layo_home_bus, "bus"+busnum, "bus");
        set_image(img_home_bus, bus_isStart, "bus"+busnum);
    }

    // 이미지 바꾸기
    public void set_image(ImageView img, boolean isColor, String username) {
        if (isColor) {
            int resourceImage = this.getResources().getIdentifier(username, "drawable", this.getPackageName());
            img.setImageResource(resourceImage);
        }
        else {
            int resourceImage = this.getResources().getIdentifier(username+"_shade", "drawable", this.getPackageName());
            img.setImageResource(resourceImage);
        }
    }

    // 로그 정보 갱신하기
    public void set_log(ArrayList<String[]> log, LinearLayout layout, String username, String who) {
        // 현재 날짜 및 시간 가정
        int _year = 2020;
        int _month = 5;
        int _day = 25;
        int _hour = 9;
        int _min = 10;

        // 기존 로그 정보 제거
        layout.removeAllViews();

        for (int i=log.size()-1; i>=0; i--) {
            // 학생 계정과 일치하는 경우만
            if (log.get(i)[0].equals(username)) {
                TextView student_log = new TextView(this);
                student_log.setTextSize(10);
                // 날짜 및 시간 구분
                int year = Integer.valueOf(log.get(i)[2].substring(0, 4));
                int month = Integer.valueOf(log.get(i)[2].substring(4, 6));
                int day = Integer.valueOf(log.get(i)[2].substring(6, 8));
                int hour = Integer.valueOf(log.get(i)[2].substring(8, 10));
                int min = Integer.valueOf(log.get(i)[2].substring(10, 12));
                String date = String.format("[ %d년 %d월 %d일 %d시 %d분 ]\n",
                        year, month, day, hour, min);

                int timedelta = (_hour*60+_min) - (hour*60+min);
                int hourdelta = timedelta / 60;
                int mindelta = timedelta - hourdelta*60;
                if (hourdelta > 0) {
                    date = date + String.format("%d시간 ", hourdelta);
                }
                date = date + String.format("%d분 전에 ", mindelta);

                if (log.get(i)[3].equals("geton")) {
                    date = date + "탑승했습니다.\n";
                    if (who.equals("student")) {
                        student_isAboard = true;
                    }
                    else if (who.equals("teacher")) {
                        teacher_isAboard = true;
                    }
                }
                if (log.get(i)[3].equals("getoff")) {
                    date = date + "하차했습니다.\n";
                    if (who.equals("student")) {
                        student_isAboard = false;
                    }
                    else if (who.equals("teacher")) {
                        teacher_isAboard = false;
                    }
                }
                if (log.get(i)[3].equals("start")) {
                    date = date + "출발했습니다.\n";
                    if (who.equals("bus")) {
                        bus_isStart = true;
                    }
                }
                if (log.get(i)[3].equals("stop")) {
                    date = date + "정지했습니다.\n";
                    if (who.equals("bus")) {
                        bus_isStart = false;
                    }
                }

                student_log.setText(date);
                student_log.setGravity(Gravity.CENTER_HORIZONTAL);
                layout.addView(student_log);
            }
        }
    }
}
