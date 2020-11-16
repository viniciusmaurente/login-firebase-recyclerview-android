package com.br.desafio4all.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.br.desafio4all.R;

public class MyViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView textView;


    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView   = itemView.findViewById(R.id.image_detalhe_evento);
        textView    = itemView.findViewById(R.id.textView_single_view);


    }
}
