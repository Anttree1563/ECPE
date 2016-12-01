package com.truemind.ecpe;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by 현석 on 2016-11-28.
 */
public class ScenarioActivity extends Activity {

    RadioGroup rdg1, rdg2, rdg3 ,rdg4, rdg5, rdg6, rdg7, rdg8;
    ImageButton btn1, btn2;
    RadioButton rb4, rb8, rb12, rb16, rb20, rb24, rb28, rb32;
    int frameScenario[] = new int[8];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
        initListener();
    }
    public void initView(){
        rb4 = (RadioButton) findViewById(R.id.radioButton4);
        rb8 = (RadioButton) findViewById(R.id.radioButton8);
        rb12 = (RadioButton) findViewById(R.id.radioButton12);
        rb16 = (RadioButton) findViewById(R.id.radioButton16);
        rb20 = (RadioButton) findViewById(R.id.radioButton20);
        rb24 = (RadioButton) findViewById(R.id.radioButton24);
        rb28 = (RadioButton) findViewById(R.id.radioButton28);
        rb32 = (RadioButton) findViewById(R.id.radioButton32);
        rb4.setChecked(true);
        rb8.setChecked(true);
        rb12.setChecked(true);
        rb16.setChecked(true);
        rb20.setChecked(true);
        rb24.setChecked(true);
        rb28.setChecked(true);
        rb32.setChecked(true);

        rdg1 = (RadioGroup) findViewById(R.id.radioGroup1);
        rdg2 = (RadioGroup) findViewById(R.id.radioGroup2);
        rdg3 = (RadioGroup) findViewById(R.id.radioGroup3);
        rdg4 = (RadioGroup) findViewById(R.id.radioGroup4);
        rdg5 = (RadioGroup) findViewById(R.id.radioGroup5);
        rdg6 = (RadioGroup) findViewById(R.id.radioGroup6);
        rdg7 = (RadioGroup) findViewById(R.id.radioGroup7);
        rdg8 = (RadioGroup) findViewById(R.id.radioGroup8);
        frameScenario[0] = rdg1.getCheckedRadioButtonId();
        frameScenario[1] = rdg2.getCheckedRadioButtonId();
        frameScenario[2] = rdg3.getCheckedRadioButtonId();
        frameScenario[3] = rdg4.getCheckedRadioButtonId();
        frameScenario[4] = rdg5.getCheckedRadioButtonId();
        frameScenario[5] = rdg6.getCheckedRadioButtonId();
        frameScenario[6] = rdg7.getCheckedRadioButtonId();
        frameScenario[7] = rdg8.getCheckedRadioButtonId();
    }

    public void initListener(){

    }
}
