package com.example.login_e_cadastro_server;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class MainActivity2 extends AppCompatActivity {

    // 1) Criando Atributos
    private TextView lbllogin,lblsenha;
    private EditText txtNovaSenha, txtnomefoto;
    private ImageView fotop;
    private Button btnListar;

    // Acessando o banco de dados
    private String Host = "https://rosamititko.serv00.net/projeto/";
    private String url, ret;
    // url = local/arquivo que quer acessar do banco de dados
    // ret = retorna o status, se foi um sucesso ou não

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // 2) Linkando os elementos da tela aos atributos
        lbllogin=(TextView) findViewById(R.id.lbllogin);
        lblsenha=(TextView)findViewById(R.id.lblsenha);
        txtNovaSenha=(EditText) findViewById(R.id.txtNovaSenha);
        txtnomefoto=(EditText) findViewById(R.id.txtnomefoto);
        fotop=(ImageView) findViewById(R.id.fotop);
        lbllogin.setText(MainActivity.loginx);
        lblsenha.setText(MainActivity.senhax);

        // carregando uma imagem do banco, caso não carregue vai exibir uma imagem definida como vazio
        Ion.with(fotop).placeholder ( R.drawable.vazio ).
                error ( R.drawable.vazio ).
                load("https://rosamititko.serv00.net/projeto/imagem/"+ MainActivity.fotox+"");

        // 3) Executando uma função ao apertar o botão. no caso Listar, Alterar e Deletar
        findViewById(R.id.btnListar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Vai exibir os dados do usuario na MainActivity3
                Intent tela=new Intent(MainActivity2.this, MainActivity3.class);
                MainActivity2.this.startActivity(tela);
            }
        });

        findViewById(R.id.btnAlterar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alterar();
            }
        });

        findViewById(R.id.btnDeletar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletar();
            }
        });
    }


    // 4) funções de Deletar e Alterar

    // Uma função de Deletar, onde vai deletar os dados do usuario no banco de dados
    // Caso de certo ele vai enviar um status de "ok" e exibir uma mensagem de sucesso,
    // se não vai exibir uma mensagem de erro na tela
    private void deletar() {
        url = Host + "deletar.php";
        Ion.with(MainActivity2.this)
                .load(url)
                .setBodyParameter("usuario", lbllogin.getText().toString())
                .setBodyParameter("senha", lblsenha.getText().toString())
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        ret = result.get("status").getAsString();
                        if (ret.equals("ok")) {
                            Toast.makeText(getApplicationContext(),
                                    " EXCLUIDO COM SUCESSO", Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(getApplicationContext(),
                                    " ERRO DE EXCLUSÃO", Toast.LENGTH_LONG).show();

                        }

                    }
                });
    }

    // Uma função de Alterar, onde vai Alterar os dados do usuario no banco de dados
    // Caso de certo ele vai enviar um status de "ok" e exibir uma mensagem de sucesso,
    // se não vai exibir uma mensagem de erro na tela
    private void alterar()
    {
        url = Host + "alterar.php";
        Ion.with(MainActivity2.this)
                .load(url)
                .setBodyParameter("usuario", lbllogin.getText().toString())
                .setBodyParameter("senha", txtNovaSenha.getText().toString())
                .setBodyParameter("foto", txtnomefoto.getText().toString())
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        ret = result.get("status").getAsString();
                        if (ret.equals("ok")) {
                            Toast.makeText(getApplicationContext(),
                                    " ALTERADO COM SUCESSO", Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(getApplicationContext(),
                                    " ERRO DE ALTERAÇÃO", Toast.LENGTH_LONG).show();

                        }

                    }
                });

    }
}