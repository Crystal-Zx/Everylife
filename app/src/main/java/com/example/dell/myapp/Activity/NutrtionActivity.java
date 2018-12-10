package com.example.dell.myapp.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.dell.myapp.R;

public class NutrtionActivity extends AppCompatActivity {

    private EditText riceRc;
    private EditText fishRc;
    private EditText dessertRc;
    private EditText chickRc;
    private EditText pigRc;
    private EditText drinkRc;

    private ImageButton toSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrition);
        bindView();
        toSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float rice,fish,dessert,chick,pig,drink,sumN = 0;
                if(riceRc.getText().toString().equals("") || fishRc.getText().toString().equals("")
                        || dessertRc.getText().toString().equals("") || chickRc.getText().toString().equals("")
                        || pigRc.getText().toString().equals("") || drinkRc.getText().toString().equals("")) {
                    Toast.makeText(NutrtionActivity.this,"食物重量不能为空！",Toast.LENGTH_LONG).show();
                }
                else {
                    rice = Float.parseFloat(riceRc.getText().toString());
                    fish = Float.parseFloat(fishRc.getText().toString());
                    dessert = Float.parseFloat(dessertRc.getText().toString());
                    chick = Float.parseFloat(chickRc.getText().toString());
                    pig = Float.parseFloat(pigRc.getText().toString());
                    drink = Float.parseFloat(drinkRc.getText().toString());
                    sumN = rice * (float)1.2 + fish * (float)1.3 + dessert * (float)3.2
                            + chick * (float)1.7 + pig * (float)1.4 + drink * (float)3.5;
                    sumN = (float)(Math.round(sumN*10))/10;
                    Intent intent = new Intent();
                    intent.putExtra("nutrition",sumN);
                    setResult(RESULT_OK,intent);
                    Toast.makeText(NutrtionActivity.this,"记录成功！",Toast.LENGTH_LONG).show();
                    finish();
                }

            }
        });
    }

    private void bindView() {
        riceRc = findViewById(R.id.rice_rc);
        fishRc = findViewById(R.id.fish_rc);
        dessertRc = findViewById(R.id.dessert_rc);
        chickRc = findViewById(R.id.chick_rc);
        pigRc = findViewById(R.id.pig_rc);
        drinkRc = findViewById(R.id.drink_rc);
        toSave = findViewById(R.id.nutrition_save);
    }
}