package com.truemind.ecpe;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 현석 on 2016-12-05.
 */
public class LogActivity extends Activity {

    private final int dataLength = 8;
    private final int crcLength = 7;
    TextView textView;
    ImageButton log_btn;
    String log;
    ProgressDialog progressdialog;

    int tempArray[] = new int[crcLength+1];
    int dataIntArray[][] = new int[8][];
    int divisorIntArray[] = new int[crcLength+1];
    int crcIntArray[][] = new int[8][];
    String slidingWindow[] = new String[3];
    String ACK[] = new String[8];
    String receive[] = new String[8];

    String frameCodeword[] = new String[8];
    String frame[] = new String[8];
    int frameScenario[] = new int[8];
    String divisor;
    int crcResult[] = new int[8];
    int error[] = new int[8];
    private Timer timer;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        initView();
        initListener();
        for(int i=0;i<8;i++){
            dataIntArray[i]=new int[dataLength+crcLength+1];
        }
        for(int i=0;i<8;i++){
            crcIntArray[i]=new int[crcLength];
        }
        progressdialog = ProgressDialog.show(this, "로딩중", "Loading...please wait", true, false);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                log = "";
                Intent intent = getIntent();

                divisor = intent.getStringExtra("divisor");
                for(int i = 0; i < crcLength+1; i++) {
                    divisorIntArray[i] = Atoi(divisor.charAt(i));
                    Log.d("MyTag", "divisorIntArray"+i+divisorIntArray[i]);
                }
                //get String method changed - due to memory allocate
                //get frameCodeWord first, and assemble into frame in LogActivity
                //(previously, assembled in TransmitActivity)
                /*
                frameCodeword[0] = intent.getStringExtra("frame[0]");
                frameCodeword[1] = intent.getStringExtra("frame[1]");
                frameCodeword[2] = intent.getStringExtra("frame[2]");
                frameCodeword[3] = intent.getStringExtra("frame[3]");
                frameCodeword[4] = intent.getStringExtra("frame[4]");
                frameCodeword[5] = intent.getStringExtra("frame[5]");
                frameCodeword[6] = intent.getStringExtra("frame[6]");
                frameCodeword[7] = intent.getStringExtra("frame[7]");

                for(int i = 0; i<8; i++) {
                    frame[i] = "01111110" + threeBitDtob(i) + frameCodeword[i] + "01111110";
                }

                */
                frame[0] = intent.getStringExtra("frame[0]");
                frame[1] = intent.getStringExtra("frame[1]");
                frame[2] = intent.getStringExtra("frame[2]");
                frame[3] = intent.getStringExtra("frame[3]");
                frame[4] = intent.getStringExtra("frame[4]");
                frame[5] = intent.getStringExtra("frame[5]");
                frame[6] = intent.getStringExtra("frame[6]");
                frame[7] = intent.getStringExtra("frame[7]");

                frameScenario[0] = intent.getIntExtra("frameScenario[0]", 4);
                frameScenario[1] = intent.getIntExtra("frameScenario[1]", 4);
                frameScenario[2] = intent.getIntExtra("frameScenario[2]", 4);
                frameScenario[3] = intent.getIntExtra("frameScenario[3]", 4);
                frameScenario[4] = intent.getIntExtra("frameScenario[4]", 4);
                frameScenario[5] = intent.getIntExtra("frameScenario[5]", 4);
                frameScenario[6] = intent.getIntExtra("frameScenario[6]", 4);
                frameScenario[7] = intent.getIntExtra("frameScenario[7]", 4);
                for(int i = 0; i<8; i++) {
                    receive[i] = "0";
                    ACK[i] = "0";
                }
                log += "------------------------------------------\n";
                log += "------------Receive/ACK initialized-----------\n";
                log += "------------------------------------------\n";
                for(int i = 0; i<8; i++) {
                    log += "Receive["+i+"]: "+receive[i]+"\n";
                }
                for(int i = 0; i<8; i++) {
                    log += "ACK["+i+"]: "+ACK[i]+"\n";
                }

