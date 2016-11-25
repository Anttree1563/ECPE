package com.truemind.ecpe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by 현석 on 2016-11-26.
 */
public class CheckActivity extends Activity {

    String data1, data2, data3, data4, data5, data6, data7, data8, divisor;
    String dataArray[] = {data1, data2, data2, data3, data4, data4, data5, data6, data7, data8};
    int dataIntArray[][] = new int[8][];
    int divisorIntArray[] = new int[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initView();
        //initListener();

        Intent intent1 = getIntent();

        data1 = intent1.getStringExtra("data1");
        data2 = intent1.getStringExtra("data2");
        data3 = intent1.getStringExtra("data3");
        data4 = intent1.getStringExtra("data4");
        data5 = intent1.getStringExtra("data5");
        data6 = intent1.getStringExtra("data6");
        data7 = intent1.getStringExtra("data7");
        data8 = intent1.getStringExtra("data8");
        divisor = intent1.getStringExtra("divisor");

        for(int j = 0; j<8; j++) {
            for (int i = 0; i < 12; i++) {
                dataIntArray[j][i] = Integer.valueOf(dataArray[j].charAt(i));
            }
        }

        for (int i = 0; i < 5; i++) {
            divisorIntArray[i] = Integer.valueOf(divisor.charAt(i));
        }


    }
}
