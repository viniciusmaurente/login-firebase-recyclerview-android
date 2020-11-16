package com.br.desafio4all.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.br.desafio4all.R;
import com.br.desafio4all.activity.EditarPerfilActivity;
import com.br.desafio4all.activity.EventoActivity;
import com.br.desafio4all.model.Evento;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;


public class EventosFragment extends Fragment {

    EditText inputSearch;
    RecyclerView recyclerView;
    FloatingActionButton floatingBtn;
    FirebaseRecyclerOptions<Evento> options;
    FirebaseRecyclerAdapter<Evento, MyViewHolder> adapter;
    DatabaseReference DataRef;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            inputSearch     = inputSearch.findViewById(R.id.inputSearch);
            recyclerView    = recyclerView.findViewById(R.id.recyclerView);
            floatingBtn     = floatingBtn.findViewById(R.id.floatingBtn);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

            LoadData();

            floatingBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getActivity(), EditarPerfilActivity.class);
                    startActivity(i);
                }

            });
        }
    }

    private void LoadData() {
        options = new FirebaseRecyclerOptions.Builder<Evento>().setQuery(DataRef, Evento.class).build();
        adapter = new FirebaseRecyclerAdapter<Evento, MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Evento model) {
                holder.textView.setText(model.getEventoNome());

                Picasso.get().load(model.getImageUrl()).into(holder.imageView);

            }

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.detalhes_evento,parent, false);

                return new MyViewHolder(v);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);


    }


    private Context getApplicationContext() {
        return null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_eventos, container, false);
    }
}