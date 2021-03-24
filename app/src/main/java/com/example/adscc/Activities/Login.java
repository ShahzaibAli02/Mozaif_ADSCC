package com.example.adscc.Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import com.example.adscc.Admin.AdminDashBoard;
import com.example.adscc.Constants;
import com.example.adscc.Model.User;
import com.example.adscc.ProgressDialogManager;
import com.example.adscc.R;
import com.example.adscc.UserData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {


    EditText editTextEmail;
    EditText editTextPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        editTextEmail=findViewById(R.id.editTextEmail);
        editTextPassword=findViewById(R.id.editTextPass);





    }

    public void onCreateAccount(View view)
    {

        finish();
        startActivity(new Intent(this,Create_Account.class));
    }

    public void onClickLogin(View view)
    {

        if(notEmpty())
        {
           tryToSignIn();

        }

    }

    private void tryToSignIn()
    {

        AlertDialog progressDialog = ProgressDialogManager.getProgressDialog(this);
        progressDialog.show();
        if(editTextEmail.getText().toString().trim().equals("admin@adscc.com") && editTextPassword.getText().toString().equals("adscc123"))
        {
            finish();
            startActivity(new Intent(Login.this, AdminDashBoard.class));
            progressDialog.dismiss();
            return;
        }
        FirebaseDatabase.getInstance().getReference().child(Constants.DBUSERS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                progressDialog.dismiss();

                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                   User user=snapshot.getValue(User.class);

                   if(user.getEmailAddress().trim().equalsIgnoreCase(editTextEmail.getText().toString().trim()) && user.getPassword().equals(editTextPassword.getText().toString()))
                   {

                       UserData.user=user;
                       finish();
                       startActivity(new Intent(Login.this,MainScreen.class));
                       return;

                   }

                }


                Toast.makeText(Login.this,"Email Or Password Is Wrong",Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Login.this,"Error",Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });


    }

    private boolean notEmpty()
    {
        for(EditText editText:new EditText[]{editTextEmail,editTextPassword})
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
}