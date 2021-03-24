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
import android.widget.LinearLayout;
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


public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder> {
    private List<Result> listData;
    Context context;

    public ResultAdapter(List<Result> listData, Context context) {
        this.listData = listData;
        this.context=context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lyt_result,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Result result=listData.get(position);
        //holder.txtPersonName.setText(result.getUserName());
        //holder.txtPersonID.setText(result.getUserEmiratesID());
        holder.txtDate.setText("Last PCR Performed on "+result.getDate()+" is");
       // holder.btnResult.setText(result.getResultReport());


        if(result.getResultReport().equals(Constants.RESULT_POSITIVE))
        {
           // holder.lytBack.setBackgroundResource(R.drawable.back_red);
            holder.txtResult.setTextColor(context.getColor(R.color.red));
        }
        if(result.getResultReport().equals(Constants.RESULT_NEGATIVE))
        {
           // holder.lytBack.setBackgroundResource(R.drawable.back_green);
            holder.txtResult.setTextColor(context.getColor(R.color.green));
        }

    }




    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        TextView txtResult;
        TextView txtDate;
        public ViewHolder(View itemView) {
            super(itemView);

            txtResult=itemView.findViewById(R.id.txtResult);
            txtDate=itemView.findViewById(R.id.txtDate);




        }
    }

}
