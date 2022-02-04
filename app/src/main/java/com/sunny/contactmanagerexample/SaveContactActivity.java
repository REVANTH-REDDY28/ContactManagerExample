package com.sunny.contactmanagerexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SaveContactActivity extends AppCompatActivity {
    EditText name;
    EditText number;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_contact);
        Intent in = getIntent();

        name = findViewById(R.id.s_name);
        number = findViewById(R.id.s_number);
        btn = findViewById(R.id.button_save_contact);

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                in.putExtra("name",String.valueOf(name.getText()));
                in.putExtra("number",String.valueOf(number.getText()));
                setResult(RESULT_OK,in);
                finish();
            }
        });

    }


}