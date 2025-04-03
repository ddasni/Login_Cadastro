package com.example.login_e_cadastro_server;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class MainActivity extends AppCompatActivity {

    // 1) Criando Atributos
    private String Host="https://rosamititko.serv00.net/projeto/"; // Acessando o banco de dados
    private String url,ret;
    // url = local/arquivo que quer acessar do banco de dados / ret = retorna o status, se foi um sucesso ou não
    public static String fotox,loginx,senhax;
    private EditText txtlogin,txtsenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 2) Linkando os elementos da tela aos atributos
        txtlogin=(EditText) findViewById(R.id.txtlogin);
        txtsenha=(EditText) findViewById(R.id.txtsenha);

        // 3) Executando uma função ao apertar o botão. no caso Inserir e Logar
        findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inserir();
            }
        });

        findViewById(R.id.button6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logar();
            }
        });
    }
    // 4) funções de Logar e Inserir
    // Uma função de Logar, onde ele verifica se o usuario existe ou não no banco de dados
    // Caso ele exista vai enviar um status de "ok", se não vai exibir uma mensagem na tela
    private void logar()
    {
        url=Host+"login.php";
        Ion.with (MainActivity.this)
                .load ( url )
                .setBodyParameter ( "usuario" ,txtlogin.getText ().toString ())
                .setBodyParameter ( "senha",txtsenha.getText () .toString ())
                .asJsonObject ()
                .setCallback ( new FutureCallback<JsonObject> () {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        ret=result.get("status").getAsString ();
                        if(ret.equals ("ok")) {
                            //  Toast.makeText(getApplicationContext(),
                            //  " login e senha ok",   Toast.LENGTH_LONG).show();
                            fotox=result.get("foto").getAsString ().toString();

                            loginx=txtlogin.getText().toString();
                            senhax=txtsenha.getText().toString();
                            Intent trocar=new Intent(MainActivity.this, MainActivity2.class);
                            MainActivity.this.startActivity(trocar);
                        }
                        else {
                            Toast.makeText(getApplicationContext(),
                                    " não existe login ou senha ",   Toast.LENGTH_LONG).show();
                        }
                    }
                } );
    }

    // Função que insere o dados (nome e senha) do usuario no banco de dados
    // se tudo der certo ele envia um status de "ok", se não vai exibir uma mensagem na tela
    private void inserir()
    {
        url=Host+"inserirt.php";
        Ion.with (MainActivity.this)
                .load ( url )
                .setBodyParameter ( "usuario" ,txtlogin.getText ().toString ())
                .setBodyParameter ( "senha",txtsenha.getText () .toString ())
                .asJsonObject ()
                .setCallback ( new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        ret=result.get("status").getAsString ();
                        if(ret.equals ( "ok" )) {
                            Toast.makeText(getApplicationContext(),
                                    " incluido com sucesso", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(),  " erro",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                } );
    }
}