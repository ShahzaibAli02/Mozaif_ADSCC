package com.example.adscc.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.adscc.Constants;
import com.example.adscc.Maps.MapsActivity;
import com.example.adscc.Model.Booking;
import com.example.adscc.ProgressDialogManager;
import com.example.adscc.R;
import com.example.adscc.UserData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Time;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HomeService extends AppCompatActivity implements  View.OnClickListener{


    EditText editTextDate;
    EditText spinnerTextTime;
    EditText editTextLocation;


    LinearLayout lytExamType1;
    LinearLayout lytExamType2;

    EditText editTextNoOfPeople;

    RadioGroup radioGroupPaymentType;


    TextView txtPriceType1;
    TextView txtPriceType2;
    TextView txtTimeType1;
    TextView txtTimeType2;
    String EXAM_TYPE;

    TextView txtPrice;
    int totalprice;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_service);


        initViews();

    }

    private void initViews()
    {


        txtPrice=findViewById(R.id.txtPrice);
        editTextDate=findViewById(R.id.edittxtDate);
        spinnerTextTime=findViewById(R.id.edittxtTime);
        editTextLocation=findViewById(R.id.editTextLocation);

        lytExamType1=findViewById(R.id.lytType1);
        lytExamType2=findViewById(R.id.lytType2);

        editTextNoOfPeople=findViewById(R.id.editTextNoOfPeople);

        radioGroupPaymentType=findViewById(R.id.radioGroup);


        txtPriceType1=findViewById(R.id.txtPriceType1);
        txtPriceType2=findViewById(R.id.txtPriceType2);

        txtTimeType1=findViewById(R.id.txtTimeType1);
        txtTimeType2=findViewById(R.id.txtTimeType2);




        lytExamType1.setOnClickListener(this);
        lytExamType2.setOnClickListener(this);

        editTextDate.setOnClickListener(this);
       spinnerTextTime.setOnClickListener(this);

        editTextLocation.setOnClickListener(this);




        editTextNoOfPeople.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                updatePrice();
            }
        });


        selectType1();
        unSelectType2();


    }

    public void onClickBookAppointment(View view)
    {


        for(EditText editText:new EditText[]{editTextDate,editTextLocation,editTextNoOfPeople})
        {
            if(TextUtils.isEmpty(editText.getText()))
            {
                Toast.makeText(HomeService.this,"Please Fill Out All The Fields",Toast.LENGTH_LONG).show();
                return;
            }

        }

        if(Integer.parseInt(editTextNoOfPeople.getText().toString())<1)
        {
            editTextNoOfPeople.setError("Should be > 0");
            editTextNoOfPeople.requestFocus();
            return;
        }

        RegisterInDB();

    }

    private String getTime(int hr,int min) {
        Time tme = new Time(hr,min,0);//seconds by default set to zero
        Format formatter;
        formatter = new SimpleDateFormat("h:mm a");
        return formatter.format(tme);
    }
    private void RegisterInDB()
    {

        AlertDialog progressDialog = ProgressDialogManager.getProgressDialog(this);
        progressDialog.show();

        Booking booking =new Booking();
        booking.setAddress(editTextLocation.getText().toString());
        booking.setUserId(UserData.user.getuID());
        booking.setDate(editTextDate.getText().toString());
        booking.setExamType(EXAM_TYPE);
        booking.setNoOfPeople(editTextNoOfPeople.getText().toString());
        booking.setPaymentMethod(getPaymentMethod());
        booking.setTime(spinnerTextTime.getText().toString());
        booking.setTotalPrice(String.valueOf(totalprice));

        booking.setUserName(UserData.user.getFirstName()+" "+UserData.user.getLastName());
        booking.setUserEmiratesID(UserData.user.getEmiratesID());
        booking.setUserMobile(UserData.user.getMobileNum());
        booking.setUserEmail(UserData.user.getEmailAddress());

        DatabaseReference bookings = FirebaseDatabase.getInstance().getReference().child(Constants.DB_HOME_SERVICES);
        String key= bookings.push().getKey();

        booking.setUid(key);

        bookings.child(key).setValue(booking).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();
                if(task.isSuccessful())
                {


                    startActivity(new Intent(HomeService.this,Appointment_Details.class).putExtra("INFO", booking));
                    finish();

                }
                else
                {
                    Toast.makeText(HomeService.this,"Failed To Register For Booking",Toast.LENGTH_LONG).show();
                }

            }
        });




    }

    private String getPaymentMethod()
    {

        if(radioGroupPaymentType.getCheckedRadioButtonId()==R.id.btnCash)
            return  "CASH";
        if(radioGroupPaymentType.getCheckedRadioButtonId()==R.id.btnApplePay)
            return  "APPLE PAY";
        if(radioGroupPaymentType.getCheckedRadioButtonId()==R.id.btnCreditCard)
            return  "CREDIT CARD";

        return "";
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onClick(View v)
    {


        if(v.getId()==R.id.lytType1)
        {

            selectType1();
            unSelectType2();

        }
        if(v.getId()==R.id.lytType2)
        {

            selectType2();
            unSelectType1();

        }

        if(v==editTextDate)
        {

            DatePickerDialog datePickerDialog=new DatePickerDialog(this);
            datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
                {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(year, month, dayOfMonth);
                    SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
                    String dateString = format.format(calendar.getTime());
                    editTextDate.setText(dateString);

                }
            });
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
            datePickerDialog.show();



        }


       if(v==spinnerTextTime)
        {


            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                    Calendar newDate = Calendar.getInstance();
                    newDate.set(0, 0, 0,selectedHour,selectedMinute,0);
                    spinnerTextTime.setText(getTime(selectedHour,selectedMinute));
                }
            }, hour, minute, false);//Yes 24 hour time

            mTimePicker.show();



        }






    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==00 && resultCode==RESULT_OK)
        {


            editTextLocation.setText( data.getStringExtra("ADDRESS"));

        }
    }

    private void selectType1()
    {

        lytExamType1.setBackgroundResource(R.color.mycolor);
        txtPriceType1.setTextColor(getResources().getColor(R.color.white));
        txtTimeType1.setTextColor(getResources().getColor(R.color.white));
        EXAM_TYPE="FT";
        updatePrice();

    }

    private void updatePrice()
    {

        int EXAM_PRICE= EXAM_TYPE.equals("FT") ? 360:180;
        totalprice=0;
        if(TextUtils.isEmpty(editTextNoOfPeople.getText().toString()))
        {
            txtPrice.setText("");

        }
        else
        {
            if(Integer.parseInt(editTextNoOfPeople.getText().toString())==0)
            {
                txtPrice.setText("");
            }
            else
            {
                totalprice=EXAM_PRICE*Integer.parseInt(editTextNoOfPeople.getText().toString());
                txtPrice.setText(String.valueOf(totalprice+" AED"));

            }

        }

    }

    private void selectType2()
    {

        lytExamType2.setBackgroundResource(R.color.mycolor);
        txtPriceType2.setTextColor(getResources().getColor(R.color.white));
        txtTimeType2.setTextColor(getResources().getColor(R.color.white));
        EXAM_TYPE="REG";
        updatePrice();

    }

    private void unSelectType1()
    {
        lytExamType1.setBackgroundResource(R.drawable.back_square_dark);
        txtPriceType1.setTextColor(getResources().getColor(R.color.gray));
        txtTimeType1.setTextColor(getResources().getColor(R.color.gray));

    }
    private void unSelectType2()
    {
        lytExamType2.setBackgroundResource(R.drawable.back_square_dark);
        txtPriceType2.setTextColor(getResources().getColor(R.color.gray));
        txtTimeType2.setTextColor(getResources().getColor(R.color.gray));

    }

    public void onClickSelectFromMap(View view) {
        startActivityForResult(new Intent(HomeService.this, MapsActivity.class),00);
    }
}