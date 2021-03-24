package com.example.adscc.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adscc.Model.Booking;
import com.example.adscc.Model.User;
import com.example.adscc.R;
import com.example.adscc.UserData;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class Appointment_Details extends AppCompatActivity {


    Booking info;

    TextView txtPersonName;
    TextView txtPersonID;
    TextView txtPersonPhone;
    TextView txtExamType;
    TextView txtDate;
    ImageView imgCode;
    TextView txtPersons;
    TextView txtPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment__details);

        initViews();

    }

    private void initViews()
    {

        info = (Booking)getIntent().getExtras().getSerializable("INFO");
        txtPersonName=findViewById(R.id.txtPersonName);
        txtPersonID=findViewById(R.id.txtPersonID);
        txtPersonPhone=findViewById(R.id.txtPersonPhone);
        txtExamType=findViewById(R.id.txtExamType);
        txtDate=findViewById(R.id.txtDate);
        imgCode=findViewById(R.id.imgCode);
        txtPersons=findViewById(R.id.txtPersons);
        txtPrice=findViewById(R.id.txtPrice);


        setVals();
    }

    private void setVals()
    {

        User user=UserData.user;
        txtPersonName.setText(user.getFirstName()+" "+user.getLastName());
        txtPersonID.setText(user.getEmiratesID());
        txtPersonPhone.setText(user.getMobileNum());
        txtExamType.setText(info.getExamType());
        txtDate.setText(info.getDate()+"\n"+info.getTime());
        txtPrice.setText(info.getTotalPrice()+" AED");
        txtPersons.setText(info.getNoOfPeople());

        setCode();

    }

    public void setCode(){
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(info.getUid(), BarcodeFormat.CODE_128,600, 100);
            Bitmap bitmap = Bitmap.createBitmap(600, 100, Bitmap.Config.RGB_565);
            for (int i = 0; i<600; i++){
                for (int j = 0; j<100; j++){
                    bitmap.setPixel(i,j,bitMatrix.get(i,j)? Color.BLACK:Color.WHITE);
                }
            }
            imgCode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}