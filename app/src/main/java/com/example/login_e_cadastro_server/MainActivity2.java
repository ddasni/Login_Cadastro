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

    private TextView lbllogin,lblsenha;
    private EditText txtNovaSenha, txtnomefoto;
    private ImageView fotop;
    private Button btnListar;
    private String Host = "https://rosamititko.serv00.net/projeto/";
    private String url, ret;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        lbllogin=(TextView) findViewById(R.id.lbllogin);
        lblsenha=(TextView)findViewById(R.id.lblsenha);
        txtNovaSenha=(EditText) findViewById(R.id.txtNovaSenha);
        txtnomefoto=(EditText) findViewById(R.id.txtnomefoto);
        fotop=(ImageView) findViewById(R.id.fotop);
        lbllogin.setText(MainActivity.loginx);
        lblsenha.setText(MainActivity.senhax);

        Ion.with(fotop).placeholder ( R.drawable.vazio ).
                error ( R.drawable.vazio ).
                load("https://rosamititko.serv00.net/projeto/imagem/"+ MainActivity.fotox+"");


        findViewById(R.id.btnListar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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