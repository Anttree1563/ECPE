package com.truemind.ecpe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * Created by 현석 on 2016-11-28.
 */
public class ScenarioActivity extends Activity {

    RadioGroup rdg1, rdg2, rdg3 ,rdg4, rdg5, rdg6, rdg7, rdg8;
    ImageButton btn1, btn2;
    RadioButton rb4, rb8, rb12, rb16, rb20, rb24, rb28, rb32;
    int frameScenario[] = new int[8];
    String frame[] = new String[8];
    String divisor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenario);
        initView();

        Intent intent = getIntent();

        frame[0] = intent.getStringExtra("frame1");
        frame[1] = intent.getStringExtra("frame2");
        frame[2] = intent.getStringExtra("frame3");
        frame[3] = intent.getStringExtra("frame4");
        frame[4] = intent.getStringExtra("frame5");
        frame[5] = intent.getStringExtra("frame6");
        frame[6] = intent.getStringExtra("frame7");
        frame[7] = intent.getStringExtra("frame8");
        divisor = intent.getStringExtra("divisor");

        initListener();
    }
    public void initView(){
        btn1 = (ImageButton) findViewById(R.id.scenario_btn1);
        btn2 = (ImageButton) findViewById(R.id.scenario_btn2);

        rdg1 = (RadioGroup) findViewById(R.id.radioGroup1);
        rdg2 = (RadioGroup) findViewById(R.id.radioGroup2);
        rdg3 = (RadioGroup) findViewById(R.id.radioGroup3);
        rdg4 = (RadioGroup) findViewById(R.id.radioGroup4);
        rdg5 = (RadioGroup) findViewById(R.id.radioGroup5);
        rdg6 = (RadioGroup) findViewById(R.id.radioGroup6);
        rdg7 = (RadioGroup) findViewById(R.id.radioGroup7);
        rdg8 = (RadioGroup) findViewById(R.id.radioGroup8);
    }

    public void initListener(){
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                frameScenario[0] = (rdg1.getCheckedRadioButtonId()-2131492954);
                frameScenario[1] = (rdg2.getCheckedRadioButtonId()-2131492959);
                frameScenario[2] = (rdg3.getCheckedRadioButtonId()-2131492964);
                frameScenario[3] = (rdg4.getCheckedRadioButtonId()-2131492969);
                frameScenario[4] = (rdg5.getCheckedRadioButtonId()-2131492974);
                frameScenario[5] = (rdg6.getCheckedRadioButtonId()-2131492979);
                frameScenario[6] = (rdg7.getCheckedRadioButtonId()-2131492984);
                frameScenario[7] = (rdg8.getCheckedRadioButtonId()-2131492989);

                for(int i = 0; i<8; i++) {
                    Toast.makeText(getApplicationContext(), "frame" + i + ": " + frameScenario[i], Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(ScenarioActivity.this, LogActivity.class);

                intent.putExtra("divisor", divisor);
                intent.putExtra("frame[0]", frame[0]);
                intent.putExtra("frame[1]", frame[1]);
                intent.putExtra("frame[2]", frame[2]);
                intent.putExtra("frame[3]", frame[3]);
                intent.putExtra("frame[4]", frame[4]);
                intent.putExtra("frame[5]", frame[5]);
                intent.putExtra("frame[6]", frame[6]);
                intent.putExtra("frame[7]", frame[7]);

                intent.putExtra("frameScenario[0]", frameScenario[0]);
                intent.putExtra("frameScenario[1]", frameScenario[1]);
                intent.putExtra("frameScenario[2]", frameScenario[2]);
                intent.putExtra("frameScenario[3]", frameScenario[3]);
                intent.putExtra("frameScenario[4]", frameScenario[4]);
                intent.putExtra("frameScenario[5]", frameScenario[5]);
                intent.putExtra("frameScenario[6]", frameScenario[6]);
                intent.putExtra("frameScenario[7]", frameScenario[7]);

                startActivity(intent);
                finish();
            }
        });


    }
}
