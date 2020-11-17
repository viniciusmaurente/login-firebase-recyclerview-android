package com.br.desafio4all.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.br.desafio4all.R;

import com.br.desafio4all.activity.EditarPerfilActivity;
import com.br.desafio4all.activity.EventoActivity;
import com.br.desafio4all.model.Usuario;
import com.br.desafio4all.util.UsuarioFirebase;
import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseUser;


public class PerfilFragment extends Fragment {


    private Button buttonEditarPerfil;
    private Button btnInserirEvento;
    private ImageView imagePerfil;
    private Usuario usuarioLogado;
    private TextView textNomePerfil;
    private TextView textEmailPerfil;
    private TextInputLayout textInputCpf;


    public PerfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        //Configurações iniciais
        usuarioLogado = UsuarioFirebase.getDadosUsuarioLogado();

        //configuraçôes dos componentes
        inicializarComponentes(view);
        //Recuperar dados do usuário
        FirebaseUser usuarioPerfil = UsuarioFirebase.getUsuarioAtual();
        textNomePerfil.setText( usuarioPerfil.getDisplayName() );
        textEmailPerfil.setText( usuarioPerfil.getEmail() );

        String caminhoFoto = usuarioLogado.getCaminhoFoto();
        if (caminhoFoto != null){
            Glide.with(getActivity())
                    .load(caminhoFoto)
                    .into(imagePerfil);
        }else {
            imagePerfil.setImageResource(R.drawable.background_degrade);
        }

        //Abre edição de perfil
        buttonEditarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), EditarPerfilActivity.class);
                startActivity(i);
            }
        });

        btnInserirEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), EventoActivity.class);
                startActivity(i);
            }
        });

        return view;
    }

    private void atualizarFotousuario(Uri url){
        //atualizar foto no perfil
        UsuarioFirebase.atualizarFotoUsuario(url);

        //atualizar foto no firebase
        usuarioLogado.setCaminhoFoto(url.toString());
        usuarioLogado.atualizar();

    }

    public void inicializarComponentes(View view){
        imagePerfil         = view.findViewById(R.id.imagePerfil);
        btnInserirEvento    = view.findViewById(R.id.btnInserirEvento);
        buttonEditarPerfil  = view.findViewById(R.id.buttonEditarPerfil);
        textNomePerfil      = view.findViewById(R.id.textNomePerfil5);
        textEmailPerfil     = view.findViewById(R.id.textEmailPerfil);
        textInputCpf        = view.findViewById(R.id.textInputCpf);

    }

}

