package com.example.user.scheduler;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    String temp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Context ctx = MainActivity.this;


        final TextView today = findViewById(R.id.today);

        final CalendarView cal = findViewById(R.id.calendar);
        final TimePicker time = findViewById(R.id.timePicker);

        final TextView year = findViewById(R.id.year);
        final TextView month = findViewById(R.id.month);
        final TextView date = findViewById(R.id.date);
        final TextView hour = findViewById(R.id.hour);
        final TextView minute = findViewById(R.id.minute);
        //xml의 태그들을 자바의 객체로 전환. identifier(id부여)


        //달력먼저 보이게 하자.
        time.setVisibility(View.INVISIBLE);

        //오늘 날짜를 띄워주자
        //자바에서 오늘 날짜 가져오는 방법
        today.setText(new SimpleDateFormat("yyyy-MM-dd hh:mm").format(new Date()));
        //패턴은 정규식을 의미. 소문자 mm은 minute를 의미
        //new Date()로 현재 시간을 가져온 뒤 이 값을 SimpleDateFormat()의 format메소드에 넣어서 원하는 값만 원하는 형태로 String으로 받음
        //그 다음에 받은 String값을 today 객체에 setText()시킴. 이런 식으로 한줄로 압축구동시켜야 작동속도가 빨라진다.


        cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                temp = year + "-" + (month+1) + "-" + dayOfMonth;
            }
        });
        //달력값은 계속 유지되어있어야 하므로 이 메소드는 객체를 먼저 생성하고 그 객체를 이용하여 메소드 작동시키자.
        //시간값등 내가 다른 값을 조정하고 완료를 누를 때까지 이 값은 유지되어있어야 함
        /*
        temp변수에 대해 생각해보자. 바깥에 String temp="" 로 두면 오류가 뜬다. 외부변수가 final이 아니기때문에.
        밖에서 final String temp="" 로 만들면 안에서 값을 변경할 수 없어져버린다.... 그렇다고 안에서 변수선언을 시키면
        메소드가 끝났을 때 값이 사라진다.
        그럼 이 temp라는, 사용자의 시간값은 어디에 저장시켜두어야 하는 걸까? 완료버튼을 누를 때까지 어디엔가는 남아야한다.
        이전에 계산기처럼 외부에 Inner class로 만들어야 할까? 그건 너무 낭비다.
        class Temp{
            String user_input_time;
            getter, setter 들..
        }
        확실히 너무 낭비다.

        final static이든 그냥 static이든 static 으로 안에서 선언해두면 값은 남을것이다. 하지만 이 값은 메소드를 실행시킬때마다
        계속 새로 생성되어 누적될 것이고 언젠가는 Ram OverFlow를 일으킬 것이다.



        자바에서 동적으로 변화가 있는 공간은 에어리어라고 한다.
        값이 변화는 안되고 공유되는 공간은 필드이다.
        (축구에서 그냥 공이 굴러다니는 필드와 어떠한 상황을 일으키는 패널티에어리어를 생각하면 된다.)

        자바
        필드 - 변수가 위치한 땅
        에어리어 - 메소드 내부
        -> 메소드들끼리는 필드라는 변수를 가지고 값을 주고받게 된다.


        에어리어 안의 값이 계속 유지되려면 변수는 필드에서 선언되어야 한다.
        우리가 내용을 작성하는 이 공간은 사실 onCreate()메소드 내부 에어리어였다.
        따라서 temp를 아예 저 위의 밖 필드에 만들어주도록 하자. 이렇게 하면 내부 에어리어에서 값변경도 가능하고 이 MainActivity클래스
        안에서는 계속 값이 유지가 된다.

        다만 temp를 선언했을 때 값을 초기화를 해주어서는 안된다.

        변수는 로컬변수와 인스턴스(멤버)변수가 존재한다.
        로컬변수는 반드시 초기화를 해야한다. 인스턴스변수(Property)는 절대 초기화해서는 안된다.
        필드에서는 연산자가 있어서는 안된다.
        연산자(알고리즘)은 에어리어 내부에서만 작동하도록 만들자.
        -> 필드가 왜 밖에서 초기회되어서는 안되냐면, 메소드를 작동시키지 않아도 지멋대로 초기화 및 연산이 되어버릴 수 있기 때문이다.

         */


        findViewById(R.id.rdDate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time.setVisibility(View.INVISIBLE);
                cal.setVisibility(View.VISIBLE);
            }
        });

        findViewById(R.id.rdTime).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cal.setVisibility(View.INVISIBLE);
                time.setVisibility(View.VISIBLE);
            }
        });

        findViewById(R.id.confirmBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("날짜값 : ", temp);
                String[] arr = temp.split("-");
                year.setText(arr[0]);
                month.setText(arr[1]);
                date.setText(arr[2]);
                hour.setText(time.getHour()+"");  //get으로 가져오는 값들이 int여서 String으로 바꿔주기 위해 빈 문자열값을 더함
                minute.setText(String.valueOf(time.getMinute()));  //또는 String의 메소드를 사용해도 된다.

                //이제 이 값을 데이터베이스에 저장시켜야 하고 이 데이터베이스의 값을 또 꺼내와야한다. 알람기능과 로그인기능 등..
                //다음주에 데이터베이스 할 것이다.
            }
        });



    }
}
