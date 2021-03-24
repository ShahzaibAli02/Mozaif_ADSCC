package com.example.adscc.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.adscc.Constants;
import com.example.adscc.R;

public class AdminDashBoard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dash_board);
    }

    public void onClickBookings(View view)
    {
        startActivity(new Intent(this,AdminBookingList.class).putExtra("TYPE", Constants.DB_BOOKINGS));
    }

    public void onClickHomeService(View view) {
        startActivity(new Intent(this,AdminBookingList.class).putExtra("TYPE",Constants.DB_HOME_SERVICES));
    }
}