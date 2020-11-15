package com.br.desafio4all.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.br.desafio4all.R;
import com.br.desafio4all.model.Usuario;
import com.br.desafio4all.util.ConfiguracaoFirebase;
import com.br.desafio4all.util.UsuarioFirebase;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.ExecutionException;

public class EditarPerfilActivity extends AppCompatActivity {

    private ImageView imagePerfil;
    private TextView buttonSalvar;
    private TextView textNomePerfil;
    private TextView textEmailPerfil;
    private TextInputLayout textInputCpf;
    private Button buttonEditarFoto;
    private Usuario usuarioLogado;
    private static final int SELECAO_GALERIA = 200;
    private StorageReference storageRef;
    private String identificadorUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        //configurações iniciais
        usuarioLogado           = UsuarioFirebase.getDadosUsuarioLogado();
        storageRef              = ConfiguracaoFirebase.getFirebaseStorage();
        identificadorUsuario    = UsuarioFirebase.getIdentificadorUsuario();


       //inicializar componentes
        inicializarComponentes();

        //Recuperar dados do usuário
        FirebaseUser usuarioPerfil = UsuarioFirebase.getUsuarioAtual();
        textNomePerfil.setText( usuarioPerfil.getDisplayName() );
        textEmailPerfil.setText( usuarioPerfil.getEmail() );


        Uri url = usuarioPerfil.getPhotoUrl();
        if (url != null){
            Glide.with(EditarPerfilActivity.this)
                    .load(url)
                    .into(imagePerfil);
        }else {
            imagePerfil.setImageResource(R.drawable.background_degrade);
        }


        //Salvar alterações do nome
        buttonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nomeAtualizado = textNomePerfil.getText().toString();

                //Atualizar nome no Perfil
                UsuarioFirebase.atualizarNomeUsuario(nomeAtualizado);
                usuarioLogado.setNome(nomeAtualizado);
                usuarioLogado.atualizar();
                finish();

                Toast.makeText(EditarPerfilActivity.this,
                        "Dados alterados com sucesso!",
                        Toast.LENGTH_SHORT).show();
            }
        });

        //alterar foto do usuario
        buttonEditarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                if (i.resolveActivity(getPackageManager()) != null){
                    startActivityForResult(i, SELECAO_GALERIA);
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            Bitmap imagem = null;

            try {
                //seleção na galeria
                switch (requestCode){
                    case SELECAO_GALERIA:
                        Uri localImagemSelecionada = data.getData();
                        imagem = MediaStore.Images.Media.getBitmap(getContentResolver(), localImagemSelecionada);
                        break;
                }

                //caso tenha sido escolhido uma imagem
                if (imagem != null){

                    //configura imagem na tela
                    imagePerfil.setImageBitmap(imagem);

                    //recuperar dados da imagem para o firebase
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    imagem.compress(Bitmap.CompressFormat.JPEG, 72, baos);
                    byte[] dadosImagem = baos.toByteArray();


                    //salvar imagem no firebase
                    final StorageReference imagemRef = storageRef
                            .child("imagens")
                            .child("perfil")
                            .child(identificadorUsuario + ".jpeg");
                    UploadTask uploadTask = imagemRef.putBytes(dadosImagem);

                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(EditarPerfilActivity.this,
                                    "Erro ao fazer upload da imagem!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            //Recuperar local da foto
                            imagemRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    Uri url = task.getResult();
                                    atualizarFotousuario(url);
                                }
                            });

                            finish();

                            Toast.makeText(EditarPerfilActivity.this,
                                    "Sucesso ao fazer upload da imagem!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            } catch (Exception e ){
                e.printStackTrace();
            }
        }
    }

    private void atualizarFotousuario(Uri url){
        //atualizar foto no perfil
        UsuarioFirebase.atualizarFotoUsuario(url);

        //atualizar foto no firebase
        usuarioLogado.setCaminhoFoto(url.toString());
        usuarioLogado.atualizar();

        Toast.makeText(EditarPerfilActivity.this,
                "Sua foto foi atualizada!",
                Toast.LENGTH_SHORT).show();
        finish();

    }

    public void inicializarComponentes(){

        imagePerfil         = findViewById(R.id.imagePerfil);
        buttonEditarFoto    = findViewById(R.id.buttonEditarFoto);
        textNomePerfil      = findViewById(R.id.textNomePerfil);
        textEmailPerfil     = findViewById(R.id.textEmailPerfil);
        buttonSalvar        = findViewById(R.id.buttonSalvar);
        textInputCpf        = findViewById(R.id.textInputCpf);


    }

}
