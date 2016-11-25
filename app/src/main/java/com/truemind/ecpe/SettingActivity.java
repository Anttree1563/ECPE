package com.truemind.ecpe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Created by 현석 on 2016-11-25.
 */
public class SettingActivity extends Activity {

    ImageButton btn1, btn2;
    EditText edittext1, edittext2, edittext3, edittext4, edittext5, edittext6, edittext7, edittext8, edittext9;
    String data1, data2, data3, data4, data5, data6, data7, data8, divisor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
        initListener();
    }
    private void initListener() {

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                Intent intent3 = new Intent(SettingActivity.this, MainActivity.class);
                startActivity(intent3);
                finish();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                String inPutText1 = edittext1.getText().toString();
                String inPutText2 = edittext2.getText().toString();
                String inPutText3 = edittext3.getText().toString();
                String inPutText4 = edittext4.getText().toString();
                String inPutText5 = edittext5.getText().toString();
                String inPutText6 = edittext6.getText().toString();
                String inPutText7 = edittext7.getText().toString();
                String inPutText8 = edittext8.getText().toString();
                String inPutText9 = edittext9.getText().toString();

                if(inPutText1.length()!=12||inPutText2.length()!=12||inPutText3.length()!=12||inPutText4.length()!=12
                        ||inPutText5.length()!=12||inPutText6.length()!=12||inPutText7.length()!=12||inPutText8.length()!=12) {
                    Toast.makeText(getApplicationContext(), "Data must be 12 bits",Toast.LENGTH_SHORT).show();
                }else if(inPutText9.length()!=5){
                    Toast.makeText(getApplicationContext(), "Divisor must be 5 bits",Toast.LENGTH_SHORT).show();
                }else{

                    Intent intent1 = new Intent(SettingActivity.this, MainActivity.class);
                    intent1.putExtra("data1", data1);
                    intent1.putExtra("data2", data2);
                    intent1.putExtra("data3", data3);
                    intent1.putExtra("data4", data4);
                    intent1.putExtra("data5", data5);
                    intent1.putExtra("data6", data6);
                    intent1.putExtra("data7", data7);
                    intent1.putExtra("data8", data8);
                    intent1.putExtra("divisor", divisor);

                    startActivity(intent1);
                    finish();
                }
            }

        });
    }

    private void initView() {
        btn1 = (ImageButton) findViewById(R.id.setting_btn1);
        btn2 = (ImageButton) findViewById(R.id.setting_btn2);
        edittext1 = (EditText) findViewById(R.id.editText1);
        edittext2 = (EditText) findViewById(R.id.editText2);
        edittext3 = (EditText) findViewById(R.id.editText3);
        edittext4 = (EditText) findViewById(R.id.editText4);
        edittext5 = (EditText) findViewById(R.id.editText5);
        edittext6 = (EditText) findViewById(R.id.editText6);
        edittext7 = (EditText) findViewById(R.id.editText7);
        edittext8 = (EditText) findViewById(R.id.editText8);
        edittext9 = (EditText) findViewById(R.id.editText9);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SettingActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
