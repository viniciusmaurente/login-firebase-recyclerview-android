package com.br.desafio4all.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.br.desafio4all.R;
import com.br.desafio4all.activity.HomeActivity;


public class EventosFragment extends Fragment {

    private EditText inputSearch;
    private RecyclerView recyclerView;
    private Button buttonEventos;

    public EventosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eventos, container, false);

        inicializarComponentes(view);


        buttonEventos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), HomeActivity.class);
                startActivity(i);
            }
        });
        return view;
    }

    public void inicializarComponentes(View view){
        inputSearch      = view.findViewById(R.id.inputSearch);
        recyclerView     = view.findViewById(R.id.recyclerView);
        buttonEventos    = view.findViewById(R.id.buttonEventos);


    }
}