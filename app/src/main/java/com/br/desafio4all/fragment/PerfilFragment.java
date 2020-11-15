package com.br.desafio4all.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;


import com.br.desafio4all.R;

import com.br.desafio4all.activity.EditarPerfilActivity;



public class PerfilFragment extends Fragment {


    private Button buttonEditarPerfil;
    private ImageView imagePerfil;

    public PerfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        //Configurações dos componentes
        imagePerfil = view.findViewById(R.id.imagePerfil);
        buttonEditarPerfil = view.findViewById(R.id.buttonEditarPerfil);

        //Abre edição de perfil
        buttonEditarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), EditarPerfilActivity.class);
                startActivity(i);
            }
        });

        return view;
    }
}