                log += "------------------------------------------\n";
                for(int i = 0; i<8; i++) {
                    log += "frame["+i+"]: "+frame[i]+"\n";
                }

                log += "------------------------------------------\n";
                //log += "error - (1) for success, (0) for error\n";
                log += "--------------------Start------------------\n";
                log += "------------------------------------------\n";
                sliding(0);

                threadhandler.sendEmptyMessage(0);
            }
        });
        thread.start();
    }


    public void initView(){

        textView = (TextView) findViewById(R.id.textView);
        log_btn = (ImageButton) findViewById(R.id.log_btn);

    }
    public void initListener(){

        textView.setMovementMethod(new ScrollingMovementMethod());

        log_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private Handler threadhandler = new Handler(){
        public void handleMessage(Message msg){
            textView.setText(log);
            progressdialog.dismiss();
        }
    };
//-------------------------------------------------------------------------------------------------------
////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////// frameCodeWord - codeword of frame        ///////////////////////////////
///////////////////////////////   flag    seq    data     CRC     flag   ///////////////////////////////
/////////////////////////////// 01111110  000  00000000 0000000 01111110 ///////////////////////////////
///////////////////////////////               | frameCodeWord |          ///////////////////////////////
/////////////////////////////// DataIntArray - integer type codeWord ///////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////
//-------------------------------------------------------------------------------------------------------
    private void setRightDataIntArray(int n){
        for (int i = 0; i < dataLength+crcLength; i++) {
            dataIntArray[n][i] = (Atoi(frameCodeword[n].charAt(i)));
        }
    }

    private void setErrorDataIntArray(int n){
        for (int i = 0; i < dataLength+crcLength; i++) {
            dataIntArray[n][i] = (Atoi(frameCodeword[n].charAt(i)));
        }
        double randomValue = Math.random();
        int intValue = (int)(randomValue * (dataLength+crcLength-1));

        if(dataIntArray[n][intValue] == 1)
            dataIntArray[n][intValue] = 0;
        else
            dataIntArray[n][intValue] = 1;
    }

    private void setframeCodeword(int n, int error){

        frameCodeword[n] ="";
        for(int i = 0; i<dataLength+crcLength; i++) {
            frameCodeword[n] += frame[n].charAt(i+11);
        }
        if(error==1)
            setErrorDataIntArray(n);
        else
            setRightDataIntArray(n);

    }
//-------------------------------------------------------------------------------------------------------
///////////////////////////////////////////////////////////////////////////////////////////////////////
/////////// Now initiate sending/receiving process, according to scenario checked behind.//////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////
//-------------------------------------------------------------------------------------------------------

    public void work(int frameScenario, int i){
        final int frameScenarioFinal = frameScenario;
        final int iFinal = i;
        long tempTime = System.currentTimeMillis();
        /*TimerTask task = new TimerTask() {
            public void run() {
                try {*/
                    //need timer

                    switch (frameScenario) {
                        case 1:
                            setframeCodeword(i, 0);
                            error[i] = errorDetection(i);
                            Log.d("MyTag", "error" + i + error[i]);
                            Log.d("MyTag", "frameCodeword" + i + frameCodeword[i]);
                            //log += "Frame"+"["+i+"] error : "+ error[i]+"\n";
                            break;
                        case 2:
                            setframeCodeword(i, 1);
                            error[i] = errorDetection(i);
                            Log.d("MyTag", "error" + i + error[i]);
                            Log.d("MyTag", "frameCodeword" + i + frameCodeword[i]);
                            //log += "Frame"+"["+i+"] error : "+ error[i]+"\n";
                            break;
                        case 3:
                            setframeCodeword(i, 0);
                            error[i] = errorDetection(i);
                            Log.d("MyTag", "error" + i + error[i]);
                            Log.d("MyTag", "frameCodeword" + i + frameCodeword[i]);
                            //log += "Frame"+"["+i+"] error : "+ error[i]+"\n";
                            break;
                        case 4:
                            setframeCodeword(i, 0);
                            error[i] = errorDetection(i);
                            Log.d("MyTag", "error" + i + error[i]);
                            Log.d("MyTag", "frameCodeword" + i + frameCodeword[i]);
                            //log += "Frame"+"["+i+"] error : "+ error[i]+"\n";
                            break;
                    }/*
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        Timer mTimer = new Timer();
        mTimer.schedule(task,300);
*/
        sendInitiate(frameScenario, i);

        log+="\n";
        //until here
    }



    private void sendInitiate(int frameScenario, int i) {
//--------------------------------------------------------------------------------------------------
/////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////// Send/receive according to scenario//////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////

        if(error[i]==1) {
            if (frameScenario == 1) {
                receive[i] = "0";
                ACK[i] = "0";
            } else if (frameScenario == 2) {
                receive[i] = frame[i];
                ACK[i] = "01111110" + threeBitDtob(i+1) + "01111110";
            } else if (frameScenario == 3) {
                receive[i] = frame[i];
                ACK[i] = "0";
            } else if (frameScenario == 4) {
                receive[i] = frame[i];
                ACK[i] = "01111110" + threeBitDtob(i+1) + "01111110";
            }
        }
        else if(error[i]==0) {
            receive[i] = "01111110" + threeBitDtob(i) + frameCodeword[i] + "01111110";
            ACK[i] = "0";
        }

        log += "\n";
        log += "Receive[" + i + "]: " + receive[i] + "\nACK[" + i + "]: " + ACK[i]+"\n";
        /*
        if (receive[i] != "0" && ACK[i] != "0" && error[i] == 1)
            log += "Frame[" + i + "]--- Success..!\n";
        else
            log += "Frame[" + i + "]--- Error..!\n";
*/
//---------------------------------------------------------------------------------------------------Find whether error or success
        if(error[i]==1) {
            if (receive[i] == "0" && ACK[i] == "0") {
                log += "Frame[" + i + "]--- Frame lost...\n";

            } else if (receive[i] != "0" && ACK[i] == "0") {
                log += "Frame[" + i + "]--- ACK lost...\n";

            } else if (receive[i] != "0" && ACK[i] != "0" && error[i] == 1)
                log += "Frame[" + i + "]--- Success..!\n";
        }
        else if(error[i]==0) {
            log += "Frame[" + i + "]--- Single bit error..\n";

        }
//--------------------------------------------------------------------------------------------------
    }


    private void sliding(int i) {
//-------------------------------------------------------------------------------------------------------
///////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////// SELECTIVE_REPEAT //////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////
//-------------------------------------------------------------------------------------------------------
/*
        while((i+2) < frame.length){
            int window = 0;
            slidingWindow[0] = "frame[" + i + "]";
            slidingWindow[1] = "frame[" + (i + 1) + "]";
            if(i+2 != 8) {
                slidingWindow[2] = "frame[" + (i + 2) + "]";
            }else{
                slidingWindow[2] = "NULL";
            }

            for(int j = 0; j<3; j++) {
                log += "slidingWindow["+j+"]: " + slidingWindow[j] + "\n";
            }
            work(frameScenario[i], i);
            work(frameScenario[i + 1], i + 1);
            work(frameScenario[i + 2], i + 2);

            if (ACK[i] == "0") {
                window = i;
            }
            if (ACK[i + 1] == "0") {
                window = i + 1;
            }
            if (ACK[i + 2] == "0") {
                window = i + 2;
            }
            if(ACK[i] != "0" && ACK[i+1] != "0" && ACK[i+2] != "0")
                window = i+3;

            log += "\n";

            slidingWindow[0] = "frame[" + window + "]";
            slidingWindow[1] = "frame[" + (window + 1) + "]";
            slidingWindow[2] = "frame[" + (window + 2) + "]";
            for(int j = 0; j<3; j++) {
                log += "slidingWindow["+j+"]: " + slidingWindow[j] + "\n";
            }

            if((window-i)==0) {
                errorRecovery(window);
                errorRecovery(window+1);
                errorRecovery(window+2);
                i = window+1;
            }
            if((window-i)==1) {
                errorRecovery(window);
                errorRecovery(window+1);
                i = window+1;
            }
            if((window-i)==2) {
                errorRecovery(window);
                i = window+1;
            }
            if((window-i)==3) {
                i = window;
            }
        }


        if(i==6){
            int window = 0;
            slidingWindow[0] = "frame[" + i + "]";
            slidingWindow[1] = "frame[" + (i + 1) + "]";
            slidingWindow[2] = "NULL";
            for(int j = 0; j<3; j++) {
                log += "slidingWindow["+j+"]: " + slidingWindow[j] + "\n";
            }
            work(frameScenario[i], i);
            work(frameScenario[i + 1], i + 1);

            if (ACK[i] == "0") {
                window = i;
            }
            else if (ACK[i + 1] == "0") {
                window = i + 1;
            }
            if((window-i)==0) {
                errorRecovery(window);
                errorRecovery(window+1);
            }
            if((window-i)==1) {
                errorRecovery(window);
            }
            slidingWindow[0] = "NULL";
            slidingWindow[1] = "NULL";
            slidingWindow[2] = "NULL";
            for(int j = 0; j<3; j++) {
                log += "slidingWindow["+j+"]: " + slidingWindow[j] + "\n";
            }
        }

        else if(i==7){
            int window = 0;
            slidingWindow[0] = "frame[" + i + "]";
            slidingWindow[1] = "NULL";
            slidingWindow[2] = "NULL";
            for(int j = 0; j<3; j++) {
                log += "slidingWindow["+j+"]: " + slidingWindow[j] + "\n";
            }

            work(frameScenario[i], i);

            if (ACK[i] == "0") {
                window = i;
            }
            if((window-i)==0) {
                errorRecovery(window);
            }
            slidingWindow[0] = "NULL";
            slidingWindow[1] = "NULL";
            slidingWindow[2] = "NULL";
            for(int j = 0; j<3; j++) {
                log += "slidingWindow["+j+"]: " + slidingWindow[j] + "\n";
            }
        }*/
//-------------------------------------------------------------------------------------------------------
///////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////// GO_BACK_N////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////
//-------------------------------------------------------------------------------------------------------
        while((i+2) < frame.length){
            int window = 0;
            slidingWindow[0] = "frame[" + i + "]";
            slidingWindow[1] = "frame[" + (i + 1) + "]";
            if(i+2 != 8) {
                slidingWindow[2] = "frame[" + (i + 2) + "]";
            }else{
                slidingWindow[2] = "NULL";
            }

            for(int j = 0; j<3; j++) {
                log += "slidingWindow["+j+"]: " + slidingWindow[j] + "\n";
            }
            work(frameScenario[i], i);
            work(frameScenario[i + 1], i + 1);
            work(frameScenario[i + 2], i + 2);

            if (ACK[i] == "0") {
                window = i;
            }
            else if (ACK[i + 1] == "0") {
                window = i + 1;
            }
            else if (ACK[i + 2] == "0") {
                window = i + 2;
            }
            else if(ACK[i] != "0" && ACK[i+1] != "0" && ACK[i+2] != "0")
                window = i+3;

            log += "\n";

            if((window-i)==0) {
                errorRecovery(window);
                errorRecovery(window+1);
                errorRecovery(window+2);
            }
            if((window-i)==1) {
                errorRecovery(window);
                errorRecovery(window+1);
            }
            if((window-i)==2) {
                errorRecovery(window);
            }
            i += 3;
            log += "\n";
        }


        if(i==6){
            int window = 0;
            slidingWindow[0] = "frame[" + i + "]";
            slidingWindow[1] = "frame[" + (i + 1) + "]";
            slidingWindow[2] = "NULL";
            for(int j = 0; j<3; j++) {
                log += "slidingWindow["+j+"]: " + slidingWindow[j] + "\n";
            }
            work(frameScenario[i], i);
            work(frameScenario[i + 1], i + 1);

            if (ACK[i] == "0") {
                window = i;
            }
            else if (ACK[i + 1] == "0") {
                window = i + 1;
            }
            if((window-i)==0) {
                errorRecovery(window);
                errorRecovery(window+1);
            }
            if((window-i)==1) {
                errorRecovery(window);
            }
            slidingWindow[0] = "NULL";
            slidingWindow[1] = "NULL";
            slidingWindow[2] = "NULL";
            for(int j = 0; j<3; j++) {
                log += "slidingWindow["+j+"]: " + slidingWindow[j] + "\n";
            }
        }


    }
//-----------------------------------------------------------------------
///////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////when error, switch into recovery mode////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////
    private void errorRecovery(int i) {
        if(error[i]==1) {
            if (receive[i] == "0" && ACK[i] == "0") {
                log += "Frame[" + i + "]--- Frame lost, resending ...\n";
                receive[i] = frame[i];
                ACK[i] = "01111110" + threeBitDtob(i+1) + "01111110";
            } else if (receive[i] != "0" && ACK[i] == "0") {
                log += "Frame[" + i + "]--- ACK lost, resending ...\n";
                ACK[i] = "01111110" + threeBitDtob(i+1) + "01111110";
            } else if (receive[i] != "0" && ACK[i] != "0" && error[i] == 1)
                log += "Frame[" + i + "]--- Success..!\n";
        }
        else if(error[i]==0) {
            log += "Frame[" + i + "]--- Single bit error, resending ...\n";
            receive[i] = frame[i];
            ACK[i] = "01111110" + threeBitDtob(i+1) + "01111110";
        }

        log += "Receive[" + i + "]: " + receive[i] + "\nACK[" + i + "]: " + ACK[i]+"\n\n";
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    private int errorDetection(int i) {

        for (int i2 = 0; i2 < crcLength+1; i2++) {
            tempArray[i2] = dataIntArray[i][i2];
            Log.d("MyTag", "data" + ": " + dataIntArray[i][i2]);
            Log.d("MyTag", "tA" + i2 + ": " + tempArray[i2]);
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
            tempArray[crcLength] = dataIntArray[i][j + (crcLength+1)];
            for (int k = 0; k < crcLength; k++) {
                Log.d("MyTag", "tA" + j + ": " + tempArray[k]);
            }
        }
        for (int j = 0; j < crcLength; j++) {
            crcIntArray[i][j] = tempArray[j];
        }
        for (int j = 0; j < crcLength; j++) {
            crcResult[i] += crcIntArray[i][j];
        }

        Log.d("MyTag", "crcResult["+i+"]"+crcResult[i]);

        if(crcResult[i] == 0)
            return 1;
        else
            return 0;
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    private int XOR(int a, int b){
        if(a==b){
            return 0;
        }
        else{
            return 1;
        }
    }

    private int Atoi(char a){
        if(a=='1'){
            return 1;
        }else{
            return 0;
        }
    }

    /*private String threeBitDtob(int a){

        String binary = "";
        int b[] = new int[4];
        for(int i = 0; i < 3; i++){
            b[i] = 0;
        }
        int c = 0;
        int d = 0;
        int s = 0;

        do{
            c = (int)a/2;
            d = a - c*2;
            s += 1;
            b[s] = d;
            a = c;
        }while(c!=0);

        for(int i =3; i>0; i--){
            binary += b[i];
        }
        return binary;
    }*/
    private String threeBitDtob(int a){

        String binary = "";

        switch(a){
            case 0: {binary = "000"; break;}
            case 1: {binary = "001"; break;}
            case 2: {binary = "010"; break;}
            case 3: {binary = "011"; break;}
            case 4: {binary = "100"; break;}
            case 5: {binary = "101"; break;}
            case 6: {binary = "110"; break;}
            case 7: {binary = "111"; break;}
            default: {binary = "000"; break;}
        }
        return binary;
    }
}
