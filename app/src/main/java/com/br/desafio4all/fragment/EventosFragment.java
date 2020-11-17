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
import com.br.desafio4all.model.Usuario;
import com.br.desafio4all.util.UsuarioFirebase;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;


public class EventosFragment extends Fragment {

    private EditText inputSearch;
    private RecyclerView recyclerView;
    private FloatingActionButton floatingBtn;
    private FirebaseRecyclerOptions<Evento> options;
    private FirebaseRecyclerAdapter<Evento, MyViewHolder> adapter;
    private DatabaseReference DataRef;
    private Usuario usuarioLogado;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_eventos, container, false);

        usuarioLogado = UsuarioFirebase.getDadosUsuarioLogado();


        inicializarComponentes(view);

        FirebaseUser usuarioLogado = UsuarioFirebase.getUsuarioAtual();
        inputSearch     = inputSearch.findViewById(R.id.inputSearch);
        recyclerView    = recyclerView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

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

                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_eventos,parent, false);

                return new MyViewHolder(v);
            }
        };

        return view;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            LoadData();

        }
    }

    private void LoadData() {

        adapter.startListening();
        recyclerView.setAdapter(adapter);


    }
    private void inicializarComponentes(View view) {
        inputSearch     = view.findViewById(R.id.inputSearch);
        recyclerView    = view.findViewById(R.id.recyclerView);


    }


    private Context getApplicationContext() {
        return null;
    }

}