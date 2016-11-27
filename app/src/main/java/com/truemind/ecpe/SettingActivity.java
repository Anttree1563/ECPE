package com.truemind.ecpe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
    String inputText1, inputText2, inputText3, inputText4, inputText5, inputText6, inputText7, inputText8, inputText9;

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

                inputText1 = edittext1.getText().toString();
                inputText2 = edittext2.getText().toString();
                inputText3 = edittext3.getText().toString();
                inputText4 = edittext4.getText().toString();
                inputText5 = edittext5.getText().toString();
                inputText6 = edittext6.getText().toString();
                inputText7 = edittext7.getText().toString();
                inputText8 = edittext8.getText().toString();
                inputText9 = edittext9.getText().toString();

                if(inputText1.length()!=12||inputText2.length()!=12||inputText3.length()!=12||inputText4.length()!=12
                        ||inputText5.length()!=12||inputText6.length()!=12||inputText7.length()!=12||inputText8.length()!=12) {
                    Toast.makeText(getApplicationContext(), "Data must be 12 bits",Toast.LENGTH_SHORT).show();
                }else if(inputText9.length()!=5){
                    Toast.makeText(getApplicationContext(), "Divisor must be 5 bits",Toast.LENGTH_SHORT).show();
                }else{

                    Intent intent1 = new Intent(SettingActivity.this, CheckActivity.class);
                    intent1.putExtra("data1", inputText1);
                    intent1.putExtra("data2", inputText2);
                    intent1.putExtra("data3", inputText3);
                    intent1.putExtra("data4", inputText4);
                    intent1.putExtra("data5", inputText5);
                    intent1.putExtra("data6", inputText6);
                    intent1.putExtra("data7", inputText7);
                    intent1.putExtra("data8", inputText8);
                    intent1.putExtra("divisor", inputText9);

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
