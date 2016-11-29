package com.truemind.ecpe;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Created by 현석 on 2016-11-27.
 */
public class TransmitActivity extends Activity{

    ImageButton transmit_local;
    String codeword1, codeword2, codeword3, codeword4, codeword5, codeword6, codeword7, codeword8, divisor;
    //String frame1, frame2, frame3, frame4, frame5, frame6, frame7, frame8;
    String frame[] = new String[8];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transmit);
        initView();
        initListener();

        Intent intent = getIntent();

        codeword1 = intent.getStringExtra("codeword1");
        codeword2 = intent.getStringExtra("codeword2");
        codeword3 = intent.getStringExtra("codeword3");
        codeword4 = intent.getStringExtra("codeword4");
        codeword5 = intent.getStringExtra("codeword5");
        codeword6 = intent.getStringExtra("codeword6");
        codeword7 = intent.getStringExtra("codeword7");
        codeword8 = intent.getStringExtra("codeword8");
        divisor = intent.getStringExtra("divisor");


        for(int i =0; i<8; i++){
            frame[i] = "";
        }

        frame[0] += "0111110"+"000"+codeword1+"01111110";
        frame[1] += "0111110"+"001"+codeword2+"01111110";
        frame[2] += "0111110"+"010"+codeword3+"01111110";
        frame[3] += "0111110"+"011"+codeword4+"01111110";
        frame[4] += "0111110"+"100"+codeword5+"01111110";
        frame[5] += "0111110"+"101"+codeword6+"01111110";
        frame[6] += "0111110"+"110"+codeword7+"01111110";
        frame[7] += "0111110"+"111"+codeword8+"01111110";


        for(int i = 0; i<8; i++) {
            Toast.makeText(getApplicationContext(), "frame" + i + ": " + frame[i], Toast.LENGTH_SHORT).show();
        }
    }

    private int Atoi(char a){
        if(a=='1'){
            return 1;
        }else{
            return 0;
        }
    }

    private int Itoa(int a){
        if(a==1){
            return '1';
        }else{
            return '0';
        }
    }
/*

    private String bitDtoB(int decimal){
        String binary = "";
        binary += Itoa((decimal/4)%2);
        binary += Itoa((decimal/2)%2);
        binary += Itoa(decimal%2);
        return binary;
    }
*/

    private void initView(){
        transmit_local = (ImageButton)(findViewById(R.id.transmit_local));
    }

    private  void initListener(){

        transmit_local.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransmitActivity.this, MainActivity.class);
                intent.putExtra("frame1", frame[0]);
                intent.putExtra("frame2", frame[1]);
                intent.putExtra("frame3", frame[2]);
                intent.putExtra("frame4", frame[3]);
                intent.putExtra("frame5", frame[4]);
                intent.putExtra("frame6", frame[5]);
                intent.putExtra("frame7", frame[6]);
                intent.putExtra("frame8", frame[7]);
                intent.putExtra("divisor", divisor);
                startActivity(intent);
                finish();
            }
        });

    }

//--------------------------------------------------------------------------------------------------
@Override
public void onBackPressed() {
        Intent intent = new Intent(TransmitActivity.this, SettingActivity.class);

        /*intent.putExtra("codeword1", codeword1);
        intent.putExtra("codeword2", codeword2);
        intent.putExtra("codeword3", codeword3);
        intent.putExtra("codeword4", codeword4);
        intent.putExtra("codeword5", codeword5);
        intent.putExtra("codeword6", codeword6);
        intent.putExtra("codeword7", codeword7);
        intent.putExtra("codeword8", codeword8);
        intent.putExtra("divisor", divisor);*/
        startActivity(intent);
        finish();
        }
}
