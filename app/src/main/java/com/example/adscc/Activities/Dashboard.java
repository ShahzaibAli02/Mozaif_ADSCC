package com.example.adscc.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.adscc.R;

public class Dashboard extends AppCompatActivity {

private Button button_create;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        button_create = (Button) findViewById(R.id.button_create);
        button_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreate_Account();
            }
        });

    }

    public void openCreate_Account(){
        Intent intent = new Intent(this,Create_Account.class);
        startActivity(intent);

    }

    public void onClickLogin(View view) {
        Intent intent = new Intent(this,Login.class);
        startActivity(intent);
    }
}


