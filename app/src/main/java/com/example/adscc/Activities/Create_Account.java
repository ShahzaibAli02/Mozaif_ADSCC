package com.example.adscc.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.adscc.Model.User;
import com.example.adscc.R;

public class Create_Account extends AppCompatActivity {


    EditText editTextEmiratesID;
    EditText editTextMobileNum;
    EditText editTextFirstName;
    EditText editTextLastName;
    EditText editTextEmail;
    EditText editTextPassword;
    Button btnCreateAccount;
    TextView txtSignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        initViews();
    }

    private void initViews()
    {

        editTextEmiratesID=findViewById(R.id.editTextEmiratesId);
        editTextMobileNum=findViewById(R.id.editTextMobileNum);
        editTextFirstName=findViewById(R.id.editTextFirstName);
        editTextLastName=findViewById(R.id.editTextLastName);
        editTextEmail=findViewById(R.id.editTextEmail);
        editTextPassword=findViewById(R.id.editTextPass);

        btnCreateAccount=findViewById(R.id.btnCreate);
        txtSignIn=findViewById(R.id.txtSignIn);



        editTextEmiratesID.setMaxEms(11);
        editTextEmiratesID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
             if(s.length()==3 || s.length()==8 || s.length() ==15)
             {
                 s.append("-");
             }

            }
        });
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

    }

    private void signUp()
    {

        if(notEmpty())
        {


            if(editTextEmail.getText().toString().trim().equalsIgnoreCase("admin@adscc.com"))
            {
                editTextEmail.setError("INVALID EMAIL");
                editTextEmail.requestFocus();
                return;

            }

            User user=new User();

            user.setEmiratesID(editTextEmiratesID.getText().toString());


            if(!editTextMobileNum.getText().toString().contains("+971"))
                user.setMobileNum("+971"+editTextMobileNum.getText().toString());
            else
                user.setMobileNum(editTextMobileNum.getText().toString());
            user.setFirstName(editTextFirstName.getText().toString());
            user.setLastName(editTextLastName.getText().toString());
            user.setEmailAddress(editTextEmail.getText().toString());
            user.setPassword(editTextPassword.getText().toString());



            startActivity(new Intent(Create_Account.this,OtpVerification.class).putExtra("Data",user));





        }

    }

    private boolean notEmpty()
    {
        for(EditText editText:new EditText[]{editTextEmiratesID,editTextMobileNum,editTextFirstName,editTextLastName,editTextEmail,editTextPassword})
        {
            if(TextUtils.isEmpty(editText.getText()))
            {

                editText.setError("Required Field");
                editText.requestFocus();
                return false;
            }

        }

        return  true;
    }


    public void onClickLogin(View view) {
        finish();
        Intent intent = new Intent(this,Login.class);
        startActivity(intent);
    }
}