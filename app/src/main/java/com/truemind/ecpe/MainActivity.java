package com.truemind.ecpe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends Activity {

    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;
    ImageButton btn1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
    }
    private void initListener() {

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                Intent intent1 = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent1);
                finish();
            }

        });
    }

    private void initView() {
        btn1 = (ImageButton) findViewById(R.id.main_btn);
    }

    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        if(0<=intervalTime && FINISH_INTERVAL_TIME >= intervalTime) {
            super.onBackPressed();
        }
        else
        {
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(), "\"Back\"버튼 을 한번 더 눌러 종료",Toast.LENGTH_SHORT).show();
        }
    }
}
