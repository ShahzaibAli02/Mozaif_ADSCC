package com.example.adscc.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.adscc.Adapter.ResultAdapter;
import com.example.adscc.Constants;
import com.example.adscc.Model.Result;
import com.example.adscc.ProgressDialogManager;
import com.example.adscc.R;
import com.example.adscc.UserData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CheckResults extends AppCompatActivity {


    RecyclerView resultsHistory;
    List<Result> resultListHistory=new ArrayList<>();
    TextView txtNoRecentHistory;

    TextView txtPersonName;
    TextView txtPersonEmiratesID;
    TextView txtlastPCRDate;
    TextView txtMessage;
    String lastDate;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_results);
        initViews();
    }

    private void initViews()
    {

       // results=findViewById(R.id.results);
        resultsHistory=findViewById(R.id.resultsHistory);
      //  txtNoRecentResults=findViewById(R.id.txtNoRecentResults);
        txtNoRecentHistory=findViewById(R.id.txtNoRecentHistory);
        txtPersonName=findViewById(R.id.txtPersonName);
        txtPersonEmiratesID=findViewById(R.id.txtEmiratesID);
        txtlastPCRDate=findViewById(R.id.txtDate);
        txtMessage=findViewById(R.id.txtMessage);

     //   results.setLayoutManager(new LinearLayoutManager(this));
        resultsHistory.setLayoutManager(new LinearLayoutManager(this));


        txtPersonName.setText(UserData.user.getFirstName()+" "+UserData.user.getLastName());
        txtPersonEmiratesID.setText(UserData.user.getEmiratesID());



        loadData();


    }

    private void loadData()
    {
        AlertDialog progressDialog = ProgressDialogManager.getProgressDialog(this);
        progressDialog.show();
        FirebaseDatabase.getInstance().getReference(Constants.DB_RESULTS).child(UserData.user.getuID()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {

                progressDialog.dismiss();
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {

                    Result value = dataSnapshot1.getValue(Result.class);
                   /* if(value.getIsSeen().equalsIgnoreCase("FALSE"))
                    {

                      //  resultList.add(value);
                        value.setIsSeen("TRUE");
                        FirebaseDatabase.getInstance().getReference(Constants.DB_RESULTS).child(UserData.user.getuID()).child(value.getUid()).setValue(value);
                    }
                    else

                    */
                        resultListHistory.add(value);


                }


               /* if(resultList.size()>0)
                {

                    results.setAdapter(new ResultAdapter(resultList,CheckResults.this));
                    results.setVisibility(View.VISIBLE);
                    txtNoRecentResults.setVisibility(View.GONE);
                }
                else
                {
                    results.setVisibility(View.GONE);
                    txtNoRecentResults.setVisibility(View.VISIBLE);
                }

                */


                if(resultListHistory.size()>0)
                {

                    resultsHistory.setAdapter(new ResultAdapter(resultListHistory,CheckResults.this));
                    resultsHistory.setVisibility(View.VISIBLE);
                    txtNoRecentHistory.setVisibility(View.GONE);

                    Result result = resultListHistory.get(resultListHistory.size() - 1);

                    
                    
                    txtMessage.setText("PCR "+result.getResultReport()+" For "+getDaysFromDate());

                    txtlastPCRDate.setText(lastDate);
                }
                else
                {
                    resultsHistory.setVisibility(View.GONE);
                    txtNoRecentHistory.setVisibility(View.VISIBLE);
                }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private String getDaysFromDate()
    {


        long PDays=0;
        Date date2= Calendar.getInstance().getTime();
        for(Result result:resultListHistory)
        {

            SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
            try {
                Date date1 = format.parse(result.getDate());
                long miliseconds=date2.getTime()-date1.getTime();
                long days = TimeUnit.DAYS.convert(miliseconds, TimeUnit.MILLISECONDS);
                if(PDays==0)
                {
                    PDays=days;
                    lastDate=result.getDate();
                }
                else
                {
                    if(days<PDays)
                    {
                        PDays=days;
                        lastDate=result.getDate();
                    }
                }


            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

        return  String.valueOf(PDays)+" Days";
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        finish();
    }
}