package com.example.adscc.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adscc.Constants;
import com.example.adscc.Model.Booking;
import com.example.adscc.Model.Result;
import com.example.adscc.ProgressDialogManager;
import com.example.adscc.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.List;


public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder> {
    private List<Booking> listData;
    Context context;

    String DBNAME;
    public BookingAdapter(List<Booking> listData, Context context,String DBNAME) {
        this.listData = listData;
        this.context=context;
        this.DBNAME=DBNAME;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lyt_booking,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Booking booking=listData.get(position);
        holder.txtPersonName.setText(booking.getUserName());
        holder.txtPersonID.setText(booking.getUserEmiratesID());
        holder.txtPersonPhone.setText(booking.getUserMobile());
        holder.txtExamType.setText(booking.getExamType());
        holder.txtDate.setText(booking.getDate()+" \n"+booking.getTime());
        holder.txtPersons.setText(booking.getNoOfPeople());
        holder.txtPrice.setText(booking.getTotalPrice()+" AED");
        setCode(booking.getUid(),holder.imgCode);
        holder.btnMarkAsNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateResult(position,booking,Constants.RESULT_NEGATIVE);

            }
        });

        holder.btnMarkAsPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateResult(position,booking,Constants.RESULT_POSITIVE);
            }
        });
    }


    public void setCode(String code,ImageView imgCode){

        new Thread()
        {
            @Override
            public void run() {
                super.run();
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try {
                    BitMatrix bitMatrix = multiFormatWriter.encode(code, BarcodeFormat.CODE_128,600, 100);
                    Bitmap bitmap = Bitmap.createBitmap(600, 100, Bitmap.Config.RGB_565);
                    for (int i = 0; i<600; i++){
                        for (int j = 0; j<100; j++){
                            bitmap.setPixel(i,j,bitMatrix.get(i,j)? Color.BLACK:Color.WHITE);
                        }
                    }

                    ((Activity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imgCode.setImageBitmap(bitmap);
                        }
                    });

                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    private void updateResult(int position,Booking booking, String result)
    {



        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Are you sure you want to set this booking As Corona : "+result+" ? ");
        builder.setCancelable(true);

        builder.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id)
                    {

                        updateOnDatabase(position,booking,result);
                        dialog.cancel();
                    }
                });

        builder.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder.create();
        alert11.show();

    }

    private void updateOnDatabase(int position,Booking booking, String result)
    {
        AlertDialog progressDialog = ProgressDialogManager.getProgressDialog(context);
        progressDialog.show();

        Result result1=new Result();
        result1.setBookingId(booking.getUid());
        result1.setDate(booking.getDate());
        result1.setIsSeen("FALSE");
        result1.setResultReport(result);
        result1.setUserName(booking.getUserName());
        result1.setUserEmail(booking.getUserEmail());
        result1.setUserID(booking.getUserId());
        result1.setUserMobile(booking.getUserMobile());
        result1.setUserEmiratesID(booking.getUserEmiratesID());

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(Constants.DB_RESULTS);

        result1.setUid(reference.child(result1.getUserID()).push().getKey());

        reference.child(result1.getUserID()).child(result1.getUid()).setValue(result1).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {


                if(task.isSuccessful())
                {

                    FirebaseDatabase.getInstance().getReference(DBNAME).child(booking.getUid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful())
                            {
                                progressDialog.dismiss();
                                listData.remove(position);
                                if(listData.size()>0)
                                {
                                    notifyDataSetChanged();
                                }
                                else
                                {
                                    ((Activity)context).finish();
                                    context.startActivity(((Activity) context).getIntent());
                                }
                            }
                        }
                    });



                }
                else
                {
                    progressDialog.dismiss();
                    Toast.makeText(context,"Error "+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView txtPersonName;
        TextView txtPersonID;
        TextView txtPersonPhone;
        TextView txtExamType;
        TextView txtDate;
        ImageView imgCode;
        Button btnMarkAsNegative;
        Button btnMarkAsPositive;
        TextView txtPrice;
        TextView txtPersons;
        public ViewHolder(View itemView) {
            super(itemView);
            txtPersonName=itemView.findViewById(R.id.txtPersonName);
            txtPersonID=itemView.findViewById(R.id.txtPersonID);
            txtPersonPhone=itemView.findViewById(R.id.txtPersonPhone);
            txtExamType=itemView.findViewById(R.id.txtExamType);
            txtDate=itemView.findViewById(R.id.txtDate);
            txtPrice=itemView.findViewById(R.id.txtPrice);
            txtPersons=itemView.findViewById(R.id.txtPersons);
            imgCode=itemView.findViewById(R.id.imgCode);

            btnMarkAsNegative=itemView.findViewById(R.id.btnMarkAsNegative);
            btnMarkAsPositive=itemView.findViewById(R.id.btnMarkAsPositive);


        }
    }

}
