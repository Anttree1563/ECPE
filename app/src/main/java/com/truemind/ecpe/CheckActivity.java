package com.truemind.ecpe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by 현석 on 2016-11-26.
 */
public class CheckActivity extends Activity {

    ImageButton btn1, btn2;
    TextView textview1, textview2, textview3, textview4, textview5, textview6, textview7, textview8;
    String data1, data2, data3, data4, data5, data6, data7, data8, divisor;

    int tempArray[] = new int[5];
    int dataIntArray[][] = new int[8][];
    int divisorIntArray[] = new int[5];
    int crcIntArray[][] = new int[8][];
    String codeWord[] = new String[8];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        initView();

        Intent intent1 = getIntent();

        for(int i=0;i<8;i++){
            dataIntArray[i]=new int[17];
        }
        for(int i=0;i<8;i++){
            crcIntArray[i]=new int[4];
        }

        data1 = intent1.getStringExtra("data1");
        data2 = intent1.getStringExtra("data2");
        data3 = intent1.getStringExtra("data3");
        data4 = intent1.getStringExtra("data4");
        data5 = intent1.getStringExtra("data5");
        data6 = intent1.getStringExtra("data6");
        data7 = intent1.getStringExtra("data7");
        data8 = intent1.getStringExtra("data8");
        divisor = intent1.getStringExtra("divisor");

//--------------------------------------------------------------------------------------------------Array initializing
        String dataArray[] = {data1, data2, data2, data3, data4, data4, data5, data6, data7, data8};
        for(int j = 0; j<8; j++) {
            for (int i = 0; i < 12; i++) {
                dataIntArray[j][i] = (Integer.valueOf(dataArray[j].charAt(i))-48);
            }
        }

        for(int j = 0; j<8; j++) {
            for(int i = 12; i<17; i++){
                dataIntArray[j][i] = 0;
            }
        }

        for (int i = 0; i < 5; i++) {
            divisorIntArray[i] = (Integer.valueOf(divisor.charAt(i))-48);
        }

        for(int i =0; i<8; i++){
            codeWord[i] = "";
        }

//--------------------------------------------------------------------------------------------------CRC 부호화 과정
        for(int i = 0; i<8; i++) {//8개의 frame data에 전부 반복

            for (int i2 = 0; i2 < 5; i2++) {
                tempArray[i2] = dataIntArray[i][i2];
            }
            for (int j = 0; j < 12; j++) {
                if (tempArray[0] == 1) {//나머지의 시작이 1일 때
                    for (int k = 0; k < 4; k++) {
                        tempArray[k] = XOR(tempArray[k + 1], divisorIntArray[k]);
                        tempArray[4] = dataIntArray[i][j + 5];
                    }
                } else {//나머지의 시작이 0일 때
                    for (int k = 0; k < 4; k++) {
                        tempArray[k] = XOR(tempArray[k + 1], 0);
                        tempArray[4] = dataIntArray[i][j + 5];
                    }
                }
            }
            for (int j = 0; j < 4; j++) {
                crcIntArray[i][j] = tempArray[j];
                Log.d("MyTag", "crc:"+i+j + crcIntArray[i][j]);
            }
//--------------------------------------------------------------------------------------------------코드워드 String으로
            for(int j2 = 0; j2<12; j2++){
                codeWord[i] +=  dataIntArray[i][j2];
            }
            for(int j3 = 0; j3<4; j3++){
                codeWord[i] +=  crcIntArray[i][j3];
            }
            Log.d("MyTag", "codeword"+i+": "+ codeWord[i]);
        }
        initListener();
  }

//--------------------------------------------------------------------------------------------------

    private void initView() {
        btn1 = (ImageButton) findViewById(R.id.check_btn1);
        btn2 = (ImageButton) findViewById(R.id.check_btn2);
        textview1 = (TextView) findViewById(R.id.textView1);
        textview2 = (TextView) findViewById(R.id.textView2);
        textview3 = (TextView) findViewById(R.id.textView3);
        textview4 = (TextView) findViewById(R.id.textView4);
        textview5 = (TextView) findViewById(R.id.textView5);
        textview6 = (TextView) findViewById(R.id.textView6);
        textview7 = (TextView) findViewById(R.id.textView7);
        textview8 = (TextView) findViewById(R.id.textView8);

    }

    private void initListener() {
        textview8.setText("  "+codeWord[0]);
        textview1.setText("  "+codeWord[1]);
        textview2.setText("  "+codeWord[2]);
        textview3.setText("  "+codeWord[3]);
        textview4.setText("  "+codeWord[4]);
        textview5.setText("  "+codeWord[5]);
        textview6.setText("  "+codeWord[6]);
        textview7.setText("  "+codeWord[7]);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                Intent intent3 = new Intent(CheckActivity.this, SettingActivity.class);
                startActivity(intent3);
                finish();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                Intent intent3 = new Intent(CheckActivity.this, MainActivity.class);
                startActivity(intent3);
                finish();
            }
        });

    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(CheckActivity.this, SettingActivity.class);
        startActivity(intent);
        finish();
    }

    private int XOR(int a, int b){
        if(a==b){
            return 0;
        }
        else{
            return 1;
        }
    }
}
