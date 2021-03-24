package com.example.adscc.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adscc.Adapter.BookingAdapter;
import com.example.adscc.Constants;
import com.example.adscc.Model.Booking;
import com.example.adscc.ProgressDialogManager;
import com.example.adscc.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminBookingList extends AppCompatActivity {


    RecyclerView recyclerList;
    String type;
    ImageView imgNotFound;
    List<Booking> list=new ArrayList<>();
    TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_booking_list);
        type=getIntent().getExtras().getString("TYPE");
        initViews();

    }

    private void initViews()
    {

        recyclerList=findViewById(R.id.recyclerList);
        txt=findViewById(R.id.txt);
        txt.setText(type);
        imgNotFound=findViewById(R.id.imgNotFound);
        recyclerList.setLayoutManager(new LinearLayoutManager(this));
        loadData();

    }

    private void loadData()
    {

        AlertDialog progressDialog = ProgressDialogManager.getProgressDialog(this);
        progressDialog.show();
        FirebaseDatabase.getInstance().getReference(type).addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {

                progressDialog.dismiss();
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    list.add(dataSnapshot1.getValue(Booking.class));
                }

                if(list.size()>0)
                {


                    recyclerList.setAdapter(new BookingAdapter(list,AdminBookingList.this,type));
                    recyclerList.setVisibility(View.VISIBLE);

                    imgNotFound.setVisibility(View.GONE);

                }
                else
                {

                    recyclerList.setVisibility(View.GONE);
                    imgNotFound.setVisibility(View.VISIBLE);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
                Toast.makeText(AdminBookingList.this,databaseError.getMessage(),Toast.LENGTH_LONG).show();
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}