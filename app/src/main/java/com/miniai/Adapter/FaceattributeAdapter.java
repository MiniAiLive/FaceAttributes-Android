package com.miniai.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.miniai.face.GenderInfo;
import com.miniai.face.LivenessInfo;
import com.miniai.facealiveness.R;
import com.miniai.facealiveness.model.FaceAtteributeModle;
import com.miniai.facealiveness.util.utilhelper;

import java.util.ArrayList;


public class FaceattributeAdapter extends RecyclerView.Adapter<FaceattributeAdapter.MyHolder> {

    ArrayList<FaceAtteributeModle> arraylist;

    Context context;
    public FaceattributeAdapter(ArrayList<FaceAtteributeModle> arraylist, Context context)
    {
        this.context=context;
        this.arraylist=arraylist;
    }
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.faceattribute_item, parent, false);
        return new MyHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        FaceAtteributeModle faceAtteributeModle=arraylist.get(position);
        if (faceAtteributeModle.getBitmap()!=null)
        {
            holder.ivShow.setImageBitmap(faceAtteributeModle.getBitmap());
        }

        holder.txtAge.setText(""+faceAtteributeModle.getAge());
        holder.txtQuality.setText(faceAtteributeModle.getQuality());
        holder.txtLuminance.setText(""+faceAtteributeModle.getLuminance());

        switch (faceAtteributeModle.getLiveness()) {
            case LivenessInfo.ALIVE:

                holder.txtLiveness.setText("ALIVE");
                break;
            case LivenessInfo.NOT_ALIVE:

                holder.txtLiveness.setText("NOT_ALIVE");
                break;
            case LivenessInfo.UNKNOWN:

                holder.txtLiveness.setText("UNKNOWN");
                break;
            case LivenessInfo.FACE_NUM_MORE_THAN_ONE:

                holder.txtLiveness.setText("FACE_NUM_MORE_THAN_ONE");
                break;
            default:
                holder.txtLiveness.setText("UNKNOWN");
                break;
        }
        if (faceAtteributeModle.getGender() == GenderInfo.MALE)
        {
            holder.txtGender.setText("MALE");
        }
        else if (faceAtteributeModle.getGender()== GenderInfo.FEMALE)
        {
            holder.txtGender.setText("FEMALE");
        }

        else if (faceAtteributeModle.getGender()== GenderInfo.UNKNOWN)
        {
            holder.txtAge.setText("UNKNOWN");
        }



    }

    @Override
    public int getItemCount() {
        return arraylist.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        private ImageView ivShow;
        private TextView txtLiveness,txtAge,txtGender,txtQuality,txtLuminance,txtAngles;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            txtLiveness = itemView.findViewById(R.id.txtLiveness);
            txtAge = itemView.findViewById(R.id.txtAge);
            txtGender = itemView.findViewById(R.id.txtGender);
            txtQuality = itemView.findViewById(R.id.txtQuality);
            txtLuminance = itemView.findViewById(R.id.txtLuminance);
            ivShow = itemView.findViewById(R.id.iv_show);

        }
    }
}
