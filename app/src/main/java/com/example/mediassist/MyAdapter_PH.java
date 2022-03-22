package com.example.mediassist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter_PH extends RecyclerView.Adapter<MyAdapter_PH.MyViewHolder> {

    Context context;
    ArrayList<MyUser_PH> myUser_phArrayList;

    public MyAdapter_PH(Context context, ArrayList<MyUser_PH> myUser_phArrayList) {
        this.context = context;
        this.myUser_phArrayList = myUser_phArrayList;
    }

    @NonNull
    @Override
    public MyAdapter_PH.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter_PH.MyViewHolder holder, int position) {
        MyUser_PH myUser_ph=myUser_phArrayList.get(position);

        holder.Pharma_VP_RV_Date.setText(myUser_ph.Date);
        holder.Pharma_VP_RV_Doctor.setText(myUser_ph.Doctor_Name);
        holder.Pharma_VP_RV_P_ID.setText(myUser_ph.Patient_Id);
        holder.Pharma_VP_RV_P_Name.setText(myUser_ph.Patient_Name);
        holder.Pharma_VP_RV_P_No.setText(myUser_ph.Patient_No);
        holder.Pharma_VP_RV_P_Address.setText(myUser_ph.Address);
        holder.Pharma_VP_RV_P_City.setText(myUser_ph.City);
        holder.Pharma_VP_RV_P_State.setText(myUser_ph.State);
        holder.Pharma_VP_RV_P_Medicinces.setText(myUser_ph.List_Of_Medicinces);
    }

    @Override
    public int getItemCount() {
        return myUser_phArrayList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView Pharma_VP_RV_Date,Pharma_VP_RV_Doctor,Pharma_VP_RV_P_ID,Pharma_VP_RV_P_Name,Pharma_VP_RV_P_No,Pharma_VP_RV_P_Address,Pharma_VP_RV_P_City,Pharma_VP_RV_P_State,Pharma_VP_RV_P_Medicinces;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Pharma_VP_RV_Date=itemView.findViewById(R.id.Pharma_VP_RV_Date);
            Pharma_VP_RV_Doctor=itemView.findViewById(R.id.Pharma_VP_RV_Doctor);
            Pharma_VP_RV_P_ID=itemView.findViewById(R.id.Pharma_VP_RV_P_ID);
            Pharma_VP_RV_P_Name=itemView.findViewById(R.id.Pharma_VP_RV_P_Name);
            Pharma_VP_RV_P_No=itemView.findViewById(R.id.Pharma_VP_RV_P_No);
            Pharma_VP_RV_P_Address=itemView.findViewById(R.id.Pharma_VP_RV_P_Address);
            Pharma_VP_RV_P_City=itemView.findViewById(R.id.Pharma_VP_RV_P_City);
            Pharma_VP_RV_P_State=itemView.findViewById(R.id.Pharma_VP_RV_P_State);
            Pharma_VP_RV_P_Medicinces=itemView.findViewById(R.id.Pharma_VP_RV_P_Medicinces);
        }
    }
}
