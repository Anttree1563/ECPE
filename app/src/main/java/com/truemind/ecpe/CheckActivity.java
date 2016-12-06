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


    private final int dataLength = 8;
    private final int crcLength = 7;

    ImageButton btn1, btn2;
    TextView textview1, textview2, textview3, textview4, textview5, textview6, textview7, textview8;
    String data1, data2, data3, data4, data5, data6, data7, data8, divisor;

    int tempArray[] = new int[crcLength+1];
    int dataIntArray[][] = new int[8][];
    int divisorIntArray[] = new int[crcLength+1];
    int crcIntArray[][] = new int[8][];
    String codeWord[] = new String[8];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        initView();

        Intent intent1 = getIntent();

        for(int i=0;i<8;i++){
            dataIntArray[i]=new int[dataLength+crcLength+1];
        }
        for(int i=0;i<8;i++){
            crcIntArray[i]=new int[crcLength];
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
//--------------------------------------------------------------------------------------------------console log
/*
        Log.d("MyTag", "data1:"+ data1);
        Log.d("MyTag", "data2:"+ data2);
        Log.d("MyTag", "data3:"+ data3);
        Log.d("MyTag", "data4:"+ data4);
        Log.d("MyTag", "data5:"+ data5);
        Log.d("MyTag", "data6:"+ data6);
        Log.d("MyTag", "data7:"+ data7);
        Log.d("MyTag", "data8:"+ data8);
        Log.d("MyTag", "data9:"+ divisor);
        */
//--------------------------------------------------------------------------------------------------Array initializing
        /*String dataArray[] = {data1, data2, data2, data3, data4, data4, data5, data6, data7, data8};
        for(int j = 0; j<8; j++) {
            for (int i = 0; i < 12; i++) {
                dataIntArray[j][i] = (Atoi(dataArray[j].charAt(i)));
            }*/
//--------------------------------------------------------------------------------------------------
        //위와 같이 dataArray를 이용하여 처리할 시 에러 발생
        //2번째, 3번째가 같거나(중복) 마지막이 다른것으로 바뀌는(스크램블)되는 현상 발견
        //따라서 dataArray스트링배열을 제거하고 for문을 사용하지 않는 경우 이런 현상이 제거됨.
        //원인은 나중에....(예상되는 원인은 for문의 속도와 데이터 연산 속도의 차이 때문으로 예상됨--> 버퍼 필요!)
        for (int i = 0; i < dataLength; i++) {
            dataIntArray[0][i] = (Atoi(data1.charAt(i)));
        }
        for (int i = 0; i < dataLength; i++) {
            dataIntArray[1][i] = (Atoi(data2.charAt(i)));
        }
        for (int i = 0; i < dataLength; i++) {
            dataIntArray[2][i] = (Atoi(data3.charAt(i)));
        }
        for (int i = 0; i < dataLength; i++) {
            dataIntArray[3][i] = (Atoi(data4.charAt(i)));
        }
        for (int i = 0; i < dataLength; i++) {
            dataIntArray[4][i] = (Atoi(data5.charAt(i)));
        }
        for (int i = 0; i < dataLength; i++) {
            dataIntArray[5][i] = (Atoi(data6.charAt(i)));
        }
        for (int i = 0; i < dataLength; i++) {
            dataIntArray[6][i] = (Atoi(data7.charAt(i)));
        }
        for (int i = 0; i < dataLength; i++) {
            dataIntArray[7][i] = (Atoi(data8.charAt(i)));
        }
        for(int j = 0; j<8; j++) {
            for(int j2 = 0; j2<dataLength; j2++) {
                Log.d("MyTag", "dataIntArray" + j + ": " + dataIntArray[j][j2]);
            }
        }
//--------------------------------------------------------------------------------------------------
        for(int j = 0; j<8; j++) {
            for(int i = dataLength; i<(dataLength+crcLength+1); i++){
                dataIntArray[j][i] = 0;
            }
        }

        for (int i = 0; i < crcLength+1; i++) {
            divisorIntArray[i] = (Integer.valueOf(divisor.charAt(i))-48);
        }

        for(int i =0; i<8; i++){
            codeWord[i] = "";
        }

        for (int i = 0; i < crcLength+1; i++) {
            Log.d("MyTag", "divisor"+i+": "+ divisorIntArray[i]);
        }
//--------------------------------------------------------------------------------------------------CRC 부호화 과정
        for(int i = 0; i<8; i++) {//8개의 frame data에 전부 반복

            for (int i2 = 0; i2 < crcLength+1; i2++) {
                tempArray[i2] = dataIntArray[i][i2];

                Log.d("MyTag", "tA"+i2+": "+ tempArray[i2]);
            }
            for (int j = 0; j < dataLength; j++) {
                if (tempArray[0] == 1) {//나머지의 시작이 1일 때
                    for (int k = 0; k < crcLength; k++) {
                        tempArray[k] = XOR(tempArray[k + 1], divisorIntArray[k+1]);
                    }
                } else {//나머지의 시작이 0일 때
                    for (int k = 0; k < crcLength; k++) {
                        tempArray[k] = XOR(tempArray[k + 1], 0);
                    }
                }
                for (int k = 0; k < crcLength; k++) {
                    tempArray[crcLength] = dataIntArray[i][j + (crcLength+1)];
                    Log.d("MyTag", "tA" + j + ": " + tempArray[k]);
                }
            }
            for (int j = 0; j < crcLength; j++) {
                crcIntArray[i][j] = tempArray[j];
                Log.d("MyTag", "crc:" + i + j + ": " + crcIntArray[i][j]);
            }
            for(int j = 0; j<dataLength; j++) {
                Log.d("MyTag", "dataIntArray" + i + ": " + dataIntArray[i][j]);
            }
//--------------------------------------------------------------------------------------------------코드워드 String으로
        }

        for(int i = 0; i<8; i++){
            for(int j2 = 0; j2<dataLength; j2++){
                codeWord[i] +=  dataIntArray[i][j2];
            }
            for(int j3 = 0; j3<crcLength; j3++){
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
//--------------------------------------------------------------------------------------------------
    private void initListener() {
        textview1.setText("  "+codeWord[0]);
        textview2.setText("  "+codeWord[1]);
        textview3.setText("  "+codeWord[2]);
        textview4.setText("  "+codeWord[3]);
        textview5.setText("  "+codeWord[4]);
        textview6.setText("  "+codeWord[5]);
        textview7.setText("  "+codeWord[6]);
        textview8.setText("  "+codeWord[7]);
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
                Intent intent3 = new Intent(CheckActivity.this, TransmitActivity.class);
                intent3.putExtra("codeword1", codeWord[0]);
                intent3.putExtra("codeword2", codeWord[1]);
                intent3.putExtra("codeword3", codeWord[2]);
                intent3.putExtra("codeword4", codeWord[3]);
                intent3.putExtra("codeword5", codeWord[4]);
                intent3.putExtra("codeword6", codeWord[5]);
                intent3.putExtra("codeword7", codeWord[6]);
                intent3.putExtra("codeword8", codeWord[7]);
                intent3.putExtra("divisor", divisor);
                startActivity(intent3);
                finish();
            }
        });
//--------------------------------------------------------------------------------------------------
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(CheckActivity.this, SettingActivity.class);
        startActivity(intent);
        finish();
    }
//--------------------------------------------------------------------------------------------------XOR bitwise
    private int XOR(int a, int b){
        if(a==b){
            return 0;
        }
        else{
            return 1;
        }
    }
//--------------------------------------------------------------------------------------------------Char into Integer
    private int Atoi(char a){
        if(a=='1'){
            return 1;
        }else{
            return 0;
        }
    }
//--------------------------------------------------------------------------------------------------
}
