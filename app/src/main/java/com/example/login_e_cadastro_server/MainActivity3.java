package com.example.login_e_cadastro_server;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

public class MainActivity3 extends AppCompatActivity {

    // 1) Criando Atributos
    private String Host="https://rosamititko.serv00.net/projeto/"; // Acessando o banco de dados
    private String url,ret; // url = local/arquivo que quer acessar do banco de dados
    private ListView Lista;
    public List<String> listinha;
    public ArrayAdapter<String> adap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        // 2) Criando uma lista que vai exibir os dados cadastrados do usuario

        Lista=(ListView) findViewById(R.id.Lista);
        listinha=new ArrayList<String>();
        url = Host + "listar.php";

        Ion.with(MainActivity3.this)
                .load( url )
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        listinha.clear();
                        for (int i = 0; i < result.size(); i++) {
                            JsonObject ret = result.get(i).getAsJsonObject();
                            listinha.add(ret.get("cod").getAsString().toString()
                                    + "  " + ret.get("login").getAsString().toString()
                                    + "  " + ret.get("senha").getAsString().toString());
                        }
                        adap = new ArrayAdapter<String>(MainActivity3.this,
                                android.R.layout.simple_list_item_1,listinha);

                        adap.setDropDownViewResource(android.R.layout.simple_list_item_checked);

                        Lista.setAdapter(adap);
                    }
                });
    }
}